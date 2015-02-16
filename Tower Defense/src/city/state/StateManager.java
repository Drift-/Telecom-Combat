package city.state;

import java.awt.Graphics;

public class StateManager {
	
	public static int state = 0;
	
	//states
	public static MenuState menu;
	public static GameState game;
	
	public StateManager() {
		menu = new MenuState();
	}
	
	public void tick() {
		if (state == 0) menu.tick();
		else if (state == 1) game.tick();
	}
	
	public void render(Graphics g) {
		if (state == 0) menu.render(g);
		else if (state == 1) game.render(g);
	}

}
