package com.bigtv.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.bigtv.main.Game;
import com.bigtv.world.Camera;
import com.bigtv.world.World;



public class Player extends Entity{
	
	public boolean right,up,left,down;
	public int rightDir = 0, leftDir = 1;;
	public int dir = rightDir;
	public static int speed = (int) 2;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	
	public static double life = 100, playerMaxLife = 100;
	public int ammo = 0;

	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		for(int i= 0; i < 4; i++ ) {
			rightPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 0, 16, 16);
		}
		for(int i= 0; i < 4; i++ ) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 16, 16, 16);
		}
		
		
	}
	
	public void tick() {
		//Movimento
		moved = false;
		if(right && World.isFree((int)(x+speed),this.getY()) && World.isFreeTree((int)(x+speed),this.getY())) {
			moved = true;
			dir = rightDir;
			x+=speed;
		}
		else if(left && World.isFree((int)(x-speed),this.getY()) && World.isFreeTree((int)(x-speed),this.getY())) {
			moved = true;
			dir = leftDir;
			x-=speed;
		}
		if(up && World.isFree(this.getX(),(int)(y-speed)) && World.isFreeTree(this.getX(), (int) (y-speed))) {
			moved = true;
			y-=speed;
		}
		else if(down && World.isFree(this.getX(),(int)(y+speed)) && World.isFreeTree(this.getX(), (int) (y+speed))){
			moved = true;
			y+=speed;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		}
		//Movimento
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*16 - Game.HEIGHT);
		
		checkItemLifepack();	
		checkItemAmmo();
		}
		
	public void checkItemAmmo() {
		for(int i = 0; i < Game.entities.size(); i++){
			Entity atual = Game.entities.get(i);
			if(atual instanceof Bullet) {
				if(Entity.isColidding(this, atual)) {
					ammo++;
					System.out.println("Muniçaõ " + ammo);
					Game.entities.remove(i);
					return;
				}
			}
			
		}
	}
	
	public void checkItemLifepack() {
		for(int i = 0; i < Game.entities.size(); i++){
			Entity atual = Game.entities.get(i);
			if(atual instanceof Lifepack) {
				if(Entity.isColidding(this, atual)) {
					life+=10;
					if(life >= 100) {
						life = 100;
					}
					Game.entities.remove(i);
					return;
				}
			}
			
		}
	 	
	}
	
	
	
	public void render(Graphics g) {
		if(dir == rightDir) {
			g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() -  Camera.y, null);
		}else if(dir == leftDir) {
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
	}
}