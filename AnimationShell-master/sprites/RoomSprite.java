import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class RoomSprite implements DisplayableSprite {
	
	//a sprite that can be displayed and moves based on its own polling of the keyboard object
	private Image image;	
	private double centerX = 0;
	private double centerY = 0;
	private double width = 50;
	private double height = 50;
	private boolean dispose = false;	

    private RoomInstance roomInstance;

	
	public RoomSprite(double centerX, double centerY, RoomInstance room, boolean isHallway) {

		this.centerX = centerX;
		this.centerY = centerY;
        this.roomInstance = room;
		
		if (image == null) {
			try {
				if (!isHallway) {
					image = ImageIO.read(new File("AnimationShell-master/res/ROOM.png"));
					width = 100;
					height = 100;
				} else {
					image = ImageIO.read(new File("AnimationShell-master/res/HALLWAY.png"));
				}
			}
			catch (IOException e) {
				System.out.println(e.toString());
			}		
		}		
	}

	public Image getImage() {
		return image;
	}
	
	//DISPLAYABLE
	
	public boolean getVisible() {
		return true;
	}
	
	public double getMinX() {
		return centerX - (width / 2);
	}

	public double getMaxX() {
		return centerX + (width / 2);
	}

	public double getMinY() {
		return centerY - (height / 2);
	}

	public double getMaxY() {
		return centerY + (height / 2);
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public double getCenterX() {
		return centerX;
	};

	public double getCenterY() {
		return centerY;
	};
	
	
	public boolean getDispose() {
		return dispose;
	}

	public void setDispose(boolean dispose) {
		this.dispose = dispose;
	}


	public void update(Universe universe, long actual_delta_time) {
				
	}
	
	public void addHallways() {
		ArrayList<Direction> doors = this.roomInstance.getDoors();

		if (doors.contains(Direction.NORTH)) {
			RoomSprite hallwayNorth = new RoomSprite(this.centerX, this.centerY - (ShellUniverse.ROOM_DISTANCE / 3), null, true);
			ShellUniverse.roomsToAdd.add(hallwayNorth);
		}
		if (doors.contains(Direction.SOUTH)) {
			RoomSprite hallwaySouth = new RoomSprite(this.centerX, this.centerY + (ShellUniverse.ROOM_DISTANCE / 3), null, true);
			ShellUniverse.roomsToAdd.add(hallwaySouth);
		}
		if (doors.contains(Direction.EAST)) {
			RoomSprite hallwayEast = new RoomSprite(this.centerX + (ShellUniverse.ROOM_DISTANCE / 3), this.centerY, null, true);
			ShellUniverse.roomsToAdd.add(hallwayEast);
		}
		if (doors.contains(Direction.WEST)) {
			RoomSprite hallwayWest = new RoomSprite(this.centerX - (ShellUniverse.ROOM_DISTANCE / 3), this.centerY, null, true);
			ShellUniverse.roomsToAdd.add(hallwayWest);
		}
	}


}
