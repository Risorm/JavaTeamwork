package main;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Character {
	public Rectangle rectangle;
	public Rectangle virtualRectangle;
	int currentFrame;
	int delay = 0;


	LinkedList<Image> walkRightAnimation;
	LinkedList<Image> walkLeftAnimation;	
	LinkedList<Image> dieLeftAnimation;
	LinkedList<Image> dieRightAnimation;
	
	Image jumpRightImage;
	Image jumpLeftImage;
	Image landingLeftImage;
	Image landingRightImage;
	
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

	boolean die;
	public void initImages() {
		walkRightAnimation = new LinkedList<>();
		walkLeftAnimation = new LinkedList<>();
		dieLeftAnimation = new LinkedList<>();
		dieRightAnimation = new LinkedList<>();
		
		for (int i = 1; i <= 3; i++) {
			walkRightAnimation.add(Utils.loadImage("res/canimations/walkRight"
					+ i + ".png"));
			walkLeftAnimation.add(Utils.loadImage("res/canimations/walkLeft"
					+ i + ".png"));
			dieLeftAnimation.add(Utils.loadImage("res/canimations/dyingLeft"
					+ i + ".png"));
			dieRightAnimation.add(Utils.loadImage("res/canimations/dyingRight"
					+ i + ".png"));
		}
		idleRightImage = Utils.loadImage("res/canimations/idleRight.png");
		idleLeftImage = Utils.loadImage("res/canimations/idleLeft.png");
		
		jumpLeftImage = Utils.loadImage("res/canimations/jumpingLeft.png");
		jumpRightImage = Utils.loadImage("res/canimations/jumpingRight.png");
		
		landingLeftImage = Utils.loadImage("res/canimations/landingLeft.png");
		landingRightImage = Utils.loadImage("res/canimations/landingRight.png");
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
			currentImage = landingRightImage;
		} else if (landing == true && walkingLeft == true) {
			currentImage = landingLeftImage;
		} else if (isJumping == true && walkingLeft == true) {
			currentImage = jumpLeftImage;
		} else if (isJumping == true && walkingRight == true) {
			currentImage = jumpRightImage;
		}
		if(die == true && walkingLeft == true)
		{
			currentImage = dieLeftAnimation.get(currentFrame);
			delay++;
			if (delay >= 6) {
				currentFrame++;
				delay = 0;
			}
		}
		if (currentFrame >= 3)
			currentFrame = 0;

	}

	public Character() {
		initImages();
		rectangle = new Rectangle(15 * 20, 0, idleLeftImage.getWidth(null),
				idleLeftImage.getHeight(null));
		virtualRectangle = rectangle;
		
		walkingLeft = walkingRight = false;
		jumpingRight = jumpingLeft = false;
		canJump = false;
		landing = true;
		idleRight = idleLeft = false;
		die = false;
		
		currentFrame = 0;
		currentImage = idleRightImage;
	}
}
