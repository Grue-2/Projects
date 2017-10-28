package jc.StringMods;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StringMods {
	public static void main(String...args) throws FileNotFoundException{
		Scanner fileScanner=new Scanner(new File("USEDRandomDump2.txt"));
		StringBuilder x=new StringBuilder();
		while(fileScanner.hasNext())x.append(fileScanner.next());
		fileScanner.close();
		for(int i=0,len=x.length();i<len;i+=1000)
			if(i+1000<len)System.out.println(x.substring(i,i+1000));
			else System.out.println(x.substring(i));
	}
}
