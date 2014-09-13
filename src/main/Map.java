package main;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

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
					tiles.add(new Tile(x,y,tempLine.charAt(x)));
				}
				y++;
			}
			scanner.close();
		}
		catch(Exception exception)
		{
			
		}
	}
	
	public void drawMap(Graphics graphics)
	{
		for(Tile tile : tiles)
		{
			graphics.setColor(tile.color);
			graphics.fillRect(tile.x * 20, tile.y * 20, 20, 20);
		}
	}
}

class Tile
{
	public Rectangle rectangle;
	public int x,y;
	public Color color;
	
	public Tile(int x,int y,char type)
	{
		this.x = x;
		this.y = y;
		if(type == 'B')
			color = Color.BLACK;
		else if(type == 'G')
			color = Color.GREEN;
		else if(type == '.')
			color = Color.WHITE;
	}
}