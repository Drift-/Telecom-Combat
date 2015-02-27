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
import city.input.Mouse;
import city.world.World;
import city.world.hexmech;
import city.world.city.City;
import city.world.tile.Tile;

public class Tower {
	
	public int x, y, xx, yy, size = 1, health = 100, moneyPT, power = 1, def_type = -1, def_x = -1, def_y = -1;
	public int[][] towers = new int[15][15];
	public String[][] tiles = new String[15][15];
	public String owner;
	
	public Color def_col;
	public ArrayList<Defense> def = new ArrayList<Defense>();
	
	private Font font_20, font_15;
	private UIImageButton def_0, def_1, def_2;
	private UIButton back, def_3, def_4, def_5, def_6, def_7, def_8;
	
	public boolean selected = false;
	
	public Tower(int x, int y, int xx, int yy, String owner) {
		this.x = x;
		this.y = y;
		this.xx = xx;
		this.yy = yy;
		this.owner = owner;
		addCities();
		back = new UIButton(TowerDefense.WIDTH - 175, TowerDefense.HEIGHT - 85, 150, 35, "Back", 20);
		def_0 = new UIImageButton(TowerDefense.WIDTH - 153, 120, 30, 30, Sprite.phone_thrower);
		def_1 = new UIImageButton(TowerDefense.WIDTH - 118, 120, 30, 30, Sprite.wifi_laser);
		def_2 = new UIImageButton(TowerDefense.WIDTH - 83, 120, 30, 30, Sprite.freezer);
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
		//reset towers
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				towers[i][j] = 0;
			}
		}
		
		tiles = new String[15][15];
		System.out.println("Reset Towers");
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
		int tower_x = TowerDefense.ran.nextInt(15);
		int tower_y = TowerDefense.ran.nextInt(15);
		towers[tower_x][tower_y] = 1;
		
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
			}
			//System.out.println();
		}
		
		//path generation
		int[][] ran = new int[5][5];
		ran[0][0] = TowerDefense.ran.nextInt(15); ran[0][1] = 0;
		ran[4][0] = tower_x; ran[4][1] = tower_y;
		for (int i = 1; i < ran.length - 1; i++) {
			int x = TowerDefense.ran.nextInt(15);
			int y = TowerDefense.ran.nextInt(15);
			while (Math.abs(x - ran[i - 1][0]) <= 3 && Math.abs(y - ran[i - 1][1]) <= 3) {
				x = TowerDefense.ran.nextInt(15);
				y = TowerDefense.ran.nextInt(15);
			}
			ran[i][0] = x;
			ran[i][1] = y;
			//System.out.println(ran[i][0] + ", " + ran[i][1]);
		}
		for (int i = 0; i < 4; i++) {
			//horiz
			String output;
			int lr = ran[i][0] - ran[i + 1][0];
			if (lr < 0) {
				lr = 1;
				output = "R";
			} else {
				lr = -1;
				output = "L";
			}
			System.out.println(owner + ": " + i);
			int dis = Math.abs(ran[i][0] - ran[i + 1][0]);
			for (int j = 0; j < dis; j++) {
				if (tiles[ran[i][0] + (j * lr)][ran[i][1]] != "^" && tiles[ran[i][0] + (j * lr)][ran[i][1]] != ";" && tiles[ran[i][0] + (j * lr)][ran[i][1]] != "'") {
					if (j != 0) {
						generateLand();
						return;
					}
				}
				tiles[ran[i][0]][ran[i][1]] = output;
				tiles[ran[i][0] + (j * lr)][ran[i][1]] = output;
				System.out.println(owner + ":  " + (ran[i][0] + (j * lr)) + ", " + ran[i][1]);
			}
			//vert
			int y_lr = ran[i][1] - ran[i + 1][1];
			if (y_lr < 0) {
				y_lr = 1;
				output = "D";
			} else {
				y_lr = -1;
				output = "U";
			}
			int y_dis = Math.abs(ran[i][1] - ran[i + 1][1]);
			for (int j = 0; j < y_dis; j++) {
				if (tiles[ran[i][0] + (dis * lr)][ran[i][1] + (j * y_lr)] != "^" && tiles[ran[i][0] + (dis * lr)][ran[i][1] + (j * y_lr)] != ";" && tiles[ran[i][0] + (dis * lr)][ran[i][1] + (j * y_lr)] != "'") {
					if (j != y_dis - 1 && j != 0) {
						generateLand();
						return;
					}
					System.out.println(j + "(" + y_dis + ")" + ": " + tiles[ran[i][0] + (dis * lr)][ran[i][1] + (j * y_lr)]);
				}
				tiles[ran[i][0] + (dis * lr)][ran[i][1]] = output;
				tiles[ran[i][0] + (dis * lr)][ran[i][1] + (j * y_lr)] = output;
			}
		}
		
	}
	
	public void tick() {
		if (selected) {
			back.tick();
			def_0.tick();
			
			if (back.click) {
				TowerDefense.stateman.game.hoverable = true;
				TowerDefense.stateman.game.render_tower = false;
				selected = false;
			}
			
			if (!back.hover) {
				TowerDefense.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
			
			if (def_0.click) {
				if (def_type == 0) def_type = -1;
				else def_type = 0;
				System.out.println(def_type);
			}
			
			if (def_type != -1) buy(def_type);
		}
	}
	
	public void buy(int type) {
		if (Mouse.getX() > 100 && Mouse.getY() > 25 && Mouse.getX() < 550 && Mouse.getY() < 475) {
			def_x = (Mouse.getX() - 100) / 30;
			def_y = (Mouse.getY() - 25) / 30;
			if (Mouse.getButton() == 1 && !isDefense(def_x, def_y)) {
				def.add(new Defense(def_x, def_y, type));
				def_x = -1;
				def_y = -1;
				def_type = -1;
			}
		} else {
			def_x = -1;
			def_y = -1;
		}
		
		if (type == 0) def_col = Color.RED;
	}
	
	public boolean isDefense(int x, int y) {
		for (Defense d : def) {
			if (d.x == x && d.y == y) return true;
		}
		return false;
	}
	
	public void render(Graphics g) {
		if (selected) {
			//map
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					if (tiles[i][j] == "^") g.setColor(new Color(72, 79, 81));
					else if (tiles[i][j] == ";") g.setColor(new Color(12, 130, 70));
					else if (tiles[i][j] == "'") g.setColor(new Color(60, 189, 89));
					else if (tiles[i][j] == "L") g.setColor(Color.decode("#7A693B"));
					else if (tiles[i][j] == "R") g.setColor(Color.decode("#7A693B"));
					else if (tiles[i][j] == "U") g.setColor(Color.decode("#7A693B"));
					else g.setColor(Color.decode("#7A693B"));
					g.fillRect(30 * i + 100, j * 30 + 25, 30, 30);
					
					//outline thing
					g.setColor(World.COLOURGRID);
					g.drawRect(i * 30 + 100, j * 30 + 25, 30, 30);
				}
			}
			
			//defenses
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					if (towers[i][j] != 0) TowerDefense.render.drawSprite(getTile(i, j), i * 30 + 100, j * 30 + 20, 32, 32);
					for (Defense d: def) {
						d.render(g);
					}
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
			
			//defense position interface
			g.setColor(def_col);
			if (def_x != -1) g.fillRect(def_x * 30 + 105, def_y * 30 + 30, 20, 20);
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
