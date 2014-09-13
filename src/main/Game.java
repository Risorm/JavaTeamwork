package main;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Game extends JFrame {

	private static boolean running = false;

	public Game() {
		initGame();
	}

	private void initGame() {
		add(new GamePanel());
		setResizable(false);

		pack();

		setTitle(GamePanel.TITLE);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.setVisible(true);
		game.setFocusable(true);
		game.requestFocus();
	}
}
