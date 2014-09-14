package main;
import javax.swing.JFrame;

public class Game extends JFrame 
{
	
	public static final int WIDTH = 320, HEIGHT = 240;
	public static final int SCALE = 2;
    public Game() 
    {
        add(new GamePanel());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH*SCALE, HEIGHT*SCALE);
        setLocationRelativeTo(null);
        setTitle("AeroBlue Platformer");
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Game();
    }
}