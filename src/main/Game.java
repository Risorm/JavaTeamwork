package main;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Game extends JFrame
{
	public static final String TITLE = "AeroBlue-Platformer";
	static JFrame window;
	
	public Game() 
	{
		initGame();
	}
	
	static private void initGame()
	{
		window = new JFrame(TITLE);
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}
	public static void main(String[] args) 
	{
		initGame();
	}
}

