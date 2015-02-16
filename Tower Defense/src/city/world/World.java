package city.world;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import city.TowerDefense;
import city.graphics.Render;
import city.world.city.City;
import city.world.game.Game;
import city.world.game.Tower;
import city.world.tile.Tile;

public class World {
	
	private static String path;
	
	private static Random ran = new Random();
	
	public static Color COLOURGRID = new Color(230, 230, 169);
	
	public static String[][] tiles = new String[5][5];
	public static ArrayList<City> cities = new ArrayList<City>();
	
	public static void generate() {
		hexmech.setXYasVertex(false); //RECOMMENDED: leave this as FALSE.
		 
		hexmech.setHeight(60); //Either setHeight or setSize must be run to initialize the hex
		hexmech.setBorders(50);
		
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				//tiles[i][j] = ran.nextInt(3);
			}
		}
	}
	
	public void load(String path) {
		hexmech.setXYasVertex(false); //RECOMMENDED: leave this as FALSE.
		 
		hexmech.setHeight(30); //Either setHeight or setSize must be run to initialize the hex
		hexmech.setBorders(50);
		
		this.path = path;
		
		File f = new File(TowerDefense.res + path + "\\terrain.txt");
		InputStream fis = null;
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		
		getFileLength(TowerDefense.res + path + "\\terrain.txt");
		 
		String line = null;
		int num = 0;
		try {
			while ((line = br.readLine()) != null) {
				for (int i = 0; i < line.length(); i++) {
					tiles[i][num] = Character.toString(line.charAt(i));//Integer.parseInt(line.split("|")[i]);
				}
				num++;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		loadCities(TowerDefense.res + path);
	}
	
	public void getFileLength(String path) {
		File f = new File(path);
		InputStream fis = null;
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		BufferedReader counter = new BufferedReader(new InputStreamReader(fis));
		 
		String line = null;
		int num = 0;
		try {
			int width2 = 0;
			while ((line = counter.readLine()) != null) {
				width2 = line.length();
				num++;
			}
			
			tiles = new String[width2][num];
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadCities(String path) {
		File f = new File(path + "\\cities.txt");
		InputStream fis = null;
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		BufferedReader counter = new BufferedReader(new InputStreamReader(fis));
		 
		String line = null;
		try {
			while ((line = counter.readLine()) != null) {
				String type = line.split(":")[0];
				int x = Integer.parseInt(line.split("\\(")[1].split(",")[0]);
				int y = Integer.parseInt(line.split(", ")[1].split("\\)")[0]);
				int[] xy = hexmech.getCityLocation(x, y);
				int xx = xy[0];
				int yy = xy[1];
				String name = line.split("\\) ")[1];
				cities.add(new City(type, name, x, y, xx, yy, Render.getWidth(name, 25)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String[] getNameInfo(String path) {
		File f = new File(path + "\\info.txt");
		InputStream fis = null;
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		BufferedReader counter = new BufferedReader(new InputStreamReader(fis));
		 
		String line = null;
		String[] ret = new String[2];
		try {
			for (int i = 0; i < 2; i++) {
				line = counter.readLine();
				if (i == 0) {
					ret[0] = line.substring(6);
				} else if (i == 1) {
					ret[1] = line.substring(13);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public static ArrayList<Integer[]> getSpawns() {
		File f = new File(TowerDefense.res + path + "\\info.txt");
		InputStream fis = null;
		try {
			fis = new FileInputStream(f);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		
		ArrayList<Integer[]> output = new ArrayList<Integer[]>();
		
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				if (line.contains("Spawn: ")) {
					Integer[] out = new Integer[2];
					out[0] = Integer.parseInt(line.split("\\(")[1].split(",")[0]);
					out[1] = Integer.parseInt(line.split(", ")[1].split("\\)")[0]);
					output.add(out);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return output;
	}
	
	public static void render() {
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				hexmech.drawHex(i, j, Render.g);
			}
			/*int x = (i % width) * 16;
			int y = (i / width) * 16;
			if (x + 50 + TowerDefense.render.xOffset > 0) {
				if (x - 50 + TowerDefense.render.yOffset < TowerDefense.WIDTH) {
					if (y + 50  + TowerDefense.render.xOffset> 0) {
						if (y - 50 + TowerDefense.render.yOffset < TowerDefense.HEIGHT) {
						} else {
							i = tiles.length - 1;
						}
					}
				} else {
					while ((i % width) != width - 1) {
						i++;
					}
				}
			}*/
		}
		
		if (!TowerDefense.stateman.game.render_tower) {
			for (City c : cities) {
				c.render(Render.g);
			}
			
			for (Tower t : Game.towers) {
				t.render(Render.g);
			}
		}
	}
	
	public static Tile getTile(int i, int j) {
		String t = tiles[i][j];
		if (t.equals("'")) return Tile.grass;
		else if (t.equals(";")) return Tile.forest;
		else if (t.equals("~")) return Tile.water;
		else if (t.equals("^")) return Tile.mountain;
		//else if (i == 5) return Tile.town;
		//else if (i == 6) return Tile.city;
		//else if (i == 7) return Tile.largecity;
		else return Tile.water;
	}

}
