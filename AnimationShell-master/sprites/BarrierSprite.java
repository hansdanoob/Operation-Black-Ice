import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BarrierSprite implements DisplayableSprite {

	private static Image image;
	private boolean visible = true;
	private double centerX;
	private double centerY;
	private double width;
	private double height;
	private boolean dispose = false;
	
	
	public BarrierSprite(double width, double height, double centerX, double centerY, boolean visible) {
	
		if (image == null && visible) {
			try {
				image = ImageIO.read(new File("AnimationShell-master/res/blue-barrier.png"));
			}
			catch (IOException e) {
				e.printStackTrace();
			}		
		}
		
		this.centerX = centerX;
		this.centerY = centerY;
		this.width = width;
		this.height = height;
		this.visible = visible;
		
		ShellUniverse.spritesToAdd.add(this);
	}
	

	public Image getImage() {
		return image;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	//DISPLAYABLE
	
	public boolean getVisible() {
		return this.visible;
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
