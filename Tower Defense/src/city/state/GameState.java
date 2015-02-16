package city.state;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

import city.TowerDefense;
import city.graphics.Render;
import city.graphics.fonts.CustomFont;
import city.graphics.uiitems.UIButton;
import city.graphics.uiitems.WorldList;
import city.input.Mouse;
import city.world.World;
import city.world.hexmech;
import city.world.city.City;
import city.world.game.Game;
import city.world.game.Tower;

public class GameState {
	
	CustomFont font = new CustomFont();
	
	Tower tower;
	
	private int state = 0;
	private UIButton newgame, loadgame, back_menu;
	private WorldList worlds;
	
	private Font font_25 = font.font(25);
	private Font font_15 = font.font(15);
	
	public boolean hoverable = true;
	public boolean render_tower = false;
	
	public GameState() {
		//initialize buttons
		newgame = new UIButton(TowerDefense.WIDTH/2 - 75, 100, 150, 35, "New Game", 20);
		loadgame = new UIButton(TowerDefense.WIDTH/2 - 75, 170, 150, 35, "Load Game", 20);
		back_menu = new UIButton(TowerDefense.WIDTH/2 - 75, 240, 150, 35, "Back", 20);
		worlds = new WorldList(150 + (TowerDefense.WIDTH - 150) / 5, TowerDefense.HEIGHT / 10, ((TowerDefense.WIDTH - 150) / 5) * 3, (TowerDefense.HEIGHT / 5) * 3);
		
		World world = new World();
		world.load("/worlds/test2");
		
		Game.SpawnPlayers();
	}
	
	public void tick() {
		Game.tick();
		newgame.tick();
		loadgame.tick();
		back_menu.tick();
		
		if (state == 0) {
			if (back_menu.click) {
				StateManager.state = 0;
			} else if (newgame.click) {
				state = 1;
			}
		} else if (state == 1) {
			worlds.tick();
		}
		
		if (true) {
			TowerDefense.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
		if (hoverable && !render_tower) {
			if (TowerDefense.key.up) Render.yOffset += 3;
			if (TowerDefense.key.down) Render.yOffset -= 3;
			if (TowerDefense.key.left) Render.xOffset += 3;
			if (TowerDefense.key.right) Render.xOffset -= 3;
		}
		
		for (Tower t : Game.towers) {
			t.tick();
		}
	}
	
	public void render(Graphics g) {
		//background
		g.setColor(World.COLOURGRID);
		g.fillRect(0, 0, TowerDefense.WIDTH, TowerDefense.HEIGHT);
				
		/*if (state == 0) {
			newgame.render(g);
			loadgame.render(g);
			back_menu.render(g);
		} else if (state == 1) {
			//left area
			
			
			//world choosing area
			g.setColor(Color.WHITE);
			g.setFont(font.font(TowerDefense.HEIGHT  / 10 - 10));
			g.drawString("Choose a World:", choose_world_width, TowerDefense.HEIGHT / 10 - 10);
			worlds.render(g);
		}*/
		
		if (!render_tower) {
			World.render();
		}
		
		if (hoverable && !render_tower) {
			Point p = hexmech.pxtoHex(Mouse.getX(), Mouse.getY());
			for (City c : World.cities) {
				g.drawString("X: " + p.x, 50, 20);
				g.drawString("Y: " + p.y, 100, 20);
				if (c.x == p.x && c.y == p.y) {
					//window background
					g.setColor(Color.decode("#E3E3E3"));
					g.fillRect(Mouse.getX() - 90, Mouse.getY() + 10, 180, 75);
					//text
					g.setColor(Color.BLACK);
					g.setFont(font_25);
					g.drawString(c.name, Mouse.getX() - (c.name.length()*25)/4, Mouse.getY() + 40);
					g.setFont(font_15);
					g.drawString("Click for more info", Mouse.getX() - (19*15)/4, Mouse.getY() + 70);
				}
			}
			for (Tower t : Game.towers) {
				if (t.x == p.x && t.y == p.y) {
					//window background
					g.setColor(Color.decode("#E3E3E3"));
					g.fillRect(Mouse.getX() - 130, Mouse.getY() + 10, 260, 75);
					//text
					g.setColor(Color.BLACK);
					g.setFont(font_25);
					g.drawString(t.owner + "'s Cell Tower", Mouse.getX() - ((t.owner + "'s Cell Tower").length()*25)/4, Mouse.getY() + 40);
					g.setFont(font_15);
					g.drawString("Click for more info", Mouse.getX() - (19*15)/4, Mouse.getY() + 70);
					
					if (Mouse.getButton() == 1) {
						render_tower = true;
						tower = t;
						hoverable = false;
					}
				}
			}
		} else if (render_tower) {
			tower.selected = true;
			tower.render(g);
		}
	}

}
