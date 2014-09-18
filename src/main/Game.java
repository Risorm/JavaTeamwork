package main;

import javax.swing.JFrame;

public class Game extends JFrame {

	public static final int DELAY_ENEMY_ANIMATIONS = 6;
	public static final int DELAY_COINS_ANIMATIONS = 6;
	public static final int DELAY_SCORE_ANIMATIONS = 3;
	public static final int DELAY_CHARACTER_WALKING = 3;
	public static final int DELAY_CHARACTER_DYING = 60;

	public static final int VELOCITY_X = 4;
	public static final int VELOCITY_Y = 4;
	public static final int VELOCITY_Y_JUMPING = 16;

	public static final int THREAD_SLEEP_VALUE = 10;

	public static final int SCORE_POSITION_X = 590;
	public static final int SCORE_POSITION_Y = 10;

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