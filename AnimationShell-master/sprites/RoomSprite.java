import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.imageio.ImageIO;

public class RoomSprite implements DisplayableSprite {
	
	//a sprite that can be displayed and moves based on its own polling of the keyboard object
	private Image image;	
	private UUID imageUUID;
	private double centerX = 0;
	private double centerY = 0;
	private double width = 400;
	private double height = 400;
	private boolean dispose = false;	

    private RoomInstance roomInstance;

	
	public RoomSprite(double centerX, double centerY, RoomInstance room, boolean isHallway) {

		this.centerX = centerX;
		this.centerY = centerY;
        this.roomInstance = room;
		
		if (image == null) {
			try {
				if (!isHallway) {
					createMergedImage();

					File file = new File("AnimationShell-master/res/room/generated/file" + imageUUID + ".png");
					image = ImageIO.read(file);
					ShellUniverse.filesToDelete.add(file);

				} else {
					image = ImageIO.read(new File("AnimationShell-master/res/HALLWAY.png"));
				}
			}
			catch (IOException e) {
				System.out.println(e.toString());
			}		
		}		
	}

	public void createMergedImage() throws IOException{

		BufferedImage image1;
		BufferedImage image2;
		BufferedImage image3;
		BufferedImage image4;


		if (this.roomInstance.getDoors().contains(Direction.NORTH)) {
			image1 = ImageIO.read(new File("AnimationShell-master/res/room/basic-door-up.png"));
		} else {
			image1 = ImageIO.read(new File("AnimationShell-master/res/room/basic-wall-up.png"));
		}
		if (this.roomInstance.getDoors().contains(Direction.WEST)) {
			image2 = ImageIO.read(new File("AnimationShell-master/res/room/basic-door-left.png"));
		} else {
			image2 = ImageIO.read(new File("AnimationShell-master/res/room/basic-wall-left.png"));
		}
		if (this.roomInstance.getDoors().contains(Direction.EAST)) {
			image3 = ImageIO.read(new File("AnimationShell-master/res/room/basic-door-right.png"));
		} else {
			image3 = ImageIO.read(new File("AnimationShell-master/res/room/basic-wall-right.png"));
		}
		if (this.roomInstance.getDoors().contains(Direction.SOUTH)) {
			image4 = ImageIO.read(new File("AnimationShell-master/res/room/basic-door-down.png"));
		} else {
			image4 = ImageIO.read(new File("AnimationShell-master/res/room/basic-wall-down.png"));
		}

		// Use base image size
		int width = image1.getWidth();
		int height = image1.getHeight();

		// Create output image with transparency support
		BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = combined.createGraphics();

		// Draw base image
		g.drawImage(image1, 0, 0, null);

		// Draw overlay image on top
		g.drawImage(image2, 0, 0, null);
		g.drawImage(image3, 0, 0, null);
		g.drawImage(image4, 0, 0, null);

		g.dispose();

		// Save result
		imageUUID = UUID.randomUUID();
		ImageIO.write(combined, "png", new File("AnimationShell-master/res/room/generated/file" + imageUUID + ".png"));
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

		double hallwayOffest = ShellUniverse.ROOM_DISTANCE / 1.9;

		if (doors.contains(Direction.NORTH)) {
			HallwaySprite hallwayNorth = new HallwaySprite(this.centerX, this.centerY - hallwayOffest, Direction.SOUTH);
			ShellUniverse.roomsToAdd.add(hallwayNorth); 
		}
		if (doors.contains(Direction.SOUTH)) {
			HallwaySprite hallwaySouth = new HallwaySprite(this.centerX, this.centerY + hallwayOffest, Direction.NORTH);
			ShellUniverse.roomsToAdd.add(hallwaySouth);
		}
		if (doors.contains(Direction.EAST)) {
			HallwaySprite hallwayEast = new HallwaySprite(this.centerX + hallwayOffest, this.centerY, Direction.WEST);
			ShellUniverse.roomsToAdd.add(hallwayEast);
		}
		if (doors.contains(Direction.WEST)) {
			HallwaySprite hallwayWest = new HallwaySprite(this.centerX - hallwayOffest, this.centerY, Direction.EAST);
			ShellUniverse.roomsToAdd.add(hallwayWest);
		}
	}


}
