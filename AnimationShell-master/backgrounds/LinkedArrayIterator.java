// import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedArrayIterator {
    
    Node startingNode;

    Node northNode;
    Node southNode;
    Node eastNode;
    Node westNode;
    Node currentNode;

    int xIndex;
    int yIndex;



    public LinkedArrayIterator() {
        currentNode = startingNode;
        northNode = currentNode.getNorth();
        southNode = currentNode.getSouth();
        eastNode = currentNode.getEast();
        westNode = currentNode.getWest();
        xIndex = 0;
        yIndex = 0;
    }

    public LinkedArrayIterator(Node node) {

        currentNode = node;
        northNode = currentNode.getNorth();
        southNode = currentNode.getSouth();
        eastNode = currentNode.getEast();
        westNode = currentNode.getWest();
        xIndex = 0;
        yIndex = 0;
    }


    public String toString() {
        return "";
    }

    public long size() {
        return Node.getRoomCount();
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

    public void moveNorth() {

        if (northNode == null) {
            throw new NoSuchElementException();
        }

        Node nextNode = northNode;

        // new references
        currentNode = nextNode;
        northNode = nextNode.getNorth();
        southNode = nextNode.getSouth();
        eastNode = nextNode.getEast();
        westNode = nextNode.getWest();

        yIndex++;
    }

    public void moveSouth() {

        if (southNode == null) {
            throw new NoSuchElementException();
        }

        Node nextNode = southNode;

        // new references
        currentNode = nextNode;
        northNode = nextNode.getNorth();
        southNode = nextNode.getSouth();
        eastNode = nextNode.getEast();
        westNode = nextNode.getWest();

        yIndex--;
    }

    public void moveEast() {

        if (eastNode == null) {
            throw new NoSuchElementException();
        }

        Node nextNode = eastNode;

        // new references
        currentNode = nextNode;
        northNode = nextNode.getNorth();
        southNode = nextNode.getSouth();
        eastNode = nextNode.getEast();
        westNode = nextNode.getWest();

        xIndex++;
    }

    public void moveWest() {

        if (westNode == null) {
            throw new NoSuchElementException();
        }

        Node nextNode = westNode;

        // new references
        currentNode = nextNode;
        northNode = nextNode.getNorth();
        southNode = nextNode.getSouth();
        eastNode = nextNode.getEast();
        westNode = nextNode.getWest();

        xIndex--;
    }
   
    public int xIndex() {
        return xIndex;
    }

    public int yIndex() {
        return yIndex;
    }

    public void set(RoomInstance room) {
        if (currentNode == null) {
            throw new IllegalStateException();
        }
        currentNode.setRoom(room);
        
    }

    public void add(RoomInstance room) {
        if (currentNode == null) {
            Node newNode = new Node(room, northNode, southNode, eastNode, westNode);
            southNode.setNorth(newNode);
            northNode.setSouth(newNode);
            eastNode.setWest(newNode);
            westNode.setEast(newNode);

            currentNode = newNode;
        }
    }
}
