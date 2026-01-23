import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class VignetteSprite implements DisplayableSprite {

	private static Image image;
	private boolean visible = true;
	private double centerX;
	private double centerY;
	private final double DEFAULT_WIDTH = 1300; // slightly larger than universe
	private final double DEFAULT_HEIGHT = 900;
	private double width;
	private double height;
	private boolean dispose = false;
	private double scale = 1;

	public VignetteSprite(double centerX, double centerY) {

		this.centerX = centerX;
		this.centerY = centerY;
		this.width = DEFAULT_WIDTH;
		this.height = DEFAULT_HEIGHT;

		try {
			image = ImageIO.read(new File("AnimationShell-master/res/vignette.png"));
		}
		catch (IOException e) {
			System.err.println(e.toString());
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

		this.scale = AnimationFrame.getScale();

		this.height = DEFAULT_HEIGHT / scale;
		this.width = DEFAULT_WIDTH / scale;
		
		this.centerX = ShellUniverse.centerX;
		this.centerY = ShellUniverse.centerY;
	}
	
}
