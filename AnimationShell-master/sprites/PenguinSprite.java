import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
	private static Image leftSlide;
	private static Image rightSlide;
	private static Image upSlide;
	private static Image downSlide;

	private long elapsedTime = 0;
	private long timeSinceLastSlide = 0;
	private static boolean canSlide = true;

	private boolean isMoving = false;
	private boolean isSliding = false;

	public static double centerX = 0;
	public static double centerY = 0;
	private double width = 32;
	private double height = 32;
	private boolean dispose = false;	

	private final double WADDLE_VELOCITY = 125;
	private final double SLIDE_VELOCITY = 650;
	private final double ACCELERATION = 15;
	private final long SLIDE_COOLDOWN = 1500;
	private double velocityX;
	private double velocityY;


	private Direction directionLooking = Direction.SOUTH;



	public PenguinSprite(double centerX, double centerY, double height, double width) {
		this(centerX, centerY);
		
		this.height = height;
		this.width = width;
	}

	
	public PenguinSprite(double centerXin, double centerYin) {

		centerX = centerXin;
		centerY = centerYin;
		
		try {
			down0 = ImageIO.read(new File("AnimationShell-master/res/penguin/waddle-down-0.png"));
			down1 = ImageIO.read(new File("AnimationShell-master/res/penguin/waddle-down-1.png"));
			downIdle = ImageIO.read(new File("AnimationShell-master/res/penguin/idle-down.png"));
			downSlide = ImageIO.read(new File("AnimationShell-master/res/penguin/slide-down.png"));

			left0 = ImageIO.read(new File("AnimationShell-master/res/penguin/waddle-left-0.png"));
			left1 = ImageIO.read(new File("AnimationShell-master/res/penguin/waddle-left-1.png"));
			leftIdle = ImageIO.read(new File("AnimationShell-master/res/penguin/idle-left.png"));
			leftSlide = ImageIO.read(new File("AnimationShell-master/res/penguin/slide-left.png"));

			up0 = ImageIO.read(new File("AnimationShell-master/res/penguin/waddle-up-0.png"));
			up1 = ImageIO.read(new File("AnimationShell-master/res/penguin/waddle-up-1.png"));
			upIdle = ImageIO.read(new File("AnimationShell-master/res/penguin/idle-up.png"));
			upSlide = ImageIO.read(new File("AnimationShell-master/res/penguin/slide-up.png"));

			right0 = ImageIO.read(new File("AnimationShell-master/res/penguin/waddle-right-0.png"));
			right1 = ImageIO.read(new File("AnimationShell-master/res/penguin/waddle-right-1.png"));
			rightIdle = ImageIO.read(new File("AnimationShell-master/res/penguin/idle-right.png"));
			rightSlide = ImageIO.read(new File("AnimationShell-master/res/penguin/slide-right.png"));
			
		}
		catch (IOException e) {
			System.err.println(e.toString());
		}		
	}

	public Image getImage() {
		
		long period = elapsedTime / PERIOD_LENGTH;
		int image = (int) (period % IMAGES_IN_CYCLE);

		

		if (isSliding) {
			if (directionLooking == Direction.NORTH) {
				return upSlide;
			} else if (directionLooking == Direction.SOUTH) {
				return downSlide;
			} else if (directionLooking == Direction.EAST) {
				return rightSlide;
			} else {
				return leftSlide;
			}
		} else if (isMoving) {
			if (image == 0) {
				if (directionLooking == Direction.NORTH) {
					return up0;
				} else if (directionLooking == Direction.SOUTH) {
					return down0;
				} else if (directionLooking == Direction.EAST) {
					return right0;
				} else {
					return left0;
				}
			} else {
				if (directionLooking == Direction.NORTH) {
					return up1;
				} else if (directionLooking == Direction.SOUTH) {
					return down1;
				} else if (directionLooking == Direction.EAST) {
					return right1;
				} else {
					return left1;
				}
			}
		} else {
			if (directionLooking == Direction.NORTH) {
				return upIdle;
			} else if (directionLooking == Direction.SOUTH) {
				return downIdle;
			} else if (directionLooking == Direction.EAST) {
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

	public static boolean canSlide() {
		return canSlide;
	}

	public void update(Universe universe, long actual_delta_time) {

		elapsedTime += actual_delta_time;
		timeSinceLastSlide += actual_delta_time;

		if (timeSinceLastSlide > SLIDE_COOLDOWN) {
			canSlide = true;
		} else {
			canSlide = false;
		}

		double targetVelocity;
		
		KeyboardInput keyboard = KeyboardInput.getKeyboard();

		// SHIFT
		if (keyboard.keyDownOnce(16) && canSlide) {
			this.timeSinceLastSlide = 0;
			isSliding = true;
			ShellUniverse.smoothingFactor = 0.06;
			if (directionLooking == Direction.NORTH) {
				velocityY = -SLIDE_VELOCITY;
			} else if (directionLooking == Direction.SOUTH) {
				velocityY = SLIDE_VELOCITY;
			} else if (directionLooking == Direction.EAST) {
				velocityX = SLIDE_VELOCITY;
			} else {
				velocityX = -SLIDE_VELOCITY;
			}
		}


		if (!isMoving) {
			targetVelocity = 0;
		} else {
			targetVelocity = WADDLE_VELOCITY;
		}

		ArrayList<Direction> directionsMoving = new ArrayList<>();

		isMoving = false;

		//LEFT	
		if (keyboard.keyDown(37)) {
			directionLooking = Direction.WEST;
			directionsMoving.add(Direction.WEST);
			isMoving = true;
		}
		// RIGHT
		if (keyboard.keyDown(39)) {
			directionLooking = Direction.EAST;
			directionsMoving.add(Direction.EAST);
			isMoving = true;
		}
		//UP
		if (keyboard.keyDown(38)) {
			directionLooking = Direction.NORTH;
			directionsMoving.add(Direction.NORTH);
			isMoving = true;
		}
		// DOWN
		if (keyboard.keyDown(40)) {
			directionLooking = Direction.SOUTH;
			directionsMoving.add(Direction.SOUTH);
			isMoving = true;
		}

		double velocityPerDirection = targetVelocity;

		if (directionsMoving.size() > 1) {
			velocityPerDirection *= 0.85;
		}


		double targetXVelocity = 0;
		double targetYVelocity = 0;
		for (int i = 0; i < directionsMoving.size(); i++) {
			if (directionsMoving.get(i) == Direction.NORTH) {
				targetYVelocity -= velocityPerDirection;
			} else if (directionsMoving.get(i) == Direction.SOUTH) {
				targetYVelocity += velocityPerDirection;
			} else if (directionsMoving.get(i) == Direction.EAST) {
				targetXVelocity += velocityPerDirection;
			} else {
				targetXVelocity -= velocityPerDirection;
			}
		}

		if (velocityX < targetXVelocity) {
			velocityX += ACCELERATION;
		} else if (velocityX > targetXVelocity) {
			velocityX -= ACCELERATION;
		}

		if (velocityY < targetYVelocity) {
			velocityY += ACCELERATION;
		} else if (velocityY > targetYVelocity) {
			velocityY -= ACCELERATION;
		}
	
		double deltaX = actual_delta_time * 0.001 * velocityX;
		double deltaY = actual_delta_time * 0.001 * velocityY;
		
		boolean collidingBarrierX = checkCollisionWithBarrier(universe.getSprites(), deltaX, 0);
		boolean collidingBarrierY = checkCollisionWithBarrier(universe.getSprites(), 0, deltaY);
		
		//only move if there is no collision with barrier in X dimension 
		if (collidingBarrierX == false) {
			centerX += deltaX;
		}
		//only move if there is no collision with barrier in Y dimension 
		if (collidingBarrierY == false) {
			centerY += deltaY;
		}

		if (isSliding) {
			if (Math.abs(velocityX) <= WADDLE_VELOCITY && Math.abs(velocityY) <= WADDLE_VELOCITY) {
				isSliding = false;
				ShellUniverse.smoothingFactor = 0.03;
			}
		}
	}

	private boolean checkCollisionWithBarrier(ArrayList<DisplayableSprite> sprites, double deltaX, double deltaY) {

		//deltaX and deltaY represent the potential change in position
		boolean colliding = false;

		for (DisplayableSprite sprite : sprites) {
			if (sprite instanceof BarrierSprite) {
				if (CollisionDetection.overlaps(this.getMinX() + deltaX, this.getMinY() + deltaY, 
						this.getMaxX()  + deltaX, this.getMaxY() + deltaY, 
						sprite.getMinX(),sprite.getMinY(), 
						sprite.getMaxX(), sprite.getMaxY())) {
					colliding = true;
					break;					
				}
			}
		}		
		return colliding;		
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
