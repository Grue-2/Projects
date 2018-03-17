package service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Java_SHA1 {
	public static boolean checkPair(String pt, String hash) {
		return hash(pt).equals(hash);
	}
	
	public static String hash(String inputString) {
		try {
			byte[] temp = inputString.getBytes("ISO-8859-1");
			temp = hash(temp);
			return new String(temp,"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static byte[] hash(byte[] inputBytes) {
		try {
			return MessageDigest.getInstance("SHA1").digest(inputBytes);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
}
