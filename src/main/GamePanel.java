package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;


public class GamePanel extends JPanel implements ActionListener {

    private Timer timer;

    Rectangle tempRectangle;
    int velx = 0, vely = 0;
    public GamePanel() {

        addKeyListener(new InputHandler());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        tempRectangle = new Rectangle(0,0,40,40);
        timer = new Timer(5, this);
        timer.start();
    }


    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.fillRect(tempRectangle.x, tempRectangle.y,40,40);

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }


    public void actionPerformed(ActionEvent e) 
    {
    	tempRectangle.x += velx;
    	tempRectangle.y += vely;
        repaint();  
    }
    
    private class InputHandler extends KeyAdapter
    {
    	public void keyPressed(KeyEvent e)
        {
        	int key = e.getKeyCode();

        	if (key == KeyEvent.VK_LEFT)
            {
                velx = -1;
            }

            if (key == KeyEvent.VK_RIGHT) 
            {
            	velx = 1;
            }

            if (key == KeyEvent.VK_UP) 
            {
            	vely = -1;
            }

            if (key == KeyEvent.VK_DOWN)
            {
            	vely = 1;
            }
        }
    	
        public void keyReleased(KeyEvent e) 
        {
        	int key = e.getKeyCode();
            
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT)
            {
                velx = 0;
            }

            if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) 
            {
            	vely = 0;
            }         
        }
    }

}
