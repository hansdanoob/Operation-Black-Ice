// import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedArrayIterator {
    
    Node startingNode;

    Node northNode;
    Node southNode;
    Node eastNode;
    Node westNode;
    Node currentNode;

    int xPosition;
    int yPosition;

    int deltaX = 0;
    int deltaY = 0;



    public LinkedArrayIterator(int xPosition, int yPosition) {
        currentNode = startingNode;
        northNode = currentNode.getNorth();
        southNode = currentNode.getSouth();
        eastNode = currentNode.getEast();
        westNode = currentNode.getWest();
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public LinkedArrayIterator(Node node) { // for starting node

        currentNode = node;
        northNode = currentNode.getNorth();
        southNode = currentNode.getSouth();
        eastNode = currentNode.getEast();
        westNode = currentNode.getWest();
        xPosition = 0;
        yPosition = 0;
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

        Node nextNode = northNode;

        // new references
        if (nextNode != null) {
            northNode = nextNode.getNorth();
            southNode = nextNode.getSouth();
            eastNode = nextNode.getEast();
            westNode = nextNode.getWest();
        } else {
            northNode = null;
            southNode = currentNode;
            eastNode = null;
            westNode = null;
        }
        currentNode = nextNode;

        yPosition -= ShellUniverse.ROOM_DISTANCE;
    }

    public void moveSouth() {

        Node nextNode = southNode;

        // new references
        if (nextNode != null) {
            northNode = nextNode.getNorth();
            southNode = nextNode.getSouth();
            eastNode = nextNode.getEast();
            westNode = nextNode.getWest();
        } else {
            northNode = currentNode;
            southNode = null;
            eastNode = null;
            westNode = null;
        }
        currentNode = nextNode;

        yPosition += ShellUniverse.ROOM_DISTANCE;
    }

    public void moveEast() {

        Node nextNode = eastNode;

        // new references
        if (nextNode != null) {
            northNode = nextNode.getNorth();
            southNode = nextNode.getSouth();
            eastNode = nextNode.getEast();
            westNode = nextNode.getWest();
        } else {
            northNode = null;
            southNode = null;
            eastNode = null;
            westNode = currentNode;
        }
        currentNode = nextNode;

        xPosition += ShellUniverse.ROOM_DISTANCE;
    }

    public void moveWest() {

        Node nextNode = westNode;

        // new references
        if (nextNode != null) {
            northNode = nextNode.getNorth();
            southNode = nextNode.getSouth();
            eastNode = nextNode.getEast();
            westNode = nextNode.getWest();
        } else {
            northNode = null;
            southNode = null;
            eastNode = currentNode;
            westNode = null;
        }
        currentNode = nextNode;

        xPosition -= ShellUniverse.ROOM_DISTANCE;
    }
    
    public int xPosition() {
        return xPosition;
    }

    public int yPosition() {
        return yPosition;
    }
    

    public void set(RoomInstance room) {
        if (currentNode == null) {
            throw new IllegalStateException();
        }
        currentNode.setRoom(room);
    }

    public void addRoom() {
        if (currentNode == null) {
            RoomInstance room  = null;

            Node newNode = new Node(room, northNode, southNode, eastNode, westNode);

            newNode.setRoom(new RoomInstance(newNode));

            try {
                southNode.setNorth(newNode);
            } catch (Exception e) {

            }

            try {
                northNode.setSouth(newNode);
            } catch (Exception e) {
                
            }

            try {
                westNode.setEast(newNode);
            } catch (Exception e) {
                
            }

            try {
                eastNode.setWest(newNode);
            } catch (Exception e) {
                
            }


            currentNode = newNode;

            RoomSprite roomSprite = new RoomSprite(this.xPosition, this.yPosition, newNode.getRoom(), false);
            ShellUniverse.roomsToAdd.add(roomSprite);
            roomSprite.addHallways();
        }
    }

    public void refreshPenguinTracking() {
        int[] target = PenguinSprite.getNearestGridPoint();
        int targetX = target[0] + this.deltaX;
        int targetY = target[1] + this.deltaY;


        if (targetX < this.xPosition) {
            this.moveWest();
        } else if (targetX > this.xPosition) {
            this.moveEast();
        }

        if (targetY < this.yPosition) {
            this.moveNorth();
        } else if (targetY > this.yPosition) {
            this.moveSouth();
        }
    }

    public void setDeltaX(int delta) {
        this.deltaX = delta;
    }

    public void setDeltaY(int delta) {
        this.deltaY = delta;
    }

    public Node getCurrentNode() {
        return this.currentNode;
    }
}
