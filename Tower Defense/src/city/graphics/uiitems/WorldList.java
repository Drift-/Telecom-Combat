package city.graphics.uiitems;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.util.ArrayList;

import city.TowerDefense;
import city.world.World;

public class WorldList {
	
	private int x, y, width, height, yOffset;
	private ArrayList<WorldListButton> buttons = new ArrayList<WorldListButton>();
	
	public WorldList(int x, int y, int width, int height) {
		//init variables
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		//create buttons
		getWorlds();
	}
	
	public void getWorlds() {
		File worlds = new File(TowerDefense.res + "\\" + "worlds");
		String[] names = worlds.list();
		
		for(String i : names) {
			File world = new File(worlds + "\\" + i);
		    if (world.isDirectory()){
		    	String name = World.getNameInfo(TowerDefense.res + "\\worlds\\" + i)[0];
		    	String info = World.getNameInfo(TowerDefense.res + "\\worlds\\" + i)[1];
		        WorldListButton b = new WorldListButton(name, info);
		        buttons.add(b);
		    }
		}
	}
	
	public void tick() {
		//call the tick function in all buttons
		for (WorldListButton b : buttons) {
			b.tick();
		}
	}
	
	public void render(Graphics g) {
		//outline
		g.setColor(Color.decode("#969696"));
		g.fillRect(x, y, width, height);
		
		//render each button
		for (int i = 0; i < buttons.size(); i++) {
			g.setColor(Color.decode("#444B4D"));
			g.fillRect(x + 10, y + 10 + (100 * i), width - 20, 90);
		}
	}

}
