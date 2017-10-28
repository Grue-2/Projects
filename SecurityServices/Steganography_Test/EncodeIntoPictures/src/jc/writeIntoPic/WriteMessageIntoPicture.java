package jc.writeIntoPic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class WriteMessageIntoPicture {
	private static final boolean NO_LENGTH_SEND_SOLUTION_YET=true;
	
	//private static final int height=900,width=1600;
	//private static final int pixelB=(255<<24)|(0<<16)|(0<<8)|0;
	//private static final int pixelW=(0<<24)|(255<<16)|(255<<8)|255; 
	public static void main(String...args) throws IOException{
		//String message=MessageCompating.utf_8StringToBinaryString("Mom says: Dios te vindiga papi.\nTell mom if youve deciphered this message.");
		writeIntoImage("testing123!@#fjdaskf",new File("notquite.png"));
		System.out.println(readFromImage(new File("notquite.png")));
	}

	public static String readFromImage(File readFrom) throws IOException{
		StringBuilder result=new StringBuilder();
		BufferedImage img=ImageIO.read(readFrom);
		int update=10;
		int x=0,y=0;
		while(y<img.getHeight()-1){
			int value=img.getRGB(x+1, y+1);
			if(img.getRGB(x,y)==value+1)result.append('1');
			else result.append('0');
			x+=update;
			if(x>img.getWidth()-2){x=0;y+=update;}
		}
		while(result.length()%8!=0)result.setLength(result.length()-1);;
		String decrypted=MessageCompating.binaryStringToUtf_8String(result.toString());
		System.out.println(decrypted);
		//return decrypted;
		return decrypted.substring(0,decrypted.indexOf("#end")-1);
	}
	public static void writeIntoImage(String message,File imageFileToWriteInto) throws IOException{
		message=MessageCompating.utf_8StringToBinaryString(message+" #end");
		int length=message.length();
		BufferedImage img=ImageIO.read(imageFileToWriteInto);
		int update=10;
		for(int i=0,len=message.length(),indexX=0,indexY=0;i<len;i++){
			if(indexX>img.getWidth()-2){indexX=0;indexY+=update;}
			int value=img.getRGB(indexX+1,indexY+1);
			if(message.charAt(i)=='0')img.setRGB(indexX,indexY,value);
			else img.setRGB(indexX,indexY,value+1);
			indexX+=update;
			
		}
		ImageIO.write(img,"png",imageFileToWriteInto);
	}
	
	
	
	// below is fine , or at least was at one point
	
	public static void writeIntoImage(String message) throws IOException{
		int length=message.length();
		BufferedImage img=ImageIO.read(new File("src/Images/flower.png"));
		int update=(int)Math.pow((img.getWidth()*img.getHeight()/length),.5);
		if(NO_LENGTH_SEND_SOLUTION_YET)update=10;
		if(update<2){System.out.println("Image to small to hide message. Quitting.");System.exit(0);}
		for(int i=0,len=message.length(),indexX=0,indexY=0;i<len;i++){
			if(indexX>img.getWidth()-2){indexX=0;indexY+=update;}
			int value=img.getRGB(indexX+1,indexY+1);
			if(message.charAt(i)=='0')img.setRGB(indexX,indexY,value);
			else img.setRGB(indexX,indexY,value+1);
			indexX+=update;
			
		}
		ImageIO.write(img,"png",new File("output.png"));
	}
	
	public static String readFromImage(int length) throws IOException{
		StringBuilder result=new StringBuilder();
		BufferedImage img=ImageIO.read(new File("output.png"));
		int update=(int)Math.pow((img.getWidth()*img.getHeight()/length),.5);
		if(NO_LENGTH_SEND_SOLUTION_YET)update=10;
		int x=0,y=0;
		while(length-->0){
			int value=img.getRGB(x+1, y+1);
			if(img.getRGB(x,y)==value+1)result.append('1');
			else result.append('0');
			x+=update;
			if(x>img.getWidth()-2){x=0;y+=update;}
		}
		return MessageCompating.binaryStringToUtf_8String(result.toString());
	}
}
