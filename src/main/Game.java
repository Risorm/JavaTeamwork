package main;

import java.awt.EventQueue;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Game extends JFrame {
	public static Thread thread;


	public Game() {
		initGame();
	}
	private static boolean running = false;
	
	
	private void initGame() {
		add(new GamePanel());
		setResizable(false);

		pack();

		setTitle(GamePanel.TITLE);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame game = new Game();
				game.setVisible(true);
				if (running==false) {
					running=true;
				}
				
				long lastTime = System.nanoTime();
		        final double numTicks = 60.0;
		        double nanoSeconds = 1000000000 / numTicks;
		        double delta = 0; 
		        int frames = 0; 
		        int ticks = 0;
		        long timer = System.currentTimeMillis();
		        
				while (running) {
					long currentTime = System.nanoTime(); //the current time during the game loop
		            delta += (currentTime - lastTime) / nanoSeconds; //because the time is changing, we need to update our delta
		            lastTime = currentTime; /* Our last time now needs to be the previous time during the game loop, we do this by storing the current time, 
		                                       this is why this goes AFTER the previous two lines */

		            if (delta >= 1) { //We want to make sure our delta doesn't remain over 1
		                tick(); //updates the game
		                ticks++; //Adds to the ticks per second
		                delta--; //Lowering the delta value for the next run through
		            }

		            render(); //renders the game
		            frames++; //Adds to the frames per second

		            if (System.currentTimeMillis() - timer > 1000) { //We are going to print our frames and ticks to the console every second
		                timer += 1000; //Time must go on!
		                System.out.println(ticks + " Ticks, FPS: " + frames); //Prints the TPS and FPS to the console
		                GamePanel.TITLE=(GamePanel.TITLE + "      FPS: " + frames);
		                ticks = 0; //If we did not do these 2 lines, we would have 10000000000000000000 ticks and fps at one point, then another 10000000000000000000000000000000000000000 the next second
		                frames = 0;
		            }
		            stop();
				}
			}

			private synchronized void start() {
		        if (running) return; //if the game is already running, we do not want to run the game again
		        running = true;
		        thread = new Thread(this); //this thread controls our game
		        thread.start(); //starts the thread, and thus the game loop
		    }

		    /**
		     * This stops the game thread It is synchronized because we do not want to
		     * do anything else until this method is 100% complete
		     */
		    private synchronized void stop() {
		        if (!running) return; //if the game has already stopped, why stop it again?
		        running = false;
		        System.exit(1); //exits the game
		    }

			private void render() {
				// TODO Auto-generated method stub
				
			}

			private void tick() {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
