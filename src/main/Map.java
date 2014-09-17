package main;

import java.awt.*;
import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class Map {
	public LinkedList<Tile> tiles;
	public LinkedList<Enemy> enemies;
	public LinkedList<Coin> coins;
	
	public Image endPointImage;
	public Rectangle endPointRectangle;
	public Image fullEndPointImage;
	public Map() {
		loadMap();
	}

	private void loadMap() {
		tiles = new LinkedList<>();
		enemies = new LinkedList<>();
		coins = new LinkedList<>();
		
		endPointImage = Utils.loadImage("res/endforeground2.png");
		fullEndPointImage = Utils.loadImage("res/endforeground.png");
		
		int y = 0;
		try {
			Scanner scanner = new Scanner(new File("res/map.txt"));
			String[] endPointLine = scanner.nextLine().split(" ");
			endPointRectangle = new Rectangle(Integer.parseInt(endPointLine[0]) * 20,Integer.parseInt(endPointLine[1]) * 20,
					endPointImage.getWidth(null),endPointImage.getHeight(null));
			while (scanner.hasNextLine()) {
				String tempLine = scanner.nextLine();
				for (int x = 0; x < tempLine.length(); x++) {
					tiles.add(new Tile(x * 20, y * 20, tempLine.charAt(x)));
				}
				y++;
			}
			scanner.close();
			//enemies
			scanner = new Scanner(new File("res/enemies.txt"));
			while(scanner.hasNextLine())
			{
				String[] tempEnemy = scanner.nextLine().split(" ");
				enemies.add(new Enemy(Integer.parseInt(tempEnemy[0]), Integer.parseInt(tempEnemy[1]), Integer.parseInt(tempEnemy[2]),Integer.parseInt(tempEnemy[3])));
			}
			scanner.close();
			//coins
			scanner = new Scanner(new File("res/coins.txt"));
			while(scanner.hasNextLine())
			{
				String[] tempCoin = scanner.nextLine().split(" ");
				coins.add(new Coin(Integer.parseInt(tempCoin[0]), Integer.parseInt(tempCoin[1])));
			}
			scanner.close();
		} catch (Exception exception) {

		}
	}

	public void drawMap(Graphics2D graphics)
	{
		for (Tile tile : tiles) {
			graphics.drawImage(tile.tileImage, tile.tileRectangle.x,
					tile.tileRectangle.y, null);
		}
		for(Coin coin : coins)
		{
			coin.drawCoin(graphics);
		}
		for(Enemy enemy : enemies)
		{
			enemy.drawEnemy(graphics);
		}
	}

	public void updateMap(int movingDir) 
	{
		//moving
		for(Enemy enemy : enemies)
		{
			enemy.rectangle.x -= movingDir;
			enemy.startPositionX -= movingDir;
			enemy.update();
		}
		for(Coin coin : coins)
		{
			coin.rectangle.x -= movingDir;
			coin.update();
		}
		for (Tile tile : tiles)
		{
			tile.tileRectangle.x -= movingDir;
		}
		endPointRectangle.x -= movingDir;
	}
}

class Tile {
	public Rectangle tileRectangle;
	public Rectangle startRectangle;
	public Color color;
	public boolean collidable;
	public Image tileImage;

	public Tile(int x, int y, char type) {
	
		tileImage = Utils.loadImage("res/tiles/" + type + ".png");
		tileRectangle = new Rectangle(x, y, tileImage.getWidth(null),
				tileImage.getHeight(null));
		startRectangle = new Rectangle(tileRectangle);
		if (type == 'g' || type == 'h' || type == 'j' || type == 'k' || type == 'e')
			collidable = true;
		else
			collidable = false;
	}
}