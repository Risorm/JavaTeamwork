package main;

import javax.swing.JFrame;

public class Game extends JFrame {

	public static final int WIDTH = 640, HEIGHT = 480;

	public Game() {
		add(new GamePanel());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setTitle("AeroBlue Platformer");
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Game();
	}
}