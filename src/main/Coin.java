package main;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Coin {

	Rectangle rectangle;
	Rectangle startRectangle;
	Animation coinAnimation;
	
	public void initImages() {
		coinAnimation = new Animation("res/coinanimations/goldCoin",9,12);
		coinAnimation.start();
	}

	public void update() {
		coinAnimation.update();
	}

	public void drawCoin(Graphics2D graphics) {
		coinAnimation.drawAnimation(graphics, rectangle.x, rectangle.y);
	}

	public Coin(int x, int y) {
		initImages();
		rectangle = new Rectangle(x * 20, y * 20, 20, 20);
		startRectangle = new Rectangle(rectangle);
	}
}
