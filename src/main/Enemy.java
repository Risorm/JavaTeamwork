package main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Enemy {

	public Rectangle rectangle;
	int currentFrame;
	int delay = 0;
	int startPositionX = 0;
	LinkedList<Image> walkRightAnimation;
	LinkedList<Image> walkLeftAnimation;
	int radius;

	boolean walkingRight;
	boolean walkingLeft;

	public void initImages() {
		walkRightAnimation = new LinkedList<>();
		walkLeftAnimation = new LinkedList<>();

		for (int i = 1; i <= 3; i++) {
			walkRightAnimation.add(Utils.loadImage("res/eanimations/walkRight"
					+ i + ".png"));
			walkLeftAnimation.add(Utils.loadImage("res/eanimations/walkLeft"
					+ i + ".png"));
		}
	}

	public void update() {
		updateMovement();
		if (walkingRight == true) {
			delay++;
			if (delay >= 6) {
				currentFrame++;
				delay = 0;
			}
		} else if (walkingLeft == true) {
			delay++;
			if (delay >= 6) {
				currentFrame++;
				delay = 0;
			}
		} else if (walkingRight == false && walkingLeft == false) {
			currentFrame = 0;
		}
		if (currentFrame >= 3)
			currentFrame = 0;
	}

	public void drawEnemy(Graphics2D graphics) {
		if (walkingLeft == true)
			graphics.drawImage(walkLeftAnimation.get(currentFrame),
					rectangle.x, rectangle.y, null);
		else if (walkingRight == true)
			graphics.drawImage(walkRightAnimation.get(currentFrame),
					rectangle.x, rectangle.y, null);
	}

	public void updateMovement() {
		if (walkingRight == true)
			rectangle.x += 1;
		if (rectangle.x + rectangle.width >= startPositionX + rectangle.width
				+ radius) {
			walkingRight = false;
			walkingLeft = true;
		}
		if (walkingLeft == true)
			rectangle.x -= 1;
		if (rectangle.x <= startPositionX - radius) {
			walkingRight = true;
			walkingLeft = false;
		}
	}

	public Enemy(int x, int y, int radius) {
		initImages();
		rectangle = new Rectangle(x * 20, y * 20, 40, 60);
		walkingLeft = false;
		walkingRight = true;
		startPositionX = x * 20;
		currentFrame = 0;
		this.radius = radius * 20;
	}
}
