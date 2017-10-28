package jc.KeyUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class KeyEater {
	public static final int NO_BINARY_KEY=1,GOOD=0,NO_AES=2;

	private static final float VERSION=.05f;
	public static int eat()throws IOException, InvalidKeyException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
		boolean bad=true;
		File input=null;
		File[] files=new File(".").listFiles();
		Arrays.sort(files);
		for(File f:files){
			if(f.toString().contains("KeyFile")&&!f.toString().contains("asBinaryString")){
				bad=false;
				input=f;
			}
			if(!bad)break;
		}
		if(bad){
			return NO_BINARY_KEY;
		}
		//	Decrypt AES during key eating process
		try{
			AES.decrypt(input.toString(),"temp.bin");
			input=new File("temp.bin");
		}catch(Exception e){
			return NO_AES;
		}
		//
		InputStream fs=new FileInputStream(input);
		PrintWriter pw=new PrintWriter(new FileOutputStream(new File("BinaryStringFile.txt")));
		byte[] buf=new byte[1];
		long i=0;
		while(fs.read(buf)!=-1){
				pw.append(byteToString(buf[0]));
				if(++i%100000==0)System.out.println(i+" bytes converted so far.");
			}
		pw.close();
		fs.close();
		input.delete();
		return GOOD;
	}
	private static String byteToString(byte b){
		StringBuilder result=new StringBuilder();
		int place=8,mask=128;
		while(place-->0){
			result.append((b&mask)==0?"0":"1");
			mask/=2;
		}
		result.append("\n");
		return result.toString();
	}
}
