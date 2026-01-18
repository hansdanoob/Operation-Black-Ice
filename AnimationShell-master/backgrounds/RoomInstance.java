
import java.util.Random;

import javax.imageio.ImageIO;

import java.awt.Image;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;

public class RoomInstance implements Background{

    static final Direction[] DIRECTIONS = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
    static final double DOOR_CHANCE = 0.6;

    private ArrayList<Direction> doors = new ArrayList<>();
    private Random random = new Random();

    private Node parentNode;

    protected static int TILE_WIDTH = 100;
    protected static int TILE_HEIGHT = 100;

    private Image image;


    public RoomInstance(Node parentNode) {

        this.parentNode = parentNode;

        // Add necessary doors to match with neighbour's doors
        RoomInstance[] neighbours = this.getNeighbours(); // return formatted North, South, East, West
        ArrayList<Direction> neighbouringWalls = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (neighbours[i] != null) {
                if (neighbours[i].getDoors().contains(DIRECTIONS[i].opposite())) {
                    this.doors.add(DIRECTIONS[i]);
                } else {
                    neighbouringWalls.add(DIRECTIONS[i]);
                }
            }
        }


        // add random new doors
        for (int i = 0; i < 4; i++) {
            if ((!doors.contains(DIRECTIONS[i])) && (!neighbouringWalls.contains(DIRECTIONS[i]))) { // if not already added and not into a neighbouring wall
                double rand = random.nextDouble();
                if (rand <= DOOR_CHANCE) {
                    doors.add(DIRECTIONS[i]);
                }
            }
        }

        try {
            this.image = ImageIO.read(new File("AnimationShell-master/res/HALLWAY.png"));
        } catch (Exception e) {

        }
    }

    public RoomInstance(Node parentNode, ArrayList<Direction> doors) {
        this.parentNode = parentNode;
        this.doors = doors;
    }



    public RoomInstance[] getNeighbours() {
        
        RoomInstance[] neighbours = {this.getNorth(), this.getSouth(), this.getEast(), this.getWest()};
        return neighbours;
    }


    public RoomInstance getNorth() {
        
        return this.parentNode.getNorth().getRoom();
    }


    public RoomInstance getSouth() {
        
        return this.parentNode.getSouth().getRoom();
    }


    public RoomInstance getEast() {
        
        return this.parentNode.getEast().getRoom();
    }


    public RoomInstance getWest() {
        
        return this.parentNode.getWest().getRoom();
    }


    public ArrayList<Direction> getDoors() {
        return this.doors;
    }


    // Background functions

    public Tile getTile(int col, int row) {

        int x = (col * TILE_WIDTH);
		int y = (row * TILE_HEIGHT);
		
		Tile newTile = new Tile(this.image, x, y, TILE_WIDTH, TILE_HEIGHT, false);
		
		return newTile;
    }
	
	public int getCol(double x) {

        //which col is x sitting at?
		int col = 0;
		if (TILE_WIDTH != 0) {
			col = (int) (x / TILE_WIDTH);
			if (x < 0) {
				return col - 1;
			}
			else {
				return col;
			}
		}
		else {
			return 0;
		}
    }
	
	public int getRow(double y) {

       //which row is y sitting at?
		int row = 0;
		if (TILE_HEIGHT != 0) {
			row = (int) (y / TILE_HEIGHT);
			if (y < 0) {
				return row - 1;
			}
			else {
				return row;
			}
		}
		else {
			return 0;
		}
    }
	
	public double getShiftX() { // ignore
        return 0;
    }
	
	public double getShiftY() { // ignore
        return 0;
    }
	
	public void setShiftX(double shiftX) { // ignore

    }

	public void setShiftY(double shiftY) { // ignore

    }
	
	public void update(Universe universe, long actual_delta_time) {
        // logic for updating image is necessary
    }

    /*
    // the following method provides a convenient way to add barriers corresponding to certain types of background tiles
	// it means that this data does not need to be duplicated elsewhere, which is best practice
	public ArrayList<DisplayableSprite> getBarriers() {
		ArrayList<DisplayableSprite> barriers = new ArrayList<DisplayableSprite>();
		for (int col = 0; col < map[0].length; col++) {
			for (int row = 0; row < map.length; row++) {
				if (map[row][col] == 1) {
					barriers.add(new BarrierSprite(col * TILE_WIDTH, row * TILE_HEIGHT, (col + 1) * TILE_WIDTH, (row + 1) * TILE_HEIGHT, false));
				}
			}
		}
		return barriers;
	}
    */
}
