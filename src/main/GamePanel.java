package main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements ActionListener {
	Image toDrawImage;
	Timer timer;

	public static final int WIDTH = 640, HEIGHT = 480;
	public static final String TITLE = "AeroBlue-Platformer";

	public GamePanel() {
		initGamePanel();
	}

	private void initGamePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setDoubleBuffered(true);
		timer = new Timer(25, this);
		timer.start();
	}

	
	protected void paintComponent(Graphics2D g) {
		super.paintComponent(g);
		drawAll(g);
	}

	public void drawAll(Graphics2D graphics) {
		// drawHere
		Toolkit.getDefaultToolkit().sync();
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}
}
