package main;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;
	
	Thread theThread;
	
	Image background;
	Image foreground;
	
	Character character;
	Map map;
	
	int velx = 0, vely = 0, backgroundX = 625, backgroundX2 = 0;
	
	int startY = 0;

	int score;
	int lives;
	LinkedList<Image> scoreAnimation;
	Image livesImage;

	int delayForScoreAnimation;
	int currentFrameScore;
	
	boolean gameOver;
	
	public GamePanel() {

		addKeyListener(this);
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);

		background = Toolkit.getDefaultToolkit().createImage(
				"res/background.jpg");
		foreground = Toolkit.getDefaultToolkit().createImage(
				"res/foreground.png");
		gameOver = false;
		map = new Map();
		currentFrameScore = 0;
		score = 0;
		lives = 3;
		delayForScoreAnimation = 0;
		scoreAnimation = new LinkedList<>();
		livesImage = Utils.loadImage("res/healthanimations/heart.png");
		for (int i = 1; i <= 9; i++) {
			scoreAnimation.add(Utils.loadImage("res/scoreanimations/goldCoin"
					+ i + ".png"));
		}
		character = new Character();
		
		theThread = new Thread(this);
		theThread.start();
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.drawImage(background, 625 - backgroundX, 0, null);
		if (character.virtualRectangle.x + 300  >= background.getWidth(null)) {
			graphics2d.drawImage(background, 2585 - backgroundX2, 0, null);
		}
		map.drawMap(graphics2d);
System.out.println(character.virtualRectangle.x);
		graphics2d.drawImage(character.currentImage, character.rectangle.x,
				character.rectangle.y, this);
		if (velx == -1) {
            graphics2d.drawImage(background, 625 - backgroundX, 0, null);
            graphics2d.drawImage(character.currentImage, 0, 0, null);
    }
		graphics2d.drawImage(foreground, 625 - backgroundX, 0, null);
		if (backgroundX >= foreground.getWidth(null)) {
			graphics2d.drawImage(foreground, 625 - backgroundX2, 0, null);
		}
		for (int i = 0; i < lives; i++) {
			graphics2d.drawImage(livesImage, i * 39, 0, null);
		}

		graphics2d.drawImage(scoreAnimation.get(currentFrameScore), 570, 0,
				null);
		graphics2d.setColor(Color.WHITE);
		graphics2d.setFont(new Font("Serif", Font.BOLD, 20));
		graphics2d.drawString(" : " + score, 590, 25);
		// System.out.println(velx2);
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}
	@Override
	public void run() 
	{
		gameOver = false;
		while(gameOver == false)
		{
			vely = 2;
			if (character.isJumping == true)
			{
				character.rectangle.y -= 8;
				for (int i = 0; i < map.tiles.size(); i++) 
				{
					if (map.tiles.get(i).collidable == true) 
					{
						if (character.rectangle
								.intersects(map.tiles.get(i).tileRectangle)) 
						{
							character.isJumping = false;
							character.landing = true;
							character.rectangle.y += 8;
						}
					}
				}
			}
			if (character.rectangle.y <= startY - 2 * character.rectangle.height
					&& character.isJumping == true) {
				character.isJumping = false;
				character.landing = true;
			}
			character.virtualRectangle.x += velx;
			backgroundX += velx;
			backgroundX2 += velx;
			map.updateMap(velx);
			for (int i = 0; i < map.tiles.size(); i++) 
			{
				if (map.tiles.get(i).collidable == true) 
				{
					if (character.rectangle
							.intersects(map.tiles.get(i).tileRectangle)
							&& (character.walkingLeft == true || character.walkingRight == true))
					{
						backgroundX -= velx;
						backgroundX2 -= velx;
						character.virtualRectangle.x -= velx;
						map.updateMap(-velx);
					}
				}
			}
			if (character.rectangle.y <= startY - 2 * character.rectangle.height
					&& character.isJumping == true) 
			{
				character.isJumping = false;
				character.landing = true;
			}
			character.virtualRectangle.x += velx;
			backgroundX += velx;
			backgroundX2 += velx;
			map.updateMap(velx);
			for (int i = 0; i < map.tiles.size(); i++)
			{
				if (map.tiles.get(i).collidable == true) 
				{
					if (character.rectangle
							.intersects(map.tiles.get(i).tileRectangle)
							&& (character.walkingLeft == true || character.walkingRight == true)) 
					{
						backgroundX -= velx;
						backgroundX2 -= velx;
						character.virtualRectangle.x -= velx;
						map.updateMap(-velx);
					}
				}
			}
			if ((character.rectangle.x - background.getWidth(null))
					% (2 * background.getWidth(null)) == 0) 
			{
				backgroundX = 0;
			}
			character.rectangle.y += vely;
			for (int i = 0; i < map.tiles.size(); i++) 
			{
				if (map.tiles.get(i).collidable == true)
				{
					if (character.rectangle
							.intersects(map.tiles.get(i).tileRectangle))
					{
						character.canJump = true;
						character.landing = false;
						character.rectangle.y -= vely;
					}
				}
			}
			// animation
			delayForScoreAnimation++;
			if (delayForScoreAnimation >= 10)
			{
				delayForScoreAnimation = 0;
				currentFrameScore++;
			}
			if (currentFrameScore > scoreAnimation.size() - 1)
				currentFrameScore = 0;
			for (int i = 0; i < map.coins.size(); i++)
			{
				if (character.rectangle.intersects(map.coins.get(i).rectangle)) 
				{
					map.coins.remove(map.coins.get(i));
					score++;
				}
			}
			character.update();
			repaint();
			try {
				Thread.sleep(8);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
	}
	
	@Override
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
	@Override
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
	@Override
	public void keyTyped(KeyEvent e)
	{
		
	}
}