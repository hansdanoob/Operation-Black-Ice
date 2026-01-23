import java.awt.Image;
import java.io.IOException;

public interface DisplayableSprite {

	public abstract Image getImage();
	
	public boolean getVisible();

	public double getMinX();

	public double getMaxX();

	public double getMinY();

	public double getMaxY();

	public double getHeight();

	public double getWidth();

	public double getCenterX();

	public double getCenterY();
	
	public boolean getDispose();
	
	public void setDispose(boolean dispose);

	public void update(Universe universe, long actual_delta_time);	
	
}
