import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ShellUniverse implements Universe {

	private boolean complete = false;
	private DisplayableSprite penguin = null;
	private DisplayableSprite vignette = null;
	private DisplayableSprite redTint = null;
	private ArrayList<DisplayableSprite> sprites = new ArrayList<DisplayableSprite>();
	private ArrayList<Background> backgrounds = new ArrayList<Background>();
	private ArrayList<DisplayableSprite> disposalList = new ArrayList<DisplayableSprite>();
	private ArrayList<DisplayableSprite> seals = new ArrayList<DisplayableSprite>();

	public static final ArrayList<Direction> START_ROOM_DOORS = new ArrayList<>(Arrays.asList(Direction.EAST, Direction.WEST));
	public static Node startingNode;
	public static RoomSprite startingRoom;
	public static LinkedArrayIterator roomArrayIterator;

	public static LinkedArrayIterator northIterator;
	public static LinkedArrayIterator southIterator;
	public static LinkedArrayIterator eastIterator;
	public static LinkedArrayIterator westIterator;

	public static double centerX;
	public static double centerY;
	public static double smoothingFactor = 0.03;

	public static final int ROOM_DISTANCE = 750;

	public static ArrayList<DisplayableSprite> spritesToAdd = new ArrayList<DisplayableSprite>();

	public static ArrayList<File> filesToDelete = new ArrayList<File>();

	//public static ArrayList<int[]> allRoomCoords = new ArrayList<int[]>(); // not working??? Not necessary??



	public ShellUniverse () {

		this.setXCenter(0);
		this.setYCenter(0);

		startingNode = new Node(new RoomInstance(startingNode, START_ROOM_DOORS), null, null, null, null);
		roomArrayIterator = new LinkedArrayIterator(startingNode);

		// initialize four surranunding iterators at center, then move them
		northIterator = new LinkedArrayIterator(startingNode);
		southIterator = new LinkedArrayIterator(startingNode);
		eastIterator = new LinkedArrayIterator(startingNode);
		westIterator = new LinkedArrayIterator(startingNode);
		northIterator.moveNorth();
		southIterator.moveSouth();
		eastIterator.moveEast();
		westIterator.moveWest();
		northIterator.setDeltaFromPenguinY(-ROOM_DISTANCE);
		southIterator.setDeltaFromPenguinY(ROOM_DISTANCE);
		eastIterator.setDeltaFromPenguinX(ROOM_DISTANCE);
		westIterator.setDeltaFromPenguinX(-ROOM_DISTANCE);

		startingRoom = new RoomSprite(0, 0, startingNode.getRoom());
		startingRoom.addHallways();
		sprites.add(startingRoom);

		vignette = new VignetteSprite(centerX, centerY);
		sprites.add(vignette);

		redTint = new RedTintSprite(centerX, centerY);
		sprites.add(redTint);

		penguin = new PenguinSprite(0,0);
		sprites.add(penguin);
	}

	public double getScale() {
		return 1;
	}

	public double getXCenter() {
		return centerX;
	}

	public double getYCenter() {
		return centerY;
	}

	public void setXCenter(double xCenter) {
	}

	public void setYCenter(double yCenter) {
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		complete = true;
	}

	public ArrayList<Background> getBackgrounds() {
		return backgrounds;
	}	

	public ArrayList<DisplayableSprite> getSprites() {
		return sprites;
	}

	public boolean centerOnPlayer() {
		return false;
	}		

	public static double getDistance(double x1, double y1, double x2, double y2) {
		double distance = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
		return distance;
	}

	public void update(Animation animation, long actual_delta_time) {

		for (int i = 0; i < sprites.size(); i++) {
			DisplayableSprite sprite = sprites.get(i);
			sprite.update(this, actual_delta_time);
    	} 
		
		disposeSprites();

		addSprites();
		pushPrioritySpritesToFront();

		double playerX = penguin.getCenterX();
        double playerY = penguin.getCenterY();
        
        centerX += (playerX - centerX) * smoothingFactor;
        centerY += (playerY - centerY) * smoothingFactor;

		roomArrayIterator.refreshPenguinTracking();
		northIterator.refreshPenguinTracking();
		southIterator.refreshPenguinTracking();
		eastIterator.refreshPenguinTracking();
		westIterator.refreshPenguinTracking();

		roomArrayIterator.updateReferences();
		northIterator.updateReferences();
		southIterator.updateReferences();
		eastIterator.updateReferences();
		westIterator.updateReferences();

		northIterator.attemptAddRoom();
		southIterator.attemptAddRoom();
		eastIterator.attemptAddRoom();
		westIterator.attemptAddRoom();


		double closestSealDistance = this.findDistanceClosestSealToPlayer();
		RedTintSprite.closestSealToPlayerDistance = closestSealDistance;
	}

	private double findDistanceClosestSealToPlayer() {
		double smallestDistance = Double.MAX_VALUE;

		for (int i = 0; i < seals.size(); i++) {
			DisplayableSprite seal = seals.get(i);
			double distance = getDistance(PenguinSprite.centerX, PenguinSprite.centerY, seal.getCenterX(), seal.getCenterY());
			if (distance < smallestDistance) {
				smallestDistance = distance;
			}
		}

		return smallestDistance;
	}

	public void addSprites() {

		for (int i = 0; i < spritesToAdd.size(); i++) {
			this.sprites.add(spritesToAdd.get(i));
		}

		spritesToAdd.clear();

	}

	public void pushPrioritySpritesToFront() {
		
		seals.clear();

		for (int i = 0; i < sprites.size(); i++) {
			DisplayableSprite sprite = sprites.get(i);
			if (sprite instanceof SealSprite) {
				seals.add(sprite);
				sprites.remove(sprite);
				i--;
			} else if (sprite == penguin || sprite == vignette || sprite == redTint) {
				sprites.remove(sprite);
				i--;
			}
		}

		for (int i = 0; i < seals.size(); i++) {
			sprites.add(seals.get(i));
		}

		sprites.add(penguin);
		sprites.add(vignette);
		sprites.add(redTint);
	}

	public static void deleteImageFiles() {
		for (int i = 0; i < filesToDelete.size(); i++) {
			boolean deleted = filesToDelete.get(i).delete();
			System.out.println("File deleted");
			if (!deleted) {
    			System.out.println("Failed to delete file");
			}
		}
		filesToDelete.clear();
	}
	
    protected void disposeSprites() {
        
    	//collect a list of sprites to dispose
    	//this is done in a temporary list to avoid a concurrent modification exception
		for (int i = 0; i < sprites.size(); i++) {
			DisplayableSprite sprite = sprites.get(i);
    		if (sprite.getDispose() == true) {
    			disposalList.add(sprite);
    		}
    	}
		
		//go through the list of sprites to dispose
		//note that the sprites are being removed from the original list
		for (int i = 0; i < disposalList.size(); i++) {
			DisplayableSprite sprite = disposalList.get(i);
			sprites.remove(sprite);
    	}
		
		//clear disposal list if necessary
    	if (disposalList.size() > 0) {
    		disposalList.clear();
    	}
    }


	public String toString() {
		return "ShellUniverse";
	}

}
