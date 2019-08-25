package com.bigtv.graficos;

import java.awt.Color;
import java.awt.Graphics;

import com.bigtv.entities.Player;
import com.bigtv.main.Game;




public class UI {
	
	
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(8,4,70,8);
		g.setColor(Color.green);
		g.fillRect(8,4,(int)((Game.player.life/Game.player.playerMaxLife)*70),8);
		g.setColor(Color.WHITE);
		g.drawString((int)Player.life + "/" + (int)Player.playerMaxLife, 25, 25);
	}

}
