package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {

	private Timer timer;
	
	Character character;
	Enemy enemy;
	int velx = 0, vely = 0;
	Map map;
	ArrayList<Coin> coins;
	public GamePanel() {

		addKeyListener(new InputHandler());
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);

		coins = new ArrayList<>();
		coins.add(new Coin(5,20));
		map = new Map();
		enemy = new Enemy(5,18,3);
		character = new Character();
		timer = new Timer(5, this);
		timer.start();
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D graphics2d = (Graphics2D) g;

		map.drawMap(graphics2d);
		
		for(Coin coin : coins)
		{
			coin.drawCoin(graphics2d);
		}
		
		enemy.drawEnemy(graphics2d);
		graphics2d.drawImage(character.currentImage, character.rectangle.x,
				character.rectangle.y, this);
		
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void actionPerformed(ActionEvent e) {
		/*if(character.isJumping == true && character.landed == false)
		{
			vely = -8;
		}*/
		vely = 2;
		character.rectangle.x += velx;
		for(int i = 0; i < map.tiles.size();i++)
		{
			if(map.tiles.get(i).collidable == true)
			{
				if(character.rectangle.intersects(map.tiles.get(i).tileRectangle))
				{
					character.rectangle.x -= velx;
				}
			}
		}
		character.rectangle.y += vely;
		for(int i = 0; i < map.tiles.size();i++)
		{
			if(map.tiles.get(i).collidable == true)
			{
				if(character.rectangle.intersects(map.tiles.get(i).tileRectangle))
				{
					if(character.isJumping == true)
						character.isJumping = false;
					character.rectangle.y -= vely;
				}
			}
		}
		//animation
		for (int i = 0; i < coins.size(); i++) {
			if(character.rectangle.intersects(coins.get(i).rectangle))
			{
				coins.remove(coins.get(i));
			}
		}
		
		for(Coin coin : coins)
		{
			coin.update();
		}
		enemy.update();
		character.update();
		repaint();
	}

	private class InputHandler extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();

			if (key == KeyEvent.VK_LEFT) {
				character.idleRight = false;
				character.idleLeft = false;
				
				character.walkingLeft = true;
				character.walkingRight = false;
				
				velx = -1;
				//character.jumpingLeft = true;
				//character.jumpingRight = true;
			}

			if (key == KeyEvent.VK_RIGHT) {
				character.idleRight = false;
				character.idleLeft = false;
				
				character.walkingLeft = false;
				character.walkingRight = true;
				
				velx = 1;
				//character.jumpingRight = true;
				//character.jumpingLeft = true;
			}

			if (key == KeyEvent.VK_UP) {
				if (character.isJumping == false) {
					character.isJumping = true;
					//character.landed = false;
					character.rectangle.y -= character.rectangle.height * 2;
				} 
			}
		}

		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();

			if (key == KeyEvent.VK_LEFT) {
				character.idleRight = false;
				character.idleLeft = true;
				
				character.walkingLeft = false;
				character.jumpingLeft = false;
				
				velx = 0;
			} 
			else if (key == KeyEvent.VK_RIGHT) {
				character.idleRight = true;
				character.idleLeft = false;
				
				character.walkingRight = false;
				character.jumpingRight = false;
				
				velx = 0;
			}
			if (key == KeyEvent.VK_UP) {
				vely = 0;
			}
		}
	}

}