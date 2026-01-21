import java.awt.Image;

public class Vignette implements DisplayableSprite { // needs to adapt to scale of game

	private static Image image;
	private boolean visible = true;
	private double centerX;
	private double centerY;
	private double width = 1200;
	private double height = 800;
	private boolean dispose = false;

	public Image getImage() {
		return null;
	}
	
	public boolean getVisible() {
		return false;
	}

	public double getMinX() {
		return 0;
	}

	public double getMaxX() {
		return 0;
	}

	public double getMinY() {
		return 0;
	}

	public double getMaxY() {
		return 0;
	}

	public double getHeight() {
		return 0;
	}

	public double getWidth() {
		return 0;
	}

	public double getCenterX() {
		return centerX;
	}

	public double getCenterY() {
		return centerY;
	}
	
	public boolean getDispose() {
		return false;
	}
	
	public void setDispose(boolean dispose) {

	}

	public void update(Universe universe, long actual_delta_time) {

	}
	
}
