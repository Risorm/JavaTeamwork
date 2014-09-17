package main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable, KeyListener {
	private static final long serialVersionUID = 1L;

	Thread theThread;

	Image background;
	Image foreground;
	Image gameOverImage;
	
	Character character;
	Map map;

	int velx = 0, vely = 0;
	int startY = 0;

	int score;
	int lives;
	Animation scoreAnimation;
	Image livesImage;

	boolean gameOver;
	boolean endPointReached;
	int numberOfBackgrounds = 5;
	
	int[] backgroundPositionsX;
	int[] foregroundPositionsX;
	int[] startingForegroundPosX;
	int[] startingBackgroundPosX;
	
	public GamePanel() {

		addKeyListener(this);
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);

		background = Utils.loadImage("res/background.jpg");
		foreground = Utils.loadImage("res/foreground.png");

		scoreAnimation = new Animation("res/scoreanimations/goldCoin", 9, Game.DELAY_SCORE_ANIMATIONS);
		scoreAnimation.start();
		livesImage = Utils.loadImage("res/healthanimations/heart.png");
		gameOverImage = Utils.loadImage("res/gameover.png");
		
		gameOver = false;
		endPointReached = false;
		
		backgroundPositionsX = new int[numberOfBackgrounds];
		foregroundPositionsX = new int[numberOfBackgrounds];
		startingBackgroundPosX =new int[numberOfBackgrounds];
		startingForegroundPosX = new int[numberOfBackgrounds];
		
		for(int i = 0; i < backgroundPositionsX.length;i++)
		{
			backgroundPositionsX[i] = i * 1535;
			startingBackgroundPosX[i] = backgroundPositionsX[i];
			
			foregroundPositionsX[i] = i * 1535;
			startingForegroundPosX[i] = foregroundPositionsX[i];
		}
		
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

		if(endPointReached == true)
		{
			
		}
		else if(gameOver == true)
		{
			graphics2d.drawImage(background, 0, 0, null);
			graphics2d.drawImage(foreground,0,0,null);
			graphics2d.drawImage(gameOverImage, 0, 0, null);
		}
		else{
			for(int position : backgroundPositionsX)
			{
					graphics2d.drawImage(background,position,0,null);
			}
	//		graphics2d.drawImage(map.fullEndPointImage,
	//				map.endPointRectangle.x - 281, map.endPointRectangle.y, null);
	
			map.drawMap(graphics2d);
			character.drawCharacter(graphics2d);
	
			for(int position : foregroundPositionsX)
			{
					graphics2d.drawImage(foreground,position,0,null);
			}
			
			for (int i = 0; i < lives; i++) {
				graphics2d.drawImage(livesImage, i * 39, 0, null);
			}
	
			scoreAnimation.drawAnimation(graphics2d, 570, 2);
	
			graphics2d.setColor(Color.WHITE);
			graphics2d.setFont(new Font("Serif", Font.BOLD, 20));
			graphics2d.drawString(" : " + score, 590, 25);
		}
		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	@Override
	public void run() {
		while (gameOver == false && endPointReached == false)
		{
			if(character.die == false && endPointReached == false)
			{
				vely = Game.VELOCITY_Y;
				if (character.isJumping == true) {
				character.rectangle.y -= Game.VELOCITY_Y_JUMPING;
				for (int i = 0; i < map.tiles.size(); i++) {
					if (map.tiles.get(i).collidable == true) {
						if (character.rectangle
								.intersects(map.tiles.get(i).tileRectangle)) {
							character.isJumping = false;
							character.landing = true;
							character.rectangle.y += Game.VELOCITY_Y_JUMPING;
						}
					}
				}
				if (character.rectangle.y <= startY - 2
						* character.rectangle.height
						&& character.isJumping == true) {
					character.isJumping = false;
					character.landing = true;
				}
			}
			else
			{
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
			}
			
			character.virtualRectangle.x += velx;
			character.rectangle.x += velx;
			if(character.rectangle.x + character.rectangle.width > 340 || character.virtualRectangle.x + character.rectangle.width > 340)
			{
				character.rectangle.x = 15 * 20;
				for(int i = 0; i < backgroundPositionsX.length; i++)
				{
					backgroundPositionsX[i] -= velx;
					foregroundPositionsX[i] -= velx;
				}
				map.redirectMap(velx);
				for (int i = 0; i < map.tiles.size(); i++) {
				if (map.tiles.get(i).collidable == true) 
				{
					if (character.rectangle
							.intersects(map.tiles.get(i).tileRectangle)
							&& (character.walkingLeft == true || character.walkingRight == true))
						{
							for(int j = 0; j < backgroundPositionsX.length; j++)
							{
								backgroundPositionsX[j] += velx;
								foregroundPositionsX[j] += velx;
							}
							character.virtualRectangle.x -= velx;
							map.redirectMap(-velx);
						}
					}
				}
			}
			if(character.rectangle.x < 0)
			{
				character.rectangle.x = 0;
			}
			if(character.rectangle.y + character.rectangle.height > 450)
			{
				character.idleLeft = false;
				character.idleRight = false;
				character.jumpingLeft = character.jumpingRight = false;
				character.isJumping = false;
				character.landing = false;
				
				character.die = true;
			}
			if(character.rectangle.intersects(map.endPointRectangle))
			{
				endPointReached = true;
			}
			// Enemies checking
			for (int i = 0; i < map.enemies.size(); i++) {
				if (character.rectangle
						.intersects(map.enemies.get(i).rectangle)) {
					character.die = true;
					character.jumpingLeft = character.jumpingRight = false;
					character.isJumping = false;
					character.landing = false;
					
					for(int j = 0; j < backgroundPositionsX.length; j++)
					{
						backgroundPositionsX[j] += velx;
						foregroundPositionsX[j] += velx;
					}
					
					character.virtualRectangle.x -= velx;
					map.redirectMap(-velx);
					character.die = true;
				}
			}
			// animation
			
				for (int i = 0; i < map.coins.size(); i++) {
					if (character.rectangle.intersects(map.coins.get(i).rectangle)) {
						map.coins.remove(map.coins.get(i));
						score++;
					}
				}
				map.updateMap();
			}
			else
			{
				character.idleLeft = false;
				character.idleRight = false;
				if(character.walkingLeft == true && character.dieLeftAnimation.done() == true ||
						character.walkingRight == true && character.dieRightAnimation.done() == true)
				{
					resetPositions();
					lives--;
				}
				if(lives <= 0)
					gameOver = true;
			}
			scoreAnimation.update();
			character.update();
			repaint();
			try {
				Thread.sleep(Game.THREAD_SLEEP_VALUE);
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
		for(int i = 0; i < backgroundPositionsX.length;i++)
		{
			backgroundPositionsX[i] = startingBackgroundPosX[i];
			foregroundPositionsX[i] = startingForegroundPosX[i];
		}
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

			velx = -Game.VELOCITY_X;
		}

		if (key == KeyEvent.VK_RIGHT) {
			character.idleRight = false;
			character.idleLeft = false;

			character.walkingLeft = false;
			character.walkingRight = true;

			velx = Game.VELOCITY_X;
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