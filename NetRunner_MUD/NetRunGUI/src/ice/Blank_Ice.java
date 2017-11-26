package ice;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Session;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class Blank_Ice extends Ice{
	public static Image imageImg, infoImg;
	static
	{
		try {
			imageImg = SwingFXUtils.toFXImage(ImageIO.read(new File("blankIce.png")), null);
			infoImg = SwingFXUtils.toFXImage(ImageIO.read(new File("blankIceInfo.png")), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Blank_Ice()
	{
		image = imageImg;
		info = infoImg;
	}
	
	public void encounter()
	{
		switch(Session.rng.nextInt(ICE_COUNT) + 1)
		{
		case 1:
			new IceWall().encounter();
			break;
		case 3:
			new DataPike().encounter();
			break;
		default:
			Session.consoleLog.append("No ice this time.\n");
			new Empty_Ice().encounter();
		}
	}
}
