package city.graphics;

import java.awt.image.BufferedImage;

public class Sprite {
	
	public BufferedImage img;
	
	public static Sprite tower = new Sprite(SpriteSheet.tilesheet, 0, 0, 16);
	public static Sprite phone_thrower = new Sprite(SpriteSheet.towers, 0, 0, 16);
	public static Sprite wifi_laser = new Sprite(SpriteSheet.towers, 1, 0, 16);
	public static Sprite freezer = new Sprite(SpriteSheet.towers, 2, 0, 16);
	/*public static Sprite grass = new Sprite(SpriteSheet.tilesheet, 0, 0, 16);
	public static Sprite forest = new Sprite(SpriteSheet.tilesheet, 1, 0, 16);
	public static Sprite sand = new Sprite(SpriteSheet.tilesheet, 2, 0, 16);
	public static Sprite water = new Sprite(SpriteSheet.tilesheet, 3, 0, 16);
	public static Sprite mountain = new Sprite(SpriteSheet.tilesheet, 4, 0, 16);
	public static Sprite town = new Sprite(SpriteSheet.tilesheet, 5, 0, 16);
	public static Sprite city = new Sprite(SpriteSheet.tilesheet, 6, 0, 16);
	public static Sprite largecity = new Sprite(SpriteSheet.tilesheet, 7, 0, 16);*/
	
	public Sprite(SpriteSheet spritesheet, int x, int y, int size) {
		BufferedImage sheet = spritesheet.sheet;
		img = sheet.getSubimage(x * size, y * size, size, size);
	}

}
