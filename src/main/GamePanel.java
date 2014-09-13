package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener
{
	public static final int WIDTH = 640, HEIGHT = 480;
	
	private Thread thread;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	private Graphics2D mainGraphics2d;
	
	public boolean running;
	
	Rectangle rectangle2d;
	
	public GamePanel() 
	{
		super();
		initGamePanel();
	}
	
	public void run() 
	{
		initTheGame();
		rectangle2d = new Rectangle(0,0,40,40);
		long start = 0;
		long elapsed;
		long wait;
		
		while(running)
		{
			update();
			drawAll();
			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed / 1000000;
			
			try 
			{
				Thread.sleep(wait);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
		}
	}
	
	private void update()
	{
		
	}
	
	public void drawAll()
	{
		//drawHere
		Graphics graphics = getGraphics();
		graphics.drawRect(rectangle2d.x, rectangle2d.y, 40, 40);
		repaint();
		graphics.dispose();
		
	}
	
	
	private void initGamePanel()
	{
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setFocusable(true);
		requestFocus(true);
		setDoubleBuffered(true);
	}
	
	private void initTheGame()
	{
		mainGraphics2d = (Graphics2D) mainGraphics2d;
		running = true;
	}
	
	@Override
	public void addNotify() 
	{
		super.addNotify();
		if(thread == null)
		{
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_A)
		{
			rectangle2d.x --;
		}
		else if(keyCode == KeyEvent.VK_D)
		{
			rectangle2d.x ++;
		}
	}
	@Override
	public void keyReleased(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_A)
		{
			
		}
	}
}
