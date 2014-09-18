package main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class Enemy {

	public Rectangle rectangle;
	public Rectangle startRectangle;
	int startCenterX;
	int centerX;

	int radius;

	Animation walkRighAnimation;
	Animation walkLeftAnimation;

	Image idleRightImage;
	Image idleLeftImage;

	boolean walkingRight;
	boolean walkingLeft;

	int speed;

	public void initImages() {
		walkRighAnimation = new Animation("res/eanimations/walkRight", 3,
				Game.DELAY_ENEMY_ANIMATIONS);
		walkLeftAnimation = new Animation("res/eanimations/walkLeft", 3,
				Game.DELAY_ENEMY_ANIMATIONS);

		idleLeftImage = Utils.loadImage("res/eanimations/idleLeft.png");
		idleRightImage = Utils.loadImage("res/eanimations/idleRight.png");
	}

	public void update() {
		updateMovement();
		if (walkingRight == true) {
			walkLeftAnimation.stop();
			walkRighAnimation.start();
			walkRighAnimation.update();
		} else if (walkingLeft == true) {
			walkLeftAnimation.start();
			walkRighAnimation.stop();
			walkLeftAnimation.update();
		} else if (walkingRight == false && walkingLeft == false) {
			walkLeftAnimation.stop();
			walkRighAnimation.stop();
		}
	}

	public void drawEnemy(Graphics2D graphics) {
		if (walkingLeft == true)
			walkLeftAnimation.drawAnimation(graphics, rectangle.x, rectangle.y);
		else if (walkingRight == true)
			walkRighAnimation.drawAnimation(graphics, rectangle.x, rectangle.y);
	}

	public void updateMovement() {
		if (walkingRight == true)
			rectangle.x += speed;
		if (rectangle.x + rectangle.width >= centerX + rectangle.width + radius) {
			walkingRight = false;
			walkingLeft = true;
		}
		if (walkingLeft == true)
			rectangle.x -= speed;
		if (rectangle.x <= centerX - radius) {
			walkingRight = true;
			walkingLeft = false;
		}
	}

	public Enemy(int x, int y, int radius, int speed) {
		initImages();
		rectangle = new Rectangle(x * 20, y * 20, 40, 60);
		startRectangle = new Rectangle(rectangle);
		this.speed = speed;
		walkingLeft = false;
		walkingRight = true;
		centerX = x * 20;
		startCenterX = centerX;
		this.radius = radius * 20;
	}
}
