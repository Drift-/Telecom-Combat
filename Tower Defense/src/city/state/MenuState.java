package city.state;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

import city.TowerDefense;
import city.graphics.fonts.CustomFont;
import city.graphics.uiitems.UIButton;

public class MenuState {
	
	Image menu = new ImageIcon((TowerDefense.res + "\\" + "textures\\states\\menu\\menu.png")).getImage();
	
	CustomFont f = new CustomFont();
	Font font;
	UIButton play, exit;
	
	public MenuState() {
		//initialize buttons
		play = new UIButton(TowerDefense.WIDTH/2 - 75, TowerDefense.HEIGHT/20 * 3 + menu.getHeight(null) * 4, 150, 35, "Play", 20);
		exit = new UIButton(TowerDefense.WIDTH/2 - 75, TowerDefense.HEIGHT/20 * 3 + menu.getHeight(null) * 4 + 70, 150, 35, "Exit", 20);
	}
	
	public void tick() {
		play.tick();
		exit.tick();
		
		//custom font
		font = f.font(35);
		
		//button click events
		if (play.click) {
			//launch "GameState"
			StateManager.game = new GameState();
			StateManager.state = 1;
			//change cursor back to normal
			TowerDefense.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		} else if (exit.click) {
			System.exit(0);
		}
		
		//hover cursor changing
		if (!play.hover && !exit.hover) {
			TowerDefense.frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	public void render(Graphics g) {
		//background
		g.setColor(Color.decode("#E3E3E3"));
		g.fillRect(0, 0, TowerDefense.WIDTH, TowerDefense.HEIGHT);
		
		//logo
		g.drawImage(menu, TowerDefense.WIDTH/2 - menu.getWidth(null)*2, TowerDefense.WIDTH/20, menu.getWidth(null) * 4, menu.getHeight(null) * 4, null);
		
		//buttons
			play.render(g);
			exit.render(g);
			g.setColor(Color.BLACK);
			g.drawString("Loading...", TowerDefense.WIDTH/2 - (10*35)/4, 300);
	}

}
