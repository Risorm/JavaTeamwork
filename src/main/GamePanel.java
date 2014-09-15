package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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

	Image background;
	Image foreground;
	Character character;
	Enemy enemy;
	int velx = 0, vely = 0, velx2 = 625, velx3 = 0;
	Map map;
	ArrayList<Coin> coins;
	int startY = 0;

	public GamePanel() {

		addKeyListener(new InputHandler());
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		background = Toolkit.getDefaultToolkit().createImage(
				"res/background.jpg");

		coins = new ArrayList<>();
		coins.add(new Coin(5, 20));
		map = new Map();
		foreground = Toolkit.getDefaultToolkit().createImage(
				"res/foreground.png");
		enemy = new Enemy(5, 18, 3);
		character = new Character();
		timer = new Timer(5, this);
		timer.start();
	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D graphics2d = (Graphics2D) g;

		if ((character.rectangle.x - (background.getWidth(null) - Game.WIDTH))
				% (2 * background.getWidth(null)) == 0) {
			velx3 = 0;
		}
		if ((character.rectangle.x - background.getWidth(null))
				% (2 * background.getWidth(null)) == 0) {
			velx2 = 0;
		}
		graphics2d.drawImage(background, 625 - velx2, 0, null);
		if (character.rectangle.x >= (background.getWidth(null) - Game.WIDTH)) {
			graphics2d.drawImage(background, 625 - velx3, 0, null);
		}
		map.drawMap(graphics2d);
		System.out.println(character.rectangle.x);
		for (Coin coin : coins) {
			coin.drawCoin(graphics2d);
		}

		enemy.drawEnemy(graphics2d);
		graphics2d.drawImage(character.currentImage, character.rectangle.x,
				character.rectangle.y, this);
		graphics2d.drawImage(foreground, 625 - velx2, 0, null);
		if (character.rectangle.x >= (foreground.getWidth(null) - Game.WIDTH)) {
			graphics2d.drawImage(foreground, 625 - velx3, 0, null);
		}
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void actionPerformed(ActionEvent e) {
		vely = 2;
		if (character.isJumping == true) {
			character.rectangle.y -= 8;
			character.rectangle.x += velx;
		}
		if (character.rectangle.y <= startY - 2 * character.rectangle.height
				&& character.isJumping == true) {
			character.isJumping = false;
			character.landing = true;
		}
		character.rectangle.x += velx;
		velx2 += velx;
		velx3 += velx;

		for (int i = 0; i < map.tiles.size(); i++) {
			if (map.tiles.get(i).collidable == true) {
				if (character.rectangle
						.intersects(map.tiles.get(i).tileRectangle)
						&& (character.walkingLeft == true || character.walkingRight == true)) {
					character.rectangle.x -= velx;
					velx2 -= velx;
					velx3 -= velx;
				}
			}
		}
		character.rectangle.y += vely;
		for (int i = 0; i < map.tiles.size(); i++) {
			if (map.tiles.get(i).collidable == true) {
				if (character.rectangle
						.intersects(map.tiles.get(i).tileRectangle)) {
					character.canJump = true;
					character.landing = false;
					character.rectangle.y -= vely;
				}
			}
		}
		// animation
		for (int i = 0; i < coins.size(); i++) {
			if (character.rectangle.intersects(coins.get(i).rectangle)) {
				coins.remove(coins.get(i));
			}
		}

		for (Coin coin : coins) {
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
			}

			if (key == KeyEvent.VK_RIGHT) {
				character.idleRight = false;
				character.idleLeft = false;

				character.walkingLeft = false;
				character.walkingRight = true;

				velx = 1;
			}

			if (key == KeyEvent.VK_UP) {
				if (character.canJump == true) {
					character.isJumping = true;
					character.canJump = false;
					startY = character.rectangle.y;
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
			} else if (key == KeyEvent.VK_RIGHT) {
				character.idleRight = true;
				character.idleLeft = false;

				character.walkingRight = false;
				character.jumpingRight = false;

				velx = 0;
			}
			if (key == KeyEvent.VK_UP) {
			}
		}
	}

}