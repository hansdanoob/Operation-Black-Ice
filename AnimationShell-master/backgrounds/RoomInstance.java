
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class RoomInstance {

    static final Direction[] DIRECTIONS = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};
    static final double DOOR_CHANCE = 0.8;

    public static ArrayList<ArrayList<RoomInstance>> roomArray = new ArrayList<ArrayList<RoomInstance>>();

    ArrayList<Direction> doors = new ArrayList<>();
    Random random = new Random();



    public RoomInstance(int xIndex, int yIndex) {

        this.addToArray(xIndex, yIndex); 

        // Add necessary doors to match with neighbour's doors
        RoomInstance[] neighbours = this.getNeighbours(); // return formatted North, South, East, West
        for (int i = 0; i < 4; i++) {
            if (neighbours[i] != null) {
                if (neighbours[i].getDoors().contains(DIRECTIONS[i].opposite())) {
                    this.doors.add(DIRECTIONS[i]);
                }
            }
        }


        // add random new doors
        for (int i = 0; i < 4; i++) {
            if (!doors.contains(DIRECTIONS[i])) {
                double rand = random.nextDouble();
                if (rand <= DOOR_CHANCE) {
                    doors.add(DIRECTIONS[i]);
                }
            }
        }
    }



    public RoomInstance[] getNeighbours() {
        
        RoomInstance[] neighbours = {this.getNorth(), this.getSouth(), this.getEast(), this.getWest()};
        return neighbours;
    }


    public RoomInstance getNorth() {
        
        int yIndex = this.getY();
        int xIndex = this.getX();

        RoomInstance room = null;
        try {
            room = roomArray.get(xIndex).get(yIndex+1);
        } catch(Exception e) {}
        
        return room;
    }


    public RoomInstance getSouth() {
        
        int yIndex = this.getY();
        int xIndex = this.getX();

        RoomInstance room = null;
        try {
            room = roomArray.get(xIndex).get(yIndex-1);
        } catch(Exception e) {}
        
        return room;
    }


    public RoomInstance getEast() {
        
        int yIndex = this.getY();
        int xIndex = this.getX();

        RoomInstance room = null;
        try {
            room = roomArray.get(xIndex+1).get(yIndex);
        } catch(Exception e) {}
        
        return room;
    }


    public RoomInstance getWest() {
        
        int yIndex = this.getY();
        int xIndex = this.getX();

        RoomInstance room = null;
        try {
            room = roomArray.get(xIndex-1).get(yIndex);
        } catch(Exception e) {}
        
        return room;
    }


    public ArrayList<Direction> getDoors() {
        return this.doors;
    }


    public int getX() {
        for (int i = 0; i < roomArray.size(); i++) {
            if (roomArray.get(i).indexOf(this) != -1) {
                return i;
            }
        }
        return -1;
    }


    public int getY() {
        int index;
        for (int i = 0; i < roomArray.size(); i++) {
            index = roomArray.get(i).indexOf(this);
            if (index != -1) {
                return index;
            }
        }
        return -1;
    }


    public void addToArray(int xIndex, int yIndex) {

        while (roomArray.size()-1 < xIndex) {
            roomArray.add(new ArrayList<RoomInstance>());
        }

        while (roomArray.get(xIndex).size() < yIndex) {
            roomArray.get(xIndex).add(null);
        }

        roomArray.get(xIndex).add(this);
    }
}
