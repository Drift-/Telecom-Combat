package city.world.city;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import city.TowerDefense;
import city.graphics.Render;
import city.world.hexmech;
import city.world.game.Game;
import city.world.game.Tower;

public class City {
	
	public int x, y, xx, yy, population;
	public String name, type;
	public ArrayList<Integer> towers = new ArrayList<Integer>();
	
	public City(String type, String Name, int x, int y, int xx, int yy, int stringwidth) {
		this.type = type;
		this.name = Name;
		this.x = x;
		this.y = y;
		this.xx = xx;
		this.yy = yy;
		
		//init population
		if (type.equals("Town")) population = 100000 - TowerDefense.ran.nextInt(75000);
		else if (type.equals("City")) population = 500000 - TowerDefense.ran.nextInt(400000);
		else if (type.equals("LargeCity")) population = 2000000 - TowerDefense.ran.nextInt(1500000);
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		if (type.equals("Town")) g.fillOval(xx + hexmech.r - 4 + Render.xOffset, yy - 20 + Render.yOffset, 10, 10);
		else if (type.equals("City")) {
			int[] xpoints = new int[3];
			xpoints[0] = xx + hexmech.r + Render.xOffset; 
			xpoints[1] = xx + hexmech.r - 6 + Render.xOffset;
			xpoints[2] = xx + hexmech.r + 6 + Render.xOffset;
			int[] ypoints = new int[3];
			ypoints[0] = yy - 22 + Render.yOffset;
			ypoints[1] = yy - 10 + Render.yOffset;
			ypoints[2] = yy - 10 + Render.yOffset;
			g.fillPolygon(xpoints, ypoints, 3);
		} else if (type.equals("LargeCity")) {
			g.fillRect(xx + hexmech.r - 6 + Render.xOffset, yy - 24 + Render.yOffset, 16, 16);
		}
	}

	public double getMarketShare(int power) {
		double full = 0;
		for (int i = 0; i < towers.size(); i++) {
			full += towers.get(i);
		}
		return ((double)power/full);
	}

}
