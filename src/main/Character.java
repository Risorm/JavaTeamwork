package main;

import java.awt.Image;
import java.util.LinkedList;

public class Character {
	public int positionX;
	public int positionY;

	int currentFrame;

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
	boolean isJumping;

	public void initImages() {
		walkRightAnimation = new LinkedList<>();
		walkLeftAnimation = new LinkedList<>();
		jumpRightAnimation = new LinkedList<>();

		for (int i = 1; i <= 3; i++) {
			walkRightAnimation.add(Utils
					.loadImage("res/walkRight" + i + ".png"));
			walkLeftAnimation.add(Utils.loadImage("res/walkLeft" + i + ".png"));
			jumpRightAnimation.add(Utils
					.loadImage("res/jumpRight" + i + ".png"));
			jumpLeftAnimation.add(Utils.loadImage("res/jumpLeft" + i + ".png"));
		}
		idleRightImage = Utils.loadImage("res/idleRight.png");
		idleLeftImage = Utils.loadImage("res/idleLeft.png");
	}

	public void update() {
		if (idleRight == true) {
			currentImage = idleRightImage;
		} else if (idleLeft == true) {
			currentImage = idleLeftImage;
		} else if (walkingRight == true) {
			currentImage = walkRightAnimation.get(currentFrame);
			currentFrame++;
		} else if (walkingLeft == true) {
			currentImage = walkLeftAnimation.get(currentFrame);
			currentFrame++;
		} else if (jumpingRight == true) {
			currentImage = jumpRightAnimation.get(currentFrame);
			currentFrame++;
		} else if (jumpingLeft == true) {
			currentImage = jumpLeftAnimation.get(currentFrame);
			currentFrame++;
		} else if (walkingRight == false && walkingLeft == false) {
			currentFrame = 0;
		}
		if (currentFrame >= 3)
			currentFrame = 0;
	}

	public Character() {
		initImages();
		positionX = 0;
		positionX = 0;
		walkingLeft = walkingRight = false;
		jumpingRight = jumpingLeft = false;
		idleRight = idleLeft = false;
		currentFrame = 0;
		currentImage = idleRightImage;
	}
}
