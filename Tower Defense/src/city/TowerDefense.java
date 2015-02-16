package city;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

import javax.swing.JFrame;

import city.graphics.Render;
import city.input.Keyboard;
import city.input.Mouse;
import city.state.StateManager;
import city.world.tile.Tile;

public class TowerDefense extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;
	public static int WIDTH = 940;
	public static int HEIGHT = 530;
	
	public static File res;
	
	public static Thread thread;
	public static boolean isRunning = false;
	public static JFrame frame;
	public static BufferStrategy bs;
	public static Graphics g;
	public static Render render;
	public static StateManager stateman;
	
	public static Keyboard key = new Keyboard();
	
	public int lastTicks = 0;
	
	private BufferedImage img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	public static Random ran = new Random();
	
	public static void main(String[] args) {
		TowerDefense game = new TowerDefense();
		frame = new JFrame();
		
		frame.add(game);
		frame.pack();
		frame.setResizable(false);
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setFocusable(true);
		frame.setTitle("Telecom Combat");
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
	}
	
	public synchronized void start() {
		isRunning = true;
		thread = new Thread(this, "Game");
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public TowerDefense() {
		//initialize directory
		File file = new File("dir");
		res = new File(file.getAbsolutePath().toString().split("\\\\dir")[0] + "\\" + "res");
			
		//init key listener
		key = new Keyboard();
		addKeyListener(key);
		
		//init mouse listener
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
		//World world = new World();
		//world.load("/worlds/Road.txt");
		stateman = new StateManager();
		//World.generate();
	}
	
	public void run() {
		Thread renderthread = new Thread("render") {
			public void run() {
				long startTime = System.currentTimeMillis();
				int frames = 0;
				int lastFPS = 0;
				
				while(true) {
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					render(lastFPS);
					
					frames++;
					
					long time = System.currentTimeMillis();
					if (time >= startTime + 1000) {
						startTime = System.currentTimeMillis();
						lastFPS = frames;
						frames = 0;
					}
				}
			}
		};
		renderthread.start();
		
		long startTime = System.currentTimeMillis();
		int ticks = 0;
		long lastTime = System.nanoTime();
		double ns = 1000000000.0 / 60;
		double delta = 0;
		
		while(true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				delta--;
				
				tick();
				
				ticks++;
				
				long time = System.currentTimeMillis();
				if (time >= startTime + 1000) {
					startTime = System.currentTimeMillis();
					lastTicks = ticks;
					System.out.println(ticks);
					ticks = 0;
				}
			}
		}
	}
	
	public void tick() {
		key.tick();
		stateman.tick();
		
		//if (!frame.isActive()) {
			//System.out.println("CLOSED!");
		//}
	}
	
	public void render(int fps) {
		bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			bs = getBufferStrategy();
		}
		g = bs.getDrawGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH + 15, HEIGHT + 15);
		g.drawImage(img, 0, 0, WIDTH + 15, HEIGHT + 15, null);
		
		render = new Render(g);
		Tile.render = render;
		
		stateman.render(g);
		
		g.setColor(Color.WHITE);
		g.drawString("FPS: " + fps, 10, 20);
		
		g.drawString("TICKS: " + lastTicks, 10, 50);
		
		g.dispose();
		bs.show();
	}

}
