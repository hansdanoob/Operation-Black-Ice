
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class RoomInstance {

    static final Direction[] DIRECTIONS = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

    static ArrayList<ArrayList<RoomInstance>> roomArray = new ArrayList<ArrayList<RoomInstance>>();

    ArrayList<Direction> doors;
    Random random = new Random();


    public RoomInstance() {
        doors = new ArrayList<>();
        
        ArrayList<RoomInstance> neighbours = this.getNeighbours();  // FINISH THIS NEXT----------------------------------------------

        for (int i = 0; i < 4; i++) {
            /*
            if (something[i]) {
                doors.add(DIRECTIONS[i]);
            } 
            */
            break;
        }

        // random for new doors
        for (int i = 0; i < 4; i++) {
            if (!doors.contains(DIRECTIONS[i])) {
                double rand = random.nextDouble();
                if (rand >= 60) {
                    doors.add(DIRECTIONS[i]);
                }
            }
        }
    }



    public ArrayList<RoomInstance> getNeighbours() {
        
        int yIndex = this.getY();
        int xIndex = this.getX();
        ArrayList<RoomInstance> neighbours = new ArrayList<>();


        // get neighbours

        try {
            neighbours.add(roomArray.get(xIndex).get(yIndex+1));
        } catch(Exception e) {}

        try {
            neighbours.add(roomArray.get(xIndex).get(yIndex-1));
        } catch(Exception e) {}

        try {
            neighbours.add(roomArray.get(xIndex+1).get(yIndex));
        } catch(Exception e) {}

        try {
            neighbours.add(roomArray.get(xIndex-1).get(yIndex));
        } catch(Exception e) {}
        
        return neighbours;
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
}
