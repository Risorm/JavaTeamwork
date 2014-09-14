package main;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.LinkedList;

public class Coin {
	
	Rectangle rectangle;
	int currentFrame;
	int delay = 0;

	LinkedList<Image> rotateAnimation;

	public Image currentImage;
	boolean rotating;

	public void initImages() {
		rotateAnimation = new LinkedList<>();

		for (int i = 1; i <= 9; i++) {
			rotateAnimation.add(Utils.loadImage("res/coinanimations/goldCoin" + i
					+ ".png"));
		}
	}

	public void update() {
		if (rotating == true) {
			currentImage = rotateAnimation.get(currentFrame);
			delay++;
			if (delay >= 6) {
				currentFrame++;
				delay = 0;
			}
		}
		if (currentFrame >= 9)
			currentFrame = 0;
	}

	public Coin(int x, int y) {
		initImages();
		rectangle = new Rectangle(x * 20,y * 20,20,20);
		currentFrame = 0;
	}
}
