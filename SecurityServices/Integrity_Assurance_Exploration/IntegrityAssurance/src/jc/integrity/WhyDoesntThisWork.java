package jc.integrity;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import jtan189.passwordSec.PasswordHash;

public class WhyDoesntThisWork {
	public static void main(String[]args) throws NoSuchAlgorithmException, InvalidKeySpecException{

		StringBuilder message=new StringBuilder("testing stuff");
		String goodHash=PasswordHash.createHash(message.toString());
		
		
		StringBuilder key1=new StringBuilder("");
		SecureRandom rng=new SecureRandom();rng.nextBytes(new byte[20]);
		for(int i=0;i<10000;i++){
			if(rng.nextInt(2)==0)key1.append("1");
			else key1.append("0");
		}
		StringBuilder key2=new StringBuilder(key1.toString());
		
		System.out.println(message.append(goodHash));
		cryptScramble(message,key1);
		System.out.println(message);
		cryptDeScramble(message,key2);
		System.out.println(message);
		int index=message.toString().indexOf("1000:");
		System.out.println(PasswordHash.validatePassword(message.substring(0,index),goodHash));
		System.out.println(key1.length());
	}
	
	private static void cryptDeScramble(StringBuilder m,StringBuilder k){
		int len=m.length();
		char[] result=new char[len];
		boolean[] filled=new boolean[len];
		for(int i=0;i<len;i++){
			int x=getNumberBetween0AndLengthMinusOne(m.length()-i,k);
			for(int z=0;z<=x;z++)
				if(filled[z]==true)x++;
			result[x]=m.charAt(i);
			filled[x]=true;
		}
		m.setLength(0);
		m.insert(0,String.valueOf(result));
	}	
	
	private static void cryptScramble(StringBuilder m,StringBuilder k){
		int len=m.length();
		StringBuilder result=new StringBuilder("");
		for(int i=0;i<len;i++){

			int hold=getNumberBetween0AndLengthMinusOne(m.length(),k);
			result.append(m.charAt(hold));
			m.deleteCharAt(hold);
		}
		m.insert(0, result);
	}
	private static int getNumberBetween0AndLengthMinusOne(int length,StringBuilder key){
		int keyUse=0,result;
		do{
			result=0;
			int i=1;
			int len=length;
			while(len>0){
				if(key.charAt(keyUse++)=='1')result+=i;
				len/=2;
				i*=2;
			}
		}while(result>length-1);
		key.delete(0,keyUse);
		return result;
	}
	
}
