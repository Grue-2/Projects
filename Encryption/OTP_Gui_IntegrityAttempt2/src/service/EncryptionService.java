package service;

import java.io.UnsupportedEncodingException;

public class EncryptionService {
	public static StringPair encrypt(String plainText, String key) {
		try {
			StringBuilder tempText = new StringBuilder(plainText).
					append("#HASH").append(Java_SHA1.hash(plainText));
			pad(tempText);
			
			StringBuilder tempKey = new StringBuilder(key);
			
			byte[] ctBytes = scramble(tempText,tempKey);
			
			oneTimePad(ctBytes, tempKey);
			
			return new StringPair(new String(ctBytes,"ISO-8859-1"),tempKey.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public static StringPair decrypt(String cipherText, String key) throws Exception {
		try {
			byte[] cipherBytes = cipherText.getBytes("ISO-8859-1");
			StringBuilder tempKey = new StringBuilder(key);
			
			oneTimePad(cipherBytes, tempKey);
			
			descramble(cipherBytes, tempKey);

			String pt = new String(cipherBytes, "ISO-8859-1");
			
			int index = pt.indexOf("#PAD");
			if(index < 0)
				throw new Exception("Decryption error.");
			
			pt = pt.substring(0,index);
			
			String[] msgAndHash = pt.split("#HASH");
			
			if(!Java_SHA1.checkPair(msgAndHash[0], msgAndHash[1]))
				throw new Exception("Decryption error.");
			
			return new StringPair(msgAndHash[0],tempKey.toString());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static void pad(StringBuilder inputString) {
		inputString.append("#PAD");
		
		int i = 1;
		{
			int tLen = inputString.length();
		
			while(tLen > i)
				i *= 2;
		}
		while(inputString.length() < i)
			inputString.append("0");
	}
	private static void oneTimePad(byte[] ctBytes, StringBuilder tempKey) {
		int used = 0;
		for(int i = 0, len = ctBytes.length * 8; i < len; ++i) {
			ctBytes[i / 8] = setBit(i, ctBytes[i / 8],
					(tempKey.charAt(used++) == '1')^(getBit(i,ctBytes[i/8]) > 0));
		}
		tempKey = new StringBuilder(tempKey.substring(used));
	}
	private static byte[] scramble(StringBuilder text, StringBuilder key) {
		try {	
			byte[] temp = text.toString().getBytes("ISO-8859-1");

			for(int chunkSize = 2,len = temp.length * 8; chunkSize <= len; chunkSize *= 2) {
				int holdSize = (int) Math.ceil(chunkSize / 8.0);
				for(int bitPlace = 0; bitPlace < len; bitPlace += chunkSize) {
					
					if(key.charAt(key.length() - 1) == '1') {
						byte[] hold = new byte[holdSize];
						int jLen = chunkSize /2;
						for(int j = 0; j < jLen; ++j) {
							hold[j/8] = setBit(j,hold[j/8],getBit((bitPlace+j),temp[(bitPlace+j)/8]) > 0);
							temp[(bitPlace+j)/8] = setBit((bitPlace+j),temp[(bitPlace+j)/8],getBit((j+jLen+bitPlace),temp[(j+jLen+bitPlace)/8]) > 0);
						}
						for(int j = 0; j < jLen; ++j)
							temp[(j+jLen+bitPlace)/8] = setBit((j+jLen+bitPlace),temp[(j+jLen+bitPlace)/8],getBit(j,hold[j/8]) > 0);
					}
					
					key.setLength(key.length() - 1);
				}
			}
			
			return temp;
		} catch (UnsupportedEncodingException e) {e.printStackTrace();}
		
		return null;
	}
	private static void descramble(byte[] textBytes, StringBuilder key) {
		int keyUsagePlusOne = textBytes.length * 8;
		
		for(int chunkSize = keyUsagePlusOne, len = keyUsagePlusOne, keyIndex = key.length() - keyUsagePlusOne + 1
			; chunkSize > 1; chunkSize /= 2)
			for(int bitPlace = len - chunkSize; bitPlace >= 0; bitPlace -= chunkSize) {
				int size = (int) Math.ceil(chunkSize / 8.0);

				if(key.charAt(keyIndex++) == '1') {
					byte[] hold = new byte[size];
					int jLen = chunkSize /2;
					for(int j = 0; j < jLen; ++j) {
						hold[j/8] = setBit(j,hold[j/8],getBit((bitPlace+j),textBytes[(bitPlace+j)/8]) > 0);
						textBytes[(bitPlace+j)/8] = setBit((bitPlace+j),textBytes[(bitPlace+j)/8],getBit((j+jLen+bitPlace),textBytes[(j+jLen+bitPlace)/8]) > 0);
					}
					for(int j = 0; j < jLen; ++j)
						textBytes[(j+jLen+bitPlace)/8] = setBit((j+jLen+bitPlace),textBytes[(j+jLen+bitPlace)/8],getBit(j,hold[j/8]) > 0);
				}
			}
		
		key.setLength(key.length() - keyUsagePlusOne + 1);
	}
	
	private static byte setBit(int bitNum, byte byteToBeAltered, boolean setTo1) {
		if(setTo1) {
			switch(bitNum % 8) {
			case 7:
				return (byte)(byteToBeAltered | 1);
			case 6:
				return (byte)(byteToBeAltered | 2);
			case 5:
				return (byte)(byteToBeAltered | 4);
			case 4:
				return (byte)(byteToBeAltered | 8);
			case 3:
				return (byte)(byteToBeAltered | 16);
			case 2:
				return (byte)(byteToBeAltered | 32);
			case 1:
				return (byte)(byteToBeAltered | 64);
			default:
				return (byte)(byteToBeAltered | 128);
			}
		}
		else {
			switch(bitNum % 8) {
			case 7:
				return (byte)(byteToBeAltered & 0b11111110);
			case 6:
				return (byte)(byteToBeAltered & 0b11111101);
			case 5:
				return (byte)(byteToBeAltered & 0b11111011);
			case 4:
				return (byte)(byteToBeAltered & 0b11110111);
			case 3:
				return (byte)(byteToBeAltered & 0b11101111);
			case 2:
				return (byte)(byteToBeAltered & 0b11011111);
			case 1:
				return (byte)(byteToBeAltered & 0b10111111);
			default:
				return (byte)(byteToBeAltered & 0b01111111);
			}
		}
	}
	
	private static byte getBit(int bitNum, byte byteToBeRead) {
		switch(bitNum % 8) {
		case 7:
			return (byte)(byteToBeRead & 1);
		case 6:
			return (byte)(byteToBeRead & 2);
		case 5:
			return (byte)(byteToBeRead & 4);
		case 4:
			return (byte)((byteToBeRead & 8));
		case 3:
			return (byte)(byteToBeRead & 16);
		case 2:
			return (byte)(byteToBeRead & 32);
		case 1:
			return (byte)(byteToBeRead & 64);
		default:
			return (byte) ((byte)(byteToBeRead & 128) == -128?1:0);
		}
	}
}
