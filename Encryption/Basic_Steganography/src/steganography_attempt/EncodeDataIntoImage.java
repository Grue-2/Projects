package steganography_attempt;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class EncodeDataIntoImage {
	private final static int STATIC_DISTANCE = 10;

	public static void writeIntoImage(String message, File imageFileToWriteInto) throws IOException {
		byte[] messageBytes = (message + " #END").getBytes("utf-8");
		BufferedImage img = ImageIO.read(imageFileToWriteInto);

		for (int i = 0, bits = messageBytes.length * 8, indexX = 0, indexY = 0; 
				i < bits;
				++i, indexX += STATIC_DISTANCE) 
		{
			if (indexX > img.getWidth() - 2) {indexX = 0; indexY += STATIC_DISTANCE;}
			
			int value = img.getRGB(indexX+1, indexY+1);
			if ((messageBytes[i >> 3] & (128/(1 << i%8))) > 0)
				img.setRGB(indexX, indexY, value+1);
			else
				img.setRGB(indexX, indexY, value);
		}
		
		ImageIO.write(img, "png", imageFileToWriteInto);
	}

	public static String readFromImage(File readFrom) throws IOException {
		BufferedImage img=ImageIO.read(readFrom);
		
		List<Byte> result = new ArrayList<>();
		byte temp = 0;
		int mask = 128;
		
		int x = 0, y = 0;
		
		while (y < img.getHeight()-1)
		{	
			if (mask < 1)
			{
				mask = 128;
				result.add(temp);
				temp = 0;
			}
			
			temp += mask * (img.getRGB(x, y) == img.getRGB(x+1, y+1)?0:1);
			
			mask /= 2;
			
			x += STATIC_DISTANCE;
			if(x>img.getWidth()-2){x=0;y += STATIC_DISTANCE;}
		}
		
		int index = -1;
		byte[] unwrapped = new byte[result.size()];
		for(Byte b: result)
			unwrapped[++index] = b;

		String wholeString = new String(unwrapped);
		
		return wholeString.substring(0,wholeString.indexOf("#END")); 
	}
	
	
}
