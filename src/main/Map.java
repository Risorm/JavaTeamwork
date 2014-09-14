package main;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

import com.sun.accessibility.internal.resources.accessibility;

public class Map
{
	public LinkedList<Tile> tiles;
	
	public Map()
	{
		loadMap();
	}
	private void loadMap()
	{
		tiles = new LinkedList<>();
		int y = 0;
		try
		{
			Scanner scanner = new Scanner(new File("res/map.txt"));
			while(scanner.hasNextLine())
			{
				String tempLine = scanner.nextLine();
				for (int x = 0; x < tempLine.length(); x++) 
				{
					tiles.add(new Tile(x * 20,y * 20,tempLine.charAt(x)));
				}
				y++;
			}
			scanner.close();
		}
		catch(Exception exception)
		{
			
		}
	}
	
	public void drawMap(Graphics2D graphics)
	{
		for(Tile tile : tiles)
		{
			graphics.drawImage(tile.tileImage,tile.tileRectangle.x , tile.tileRectangle.y, null);
		}
	}
}

class Tile
{
	public Rectangle tileRectangle;
	public Color color;
	public boolean collidable;
	public Image tileImage;
	
	public Tile(int x,int y,char type)
	{
		tileImage = Utils.loadImage("res/tiles/" + type + ".png");
		tileRectangle = new Rectangle(x,y,tileImage.getWidth(null),tileImage.getHeight(null));
		
		if(type == 'V' || type == 'Z')
			collidable = true;
		else
			collidable = false;
	}
}