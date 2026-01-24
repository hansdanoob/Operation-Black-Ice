// import java.util.ListIterator;
import java.util.Random;

public class LinkedArrayIterator {
    

    Node northNode;
    Node southNode;
    Node eastNode;
    Node westNode;
    Node currentNode;

    int xPosition;
    int yPosition;

    int deltaFromPenguinX = 0;
    int deltaFromPenguinY = 0;

    private Random random = new Random();
    private static final double SEAL_CHANCE = 0.1;
    private static final double FISH_CHANCE = 0.4;
    private static final double ROOM_TO_LOOK_LIKE_HALLWAY_CHANCE = 0.1;



    public LinkedArrayIterator(Node node) {

        currentNode = node;
        northNode = currentNode.getNorth();
        southNode = currentNode.getSouth();
        eastNode = currentNode.getEast();
        westNode = currentNode.getWest();
        this.xPosition = 0;
        this.yPosition = 0;
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

            northNode = null; // not possible to determine northNode
            southNode = currentNode;
            try {
                eastNode = eastNode.getNorth();
            } catch (Exception e) {
                eastNode = null;
            }
            try {
                westNode = westNode.getNorth();
            } catch (Exception e) {
                westNode = null;
            }
        }
        currentNode = nextNode;

        yPosition -= ShellUniverse.ROOM_DISTANCE;

        attemptSealGeneration();
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

            southNode = null; // not possible to determine northNode
            northNode = currentNode;
            try {
                eastNode = eastNode.getSouth();
            } catch (Exception e) {
                eastNode = null;
            }
            try {
                westNode = westNode.getSouth();
            } catch (Exception e) {
                westNode = null;
            }
        }
        currentNode = nextNode;

        yPosition += ShellUniverse.ROOM_DISTANCE;

        attemptSealGeneration();
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

            eastNode = null; // not possible to determine northNode
            westNode = currentNode;
            try {
                northNode = northNode.getEast();
            } catch (Exception e) {
                northNode = null;
            }
            try {
                southNode = southNode.getEast();
            } catch (Exception e) {
                southNode = null;
            }
        }
        currentNode = nextNode;

        xPosition += ShellUniverse.ROOM_DISTANCE;

        attemptSealGeneration();
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

            westNode = null; // not possible to determine northNode
            eastNode = currentNode;
            try {
                northNode = northNode.getWest();
            } catch (Exception e) {
                northNode = null;
            }
            try {
                southNode = southNode.getWest();
            } catch (Exception e) {
                southNode = null;
            }
        }
        currentNode = nextNode;

        xPosition -= ShellUniverse.ROOM_DISTANCE;

        attemptSealGeneration();
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

    public void attemptAddRoom() {
        if (currentNode == null) {
            RoomInstance room  = null;

            Node newNode = new Node(room, northNode, southNode, eastNode, westNode);

            newNode.setRoom(new RoomInstance(newNode));

            try {
                southNode.setNorth(newNode);
            } catch (Exception e) {}

            try {
                northNode.setSouth(newNode);
            } catch (Exception e) {}

            try {
                westNode.setEast(newNode);
            } catch (Exception e) {}

            try {
                eastNode.setWest(newNode);
            } catch (Exception e) {}


            currentNode = newNode;

            boolean looksLikeHallway = false;
            if (random.nextDouble() < ROOM_TO_LOOK_LIKE_HALLWAY_CHANCE) {
                looksLikeHallway = true;
            }

            RoomSprite roomSprite = new RoomSprite(this.xPosition, this.yPosition, newNode.getRoom(), looksLikeHallway);
            ShellUniverse.spritesToAdd.add(roomSprite);
            roomSprite.addHallways();
            
            if (!looksLikeHallway) {
                attemptFishGeneration();
            }
        }
    }

    public void attemptSealGeneration() {
        if (random.nextDouble() < SEAL_CHANCE) {
                SealSprite sealSprite = new SealSprite(this.xPosition, this.yPosition);
                ShellUniverse.spritesToAdd.add(sealSprite);
            }
    }

    public void attemptFishGeneration() {
        if (random.nextDouble() < FISH_CHANCE) {
                FishSprite fishSprite = new FishSprite(this.xPosition, this.yPosition);
                ShellUniverse.spritesToAdd.add(fishSprite);
            }
    }

    public void refreshPenguinTracking() {
        int[] target = PenguinSprite.getNearestGridPoint();
        int targetX = target[0] + this.deltaFromPenguinX;
        int targetY = target[1] + this.deltaFromPenguinY;


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

    public void setDeltaFromPenguinX(int delta) {
        this.deltaFromPenguinX = delta;
    }

    public void setDeltaFromPenguinY(int delta) {
        this.deltaFromPenguinY = delta;
    }

    public Node getCurrentNode() {
        return this.currentNode;
    }

    public void updateReferences() {
        if (this.northNode == null) {
            try {
                this.northNode = this.currentNode.getNorth();
            } catch (Exception e) {}
        }
        if (this.southNode == null) {
            try {
                this.southNode = this.currentNode.getSouth();
            } catch (Exception e) {}
        }
        if (this.eastNode == null) {
            try {
                this.eastNode = this.currentNode.getEast();
            } catch (Exception e) {}
        }
        if (this.westNode == null) {
            try {
                this.westNode = this.currentNode.getWest();
            } catch (Exception e) {}
        }
    }
}
