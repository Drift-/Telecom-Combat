package city.graphics.uiitems;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;

import city.TowerDefense;
import city.graphics.Render;
import city.graphics.fonts.CustomFont;
import city.input.Mouse;

public class UIButton {
	
	public int x, y, width, height, ts, textx, texty;
	Color col, tec;
	String text;
	
	CustomFont cus_font;
	Font font;
	
	public boolean click, hover;
	boolean wasClicked;
	
	public UIButton(int x, int y, int width, int height, String text, int ts) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		col = Color.decode("#444B4D");
		tec = Color.white;
		this.ts = ts;
		click = false;
		wasClicked = false;
		
		cus_font = new CustomFont();
		font = cus_font.font(ts);
		
		textx = x + (width - Render.getWidth(text, ts)) / 2;
		texty = y + (height + Render.getHeight(text, ts)) / 2;
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
		
		//text part
		g.setFont(font);
		g.setColor(tec);
		g.drawString(text, textx, texty);
	}

}
