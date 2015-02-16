package city.world.tile;

import java.awt.Color;

import city.TowerDefense;
import city.graphics.Render;
import city.graphics.Sprite;
import city.world.hexmech;

public class Tile {
	
	public static Render render = TowerDefense.render;
	public Color col;
	
	public static Tile grass = new Tile(new Color(60, 189, 89));
	public static Tile forest = new Tile(new Color(12, 130, 70));
	public static Tile water = new Tile(new Color(45, 173, 198));
	public static Tile mountain = new Tile(new Color(72, 79, 81));
	//public static Tile town = new Tile(Sprite.town);
	//public static Tile city = new Tile(Sprite.city);
	//public static Tile largecity = new Tile(Sprite.largecity);
	
	public Tile(Color col) {
		this.col = col;
	}
	
	public void render(int x, int y) {
		hexmech.drawHex(y, y, Render.g);
	}
	
	public Color getColor() {
		return col;
	}

}
