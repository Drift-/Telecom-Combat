package city.graphics;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import city.TowerDefense;
import city.graphics.fonts.CustomFont;
import city.world.tile.Tile;

public class Render {
	
	public static int xOffset = 0, yOffset = 0;
	public static Graphics g;
	
	public Render(Graphics g) {
		this.g = g;
	}
	
	public void renderTile(Tile tile, int x, int y) {
		//g.drawImage(tile.sprite.img, x * 32 + xOffset, y * 32 + yOffset, 32, 32, null);
	}
	
	public void drawSprite(Sprite sprite, int x, int y) {
		g.drawImage(sprite.img, x, y, 16, 16, null);
	}
	
	public void drawSprite(Sprite sprite, int x, int y, int width, int height) {
		g.drawImage(sprite.img, x, y, width, height, null);
	}
	
	public static int getWidth(String text, int size) {
		CustomFont cf = new CustomFont();
		Font font = cf.font(size);
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
		return (int)(font.getStringBounds(text, frc).getWidth());
	}
	
	public static int getHeight(String text, int size) {
		CustomFont cf = new CustomFont();
		Font font = cf.font(size);
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
		return (int)(font.getStringBounds(text, frc).getHeight());
	}
	
	public static void setResolution(int width, int height) {
		TowerDefense.WIDTH = width;
		TowerDefense.HEIGHT = height;
		TowerDefense.frame.setSize(width, height);
	}

}
