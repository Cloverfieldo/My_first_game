package com.bigtv.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.bigtv.entities.Enemy;
import com.bigtv.entities.Entity;
import com.bigtv.entities.Player;
import com.bigtv.graficos.Spritesheet;
import com.bigtv.graficos.UI;
import com.bigtv.world.*;





public class Game extends Canvas implements Runnable,KeyListener{
	
	
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	
	public static final int WIDTH= 240;
	public static final int HEIGHT = 160;
	public  static final int SCALE = 3;
	
	public static BufferedImage image;
	
	public static List<Entity> entities;
	public static List<Enemy> enemies;
	
	public static Spritesheet spritesheet;
	
	public static Player player;

	public World world;
	
	public static Random rand;
		
	public UI ui;
	
	public Game() {
		rand = new Random();
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		//Inicializando objetos
		ui = new UI();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>(); 
		enemies = new ArrayList<Enemy>();
		spritesheet = new Spritesheet("/spritesheet.png");
		player = new Player(0,0,0,0,spritesheet.getSprite(32, 0, 16, 16));
		world = new World("/map.png");
		
		
	}
	
	public void initFrame() {
		/**Janela**/
		frame = new JFrame("Game 001");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		/***/
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
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
	
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
		
	}

	public void tick() {
		
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}
		
	}
	
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0,WIDTH,HEIGHT);
		
		
		
		
		/**Renderiza��o do jogo**/
		
		
		//Graphics2D g2 = (Graphics2D) g;
		world.render(g);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
			ui.render(g);
		}

		/***/
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		//Desenhar na tela sem pixels
		g.setFont(new Font("arial", Font.BOLD, 20));
		g.setColor(Color.orange);
		g.drawString("Munição : " + player.ammo, 590, 30);
		bs.show();
		//
		
		
	}
	
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while(isRunning) {
			long now = System.nanoTime();
			delta +=(now - lastTime) / ns;
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
				
				
			}
			
			if(System.currentTimeMillis() - timer >= 100) {
				System.out.println("FPS" + frames);
				frames = 0;
				timer+=1000;
			}
		}stop();

		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_RIGHT || arg0.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		
	}
		
		if(arg0.getKeyCode() == KeyEvent.VK_LEFT || arg0.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
			
		}
		
		if(arg0.getKeyCode() == KeyEvent.VK_UP || arg0.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
			
		}
		if(arg0.getKeyCode() == KeyEvent.VK_DOWN || arg0.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_RIGHT || arg0.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		
	}
		
		if(arg0.getKeyCode() == KeyEvent.VK_LEFT || arg0.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
			
		}
		
		if(arg0.getKeyCode() == KeyEvent.VK_UP || arg0.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
			
		}
		if(arg0.getKeyCode() == KeyEvent.VK_DOWN || arg0.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
			
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}


}