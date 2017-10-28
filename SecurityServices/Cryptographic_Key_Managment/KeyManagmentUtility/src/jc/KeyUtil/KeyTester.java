package jc.KeyUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Scanner;

public class KeyTester {
	public static boolean testSuite(String fileName) throws FileNotFoundException{
		int result=0;
		System.out.println("\nStarting tests for randomness a=.01\n"
						 + "Can fail one small test and continue.\n\n");
		System.out.println("\nbyte test\n--------");
		result+=chiSquareTest(fileName);
		System.out.println("\nserial test(1-2 bit)\n--------");
		result+=serialTest(fileName); // two tests
		System.out.println("\nautocorrelation test (may take a 3-5 min)\n--------");
		result+=autocorrelationTest(fileName); // 2 point weight
		System.out.println("\nruns test (takes 30min-45min)\n---------");
		if(runsTest(fileName,(int)3E8))result+=2;
		return result>5; 
	}
	private static boolean runsTest(String inputFile,int n) throws FileNotFoundException{
		if(n>Integer.MAX_VALUE){
			System.out.println("Test only functions for values under Integer.MAX_VALUE java");
			return false;
		}
		System.out.println("runsTest- calculating k");
		int k=findK(n);
		if(k<0){
			System.out.println("Can't find file length for some reason.");
			return false;
		}
		System.out.println("runsTest- setting up.");
		int[] bi=new int[k],gi=new int[k];
		StringBuilder holdB=new StringBuilder("11"),holdG=new StringBuilder("00");
		for(int i=0;i<k;i++){
			holdB.append("1");
			holdG.append("0");
		}
		String constBlock="1"+holdG.substring(2)+"1",constGap="0"+holdB.substring(2)+"0";
		Scanner fs=new Scanner(new File(inputFile));fs.useDelimiter("");
		int ncheck=0;
		System.out.println("runsTest- executing test.");
		while(fs.hasNext()){
			String x=fs.next();while(fs.hasNext()&&!(x.equals("0")||x.equals("1")))x=fs.next();
			if(x.equals("0")||x.equals("1")){
				// its now off by one but won't show up at the start
				if(++ncheck%1000000==0)System.out.println(ncheck/1000000+"E6 bits processed");
				holdB.deleteCharAt(0);holdG.deleteCharAt(0);
				holdB.append(x);holdG.append(x);
				String holdStringB=holdB.toString(),holdStringG=holdG.toString(),compareBlock=constBlock,
						compareGap=constGap;
				for(int i=k;i>0;i--){
					if(holdStringG.equals(compareGap))gi[i-1]++;
					else if(holdStringB.equals(compareBlock))bi[i-1]++;
					holdStringG=holdStringG.substring(1);holdStringB=holdStringB.substring(1);
					compareBlock=compareBlock.substring(0,1)+compareBlock.substring(2);
					compareGap=compareGap.substring(0,1)+compareGap.substring(2);
				}
			}
		}
		System.out.println(ncheck+" vs "+n+" assumed");
		fs.close();
		// artificial last bump onto  -- yes yes I know its duplicate code but this sucks otherwise as well algorithmically xD
		holdB.deleteCharAt(0);holdB.append("1");
		holdG.deleteCharAt(0);holdG.append("0");
		String holdStringB=holdB.toString(),holdStringG=holdG.toString(),compareBlock=constBlock,
				compareGap=constGap;
		for(int i=k;i>0;i--){
			if(holdStringG.equals(compareGap))gi[i-1]++;
			else if(holdStringB.equals(compareBlock))bi[i-1]++;
			holdStringG=holdStringG.substring(1);holdStringB=holdStringB.substring(1);
			compareBlock=compareBlock.substring(0,1)+compareBlock.substring(2);
			compareGap=compareGap.substring(0,1)+compareGap.substring(2);
		}
		double x=0;
		for(int i=1;i<=k;i++){
			double ei=(n-i+3)/(Math.pow(2,i+2));
			x+=Math.pow(bi[i-1]-ei,2)/ei+Math.pow(gi[i-1]-ei,2)/ei;
		}
		boolean pass=x<68.71;
		DecimalFormat f=new DecimalFormat("#.00");
		System.out.println((pass?"Passed":"Failed")+" runs test with "+f.format(x)+" "+(pass?"<":">=")+" 68.71 (a=.01 value for n=3E8)");
		return pass;
	}
	private static int findK(int n){
		int max=-1,i=1;
		while(true)
			if(((n-i+3)/(Math.pow(2,(i++)+2)))<5)
				break;
		return i-2;
	}
	private static int autocorrelationTest(String inputFile) throws FileNotFoundException{
		return autocorrelationTest(inputFile,5);
	}
	private static int autocorrelationTest(String inputFile,int shift) throws FileNotFoundException{
		Scanner scan=new Scanner(new File(inputFile));scan.useDelimiter("");
		Scanner shiftScan=new Scanner(new File(inputFile));shiftScan.useDelimiter("");
		int d=shift,A=0,n=shift;
		for(int i=0;i<d;i++){
			if(shiftScan.hasNext()){
				String a=shiftScan.next();
				while(!a.equals("1")&&!a.equals("0"))a=shiftScan.next();
			}
			else{
				System.out.println("Need at least n="+(10+d)+" to run autocorrelation test");
				scan.close();shiftScan.close();
				return 0;
			}
		}
		while(shiftScan.hasNext()){
			if(n%8000000==0)System.out.println(n/8000000+"E6 bytes read for autocorrelation test.");
			String a=scan.next(),b=shiftScan.next();
			while( !(a.equals("0")||a.equals("1")) ){
				if(scan.hasNext())a=scan.next();
				else break;
			}
			while( !(b.equals("0")||b.equals("1")) ){
				if(shiftScan.hasNext())b=shiftScan.next();
				else break;
			}
			n++;
			if(!a.equals(b))A++;
		}
		scan.close();shiftScan.close();
		if(n<10+d){
			System.out.println("Need at least n="+(10+d)+" to run autocorrelation test");
			return 0;
		}
		double rootNminusDTerm=Math.pow(n-d,0.5);
		double result=(A/rootNminusDTerm-(n-d)/2/rootNminusDTerm)*2;
		boolean pass=Math.abs(result)<=2.57;
		System.out.println((pass?"Passed":"Failed")+" autocorrelation test with |"+result+"| "+(pass?"<=":">")+" 2.57");
		return pass?2:0;
	}
	private static int serialTest(String inputFile) throws FileNotFoundException{
		Scanner fs=new Scanner(new File(inputFile));fs.useDelimiter("");
		int[] data=new int[7];// n,0,1,00,01,10,11
		StringBuilder hold=new StringBuilder("");
		while(fs.hasNext()){
			String x=fs.next();
			if(x.equals("0")){data[1]++;hold.append(x);data[0]++;}
			else if(x.equals("1")){data[2]++;hold.append(x);data[0]++;}
			if(hold.length()==2){
				if(hold.toString().equals("00"))data[3]++;
				else if(hold.toString().equals("01"))data[4]++;
				else if(hold.toString().equals("10"))data[5]++;
				else data[6]++;
				hold.deleteCharAt(0);
			}
		}
		fs.close();
		if(data[0]<21){
			System.out.println("Need at least n=21 to run test, want n>>10000 to be useful.");
			return 0;
		}
		double bitChi=Math.pow(data[1]-data[2],2)/data[0];
		boolean bitResult=bitChi<=6.635;
		DecimalFormat f=new DecimalFormat("#.00");
		System.out.println((bitResult?"Passed":"failed")+" 1-bit test a=.01 with "+f.format(bitChi)+" "+(bitResult?"<=":">")+" 6.635");
		double twoBitChi=1+(
								(Math.pow(data[3],2)/(data[0]-1)
								+Math.pow(data[4],2)/(data[0]-1)
								+Math.pow(data[5],2)/(data[0]-1)
								+Math.pow(data[6],2)/(data[0]-1)
								)*4
							)
						  -(Math.pow(data[1],2)/data[0]
						   +Math.pow(data[2],2)/data[0])*2;	
		boolean twoBitResult=twoBitChi<=9.21;
		System.out.println((twoBitResult?"Passed":"failed")+" 2-bit serial test a=.01 with "+f.format(twoBitChi)+" "+(twoBitResult?"<=":">")+" 9.21");
		int result=(bitResult?1:0)+(twoBitResult?1:0);
		return result;
	}
	private static int chiSquareTest(String fileName) throws FileNotFoundException{
		Scanner fs=new Scanner(new File(fileName));
		int data[]=new int[256],count=0;
		StringBuilder file;
		while(fs.hasNext()){
			file=new StringBuilder(fs.next());
			while(file.length()%8!=0)file.setLength(file.length()-1);
			while(file.length()!=0){
				String chunk=file.substring(0,8);file=new StringBuilder(file.substring(8));
				int result=0;
				for(int i=0,value=128;i<8;i++,value/=2)if(chunk.charAt(i)=='1')result+=value;
				data[result]++;
				count++;
			}
		}
		double expected=count/256.0;
		double chiSquared=0;
		for(int d:data)chiSquared+=Math.pow(d-expected,2)/expected;
		boolean pass=chiSquared<310.4574;
		DecimalFormat f=new DecimalFormat("#.00");
		System.out.println("chi square result: "+f.format(chiSquared)+" vs. 310.46 passes?: "+pass);
		return pass?1:0;
	}
}
