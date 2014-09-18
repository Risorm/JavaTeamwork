package main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.LinkedList;

public class Animation {
	int startFrame;
	int currentFrame;
	int delay;
	int timeToDelay;
	LinkedList<Image> images;
	Image currentImage;
	boolean start;

	public void drawAnimation(Graphics2D graphics, int x, int y) {
		graphics.drawImage(images.get(currentFrame), x, y, null);
	}

	public boolean done() {
		if (currentFrame == images.size() - 1)
			return true;
		else
			return false;
	}

	public void stop() {
		currentFrame = 0;
		start = false;
	}

	public void start() {
		start = true;
	}

	public void update() {
		if (start == true) {
			currentImage = images.get(currentFrame);
			timeToDelay++;
			if (timeToDelay > delay) {
				timeToDelay = 0;
				currentFrame++;
			}
			if (currentFrame > images.size() - 1) {
				currentFrame = 0;
			}
		}
	}

	public Animation() {
		images = new LinkedList<>();
		delay = 0;
		currentImage = null;
		currentFrame = 0;
		start = false;
		timeToDelay = 0;
	}

	public Animation(String path, int numImages, int delay) {
		images = new LinkedList<>();
		start = false;
		for (int i = 1; i <= numImages; i++) {
			images.add(Utils.loadImage(path + i + ".png"));
		}
		currentFrame = 0;
		this.delay = delay;
		timeToDelay = 0;
		currentImage = images.get(currentFrame);
	}
}
