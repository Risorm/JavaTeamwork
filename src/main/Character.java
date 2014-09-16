package main;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Character {
	public Rectangle rectangle;

<<<<<<< HEAD
	    int currentFrame;
=======
	int currentFrame;
	int delay = 0;
>>>>>>> origin/master

	LinkedList<Image> walkRightAnimation;
	LinkedList<Image> walkLeftAnimation;
	LinkedList<Image> jumpRightAnimation;
	LinkedList<Image> jumpLeftAnimation;

	Image idleRightImage;
	Image idleLeftImage;
	public Image currentImage;

	boolean walkingRight;
	boolean walkingLeft;

	boolean idleLeft;
	boolean idleRight;
	boolean jumpingRight;
	boolean jumpingLeft;

	boolean landing;
	boolean isJumping;
	boolean canJump;

	public void initImages() {
		walkRightAnimation = new LinkedList<>();
		walkLeftAnimation = new LinkedList<>();
		jumpRightAnimation = new LinkedList<>();
		jumpLeftAnimation = new LinkedList<>();

		for (int i = 1; i <= 3; i++) {
			walkRightAnimation.add(Utils.loadImage("res/canimations/walkRight"
					+ i + ".png"));
			walkLeftAnimation.add(Utils.loadImage("res/canimations/walkLeft"
					+ i + ".png"));
			jumpRightAnimation.add(Utils.loadImage("res/canimations/jumpRight"
					+ i + ".png"));
			jumpLeftAnimation.add(Utils.loadImage("res/canimations/jumpLeft"
					+ i + ".png"));
		}
		idleRightImage = Utils.loadImage("res/canimations/idleRight.png");
		idleLeftImage = Utils.loadImage("res/canimations/idleLeft.png");
	}

	public void update() {
		if (idleRight == true) {
			currentImage = idleRightImage;
		} else if (idleLeft == true) {
			currentImage = idleLeftImage;
		} else if (walkingRight == true && isJumping == false
				&& landing == false) {
			currentImage = walkRightAnimation.get(currentFrame);
			delay++;
			if (delay >= 6) {
				currentFrame++;
				delay = 0;
			}
		} else if (walkingLeft == true && isJumping == false
				&& landing == false) {
			currentImage = walkLeftAnimation.get(currentFrame);
			delay++;
			if (delay >= 6) {
				currentFrame++;
				delay = 0;
			}
		} else if (walkingRight == false && walkingLeft == false) {
			currentFrame = 0;
		} else if (landing == true && walkingRight == true) {
			currentImage = jumpRightAnimation.get(2);
			// currentFrame++;
		} else if (landing == true && walkingLeft == true) {
			currentImage = jumpLeftAnimation.get(2);
			// currentFrame++;
		} else if (isJumping == true && walkingLeft == true) {
			currentImage = jumpLeftAnimation.get(1);
		} else if (isJumping == true && walkingRight == true) {
			currentImage = jumpRightAnimation.get(1);
		}

		if (currentFrame >= 3)
			currentFrame = 0;

	}

	public Character() {
		initImages();
		rectangle = new Rectangle(0, 0, idleLeftImage.getWidth(null),
				idleLeftImage.getHeight(null));
		walkingLeft = walkingRight = false;
		jumpingRight = jumpingLeft = false;
		canJump = false;
		landing = false;
		idleRight = idleLeft = false;
		currentFrame = 0;
		currentImage = idleRightImage;
	}
}
