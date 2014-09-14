package main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Coin {
	
	Rectangle rectangle;
	int currentFrame;
	int delay = 0;

	LinkedList<Image> rotateAnimation;

	public void initImages() {
		rotateAnimation = new LinkedList<>();

		for (int i = 1; i <= 9; i++) {
			rotateAnimation.add(Utils.loadImage("res/coinanimations/goldCoin" + i
					+ ".png"));
		}
	}

	public void update() {
		delay++;
		if (delay >= 12) {
			currentFrame++;
			delay = 0;
		}
		if (currentFrame >= 8)
			currentFrame = 0;
	}

	public void drawCoin(Graphics2D graphics)
	{
		graphics.drawImage(rotateAnimation.get(currentFrame),rectangle.x,rectangle.y,null);
	}
	public Coin(int x, int y) {
		initImages();
		rectangle = new Rectangle(x * 20,y * 20,20,20);
		currentFrame = 0;
	}
}
