package main;
import java.awt.EventQueue;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Game extends JFrame
{
	public Game() 
	{
		initGame();
	}
	
	private void initGame()
	{
		add(new GamePanel());
		setResizable(false);
		
		pack();
		
		setTitle(GamePanel.TITLE);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run() 
			{
				JFrame game = new Game();
				game.setVisible(true);
			}
		});
	}
}
