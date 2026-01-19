
public class Node 
{
	private Node north;
	private Node south;
	private Node east;
	private Node west;
	private RoomInstance room;

	private static long roomCount = 0;
	
	public Node(RoomInstance room, Node north, Node south, Node east, Node west)												// comment needed to ensure that i properly fixed a git mistake
	{
		this.room = room;
		roomCount++;

		this.north = north;
		this.south = south;
		this.east = east;
		this.west = west;
	}
	
	public RoomInstance getRoom() {
		return this.room;
	}

	public Node getNorth() {
		return this.north;
	}

	public Node getSouth() {
		return this.south;
	}
	
	public Node getEast() {
		return this.east;
	}

	public Node getWest() {
		return this.west;
	}

	public void setRoom(RoomInstance newRoom) {
		this.room = newRoom;
	}
	
	public void setNorth(Node north) {
		this.north = north;
	}
	
	public void setSouth(Node south) {
		this.south = south;
	}

	public void setEast(Node east) {
		this.east = east;
	}
	
	public void setWest(Node west) {
		this.west = west;
	}
	
	public Node copyNode() {
		Node copyNode = new Node(this.room, this.north, this.south, this.east, this.west);
		return copyNode;
	}
	
	protected static void reset() {

		roomCount = 0;
	}
	

	public static long getRoomCount() {
		return roomCount;
	}
	
    
    public String toString() {

    	RoomInstance north = (this.north != null ? this.north.room : null);
    	RoomInstance south = (this.south != null ? this.south.room : null);
		RoomInstance east = (this.east != null ? this.east.room : null);
    	RoomInstance west = (this.west != null ? this.west.room : null);
    	return String.format("room: %-10s; north: %-10s; south: %-10s; east: %-10s; west: %-10s", this.room, north, south, east, west);    	
    	
    }
	
}
