package city.graphics;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class SpriteSheet {
	
	public BufferedImage sheet;
	
	public static SpriteSheet tilesheet = new SpriteSheet("/tilesheet.png");
	
	public SpriteSheet(String path) {
		Image image = new ImageIcon(this.getClass().getResource(path)).getImage();
		BufferedImage img = toBufferedImage(image);
		this.sheet = img;
	}
	
	public BufferedImage toBufferedImage(Image img) {
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}

}
