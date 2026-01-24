import java.awt.Image;
import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import java.util.Random;


public class FishSprite implements DisplayableSprite {

    private ArrayList<Image> images = new ArrayList<Image>();
	private Image image;
	private boolean visible = true;
	private double centerX;
	private double centerY;
	private double width = 25;
	private double height = 25;
	private boolean dispose = false;
	private Random random = new Random();



	public FishSprite(double centerX, double centerY) {

		this.centerX = centerX;
		this.centerY = centerY;

        try {
		    images.add(ImageIO.read(new File("AnimationShell-master/res/fish/blue-0.png")));
			images.add(ImageIO.read(new File("AnimationShell-master/res/fish/blue-1.png")));
			images.add(ImageIO.read(new File("AnimationShell-master/res/fish/pink-0.png")));
			images.add(ImageIO.read(new File("AnimationShell-master/res/fish/pink-1.png")));
			images.add(ImageIO.read(new File("AnimationShell-master/res/fish/brown-0.png")));
			images.add(ImageIO.read(new File("AnimationShell-master/res/fish/brown-1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

		int rand = random.nextInt(6);
		image = images.get(rand);
	}

	public Image getImage() {
        return image;
	}
	
	public boolean getVisible() {
		return visible;
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
		
		if (checkCollisionWithPenguin(universe.getSprites())) {
			PenguinSprite.incrementFishCollected();
			this.dispose = true;
		}
	}

	private boolean checkCollisionWithPenguin(ArrayList<DisplayableSprite> sprites) {

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
	
}
