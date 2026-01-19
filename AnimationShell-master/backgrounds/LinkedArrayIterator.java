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

    // int xIndex;
    // int yIndex;



    public LinkedArrayIterator(int xPosition, int yPosition) {												// comment needed to ensure that i properly fixed a git mistake
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

            DisplayableSprite roomSprite = new RoomSprite(this.xPosition, this.yPosition, room);
            ShellUniverse.roomsToAdd.add(roomSprite);
        }
    }

    public void refreshPenguinTracking() {
        int[] target = PenguinSprite.getNearestGridPoint();
        int targetX = target[0];
        int targetY = target[1];


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
}
