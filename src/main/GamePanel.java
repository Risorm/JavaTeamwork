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

    Character character;
    int velx = 0, vely = 0;
    Map map;
    public GamePanel() {

        addKeyListener(new InputHandler());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        
        map = new Map();
        System.out.println(map.tiles.size());
        character = new Character();
        timer = new Timer(5, this);
        timer.start();
    }


    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
      //  g2d.fillRect(tempRectangle.x, tempRectangle.y,40,40);
        
        map.drawMap(g);
        g2d.drawImage(character.currentImage, character.positionX, character.positionY, this);
       
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }


    public void actionPerformed(ActionEvent e) 
    {
    	character.positionX += velx;
    	//tempRectangle.y += vely;
    	if(character.positionY < 480 - 72)
    	{
    		character.positionY += 4;
    	}
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
            	//tempRectangle.y = -80;
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
