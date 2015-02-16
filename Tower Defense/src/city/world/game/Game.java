package city.world.game;

import java.util.ArrayList;

import city.TowerDefense;
import city.world.World;
import city.world.hexmech;

public class Game {
	
	public static int playersamount = 3, tick = 10*40 - 1;
	
	public static ArrayList<Tower> towers = new ArrayList<Tower>();
	
	public static void SpawnPlayers() {
		ArrayList<Integer[]> spawns = new ArrayList<Integer[]>();
		spawns = World.getSpawns();
		
		for (int i = 0; i < playersamount; i++) {
			boolean continueon = false;
			while (!continueon) {
				continueon = true;
				int ran = TowerDefense.ran.nextInt(spawns.size());
				for (Tower t : towers) {
					if (t.x == spawns.get(ran)[0] && t.y == spawns.get(ran)[1]) {
						continueon = false;
					} 
				}
				if (continueon) {
					int[] xy = hexmech.getCityLocation(spawns.get(ran)[0], spawns.get(ran)[1]);
					int xx = xy[0];
					int yy = xy[1];
					towers.add(new Tower(spawns.get(ran)[0], spawns.get(ran)[1], xx, yy, "" + i));
				}
			}
		}
	}
	
	public static void tick() {
		tick++;
		if (tick == (10*40)) {
			gametick();
			tick = 0;
		}
	}
	
	public static void gametick() {
		for (Tower t : towers) {
			t.moneyPT = t.getMoneyPT();
		}
	}

}
