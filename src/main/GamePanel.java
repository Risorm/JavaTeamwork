package main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;

	Thread theThread;

	Image background;
	Image foreground;

	Character character;
	Map map;

	int velx = 0, vely = 0, backgroundX2 = 640, backgroundX = 0;

	int startY = 0;

	int score;
	int lives;
	Animation scoreAnimation;
	Image livesImage;

	boolean gameOver;

	public GamePanel() {

		addKeyListener(this);
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);

		background = Utils.loadImage("res/background.jpg");
		foreground = Utils.loadImage("res/foreground.png");

		scoreAnimation = new Animation("res/scoreanimations/goldCoin", 9, 6);
		scoreAnimation.start();
		livesImage = Utils.loadImage("res/healthanimations/heart.png");

		gameOver = false;

		score = 0;
		lives = 3;
		map = new Map();
		character = new Character();

		theThread = new Thread(this);
		theThread.start();
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		if ((character.virtualRectangle.x - 2282)
				% (background.getWidth(null) * 2) == 0)
			backgroundX = 0;
		if ((character.virtualRectangle.x - 5882)
				% (background.getWidth(null) * 2) == 0)
			backgroundX2 = 0;

		graphics2d.drawImage(background, 640 - backgroundX2, 0, null);
		if (character.virtualRectangle.x > 2282) {
			graphics2d.drawImage(background, 930 - backgroundX, 0, null);
		}


//		graphics2d.drawImage(map.fullEndPointImage,
//				map.endPointRectangle.x - 281, map.endPointRectangle.y, null);

		System.out.println(backgroundX + "X");
		System.out.println(backgroundX2 + "X2");
		System.out.println(character.virtualRectangle.x + "C");
		System.out.println(background.getWidth(null) + "B");
		graphics2d.drawImage(map.fullEndPointImage,
				map.endPointRectangle.x - 281, map.endPointRectangle.y, null);

		map.drawMap(graphics2d);
		character.drawCharacter(graphics2d);

		graphics2d.drawImage(foreground, 640 - backgroundX2, 0, null);
		if (character.virtualRectangle.x > 2282) {
			graphics2d.drawImage(foreground, 930 - backgroundX, 0, null);
		}

//		graphics2d.drawImage(map.endPointImage, map.endPointRectangle.x,
//				map.endPointRectangle.y, null);

		for (int i = 0; i < lives; i++) {
			graphics2d.drawImage(livesImage, i * 39, 0, null);
		}

		scoreAnimation.drawAnimation(graphics2d, 570, 10);

		graphics2d.setColor(Color.WHITE);
		graphics2d.setFont(new Font("Serif", Font.BOLD, 20));
		graphics2d.drawString(" : " + score, 590, 25);
		// System.out.println(velx2);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	@Override
	public void run() {
		gameOver = false;
		while (gameOver == false) {
			if(character.die == false){
			vely = 2;
			if (character.isJumping == true) {
				character.rectangle.y -= 8;
				for (int i = 0; i < map.tiles.size(); i++) {
					if (map.tiles.get(i).collidable == true) {
						if (character.rectangle
								.intersects(map.tiles.get(i).tileRectangle)) {
							character.isJumping = false;
							character.landing = true;
							character.rectangle.y += 8;
						}
					}
				}
			}
			if (character.rectangle.y <= startY - 2
					* character.rectangle.height
					&& character.isJumping == true) {
				character.isJumping = false;
				character.landing = true;
			}
			character.virtualRectangle.x += velx;
			backgroundX += velx;
			backgroundX2 += velx;
			map.updateMap(velx);
			for (int i = 0; i < map.tiles.size(); i++) {
				if (map.tiles.get(i).collidable == true) {
					if (character.rectangle
							.intersects(map.tiles.get(i).tileRectangle)
							&& (character.walkingLeft == true || character.walkingRight == true)) {
						backgroundX -= velx;
						backgroundX2 -= velx;
						character.virtualRectangle.x -= velx;
						map.updateMap(-velx);
					}
				}
			}
			// Enemies checking
			for (int i = 0; i < map.enemies.size(); i++) {
				if (character.rectangle
						.intersects(map.enemies.get(i).rectangle)) {
					character.die = true;
					character.jumpingLeft = character.jumpingRight = false;
					character.isJumping = false;
					character.landing = false;
					
					backgroundX -= velx;
					backgroundX2 -= velx;
					character.virtualRectangle.x -= velx;
					map.updateMap(-velx);
					character.die = true;
				}
			}

			if (character.rectangle.y <= startY - 2
					* character.rectangle.height
					&& character.isJumping == true) {
				character.isJumping = false;
				character.landing = true;
			}
			character.virtualRectangle.x += velx;
			backgroundX += velx;
			backgroundX2 += velx;
			map.updateMap(velx);
			for (int i = 0; i < map.tiles.size(); i++) {
				if (map.tiles.get(i).collidable == true) {
					if (character.rectangle
							.intersects(map.tiles.get(i).tileRectangle)
							&& (character.walkingLeft == true || character.walkingRight == true)) {
						backgroundX -= velx;
						backgroundX2 -= velx;
						character.virtualRectangle.x -= velx;
						map.updateMap(-velx);
					}
				}
			}
			if ((character.rectangle.x - background.getWidth(null))
					% (2 * background.getWidth(null)) == 0) {
				backgroundX = 0;
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
			scoreAnimation.update();
			for (int i = 0; i < map.coins.size(); i++) {
				if (character.rectangle.intersects(map.coins.get(i).rectangle)) {
					map.coins.remove(map.coins.get(i));
					score++;
				}
			}
			}
			else {
				if(character.walkingLeft == true && character.dieLeftAnimation.done() == true ||
						character.walkingRight == true && character.dieRightAnimation.done() == true)
				{
					resetPositions();
					lives--;
				}
			}
			character.update();
			repaint();
			try {
				Thread.sleep(6);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void resetPositions()
	{
		character = new Character();
		velx = 0;
		map.resetPositions();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(character.die == false){
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
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(character.die == false){
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
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}