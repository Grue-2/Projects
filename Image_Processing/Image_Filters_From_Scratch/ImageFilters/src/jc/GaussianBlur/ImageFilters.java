package jc.GaussianBlur;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.GrayFilter;
	/*
	 * Failed project, needs to be on intensity, even with grayscale im still
	 * blurring rgb together, neat results but not exactly what I wanted to do
	 * 
	 */


	//ImageIO.read(new File("src/Images/name.png"));
public class ImageFilters {
	private static final int pixelB=(255<<24)|(0<<16)|(0<<8)|0;
	private static final int pixelW=(0<<24)|(255<<16)|(255<<8)|255; 
	public static void main(String[] args) throws IOException {
		// chain: greyScale, gaussianBlur, sobbelEdgeDetection, cannyEdgeDetection, twolayer median
		//layeredChain("dexter.png","dexterLayered5.png",.9,.85,3);// <-- current 
		//layeredChain("fire.png","fireLayered2.png",.9,.85,3);// <-- don't blur
		
		layeredChain(
					 "src/Image/highRestest.png",
					 "src/Image/edgeDetectionExample2.png",
					  .995,
					  .98,
					  3,
					  true
					 );
		
	}
	private static void layeredChain(String inputFilePath,String outputFilePath,double median1,double median2,int layers,boolean blur) throws IOException{
		int x;
		greyScaleAnImage(inputFilePath,outputFilePath);
		if(blur)gaussianBlur(outputFilePath,outputFilePath);
		x=3;while(x-->0)gaussianBlur(outputFilePath,outputFilePath);
		
		//gaussianBlur(outputFilePath,outputFilePath);
		sobelEdgeDetectionX(outputFilePath,"sobelXintermediate.png");
		//sobelEdgeDetectionX("sobelXintermediate.png","sobelXintermediate.png");
		sobelEdgeDetectionY(outputFilePath,"sobelYintermediate.png");
		//sobelEdgeDetectionY("sobelYintermediate.png","sobelYintermediate.png");
		sobelEdgeDetection(outputFilePath,outputFilePath);
		//sobelEdgeDetection(outputFilePath,outputFilePath);
		
		x=4;while(x-->0){
		partOneOfCannyEdgeDetector(outputFilePath,"sobelXintermediate.png","sobelYintermediate.png",outputFilePath);
		}
		twoMedianThresholdFiltering(outputFilePath,outputFilePath,median1,median2,layers);
		x=4;while(x-->0){
		partOneOfCannyEdgeDetector(outputFilePath,"sobelXintermediate.png","sobelYintermediate.png",outputFilePath);
		}
		//
		x=3;while(x-->0){
		twoMedianThresholdFiltering(outputFilePath,outputFilePath,median1,median2,layers);
		partOneOfCannyEdgeDetector(outputFilePath,"sobelXintermediate.png","sobelYintermediate.png",outputFilePath);
		partOneOfCannyEdgeDetector(outputFilePath,"sobelXintermediate.png","sobelYintermediate.png",outputFilePath);
		}
	}
	
	private static void fullChain(String inputFilePath,String outputFilePath,double median1,double median2,int layers) throws IOException{
		greyScaleAnImage(inputFilePath,outputFilePath);
		gaussianBlur(outputFilePath,outputFilePath);
		sobelEdgeDetectionX(outputFilePath,"sobelXintermediate.png");
		sobelEdgeDetectionY(outputFilePath,"sobelYintermediate.png");
		sobelEdgeDetection(outputFilePath,outputFilePath);
		partOneOfCannyEdgeDetector(outputFilePath,"sobelXintermediate.png","sobelYintermediate.png",outputFilePath);
		twoMedianThresholdFiltering(outputFilePath,outputFilePath,median1,median2,layers);
	}
	
	private static void partOneOfCannyEdgeDetector(String inputFilePath,String sobelX,String sobelY,String outputFilePath) throws IOException{
		BufferedImage img=ImageIO.read(new File(inputFilePath));
		BufferedImage sX=ImageIO.read(new File(sobelX));
		BufferedImage sY=ImageIO.read(new File(sobelY));
		int height=img.getHeight(),width=img.getWidth();
		//BufferedImage result=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		for(int x=2;x<width-2;x++)for(int y=2;y<height-2;y++){
			int directionX=sX.getRGB(x,y);
			int directionY=sY.getRGB(x,y);
			// arctan ( sumY/sumX radians or degrees to find direction ) <-- I don't know trig t.t
			int quadrant=findQuadrant(directionX,directionY);
			switch(quadrant){
				case 0:case 4:
					if(((img.getRGB(x,y+1)>img.getRGB(x,y))
							&&
						   ((findQuadrant(sX.getRGB(x,y+1),sY.getRGB(x,y+1))==0)||
							 findQuadrant(sX.getRGB(x,y+1),sY.getRGB(x,y+1))==4)
						   )
					 ||
					   ((img.getRGB(x,y-1)>img.getRGB(x,y))
							   &&
						   ((findQuadrant(sX.getRGB(x,y-1),sY.getRGB(x,y-1))==0)||
							 findQuadrant(sX.getRGB(x,y-1),sY.getRGB(x,y-1))==4)
						   )
//					 ||
//					 ((img.getRGB(x,y+2)>img.getRGB(x,y))&&
//							   ((findQuadrant(sX.getRGB(x,y+2),sY.getRGB(x,y+2))==0)||
//								 findQuadrant(sX.getRGB(x,y+2),sY.getRGB(x,y+2))==4))
//						 ||
//						   ((img.getRGB(x,y-2)>img.getRGB(x,y))&&
//							   ((findQuadrant(sX.getRGB(x,y-2),sY.getRGB(x,y-2))==0)||
//								 findQuadrant(sX.getRGB(x,y-2),sY.getRGB(x,y-2))==4))
					  )img.setRGB(x,y,pixelB);
					break;
				case 1:case 5:
					if(((img.getRGB(x-1,y+1)>img.getRGB(x,y))
							&&
							   ((findQuadrant(sX.getRGB(x-1,y+1),sY.getRGB(x-1,y+1))==1)||
								 findQuadrant(sX.getRGB(x-1,y+1),sY.getRGB(x-1,y+1))==5)
							   )
						 ||
						   ((img.getRGB(x+1,y-1)>img.getRGB(x,y))
								   &&
							   ((findQuadrant(sX.getRGB(x+1,y-1),sY.getRGB(x+1,y-1))==1)||
								 findQuadrant(sX.getRGB(x+1,y-1),sY.getRGB(x+1,y-1))==5)
							   )
//						 ||
//						 ((img.getRGB(x-2,y+2)>img.getRGB(x,y))&&
//								   ((findQuadrant(sX.getRGB(x-2,y+2),sY.getRGB(x-2,y+2))==1)||
//									 findQuadrant(sX.getRGB(x-2,y+2),sY.getRGB(x-2,y+2))==5))
//							 ||
//							   ((img.getRGB(x+2,y-2)>img.getRGB(x,y))&&
//								   ((findQuadrant(sX.getRGB(x+2,y-2),sY.getRGB(x+2,y-2))==1)||
//									 findQuadrant(sX.getRGB(x+2,y-2),sY.getRGB(x+2,y-2))==5))
						  )img.setRGB(x,y,pixelB);
					break;
				case 2:case 6:
					if(((img.getRGB(x-1,y)>img.getRGB(x,y))
							&&
							   ((findQuadrant(sX.getRGB(x-1,y),sY.getRGB(x-1,y))==2)||
								 findQuadrant(sX.getRGB(x-1,y),sY.getRGB(x-1,y))==6)
							   )
						 ||
						   ((img.getRGB(x+1,y)>img.getRGB(x,y))
								   &&
							   ((findQuadrant(sX.getRGB(x+1,y),sY.getRGB(x+1,y))==2)||
								 findQuadrant(sX.getRGB(x+1,y),sY.getRGB(x+1,y))==6)
							   )
//						 ||
//						 ((img.getRGB(x-2,y)>img.getRGB(x,y))&&
//								   ((findQuadrant(sX.getRGB(x-2,y),sY.getRGB(x-2,y))==2)||
//									 findQuadrant(sX.getRGB(x-2,y),sY.getRGB(x-2,y))==6))
//							 ||
//							   ((img.getRGB(x+2,y)>img.getRGB(x,y))&&
//								   ((findQuadrant(sX.getRGB(x+2,y),sY.getRGB(x+2,y))==2)||
//									 findQuadrant(sX.getRGB(x+2,y),sY.getRGB(x+2,y))==6))
						  )img.setRGB(x,y,pixelB);
					break;
				case 3:case 7:
					if(((img.getRGB(x-1,y-1)>img.getRGB(x,y))
							&&
							   ((findQuadrant(sX.getRGB(x-1,y-1),sY.getRGB(x-1,y-1))==2)||
								 findQuadrant(sX.getRGB(x-1,y-1),sY.getRGB(x-1,y-1))==6)
							   )
						 ||
						   ((img.getRGB(x+1,y+1)>img.getRGB(x,y))
								   &&
							   ((findQuadrant(sX.getRGB(x+1,y+1),sY.getRGB(x+1,y+1))==2)||
								 findQuadrant(sX.getRGB(x+1,y+1),sY.getRGB(x+1,y+1))==6)
							   )
//						 ||
//						 ((img.getRGB(x-2,y-2)>img.getRGB(x,y))&&
//								   ((findQuadrant(sX.getRGB(x-2,y-2),sY.getRGB(x-2,y-2))==2)||
//									 findQuadrant(sX.getRGB(x-2,y-2),sY.getRGB(x-2,y-2))==6))
//							 ||
//							   ((img.getRGB(x+1,y+1)>img.getRGB(x,y))&&
//								   ((findQuadrant(sX.getRGB(x+2,y+2),sY.getRGB(x+2,y+2))==2)||
//									 findQuadrant(sX.getRGB(x+2,y+2),sY.getRGB(x+2,y+2))==6))
						  )img.setRGB(x,y,pixelB);
					break;
			}
		}
		ImageIO.write(img,"png",new File(outputFilePath));
	}
	/*	etc.	|2
	 * 			|			1
	 * 			|	15 degrees etiher side			
	 * ------------------------ 0
	 * 			|	15 degrees
	 *			|
	 *			| 		
	 * tan(15)=y/x -> 3.73205070757 * Y= X
	 */			
	private static int findQuadrant(int directionX,int directionY){
		int quadrant;
		if(directionX>=0){
			if(directionY>=0){
				if(directionY*3.73205080757<directionX)quadrant=0;
				else if(directionX*3.73205080757<directionY)quadrant=2;
				else quadrant=1;
			}
			else{
				directionY=-directionY;
				if(directionY*3.73205080757<directionX)quadrant=0;
				else if(directionX*3.73205080757<directionY)quadrant=6;
				else quadrant=7;
			}
		}
		else{
			directionX=-directionX;
			if(directionY>=0){
				if(directionY*3.73205080757<directionX)quadrant=4;
				else if(directionX*3.73205080757<directionY)quadrant=2;
				else quadrant=3;
			}
			else{
				directionY=-directionY;
				if(directionY*3.73205080757<directionX)quadrant=4;
				else if(directionX*3.73205080757<directionY)quadrant=6;
				else quadrant=5;
			}
		}
		return quadrant;
	}
	
	
	private static void twoMedianThresholdFiltering(String inputFilePath,String outputFilePath,double median,double median2,int layers) throws IOException{
		BufferedImage img=ImageIO.read(new File(inputFilePath));
		int height=img.getHeight(),width=img.getWidth();
		List<Integer> values=new ArrayList<>();
		//graph(values);
		for(int i=0;i<width;i++)for(int k=0;k<height;k++)values.add(img.getRGB(i,k));
		Collections.sort(values);
		int threeQuartersMedian=values.get((int)(height*width*(median)));
		int lowerMedian=values.get((int)(height*width*median2));
		for(int i=layers;i<width-layers;i++)for(int k=layers;k<height-layers;k++){
			if(img.getRGB(i,k)<threeQuartersMedian){
				boolean adjacent=false;
				for(int a=-layers;a<layers+1;a++)for(int b=-layers;b<layers+1;b++){
					if(img.getRGB(i+a,k+b)>threeQuartersMedian)adjacent=true;
				}
				if(adjacent&&img.getRGB(i,k)>lowerMedian)img.setRGB(i,k,threeQuartersMedian+1);
				else img.setRGB(i,k,pixelB);
			}
		}
		ImageIO.write(img,"png",new File(outputFilePath));
	}
	/*
	 * unfinished
	 */
	private static void graph(List<Integer> values){
		BufferedImage img=new BufferedImage(800,600,BufferedImage.TYPE_INT_ARGB);
		for(int i=0;i<800;i++)for(int k=0;k<600;k++){
			
		}
	}
	private static void medianThresholdFiltering(String inputFilePath,String outputFilePath,double median) throws IOException{
		BufferedImage img=ImageIO.read(new File(inputFilePath));
		int height=img.getHeight(),width=img.getWidth();
		List<Integer> values=new ArrayList<>();
		for(int i=0;i<width;i++)for(int k=0;k<height;k++)values.add(img.getRGB(i,k));
		Collections.sort(values);
		int threeQuartersMedian=values.get((int)(height*width*(median)));
		for(int i=0;i<width;i++)for(int k=0;k<height;k++){
			if(img.getRGB(i,k)<threeQuartersMedian)img.setRGB(i,k,pixelB);
		}
		ImageIO.write(img,"png",new File(outputFilePath));
	}
	
	private static void sobelEdgeDetection(String inputFilePath,String outputFilePath) throws IOException{
		BufferedImage img=ImageIO.read(new File(inputFilePath));
		int height=img.getHeight(),width=img.getWidth();
		int[]kernalY=new int[]{1,2,1,0,0,0,-1,-2,-1};
		int[]kernalX=new int[]{-1,0,1,-2,0,2,-1,0,1};
		BufferedImage result=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		for(int x=1;x<width-1;x++)for(int y=1;y<height-1;y++){
			int sumX=0,placeX=0;
			int sumY=0,placeY=0;
			for(int i=-1;i<2;i++)for(int k=-1;k<2;k++){
				sumX+=((img.getRGB(x+i,y+k))/9.0)*(kernalX[placeX++]);
			}
			for(int i=-1;i<2;i++)for(int k=-1;k<2;k++){
				sumY+=((img.getRGB(x+i,y+k))/9.0)*(kernalY[placeY++]);
			}
			result.setRGB(x, y,(int)Math.sqrt(sumX*sumX+sumY*sumY));
			// arctan ( sumY/sumX radians or degrees to find direction )
		}
		ImageIO.write(result,"png",new File(outputFilePath));
	}
	private static void sobelEdgeDetectionY(String inputFilePath,String outputFilePath) throws IOException{
		BufferedImage img=ImageIO.read(new File(inputFilePath));
		int height=img.getHeight(),width=img.getWidth();
		int[]kernal=new int[]{1,2,1,0,0,0,-1,-2,-1};
		BufferedImage result=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		for(int x=1;x<width-1;x++)for(int y=1;y<height-1;y++){
			int sum=0,place=0;
			for(int i=-1;i<2;i++)for(int k=-1;k<2;k++){
				sum+=((img.getRGB(x+i,y+k))/9.0)*(kernal[place++]);
			}
			result.setRGB(x, y,sum);
		}
		ImageIO.write(result,"png",new File(outputFilePath));
	}
	private static void sobelEdgeDetectionX(String inputFilePath,String outputFilePath) throws IOException{
		BufferedImage img=ImageIO.read(new File(inputFilePath));
		int height=img.getHeight(),width=img.getWidth();
		int[]kernal=new int[]{-1,0,1,-2,0,2,-1,0,1};
		BufferedImage result=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		for(int x=1;x<width-1;x++)for(int y=1;y<height-1;y++){
			int sum=0,place=0;
			for(int i=-1;i<2;i++)for(int k=-1;k<2;k++){
				sum+=((img.getRGB(x+i,y+k))/9.0)*(kernal[place++]);
			}
			result.setRGB(x, y,sum);
		}
		ImageIO.write(result,"png",new File(outputFilePath));
	}
	
	private static void mirrorImage(String inputFilePath,String outputFilePath) throws IOException{
		BufferedImage img=ImageIO.read(new File(inputFilePath));
		int height=img.getHeight(),width=img.getWidth();
		BufferedImage result=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		for(int x=0;x<width;x++)for(int y=0;y<height;y++){
			result.setRGB((width-1)-x,y,img.getRGB(x, y));
		}
		ImageIO.write(result,"png",new File(outputFilePath));
	}
	private static void invertXY(String inputFilePath,String outputFilePath) throws IOException{
		BufferedImage img=ImageIO.read(new File(inputFilePath));
		int height=img.getHeight(),width=img.getWidth();
		//BufferedImage result=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		//									    V swapped
		BufferedImage result=new BufferedImage(height,width,BufferedImage.TYPE_INT_RGB);
		for(int x=0;x<width;x++)for(int y=0;y<height;y++){
			result.setRGB(y,x,img.getRGB(x, y));
		}
		ImageIO.write(result,"png",new File(outputFilePath));
	}
	
	private static void gaussianBlur(String inputFilePath,String outputFilePath) throws IOException{
		BufferedImage img=ImageIO.read(new File(inputFilePath));
		int height=img.getHeight(),width=img.getWidth();
		int[]kernal=new int[]{1,2,1,2,4,2,1,2,1};
		BufferedImage result=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		for(int x=1;x<width-1;x++)for(int y=1;y<height-1;y++){
			int sum=0,place=0;
			for(int i=-1;i<2;i++)for(int k=-1;k<2;k++){
				sum+=((img.getRGB(x+i,y+k))/9.0)*(kernal[place++]/(16.0/9));
			}
			result.setRGB(x, y,sum);
		}
		ImageIO.write(result,"png",new File(outputFilePath));
	} 
	
	private static void meanBlur(String inputFilePath,String outputFilePath) throws IOException{
		BufferedImage img=ImageIO.read(new File(inputFilePath));
		int height=img.getHeight(),width=img.getWidth();
		BufferedImage result=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		for(int x=1;x<width-1;x++)for(int y=1;y<height-1;y++){
			int sum=0;
			for(int i=-1;i<2;i++)for(int k=-1;k<2;k++)
				sum+=img.getRGB(x+i,y+k)/9;
			
			result.setRGB(x, y,sum);
		}
		ImageIO.write(result,"png",new File(outputFilePath));
	}
	private static void greyScaleAnImage(String inputFilePath,String outputFilePath) throws IOException{
		BufferedImage coloredImage=ImageIO.read(new File(inputFilePath));
		ImageFilter filter=new GrayFilter(true,50);
		ImageProducer producer=new FilteredImageSource(coloredImage.getSource(),filter);
		Image img=Toolkit.getDefaultToolkit().createImage(producer);
		
		BufferedImage result=new BufferedImage(img.getWidth(null),img.getHeight(null),
				BufferedImage.TYPE_INT_RGB);
		Graphics g=result.createGraphics();
		g.drawImage(img,0,0,null);
		g.dispose();
				
		
		ImageIO.write(result,"png",new File(outputFilePath));
	}
}
