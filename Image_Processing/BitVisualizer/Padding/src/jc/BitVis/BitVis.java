package jc.BitVis;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class BitVis {
	
	private static final byte[] seed=new byte[20];
			/*{0,0,0,0,0,
			0,0,0,0,0,
			0,0,0,0,0,
			0,0,0,0,0};*/
	private static SecureRandom rng=new SecureRandom();
	static{rng.nextBytes(seed);}
	
	
	private static final int height=900,width=1600;
	//private static final String sourceFile="RandomDump2.txt";
	private static final String outputFile="secureRandom.png";
	
	private static final int pixelB=(255<<24)|(0<<16)|(0<<8)|0;
	private static final int pixelW=(0<<24)|(255<<16)|(255<<8)|255; 
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		//rng=SecureRandom.getInstance("NativePRNG");
		for(byte x:seed)System.out.println(x);
		System.out.println(rng.getAlgorithm());
		//Scanner fs=new Scanner(new File(sourceFile));
		StringBuilder sb=new StringBuilder();
		//while(fs.hasNext())sb.append(fs.next());
		//fs.close();
		String sample=sb.toString();
		int index=0;
		BufferedImage img=new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		for(int i=0;i<height;i++)
			for(int k=0;k<width;k++){
					//img.getRGB(k,i); <-- can encode messages into images
					if(rng.nextInt(2)==1)img.setRGB(k,i,pixelB);
					//if(sample.substring(index,(index++)+1).equals("1"))img.setRGB(k,i,pixelB);
					else img.setRGB(k,i,pixelW);
				}
		File f=new File(outputFile);
		ImageIO.write(img,"png",f);
	}

}
