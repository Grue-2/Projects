package ice;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Session;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class Empty_Ice extends Ice{
	public static Image imageImg, infoImg;
	static
	{
		try {
			imageImg = SwingFXUtils.toFXImage(ImageIO.read(new File("emptyIce.png")), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Empty_Ice()
	{
		image = imageImg;
		info = null;
	}
	
	public void encounter()
	{
		Session.continueRun();
	}
}
