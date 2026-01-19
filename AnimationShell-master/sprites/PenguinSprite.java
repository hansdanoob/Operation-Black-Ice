import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PenguinSprite implements DisplayableSprite {

	private static final int PERIOD_LENGTH = 200;
	private static final int IMAGES_IN_CYCLE = 2;

	private static Image left0;
	private static Image right0;
	private static Image up0;
	private static Image down0;
	private static Image left1;
	private static Image right1;
	private static Image up1;
	private static Image down1;
	private static Image leftIdle;
	private static Image rightIdle;
	private static Image upIdle;
	private static Image downIdle;

	private long elapsedTime = 0;

	private boolean isMoving = false;

	private static double centerX = 0;
	private static double centerY = 0;
	private double width = 32;
	private double height = 32;
	private boolean dispose = false;	

	private final double VELOCITY = 150;


	private Direction direction = Direction.DOWN;
	
	private enum Direction { UP(0), DOWN(1), LEFT(2), RIGHT(3);	
		private int value = 0;
		private Direction(int value) {
			this.value = value; 
		} 
	};





	public PenguinSprite(double centerX, double centerY, double height, double width) {
		this(centerX, centerY);
		
		this.height = height;
		this.width = width;
	}

	
	public PenguinSprite(double centerX, double centerY) {

		this.centerX = centerX;
		this.centerY = centerY;
		
		try {
			down0 = ImageIO.read(new File("AnimationShell-master/res/penguin/waddle-down-0.png"));
			down1 = ImageIO.read(new File("AnimationShell-master/res/penguin/waddle-down-1.png"));
			downIdle = ImageIO.read(new File("AnimationShell-master/res/penguin/idle-down.png"));
			left0 = ImageIO.read(new File("AnimationShell-master/res/penguin/waddle-left-0.png"));
			left1 = ImageIO.read(new File("AnimationShell-master/res/penguin/waddle-left-1.png"));
			leftIdle = ImageIO.read(new File("AnimationShell-master/res/penguin/idle-left.png"));
			up0 = ImageIO.read(new File("AnimationShell-master/res/penguin/waddle-up-0.png"));
			up1 = ImageIO.read(new File("AnimationShell-master/res/penguin/waddle-up-1.png"));
			upIdle = ImageIO.read(new File("AnimationShell-master/res/penguin/idle-up.png"));
			right0 = ImageIO.read(new File("AnimationShell-master/res/penguin/waddle-right-0.png"));
			right1 = ImageIO.read(new File("AnimationShell-master/res/penguin/waddle-right-1.png"));
			rightIdle = ImageIO.read(new File("AnimationShell-master/res/penguin/idle-right.png"));
			
		}
		catch (IOException e) {
			System.err.println(e.toString());
		}		
	}

	public Image getImage() {
		/*
		 * Calculation for which image to display
		 * 1. calculate how many periods of time have elapsed since this sprite was instantiated?
		 * 2. calculate which image (aka 'frame') of the sprite animation should be shown out of the cycle of images
		 * 3. use some conditional logic to determine the right image for the current direction
		 */
		long period = elapsedTime / PERIOD_LENGTH;
		int image = (int) (period % IMAGES_IN_CYCLE);

		if (isMoving) {
			if (image == 0) {
				if (direction == Direction.UP) {
					return up0;
				} else if (direction == Direction.DOWN) {
					return down0;
				} else if (direction == Direction.RIGHT) {
					return right0;
				} else {
					return left0;
				}
			} else {
				if (direction == Direction.UP) {
					return up1;
				} else if (direction == Direction.DOWN) {
					return down1;
				} else if (direction == Direction.RIGHT) {
					return right1;
				} else {
					return left1;
				}
			}
		} else {
			if (direction == Direction.UP) {
				return upIdle;
			} else if (direction == Direction.DOWN) {
				return downIdle;
			} else if (direction == Direction.RIGHT) {
				return rightIdle;
			} else {
				return leftIdle;
			}
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

	public void update(Universe universe, long actual_delta_time) {

		elapsedTime += actual_delta_time;
		
		double velocityX = 0;
		double velocityY = 0;
		
		KeyboardInput keyboard = KeyboardInput.getKeyboard();

		//LEFT	
		if (keyboard.keyDown(37)) {
			velocityX = -VELOCITY;
			direction = Direction.LEFT;
		}
		//UP
		if (keyboard.keyDown(38)) {
			velocityY = -VELOCITY;
			direction = Direction.UP;
		}
		// RIGHT
		if (keyboard.keyDown(39)) {
			velocityX += VELOCITY;
			direction = Direction.RIGHT;
		}
		// DOWN
		if (keyboard.keyDown(40)) {
			velocityY += VELOCITY;
			direction = Direction.DOWN;	
		}


		if (velocityX != 0 || velocityY != 0) {
			isMoving = true;
		} else {
			isMoving = false;
		}


		double deltaX = actual_delta_time * 0.001 * velocityX;
        this.centerX += deltaX;
		
		double deltaY = actual_delta_time * 0.001 * velocityY;
    	this.centerY += deltaY;

	}


	@Override
	public void setDispose(boolean dispose) {
		this.dispose = true;
	}

	public static int[] getNearestGridPoint() {

		int gridX = (int) Math.round(centerX / ShellUniverse.ROOM_DISTANCE);
        int gridY = (int) Math.round(centerY / ShellUniverse.ROOM_DISTANCE);

        int closestX = gridX * ShellUniverse.ROOM_DISTANCE;
        int closestY = gridY * ShellUniverse.ROOM_DISTANCE;

		int[] coords = {closestX, closestY};

		return coords;
	}

}
