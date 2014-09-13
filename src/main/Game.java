package main;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Game extends JFrame 
{
    public static final String TITLE = "AeroBlue-Platformer";
    public Game() 
    {
        add(new GamePanel());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 480);
        setLocationRelativeTo(null);
        setTitle(TITLE);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Game();
    }
}
