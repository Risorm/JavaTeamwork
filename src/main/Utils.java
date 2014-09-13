import java.awt.Image;

import javax.swing.ImageIcon;


public class Utils 
{
	public static Image loadImage(String path)
	{
		ImageIcon tempImageIcon = new ImageIcon(path);
		return tempImageIcon.getImage();
	}
}
