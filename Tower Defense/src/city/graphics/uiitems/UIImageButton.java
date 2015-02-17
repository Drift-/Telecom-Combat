package city.graphics.uiitems;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;

import city.TowerDefense;
import city.graphics.Sprite;
import city.input.Mouse;

public class UIImageButton {
	
	public int x, y, width, height, ts, textx, texty;
	private Color col;
	private Image img;
	
	public boolean click, hover;
	private boolean wasClicked;
	
	public UIImageButton(int x, int y, int width, int height, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.img = sprite.img;
		col = Color.decode("#444B4D");
		click = false;
		wasClicked = false;
	}
	
	public void tick() {
		//text position on resize
		//textx = x + (width - Render.getWidth(text, ts)) / 2;
		//texty = y + (height + Render.getHeight(text, ts)) / 2;
		
		//hover
		if (Mouse.getX() >= x && Mouse.getX() <= x + width && Mouse.getY() >= y && Mouse.getY() <= y + height) {
			TowerDefense.frame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			hover = true;
		} else {
			hover = false;
		}
		
		click = false;
		
		if (wasClicked && Mouse.getButton() != 1) {
			 if (Mouse.getX() >= x && Mouse.getX() <= x + width && Mouse.getY() >= y && Mouse.getY() <= y + height) {
				 click = true;
			 } else {
			 }
		}
		
		wasClicked = false;
		
		if (Mouse.getButton() == 1 && Mouse.getX() >= x && Mouse.getX() <= x + width && Mouse.getY() >= y && Mouse.getY() <= y + height) {
			wasClicked = true;
		}
	}
	
	public void render(Graphics g) {
		//box part
		g.setColor(col);
		g.fillRect(x, y, width, height);
		
		//image part
		g.drawImage(img, x, y, width, height, null);
	}

}
