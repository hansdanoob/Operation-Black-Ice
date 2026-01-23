import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;


public class FishSprite implements DisplayableSprite {

    private Image image;
	private boolean visible = true;
	private double centerX;
	private double centerY;
	private double width = 25;
	private double height = 25;
	private boolean dispose = false;

	public FishSprite(double centerX, double centerY) {

		this.centerX = centerX;
		this.centerY = centerY;

        try {
		    image = ImageIO.read(new File("AnimationShell-master/res/blue-barrier.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

	}
	
}
