package jc.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class RandOrgSpaceRemover {
	private final static String inFile="dumpRand3.txt",
							   outFile="unspacedDumpRand3.txt";
	
	public static void main(String...args) throws FileNotFoundException{
		StringBuilder sb=new StringBuilder();
		Scanner fileScanner=new Scanner(new File(inFile));
		while(fileScanner.hasNextLine()){
			String line=fileScanner.nextLine();
			Scanner lineScanner=new Scanner(line);
			while(lineScanner.hasNext())sb.append(lineScanner.next());
			sb.append("\n");
			lineScanner.close();
		}
		fileScanner.close();
		PrintWriter fw=new PrintWriter(new File(outFile));
		fw.write(sb.toString());
	}
}
