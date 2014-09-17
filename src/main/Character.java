package main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

public class Character {
	public Rectangle rectangle;
	public Rectangle virtualRectangle;
	public Rectangle startRectangle;
	
	Animation walkRightAnimation;
	Animation walkLeftAnimation;
	Animation dieLeftAnimation;
	Animation dieRightAnimation;

	Image jumpRightImage;
	Image jumpLeftImage;
	Image landingLeftImage;
	Image landingRightImage;

	Image idleRightImage;
	Image idleLeftImage;

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
		walkRightAnimation = new Animation("res/canimations/walkRight", 3, Game.DELAY_CHARACTER_WALKING);
		walkLeftAnimation = new Animation("res/canimations/walkLeft", 3, Game.DELAY_CHARACTER_WALKING);

		dieLeftAnimation = new Animation("res/canimations/dyingLeft", 3, Game.DELAY_CHARACTER_DYING);
		dieRightAnimation = new Animation("res/canimations/dyingRight", 3, Game.DELAY_CHARACTER_DYING);

		idleRightImage = Utils.loadImage("res/canimations/idleRight.png");
		idleLeftImage = Utils.loadImage("res/canimations/idleLeft.png");

		jumpLeftImage = Utils.loadImage("res/canimations/jumpingLeft.png");
		jumpRightImage = Utils.loadImage("res/canimations/jumpingRight.png");

		landingLeftImage = Utils.loadImage("res/canimations/landingLeft.png");
		landingRightImage = Utils.loadImage("res/canimations/landingRight.png");
	}

	public void drawCharacter(Graphics2D graphics) {
		if (idleRight == true) {
			graphics.drawImage(idleRightImage, rectangle.x, rectangle.y, null);
		} else if (idleLeft == true) {
			graphics.drawImage(idleLeftImage, rectangle.x, rectangle.y, null);
		} else if (walkingRight == true && isJumping == false
				&& landing == false && die != true) {
			walkRightAnimation.start();
			walkRightAnimation.drawAnimation(graphics, rectangle.x, rectangle.y);
		} else if (walkingLeft == true && isJumping == false
				&& landing == false && die != true) {
			walkLeftAnimation.start();
			walkLeftAnimation.drawAnimation(graphics, rectangle.x, rectangle.y);
		} else if (walkingRight == false && walkingLeft == false) {
			walkRightAnimation.stop();
			walkLeftAnimation.stop();
		} else if (landing == true && walkingRight == true && die == false) {
			graphics.drawImage(landingRightImage,rectangle.x,rectangle.y,null);
		} else if (landing == true && walkingLeft == true && die == false) {
			graphics.drawImage(landingLeftImage,rectangle.x,rectangle.y,null);
		} else if (isJumping == true && walkingLeft == true) {
			graphics.drawImage(jumpLeftImage,rectangle.x,rectangle.y,null);
		} else if (isJumping == true && walkingRight == true) {
			graphics.drawImage(jumpRightImage,rectangle.x,rectangle.y,null);
		}
		if (die == true && walkingLeft == true) {
			dieLeftAnimation.start();
			dieLeftAnimation.drawAnimation(graphics, rectangle.x, rectangle.y);
		}
		else if (die == true && walkingRight == true) {
			dieRightAnimation.start();
			dieRightAnimation.drawAnimation(graphics, rectangle.x, rectangle.y);
		}
	}
	
	public void update()
	{
		dieLeftAnimation.update();
		dieRightAnimation.update();
		walkLeftAnimation.update();
		walkRightAnimation.update();
	}
	
	public Character() {
		initImages();
		rectangle = new Rectangle(15 * 20, 0, idleLeftImage.getWidth(null),
				idleLeftImage.getHeight(null));
		startRectangle = new Rectangle(rectangle);
		virtualRectangle = new Rectangle(rectangle);
		walkingLeft = walkingRight = false;
		jumpingRight = jumpingLeft = false;
		canJump = false;
		landing = true;
		idleRight = true;
		idleLeft = false;
		die = false;
	}
}
