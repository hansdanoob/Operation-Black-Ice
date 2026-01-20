
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.imageio.ImageIO;

public class HallwaySprite implements DisplayableSprite {
	
	//a sprite that can be displayed and moves based on its own polling of the keyboard object
	private Image image;	
	private UUID imageUUID;
	private double centerX = 0;
	private double centerY = 0;
	private double width = 400;
	private double height = 400;
	private boolean dispose = false;	


	
	public HallwaySprite(double centerX, double centerY, Direction directionOfDoor) {

		this.centerX = centerX;
		this.centerY = centerY;
		
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

}

