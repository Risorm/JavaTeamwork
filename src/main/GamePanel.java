
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener
{
	Image toDrawImage;
	Timer timer;
	
	public static final int WIDTH = 640, HEIGHT = 480;
	
	
	public GamePanel() 
	{
		initGamePanel();
	}
	
	private void initGamePanel()
	{
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setDoubleBuffered(true);
		
		timer = new Timer(25, this);
		timer.start();
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		drawAll(g);
	}
	
	public void drawAll(Graphics graphics)
	{
		//drawHere
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		repaint();
	}
}
