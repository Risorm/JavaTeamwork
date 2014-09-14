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

import javax.swing.JPanel;
import javax.swing.Timer;

import jdk.internal.org.objectweb.asm.tree.JumpInsnNode;

import com.sun.xml.internal.bind.v2.util.CollisionCheckStack;

public class GamePanel extends JPanel implements ActionListener {

	private Timer timer;
	
	Character character;
	Enemy enemy;
	int velx = 0, vely = 0;
	Map map;

	public GamePanel() {

		addKeyListener(new InputHandler());
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);

		map = new Map();
		enemy = new Enemy(5,20,5);
		character = new Character();
		timer = new Timer(5, this);
		timer.start();
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;

		map.drawMap(g2d);
		g2d.drawImage(character.currentImage, character.rectangle.x,
				character.rectangle.y, this);
		g2d.drawImage(enemy.currentImage, enemy.rectangle.x,
				enemy.rectangle.y, this);

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void actionPerformed(ActionEvent e) {
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
		character.rectangle.y += 2;
		for(int i = 0; i < map.tiles.size();i++)
		{
			if(map.tiles.get(i).collidable == true)
			{
				if(character.rectangle.intersects(map.tiles.get(i).tileRectangle))
				{
					if(character.isJumping == true)
						character.isJumping = false;
					character.rectangle.y -=2 ;
				}
			}
		}
		/*if(character.rectangle.x + character.rectangle.width >= 320)
		{
			character.rectangle.x = 320 - character.rectangle.width;
			map.updateMap(velx);
		}*/
		
		character.update();
		repaint();
	}

	private class InputHandler extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();

			if (key == KeyEvent.VK_LEFT) {
				velx = -1;
				character.walkingLeft = true;
				character.walkingRight = false;
				character.idleRight = false;
				character.idleLeft = false;
				//character.jumpingLeft = true;
				//character.jumpingRight = true;
			}

			if (key == KeyEvent.VK_RIGHT) {
				character.idleRight = false;
				character.idleLeft = false;
				velx = 1;
				character.walkingLeft = false;
				character.walkingRight = true;
				//character.jumpingRight = true;
				//character.jumpingLeft = true;
			}

			if (key == KeyEvent.VK_UP) {
				if (character.isJumping == false) {
					character.isJumping = true;
					//character.rectangle.y -= character.rectangle.height * 2;
				} else if (character.isJumping == true) {
					character.jumpingLeft = false;
					character.jumpingRight = true;
				}
			}
		}

		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();

			if (key == KeyEvent.VK_LEFT) {
				character.idleRight = false;
				character.idleLeft = true;
				velx = 0;
				character.walkingLeft = false;
				character.jumpingLeft = false;
			} else if (key == KeyEvent.VK_RIGHT) {
				character.idleRight = true;
				character.idleLeft = false;
				velx = 0;
				character.walkingRight = false;
				character.jumpingRight = false;
			}
			if (key == KeyEvent.VK_UP) {
				vely = 0;
				character.jumpingRight = character.jumpingLeft = false;
			}
		}
	}

}