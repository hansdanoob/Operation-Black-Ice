
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

public class HallwaySprite implements DisplayableSprite {
	
	private Image image;	
	private UUID imageUUID;
	private double centerX = 0;
	private double centerY = 0;
	private double width = 400;
	private double height = 400;
	private boolean dispose = false;
	private Direction directionOfDoor;
	private int BARRIER_OFFSET_WIDTH = 90;
	private int BARRIER_OFFSET_LENGTH = 65;
	private int BARRIER_THICKNESS = 40;
	private int BARRIER_BOTTOM_OFFSET = 15;


	
	public HallwaySprite(double centerX, double centerY, Direction directionOfDoor) {

		this.centerX = centerX;
		this.centerY = centerY;
		this.directionOfDoor = directionOfDoor;
		
		if (image == null) {
			try {
                if (directionOfDoor == Direction.NORTH) {
				    image = ImageIO.read(new File("AnimationShell-master/res/hallway/basic-up.png"));
                } else if (directionOfDoor == Direction.SOUTH) {
				    image = ImageIO.read(new File("AnimationShell-master/res/hallway/basic-down.png"));
                } else if (directionOfDoor == Direction.EAST) {
				    image = ImageIO.read(new File("AnimationShell-master/res/hallway/basic-right.png"));
                } else {
                    image = ImageIO.read(new File("AnimationShell-master/res/hallway/basic-left.png"));
                }
			}
			catch (IOException e) {
				System.out.println(e.toString());
			}		
		}	
		
		this.generateBarrierSprites();
	}

	
	public Image getImage() {
		return image;
	}

	public void generateBarrierSprites() {

		if (this.directionOfDoor == Direction.NORTH) {
			new BarrierSprite(BARRIER_THICKNESS, this.height / 2, this.centerX - BARRIER_OFFSET_WIDTH, centerY - BARRIER_OFFSET_LENGTH, false);
			new BarrierSprite(BARRIER_THICKNESS, this.height / 2, this.centerX + BARRIER_OFFSET_WIDTH, centerY - BARRIER_OFFSET_LENGTH, false);
		} else if (this.directionOfDoor == Direction.SOUTH) {
			new BarrierSprite(BARRIER_THICKNESS, this.height / 2, this.centerX - BARRIER_OFFSET_WIDTH, centerY + BARRIER_OFFSET_LENGTH, false);
			new BarrierSprite(BARRIER_THICKNESS, this.height / 2, this.centerX + BARRIER_OFFSET_WIDTH, centerY + BARRIER_OFFSET_LENGTH, false);
		} else if (this.directionOfDoor == Direction.EAST) {
			new BarrierSprite(this.width / 2, BARRIER_THICKNESS, this.centerX + (BARRIER_OFFSET_LENGTH + 30), centerY - BARRIER_OFFSET_WIDTH, false); // "Magic number" 30 is due to x axis differing from y axis, not sure why
			new BarrierSprite(this.width / 2, BARRIER_THICKNESS, this.centerX + (BARRIER_OFFSET_LENGTH + 30), centerY + BARRIER_OFFSET_WIDTH - BARRIER_BOTTOM_OFFSET, false);

		} else { // WEST
			new BarrierSprite(this.width / 2, BARRIER_THICKNESS, this.centerX - (BARRIER_OFFSET_LENGTH + 30), centerY - BARRIER_OFFSET_WIDTH, false);
			new BarrierSprite(this.width / 2, BARRIER_THICKNESS, this.centerX - (BARRIER_OFFSET_LENGTH + 30), centerY + BARRIER_OFFSET_WIDTH - BARRIER_BOTTOM_OFFSET, false);

		}
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

}

