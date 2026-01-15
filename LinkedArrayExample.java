import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedArrayExample {
    
    Node southEastCorner = null; // useless?
    Node southWestCorner = null;
    Node northEastCorner = null;
    Node northWestCorner = null;

    int xSize = 0;
    int ySize = 0;

    Node northNode;
    Node southNode;
    Node eastNode;
    Node westNode;
    Node currentNode;

    int xIndex;
    int yIndex;
    Node lastCall;


    // current toString() won't work due to xSize and ySize not being prperly updateable

    public String toString() { //Return a text representation of the linked list in the form [element1, element2, element2]
        if (xSize <= 0 || ySize <= 0) {
            return "[]";
        } else {
            String string = "[";
            Node current = northWestCorner;

            for (int j = 0; j < ySize; j++) {
                for (int i = 0; i < xSize; i++) {
                    string += current.getRoom();
                    current = current.getEast();
                    if (i+1 < xSize) { // if not last element in row
                        string += ", ";
                    }
                }
                string += "]";
                if (j+1 < ySize) { // if not last row
                    string += "\n";
                }
            }

            string += "]";
            return string;
        }
    }


    public long size() { //Returns the number of elements in this list
        return Node.getRoomCount();
    }


    // wont work due to dimension issues

    public void clear() { //Removes all of the elements from the list
        int originalxSize = xSize;
        int originalySize = ySize;
        for (int i = 0; i < originalySize; i++) {
            for (int j = 0; j < originalxSize; j++) {
                this.set(i, j, null);
            }
        }
    }


    // won't work due to southWestCorner causing problems

    public RoomInstance get(int xIndex, int yIndex) { //Returns the element at the specified position in the list. Return null if the index is "out of bounds".
        if (xIndex < 0 || yIndex < 0) {
            return null;
        } else {
            Node current = southWestCorner;
            for (int i = 0; i < xIndex; i++) {
                current = current.getEast();
            }
            for (int i = 0; i < yIndex; i++) {
                current = current.getNorth();
            }
            return current.getRoom();
        }
    }


    public void set(int xIndex, int yIndex, RoomInstance room) { //Sets the element at the specified position in the list to the given value.
        if (xIndex >= 0) {
            Node current = southWestCorner;
            for (int i = 0; i < xIndex; i++) {
                current = current.getEast();
            }
            for (int i = 0; i < yIndex; i++) {
                current = current.getNorth();
            }
            current.setRoom(room);
        }
    }

// ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public LinkedArrayExample listIterator() {
        currentNode = southWestCorner;
        northNode = currentNode.getNorth();
        southNode = currentNode.getSouth();
        eastNode = currentNode.getEast();
        westNode = currentNode.getWest();
        xIndex = 0;
        yIndex = 0;
        return this;
    }

    public boolean hasCurrent() {
        if (currentNode != null) {return true;} else {return false;}
    }

    public boolean hasNorth() {
        if (northNode != null) {return true;} else {return false;}
    }

    public boolean hasSouth() {
        if (southNode != null) {return true;} else {return false;}
    }

    public boolean hasEast() {
        if (eastNode != null) {return true;} else {return false;}
    }

    public boolean hasWest() {
        if (westNode != null) {return true;} else {return false;}
    }

    public Object north() {

        if (northNode == null) {
            throw new NoSuchElementException();
        }

        lastCall = northNode;

        Node nextNode = northNode;

        RoomInstance returnValue = nextNode.getRoom();

        // new references
        currentNode = nextNode;
        northNode = nextNode.getNorth();
        southNode = nextNode.getSouth();
        eastNode = nextNode.getEast();
        westNode = nextNode.getWest();

        xIndex++;

        return returnValue;
    }

    public Object south() {

        if (southNode == null) {
            throw new NoSuchElementException();
        }

        lastCall = southNode;

        Node nextNode = southNode;

        RoomInstance returnValue = nextNode.getRoom();

        // new references
        currentNode = nextNode;
        northNode = nextNode.getNorth();
        southNode = nextNode.getSouth();
        eastNode = nextNode.getEast();
        westNode = nextNode.getWest();

        xIndex++;

        return returnValue;
    }

    public Object east() {

        if (eastNode == null) {
            throw new NoSuchElementException();
        }

        lastCall = eastNode;

        Node nextNode = eastNode;

        RoomInstance returnValue = nextNode.getRoom();

        // new references
        currentNode = nextNode;
        northNode = nextNode.getNorth();
        southNode = nextNode.getSouth();
        eastNode = nextNode.getEast();
        westNode = nextNode.getWest();

        xIndex++;

        return returnValue;
    }

    public Object west() {

        if (westNode == null) {
            throw new NoSuchElementException();
        }

        lastCall = westNode;

        Node nextNode = westNode;

        RoomInstance returnValue = nextNode.getRoom();

        // new references
        currentNode = nextNode;
        northNode = nextNode.getNorth();
        southNode = nextNode.getSouth();
        eastNode = nextNode.getEast();
        westNode = nextNode.getWest();

        xIndex++;

        return returnValue;
    }

   
    public int nextXIndex() {
        return xIndex;
    }

    public int nextYIndex() {
        return yIndex;
    }

    public int previousXIndex() {
        return xIndex-1;
    }

    public int previousYIndex() {
        return yIndex-1;
    }


    public void set(RoomInstance room) {
        if (lastCall == null) {
            throw new IllegalStateException();
        }
        lastCall.setRoom(room);
        
    }

    public void add(RoomInstance room) {

        Node newNode = new Node(room, northNode, southNode, eastNode, westNode);
        southNode.setNorth(newNode);
        northNode.setSouth(newNode);
        eastNode.setWest(newNode);
        westNode.setEast(newNode);

        currentNode = newNode;

        lastCall = null;
    }
}
