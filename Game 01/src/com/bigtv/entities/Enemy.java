package com.bigtv.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.bigtv.main.Game;
import com.bigtv.world.Camera;
import com.bigtv.world.World;



public class Enemy  extends Entity{
	Random rand = new Random();
	
	private double enemySpeed = 1;
	
	private int maskx = 8,masky = 8, maskw = 10, maskh = 10;
	
	private int frames = 0, maxFrames = 15, index = 0, maxIndex = 1;
	
	private BufferedImage[] sprites;

	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		sprites = new BufferedImage[2];
		
		sprites[0] = Game.spritesheet.getSprite(112,16, 16, 16);
		sprites[1] = Game.spritesheet.getSprite(112+16,16, 16, 16);
	}
	
	public void tick() {
		//maskx = 5;
		//masky = 5;
		//maskw = 5;
		//maskh = 5;
		if(this.isCollidingWithPlayer() == false) {
			if(x < Game.player.getX() && World.isFree((int) (x+enemySpeed), this.getY())
					&& World.isFreeTree((int) (x+enemySpeed), this.getY())
					&&  !isColliding((int) (x+enemySpeed), this.getY()))	{
				// && World.isFreeTree(this.getX(), (int) (y-speed))
			x+=enemySpeed;
			
			}if(x > Game.player.getX() && World.isFree((int) (x-enemySpeed), this.getY())
					&& World.isFreeTree((int) (x-enemySpeed), this.getY())
					&& !isColliding((int) (x-enemySpeed), this.getY())) {
			x-=enemySpeed;
			
			} if(y < Game.player.getY() && World.isFree(this.getX(), (int) (y+enemySpeed))
					&& World.isFreeTree(this.getX(), (int) (y-enemySpeed))
					&& !isColliding(this.getX(), (int) (y+enemySpeed))) {
			y+=enemySpeed;
			
			}if(y > Game.player.getY() && World.isFree(this.getX(), (int) (y-enemySpeed))
					&& World.isFreeTree(this.getX(), (int) (y-enemySpeed))
					&& !isColliding(this.getX(), (int) (y-enemySpeed))) {
			y-=enemySpeed;
			
		}
	}else {
		
		//colis�o do player co o inimigo // eu n�o preciso comentar em ingles
		if(rand.nextInt(100) < 50) {
			Game.player.life--;
			Game.player.life--;
			Game.player.life--;
				
			
		}
		if(Game.player.life <= 0) {
			System.exit(1);
			
		}
		System.out.println("vida "+ Game.player.life);
	}
			
				frames++;
				if(frames == maxFrames) {
					frames = 0;
					index++;
					if(index > maxIndex) {
						index = 0;
					}
				}
			
		
	}
	
	public boolean isCollidingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx,this.getY() + masky,maskw,maskh);
		Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), 16, 16);
		return enemyCurrent.intersects(player);
	}
		
	public boolean isColliding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskx,ynext + masky,maskw,maskh);
		for(int i = 0; i < Game.enemies.size();i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this) 
				continue;
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx,e.getY() + masky,maskw,maskh);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
			
			
		}
		return false;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		//super.render(g);
		//g.setColor(Color.black);
		//g.fillRect(this.getX() + maskx - Camera.x , this.getY() + masky - Camera.y, maskw, maskh);
	}

}
