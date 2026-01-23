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
	private long timeSinceLastStun = Integer.MAX_VALUE;
	private final long STUN_LENGTH = 1000;

	private boolean isMoving = false;
	private boolean isAgressive = false;
	private boolean isStunned = false;

	private final double PLAYER_DETECTION_RADIUS = 500;
	private final double DESPAWN_RADIUS = 2000;

	public double centerX = 0;
	public double centerY = 0;
	private double width = 50;
	private double height = 50;
	private boolean dispose = false;	

	private static final Direction[] DIRECTIONS = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

	private final double WALK_VELOCITY = 160;
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

		if (random.nextDouble() > 0.5) {
			directionsMoving.add(Direction.NORTH);
		} else {
			directionsMoving.add(Direction.SOUTH);
		}

		if (random.nextDouble() > 0.5) {
			directionsMoving.add(Direction.WEST);
		} else {
			directionsMoving.add(Direction.EAST);
		}
		
		
		
		try {
			up0 = ImageIO.read(new File("AnimationShell-master/res/seal/walk-up-0.png"));
			up1 = ImageIO.read(new File("AnimationShell-master/res/seal/walk-up-1.png"));
			upIdle = ImageIO.read(new File("AnimationShell-master/res/seal/idle-up.png"));
			
			down0 = ImageIO.read(new File("AnimationShell-master/res/seal/walk-down-0.png"));
			down1 = ImageIO.read(new File("AnimationShell-master/res/seal/walk-down-1.png"));
			downIdle = ImageIO.read(new File("AnimationShell-master/res/seal/idle-down.png"));

			left0 = ImageIO.read(new File("AnimationShell-master/res/seal/walk-left-0.png"));
			left1 = ImageIO.read(new File("AnimationShell-master/res/seal/walk-left-1.png"));
			leftIdle = ImageIO.read(new File("AnimationShell-master/res/seal/idle-left.png"));

			right0 = ImageIO.read(new File("AnimationShell-master/res/seal/walk-right-0.png"));
			right1 = ImageIO.read(new File("AnimationShell-master/res/seal/walk-right-1.png"));
			rightIdle = ImageIO.read(new File("AnimationShell-master/res/seal/idle-right.png"));			
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


	public void update(Universe universe, long actual_delta_time) {

		elapsedTime += actual_delta_time;
		timeSinceLastStun += actual_delta_time;

		double distanceToPlayer = ShellUniverse.getDistance(this.getCenterX(), this.getCenterY(), PenguinSprite.centerX, PenguinSprite.centerY);
		if (distanceToPlayer > DESPAWN_RADIUS) {
			this.dispose = true;
		} else if (distanceToPlayer < PLAYER_DETECTION_RADIUS) { // Detects player
			isAgressive = true;
		} else { 	// Default movement
			isAgressive = false;
		}


		double targetVelocity;

		isMoving = true; // For current implementation of SealSprite, it will always be moving

		if (timeSinceLastStun <= STUN_LENGTH) {
			isStunned = true;
		} else {
			isStunned = false;
		}



		if (!isMoving) {
			targetVelocity = 0;
		} else {
			targetVelocity = WALK_VELOCITY;
		}

		if (isStunned) {
			velocityX = 0;
			velocityY = 0;
		} else if (isAgressive) {

			double distanceX = PenguinSprite.centerX - centerX;
			double distanceY = PenguinSprite.centerY - centerY;

			velocityX = (distanceX / distanceToPlayer) * targetVelocity;
			velocityY = (distanceY / distanceToPlayer) * targetVelocity;


		} else {
			double velocityPerDirection = targetVelocity;

			if (directionsMoving.size() > 1) {
				velocityPerDirection *= 0.85;
			}

			velocityX = 0;
			velocityY = 0;

			for (int i = 0; i < directionsMoving.size(); i++) {
				if (directionsMoving.get(i) == Direction.NORTH) {
					velocityY -= velocityPerDirection;
				} else if (directionsMoving.get(i) == Direction.SOUTH) {
					velocityY += velocityPerDirection;
				} else if (directionsMoving.get(i) == Direction.EAST) {
					velocityX += velocityPerDirection;
				} else {
					velocityX -= velocityPerDirection;
				}
			}
		}

	

		// Determine directionLooking
		if (Math.abs(velocityX) > Math.abs(velocityY)) {
			if (velocityX <= 0) {
				directionLooking = Direction.WEST;
			} else {
				directionLooking = Direction.EAST;
			}
		} else {
			if (velocityY <= 0) {
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
		} else { // colliding in X axis
			if (directionsMoving.contains(Direction.EAST)) {
				directionsMoving.remove(Direction.EAST);
				directionsMoving.add(Direction.WEST);
			} else {
				directionsMoving.remove(Direction.WEST);
				directionsMoving.add(Direction.EAST);
			}
		}
		//only move if there is no collision with barrier in Y dimension 
		if (collidingBarrierY == false) {
			centerY += deltaY;
		} else { // colliding in Y axis
			if (directionsMoving.contains(Direction.NORTH)) {
				directionsMoving.remove(Direction.NORTH);
				directionsMoving.add(Direction.SOUTH);
			} else {
				directionsMoving.remove(Direction.SOUTH);
				directionsMoving.add(Direction.NORTH);
			}
		}

		if (checkCollisionWithPenguin(universe.getSprites())) {
			if (!PenguinSprite.isSliding() && (PenguinSprite.getTimeSinceLastDamage() > PenguinSprite.IFRAMES)) { // Can hit

				PenguinSprite.resetTimeSinceLastDamge();
				PenguinSprite.decrementHealth();
				this.timeSinceLastStun = 0;

				if (PenguinSprite.getHealth() <= 0) {
					PenguinSprite.centerX = 0;
					PenguinSprite.centerY = 0;
					this.dispose = true;
					PenguinSprite.setHealth(PenguinSprite.MAX_HEALTH);
				}
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

	private boolean checkCollisionWithPenguin(ArrayList<DisplayableSprite> sprites) {

		//deltaX and deltaY represent the potential change in position
		boolean colliding = false;

		for (DisplayableSprite sprite : sprites) {
			if (sprite instanceof PenguinSprite) {
				if (CollisionDetection.overlaps(this.getMinX(), this.getMinY(), 
						this.getMaxX(), this.getMaxY(), 
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
}
