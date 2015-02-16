package city.world;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

import city.graphics.Render;

public class hexmech {
	public static boolean XYVertex=true;
	
	private static int BORDERS=50;	//default number of pixels for the border.
 
	private static int s=0;	// length of one side
	private static int t=0;	// short side of 30o triangle outside of each hex
	public static int r=0;	// radius of inscribed circle (centre to middle of each side). r= h/2
	private static int h=0;	// height. Distance between centres of two adjacent hexes. Distance between two opposite sides in a hex.
 
	public static void setXYasVertex(boolean b) {
		XYVertex=b;
	}
	public static void setBorders(int b){
		BORDERS=b;
	}
 
	public static void setSide(int side) {
		s=side;
		t =  (int) (s / 2);			//t = s sin(30) = (int) CalculateH(s);
		r =  (int) (s * 0.8660254037844);	//r = s cos(30) = (int) CalculateR(s); 
		h=2*r;
	}
	
	public static void setHeight(int height) {
		h = height;			// h = basic dimension: height (distance between two adj centresr aka size)
		r = h/2;			// r = radius of inscribed circle
		s = (int) (h / 1.73205);	// s = (h/2)/cos(30)= (h/2) / (sqrt(3)/2) = h / sqrt(3)
		t = (int) (r / 1.73205);	// t = (h/2) tan30 = (h/2) 1/sqrt(3) = h / (2 sqrt(3)) = r / sqrt(3)
	}
 
	public static Polygon hex (int x0, int y0) {
 
		int y = y0 + BORDERS;
		int x = x0 + BORDERS; // + (XYVertex ? t : 0); //Fix added for XYVertex = true. 
				      // NO! Done below in cx= section
		if (s == 0  || h == 0) {
			System.out.println("ERROR: size of hex has not been set");
			return new Polygon();
		}
 
		int[] cx,cy;
 
		if (XYVertex) 
			cx = new int[] {x,x+s,x+s+t,x+s,x,x-t};  //this is for the top left vertex being at x,y. Which means that some of the hex is cutoff.
		else
			cx = new int[] {x+t,x+s+t,x+s+t+t,x+s+t,x+t,x};	//this is for the whole hexagon to be below and to the right of this point
 
		cy = new int[] {y,y,y+r,y+r+r,y+r+r,y+r};
		return new Polygon(cx,cy,6);
 
	}
	
	public static int[] getCityLocation(int i, int j) {
		int[] test = new int[2];
		test[0] = i * (s+t) + Render.xOffset + BORDERS;
		test[1] = (j + 1) * h + (i%2) * h/2 + Render.yOffset + BORDERS;
		return test;
	}
 
	public static void drawHex(int i, int j, Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		int x = i * (s+t) + Render.xOffset;
		int y = j * h + (i%2) * h/2 + Render.yOffset;
		Polygon poly = hex(x,y);
		g2.setColor(World.getTile(i, j).getColor());
		//g2.fillPolygon(hexmech.hex(x,y));
		g2.fillPolygon(poly);
		g2.setColor(World.COLOURGRID);
		g2.setStroke(new BasicStroke(1));
		g2.drawPolygon(poly);
	}
 
	public static void fillHex(int i, int j, int n, Graphics2D g2) {
		char c='o';
		int x = i * (s+t);
		int y = j * h + (i%2) * h/2;
	}
 
	//This function changes pixel location from a mouse click to a hex grid location
	public static Point pxtoHex(int mx, int my) {
		Point p = new Point(-1,-1);
 
		//correction for BORDERS and XYVertex
		mx += -BORDERS - Render.xOffset;
		my += -BORDERS - Render.yOffset;
		if (XYVertex) mx += t;
 
		int x = (int) ((mx) / (s+t)); //this gives a quick value for x. It works only on odd cols and doesn't handle the triangle sections. It assumes that the hexagon is a rectangle with width s+t (=1.5*s).
		int y = (int) (((my) - (x%2)*r)/h); //this gives the row easily. It needs to be offset by h/2 (=r)if it is in an even column
 
		/******FIX for clicking in the triangle spaces (on the left side only)*******/
		//dx,dy are the number of pixels from the hex boundary. (ie. relative to the hex clicked in)
		int dx = mx - x*(s+t);
		int dy = my - y*h;
 
		if (my - (x%2)*r < 0) return p; // prevent clicking in the open halfhexes at the top of the screen
 
		//System.out.println("dx=" + dx + " dy=" + dy + "  > " + dx*r/t + " <");
		
		//even columns
		if (x%2==0) {
			if (dy > r) {	//bottom half of hexes
				if (dx * r /t < dy - r) {
					x--;
				}
			}
			if (dy < r) {	//top half of hexes
				if ((t - dx)*r/t > dy ) {
					x--;
					y--;
				}
			}
		} else {  // odd columns
			if (dy > h) {	//bottom half of hexes
				if (dx * r/t < dy - h) {
					x--;
					y++;
				}
			}
			if (dy < h) {	//top half of hexes
				//System.out.println("" + (t- dx)*r/t +  " " + (dy - r));
				if ((t - dx)*r/t > dy - r) {
					x--;
				}
			}
		}
		p.x=x;
		p.y=y;
		return p;
	}
}