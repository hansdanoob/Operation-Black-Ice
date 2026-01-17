
import java.util.Random;
import java.util.ArrayList;

public class RoomInstance {

    static final Direction[] DIRECTIONS = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
    static final double DOOR_CHANCE = 0.6;

    private ArrayList<Direction> doors = new ArrayList<>();
    private Random random = new Random();

    private Node parentNode;


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
}
