package main;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	private Timer timer;

	Image background;
	Image foreground;
	Character character;
	int velx = 0, vely = 0, velx2 = 625, velx3 = 0;
	Map map;
	int startY = 0;
	
	
	int score;
	int lives;
	LinkedList<Image> scoreAnimation;

	Image livesImage;
	
	int delayForScoreAnimation;
	int currentFrameScore;
	
	public GamePanel() {

		addKeyListener(new InputHandler());
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		
		background = Toolkit.getDefaultToolkit().createImage(
				"res/background.jpg");
		foreground = Toolkit.getDefaultToolkit().createImage(
				"res/foreground.png");
		
		map = new Map();
		currentFrameScore = 0;
		score = 0;
		lives = 3;
		delayForScoreAnimation = 0;
		scoreAnimation = new LinkedList<>();
		livesImage = Utils.loadImage("res/healthanimations/heart.png");
		for (int i = 1; i <= 9; i++)
		{
			scoreAnimation.add(Utils.loadImage("res/scoreanimations/goldCoin" + i + ".png"));
		}
		character = new Character();
		timer = new Timer(5, this);
		timer.start();
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.drawImage(background, 625 - velx2, 0, null);
		if (velx2 >= background.getWidth(null)) {
			graphics2d.drawImage(background, 625 - velx3, 0, null);
		}
		map.drawMap(graphics2d);
		
		graphics2d.drawImage(character.currentImage, character.rectangle.x,
				character.rectangle.y, this);
		graphics2d.drawImage(foreground, 625 - velx2, 0, null);
		if (velx2 >= foreground.getWidth(null)) {
			graphics2d.drawImage(foreground, 625 - velx3, 0, null);
		}
		for (int i = 0; i < lives; i++) 
		{
			graphics2d.drawImage(livesImage,i * 24,0,null);
		}
		
		graphics2d.drawImage(scoreAnimation.get(currentFrameScore), 570, 0, null);
		graphics2d.setColor(Color.WHITE);
	    graphics2d.setFont(new Font("Serif", Font.BOLD, 20));
		graphics2d.drawString(" : " + score, 590, 25);
	//	System.out.println(velx2);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void actionPerformed(ActionEvent e) {
		vely = 2;
		if (character.isJumping == true) {
			character.rectangle.y -= 8;
		}
		if (character.rectangle.y <= startY - 2 * character.rectangle.height
				&& character.isJumping == true) {
			character.isJumping = false;
			character.landing = true;
		}
		//character.virtualRectangle.x += velx;
		velx2 += velx;
		velx3 += velx;
		map.updateMap(velx);
		for (int i = 0; i < map.tiles.size(); i++) {
			if (map.tiles.get(i).collidable == true) {
				if (character.rectangle
						.intersects(map.tiles.get(i).tileRectangle)
						&& (character.walkingLeft == true || character.walkingRight == true)) {
					velx2 -= velx;
					velx3 -= velx;
					//character.virtualRectangle.x -= velx;
					map.updateMap(-velx);
				}
			}
		}
		if ((character.rectangle.x - (background.getWidth(null) - Game.WIDTH))
				% (2 * background.getWidth(null)) == 0) {
			velx3 = 0;
		}
		if ((character.rectangle.x - background.getWidth(null))
				% (2 * background.getWidth(null)) == 0) {
			velx2 = 0;
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
		delayForScoreAnimation++;
		if(delayForScoreAnimation >= 10)
		{
			delayForScoreAnimation = 0;
			currentFrameScore++;
		}
		if(currentFrameScore > scoreAnimation.size() - 1)
			currentFrameScore = 0;
		for (int i = 0; i < map.coins.size(); i++) {
			if (character.rectangle.intersects(map.coins.get(i).rectangle))
			{
				map.coins.remove(map.coins.get(i));
				score++;
			}
		}

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