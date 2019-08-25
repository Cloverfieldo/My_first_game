package com.bigtv.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.bigtv.entities.*;

import com.bigtv.main.Game;


public class World{
	
	public static int HEIGHT,WIDTH;
	
	public static Tile[] tiles;
	public static int TILE_SIZE = 16;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH= map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					
					
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
					if(pixelAtual == 0xFF000000) {
						//Floor
					}else if(pixelAtual == 0xFFFFFFFF) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.TILE_WALL);
						//Wall
					}else if(pixelAtual == 0xFF4800FF) {
						Game.player = new Player(xx * 16,yy * 16,16,16,Game.spritesheet.getSprite(32, 0, 16, 16));
						Game.entities.add(Game.player);
						
						
						//Player
					}else if(pixelAtual == 0xFFFF006E){
						Game.entities.add(new Lifepack(xx*16,yy*16,16,16,Entity.LIFEPACK_EN));
						//Life pack
					}else if(pixelAtual == 0xFFFF0000) {
						Enemy en = new Enemy(xx*16,yy*16,16,16,Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
						
						//Enemy
					}else if(pixelAtual == 0xFFFFD800) {
						Game.entities.add(new Bullet(xx*16,yy*16,16,16,Entity.BULLET_EN));
						//Bullet
					}else if(pixelAtual == 0xFF00FF21) {
						Game.entities.add(new Weapon(xx*16,yy*16,16,16,Entity.WEAPON_EN));
						
						//Weapon
					}else if(pixelAtual == 0xFF404018){
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
						tiles[xx + (yy * WIDTH)] = new Tree(xx*16,yy*16,Tile.TREE);
						//404018
						//Tree
					}
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
public static boolean isFree(int xnext,int ynext){
		
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
	}


public static boolean isFreeTree(int xnext,int ynext){
	
	int x1 = xnext / TILE_SIZE;
	int y1 = ynext / TILE_SIZE;
	
	int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
	int y2 = ynext / TILE_SIZE;
	
	int x3 = xnext / TILE_SIZE;
	int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
	
	int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
	int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
	
	return !((tiles[x1 + (y1*World.WIDTH)] instanceof Tree) ||
			(tiles[x2 + (y2*World.WIDTH)] instanceof Tree) ||
			(tiles[x3 + (y3*World.WIDTH)] instanceof Tree) ||
			(tiles[x4 + (y4*World.WIDTH)] instanceof Tree));
}


	
	
	
	
	public void render(Graphics g){
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
}
