import java.util.ArrayList;
import java.util.Arrays;

public class ShellUniverse implements Universe {

	private boolean complete = false;
	private DisplayableSprite penguin = null;
	private ArrayList<DisplayableSprite> sprites = new ArrayList<DisplayableSprite>();
	private ArrayList<Background> backgrounds = new ArrayList<Background>();
	private ArrayList<DisplayableSprite> disposalList = new ArrayList<DisplayableSprite>();

	public static final ArrayList<Direction> START_ROOM_DOORS = new ArrayList<>(Arrays.asList(Direction.NORTH));
	public static Node startingNode;
	public static RoomSprite startingRoom;
	public static LinkedArrayIterator roomArrayIterator;

	private double centerX;
	private double centerY;
	private static final double SMOOTHING_FACTOR = 0.03;

	public static final int ROOM_DISTANCE = 100;

	public static ArrayList<DisplayableSprite> roomsToAdd = new ArrayList<DisplayableSprite>();



	public ShellUniverse () {

		this.setXCenter(0);
		this.setYCenter(0);

		startingNode = new Node(new RoomInstance(startingNode, START_ROOM_DOORS), null, null, null, null);
		roomArrayIterator = new LinkedArrayIterator(startingNode);

		startingRoom = new RoomSprite(0, 0, startingNode.getRoom());
		sprites.add(startingRoom);

		penguin = new PenguinSprite(0,0);
		sprites.add(penguin);


		// TEMP -----------------------------------------------------
		roomArrayIterator.moveNorth();
		roomArrayIterator.addRoom();

		roomArrayIterator.moveEast();
		roomArrayIterator.addRoom();

		roomArrayIterator.moveEast();
		roomArrayIterator.addRoom();

		roomArrayIterator.moveSouth();
		roomArrayIterator.addRoom();

	}

	public double getScale() {
		return 1;
	}

	public double getXCenter() {
		return this.centerX;
	}

	public double getYCenter() {
		return this.centerY;
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

	public void update(Animation animation, long actual_delta_time) {

		for (int i = 0; i < sprites.size(); i++) {
			DisplayableSprite sprite = sprites.get(i);
			sprite.update(this, actual_delta_time);
    	} 
		
		disposeSprites();

		// add all newly generated rooms
		this.sprites.remove(penguin);
		for (int i = 0; i < roomsToAdd.size(); i++) {
			this.sprites.add(roomsToAdd.get(i));
		}
		roomsToAdd.clear();
		this.sprites.add(penguin);

		double playerX = penguin.getCenterX();
        double playerY = penguin.getCenterY();
        
        this.centerX += (playerX - this.centerX) * SMOOTHING_FACTOR; //implemented linear-interpolation smoothing based on online formulas and ChatGPT
        this.centerY += (playerY - this.centerY) * SMOOTHING_FACTOR; //:D
		
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
