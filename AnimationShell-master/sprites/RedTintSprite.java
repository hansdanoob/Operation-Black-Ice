import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class RedTintSprite implements DisplayableSprite {

    private BufferedImage tintedImage;
    private BufferedImage baseImage;
	private boolean visible = true;
	private double centerX;
	private double centerY;
	private final double DEFAULT_WIDTH = 1300; // slightly larger than universe
	private final double DEFAULT_HEIGHT = 900;
	private double width;
	private double height;
	private boolean dispose = false;
	private double scale = 1;
    public static double closestSealToPlayerDistance;
    private final double DANGER_RADIUS = 400; // screen will start to tine as of 600 distance
    private double transparency = 0;

	public RedTintSprite(double centerX, double centerY) {

		this.centerX = centerX;
		this.centerY = centerY;
		this.width = DEFAULT_WIDTH;
		this.height = DEFAULT_HEIGHT;

        try {
		    baseImage = ImageIO.read(new File("AnimationShell-master/res/redTint.png"));
            tintedImage = baseImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public Image getImage() {
        return tintedImage;
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

    public static BufferedImage applyTransparency(BufferedImage image, float alpha) {
        // alpha: 0.0f = fully transparent, 1.0f = fully opaque

        BufferedImage transparentImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = transparentImage.createGraphics();

        // Enable alpha compositing
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return transparentImage;
    }

	public void update(Universe universe, long actual_delta_time) {

		this.scale = AnimationFrame.getScale();

		this.height = DEFAULT_HEIGHT / scale;
		this.width = DEFAULT_WIDTH / scale;
		
		this.centerX = ShellUniverse.centerX;
		this.centerY = ShellUniverse.centerY;

        transparency = 1 - (closestSealToPlayerDistance / DANGER_RADIUS);

        // Clamp value (VERY important)
        transparency = Math.max(0, Math.min(1, transparency));

        transparency *= 0.3; // set 0.2 to be max

        tintedImage = applyTransparency(baseImage, (float) transparency);

        System.out.println("Alpha: " + transparency);

	}
	
}
