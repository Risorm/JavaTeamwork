package main;

import javax.swing.JFrame;

public class Game extends JFrame {

	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 640, HEIGHT = 480;

	public Game() {
		add(new GamePanel());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setTitle("Pig Ninja vs Androids");
		setResizable(false);
		setVisible(true);
	}

	public static void main(String[] args) {
		Game game = new Game();
	}
}