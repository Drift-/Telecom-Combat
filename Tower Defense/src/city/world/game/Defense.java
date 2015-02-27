package city.world.game;

import java.awt.Color;
import java.awt.Graphics;

public class Defense {
	
	public int x, y, type;
	
	public Defense(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		if (type == 0) {
			g.setColor(Color.RED);
			g.fillRect(30 * x + 105, 30 * y + 30, 20, 20);
		} else if (type == 1) {
			
		} else if (type == 2) {
			
		}
	}

}
