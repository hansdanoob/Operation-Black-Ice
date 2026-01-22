import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


import javax.imageio.ImageIO;

public class SealSprite implements DisplayableSprite {

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
	private boolean isAgressive = false;

	private final double PLAYER_DETECTION_RADIUS = 700;
	private final double DESPAWN_RADIUS = 2000;

	public static double centerX = 0;
	public static double centerY = 0;
	private double width = 32;
	private double height = 32;
	private boolean dispose = false;	

	private static final Direction[] DIRECTIONS = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

	private final double WALK_VELOCITY = 200;
	private final double ACCELERATION = 15;
	private double velocityX;
	private double velocityY;

	private Random random = new Random();

	private Direction directionLooking = Direction.SOUTH;
	private ArrayList<Direction> directionsMoving = new ArrayList<>();



	public SealSprite(double centerX, double centerY, double height, double width) {
		this(centerX, centerY);
		
		this.height = height;
		this.width = width;
	}

	
	public SealSprite(double centerXin, double centerYin) {

		centerX = centerXin;
		centerY = centerYin;

		directionsMoving.add(Direction.NORTH);

		int randIndex = ((int) random.nextDouble() * 2) + 2; // either 2 or 3, corresponds to East or West
		directionsMoving.add(DIRECTIONS[randIndex]);
		
		
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
		
		long period = elapsedTime / PERIOD_LENGTH;
		int image = (int) (period % IMAGES_IN_CYCLE);

		

		if (isMoving) {
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

	private double getDistance(double x1, double y1, double x2, double y2) {
		double distance = Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
		return distance;
	}


	public void update(Universe universe, long actual_delta_time) {

		elapsedTime += actual_delta_time;

		double distance = this.getDistance(this.getCenterX(), this.getCenterY(), PenguinSprite.centerX, PenguinSprite.centerY);
		// --------------------------------------------------


		double targetVelocity;

		if (!isMoving) {
			targetVelocity = 0;
		} else {
			targetVelocity = WALK_VELOCITY;
		}


		isMoving = true; // For current implementation of SealSprite, it will always be moving

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

		// Apply acceleration
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

		// Determine directionLooking
		if (Math.abs(velocityX) > Math.abs(velocityY)) {
			if (velocityX < 0) {
				directionLooking = Direction.WEST;
			} else {
				directionLooking = Direction.EAST;
			}
		} else {
			if (velocityY < 0) {
				directionLooking = Direction.NORTH;
			} else {
				directionLooking = Direction.SOUTH;
			}
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
