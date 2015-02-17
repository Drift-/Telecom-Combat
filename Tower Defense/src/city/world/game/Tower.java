package city.world.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import city.TowerDefense;
import city.graphics.Render;
import city.graphics.Sprite;
import city.graphics.fonts.CustomFont;
import city.graphics.uiitems.UIButton;
import city.graphics.uiitems.UIImageButton;
import city.world.World;
import city.world.hexmech;
import city.world.city.City;
import city.world.tile.Tile;

public class Tower {
	
	public int x, y, xx, yy, size = 1, health = 100, moneyPT, power = 1;
	public int[][] towers = new int[15][15];
	public String[][] tiles = new String[15][15];
	public String owner;
	
	private Font font_20, font_15;
	private UIImageButton def_0;
	private UIButton back, def_1, def_2, def_3, def_4, def_5, def_6, def_7, def_8;
	
	public boolean selected = false;
	
	public Tower(int x, int y, int xx, int yy, String owner) {
		this.x = x;
		this.y = y;
		this.xx = xx;
		this.yy = yy;
		this.owner = owner;
		addCities();
		back = new UIButton(TowerDefense.WIDTH - 175, TowerDefense.HEIGHT - 85, 150, 35, "Back", 20);
		def_0 = new UIImageButton(TowerDefense.WIDTH - 153, 120, 30, 30, Sprite.ran_tower);
		def_1 = new UIButton(TowerDefense.WIDTH - 118, 120, 30, 30, "", 15);
		def_2 = new UIButton(TowerDefense.WIDTH - 83, 120, 30, 30, "", 15);
		def_3 = new UIButton(TowerDefense.WIDTH - 153, 155, 30, 30, "", 15);
		def_4 = new UIButton(TowerDefense.WIDTH - 118, 155, 30, 30, "", 15);
		def_5 = new UIButton(TowerDefense.WIDTH - 83, 155, 30, 30, "", 15);
		def_6 = new UIButton(TowerDefense.WIDTH - 153, 190, 30, 30, "", 15);
		def_7 = new UIButton(TowerDefense.WIDTH - 118, 190, 30, 30, "", 15);
		def_8 = new UIButton(TowerDefense.WIDTH - 83, 190, 30, 30, "", 15);
		
		//custom font
		CustomFont f = new CustomFont();
		font_20 = f.font(20);
		font_15 = f.font(15);
		
		//generate land
		generateLand();
		
		//test who to attack thing
		ArrayList<Integer> attack = new ArrayList<Integer>();
		for (int i = 0; i < 2; i++) {
			int towers = TowerDefense.ran.nextInt(4) + 1;
			int like = (towers * 20) + 20 - TowerDefense.ran.nextInt(40);
			//System.out.println("TOWERS STUFF SO LOOK: " + towers + "; " + like);
		}
	}
	public void addCities() {
		for (City c : World.cities) {
			int x_diff = c.x - x;
			if (x_diff < size + 1 && x_diff > (-1*(size) - 1)) {
				int y_diff = c.y - y;
				if (y_diff < (size + 1) && y_diff > (-1*(size) - 1)) {
					c.towers.add(power);
				}
			}
		}
	}
	
	public int getMoneyPT() {
		for (City c : World.cities) {
			int x_diff = c.x - x;
			if (x_diff < size + 1 && x_diff > (-1*(size) - 1)) {
				int y_diff = c.y - y;
				if (y_diff < (size + 1) && y_diff > (-1*(size) - 1)) {
					double market_share = c.getMarketShare(power);
					int pop = (int)(market_share * (0.75 * c.population));
					return (int)(pop * 0.0001);
				}
			}
		}
		return 0;
	}
	
	public void generateLand() {
		Tile tile = World.getTile(x, y);
		if (tile.equals(Tile.mountain)) {
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					int ran = TowerDefense.ran.nextInt(100);
					if (ran < 60) {
						tiles[i][j] = "^";
					} else if (ran < 95) {
						tiles[i][j] = ";";
					} else {
						tiles[i][j] = "'";
					}
				}
			}
		} else if (tile.equals(Tile.grass)) {
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					int ran = TowerDefense.ran.nextInt(100);
					if (ran < 90) {
						tiles[i][j] = "'";
					} else if (ran < 98) {
						tiles[i][j] = ";";
					} else {
						tiles[i][j] = "^";
					}
				}
			}
		} else if (tile.equals(Tile.forest)) {
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					int ran = TowerDefense.ran.nextInt(100);
					if (ran < 80) {
						tiles[i][j] = ";";
					} else if (ran < 95) {
						tiles[i][j] = "'";
					} else {
						tiles[i][j] = "^";
					}
				}
			}
		}
		
		//tower and initial defense
		towers[7][7] = 1;
	}
	
	public void tick() {
		if (selected) {
			back.tick();
			
			if (back.click) {
				TowerDefense.stateman.game.hoverable = true;
				TowerDefense.stateman.game.render_tower = false;
				selected = false;
			}
			
			if (!back.hover) {
				TowerDefense.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}
	
	public void render(Graphics g) {
		if (selected) {
			//map
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					if (tiles[i][j] == "^") g.setColor(new Color(72, 79, 81));
					else if (tiles[i][j] == ";") g.setColor(new Color(12, 130, 70));
					else g.setColor(new Color(60, 189, 89));
					g.fillRect(30 * i + 100, j * 30 + 25, 30, 30);
					
					//outline thing
					g.setColor(World.COLOURGRID);
					g.drawRect(i * 30 + 100, j * 30 + 25, 30, 30);
				}
			}
			
			//defenses
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					if (towers[i][j] != 0) TowerDefense.render.drawSprite(getTile(i, j), i * 30 + 100, j * 30 + 25, 32, 32);
				}
			}
			
			//right info panel
			g.setColor(Color.decode("#E3E3E3"));
			g.fillRect(TowerDefense.WIDTH - 200, 0, 200, TowerDefense.HEIGHT);
			g.setColor(Color.BLACK);
			g.setFont(font_20);
			g.drawString(owner + "'s Tower", TowerDefense.WIDTH - 205 + (200 - (((owner + "'s Tower").length()*20)/2))/2, 30);
			g.fillRect(TowerDefense.WIDTH - 175, 35, 150, 2);
			g.setFont(font_15);
			g.drawString("Money Per Tick: " + moneyPT, TowerDefense.WIDTH - 205 + (200 - ((("Money Per Turn: " + moneyPT).length()*15)/2))/2, 60);
			g.drawString("Health: " + health, TowerDefense.WIDTH - 205 + (200 - ((("Health: " + health).length()*15)/2))/2, 80);
			
			g.drawString("Defenses", TowerDefense.WIDTH - 205 + (200 - ((("Defenses").length()*15)/2))/2, 110);
			def_0.render(g); def_1.render(g); def_2.render(g);
			def_3.render(g); def_4.render(g); def_5.render(g);
			def_6.render(g); def_7.render(g); def_8.render(g);
			back.render(g);
		} else {
			//actual tower
			TowerDefense.render.drawSprite(Sprite.tower, xx + hexmech.r - 6 + Render.xOffset, yy - 25 + Render.yOffset);
			
			//territory
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(Color.BLUE);
			g2.setStroke(new BasicStroke(3));
			g2.drawOval(xx + hexmech.r - 44 + Render.xOffset, yy - 60 + Render.yOffset, 90, 90);
		}
	}
	
	private Sprite getTile(int i, int j) {
		if (towers[i][j] == 1) return Sprite.tower;
		else return null;
	}

}
