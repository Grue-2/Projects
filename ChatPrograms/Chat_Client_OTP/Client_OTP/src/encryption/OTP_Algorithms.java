package encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import key_managment.EntropyProvider;

public class OTP_Algorithms {
	
	private OTP_Algorithms(){}
	
	public static String sha1(String input) throws NoSuchAlgorithmException
	{
		input = input.toLowerCase();
		byte[] result = MessageDigest.getInstance("SHA1").digest(input.getBytes());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < result.length; ++i)
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		return sb.toString();
	}
	
	public static byte[] pad(byte[] text,EntropyProvider key) throws Exception
	{
		byte[] result = new byte[text.length];
		
		for(int i = 0, len = result.length; i < len; ++i)
			result[i] = (byte)(text[i] ^ key.provide(true));
		
		return result;
	}
	
	public static byte[] scramble(byte[] text,EntropyProvider key) throws Exception
	{
		byte[] result = new byte[text.length];
		
		int leftToPlace = result.length * 8;
		boolean[] placed = new boolean[leftToPlace];
		
		for(int i = 0, iterations = leftToPlace; i < iterations;
				++i, --leftToPlace)
		{
			int place = rejectionSample(leftToPlace, key);
			
			for(int z = 0; z <= place; ++z)
				if (placed[z])
					++place;
			
			placed[place] = true;
			
			if ((text[i >> 3] & (128 >> (i % 8))) > 0)
				result[place >> 3] |= 128 >> (place % 8);  
		}
		
		return result;
	}
	
	public static byte[] descramble(byte[] text,EntropyProvider key) throws Exception
	{
		byte[] result = new byte[text.length];
		
		int leftToPlace = result.length * 8;
		boolean[] placed = new boolean[leftToPlace];
		
		for(int i = 0, iterations = leftToPlace; i < iterations;
				++i, --leftToPlace)
		{
			int place = rejectionSample(leftToPlace, key);
			
			for(int z = 0; z <= place; ++z)
				if (placed[z])
					++place;
			
			placed[place] = true;
			
			if ((text[place >> 3] & (128 >> place % 8)) > 0)
				result[i >> 3] |= 128 >> (i % 8); 
		}
		
		return result;
	}

	private static int rejectionSample(int n,EntropyProvider key) throws Exception
	{		
		int result, nMinusOne = n-1;
		do{
			result = 0;
			int i = 1, len = n, place = 0;
			byte currentByte = key.provide(false);
			
			while (len > 0)
			{
				if(place%8 == 0){
					currentByte = key.provide(false);
					place = 0;
				}
					
				if ((currentByte & (1 << place++)) == 0)
					result += i;
				
				len /= 2;
				i *= 2;
			}
		}while (result > nMinusOne);
		
		return result;
	}
}
