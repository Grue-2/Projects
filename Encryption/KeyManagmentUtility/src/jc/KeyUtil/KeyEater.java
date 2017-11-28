/*
JC

11/01/2017

@version GitHub-date
 */

package jc.KeyUtil;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import jc.Test_Randomness.Testing_Randomness;

public class KeyEater {
	public static final byte NO_BINARY_KEY = 1, GOOD = 0, NO_AES = 2, FAILED_TEST = 3;

	public static int eat(boolean test)
			throws IOException, InvalidKeyException, ClassNotFoundException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		boolean bad = true;
		File input = null;
		File[] files = new File(".").listFiles();
		Arrays.sort(files);
		for (File f : files) {
			if (f.toString().contains("KeyFile") && !f.toString().contains("asBinaryString")) {
				bad = false;
				input = f;
				break; // TODO: untested change
			}
		}
		if (bad) 
			return NO_BINARY_KEY;
		
		// Decrypt AES during key eating process
		try {
			AES.decrypt(input.toString(), "temp.bin");
			input = new File("temp.bin");
		} catch (Exception e) {
			System.err.println("May also be you have an unencrypted file named KeyFile which\n"
					+ "throws an exception for the decryption function.");
			return NO_AES;
		}

		// Statistical tests
		if (test && !Testing_Randomness.testSuite(input.getAbsolutePath()))
			return FAILED_TEST;

		BufferedInputStream fs = new BufferedInputStream(new FileInputStream(input));
		BufferedWriter pw = new BufferedWriter(new PrintWriter(new FileOutputStream(new File("BinaryStringFile.txt"))));
		int buf;
		long i = 0;
		while ((buf = fs.read()) != -1) {
		
			pw.append(byteToString(buf));
			if (++i % 1000000 == 0)
				System.out.println(i / 1000000 + "e6 bytes expanded so far.");
		}

		pw.close();
		fs.close();
		input.delete();
		return GOOD;
	}

	private static StringBuilder byteToString(int b) {
		StringBuilder result = new StringBuilder();

		int place = 8, mask = 128;

		while (place-- > 0) {
			result.append((b & mask) == 0 ? "0" : "1");
			mask /= 2;
		}
		result.append("\n");
		return result;
	}
}

/*
 * I really wanted to use this thing. But sadly its not where the bottle neck is
 * so its not really helping, and is probably considered more gouache then the
 * other way. Still though I like the idea of having code write my code for me.
 */
// precompute at memory cost ? -- test if faster
/*
 * generated with this string in loop creating beast public static void
 * main(String...args) { System.out.println("switch (b & 0xff)\n{"); int count =
 * -1; for(int i1 = 0; i1 < 2; ++i1) for(int i2 = 0; i2 < 2; ++i2) for(int i3 =
 * 0; i3 < 2; ++i3) for(int i4 = 0; i4 < 2; ++i4) for(int i5 = 0; i5 < 2; ++i5)
 * for(int i6 = 0; i6 < 2; ++i6) for(int i7 = 0; i7 < 2; ++i7) for(int i8 = 0;
 * i8 < 2; ++i8) { System.out.println("case "+(++count)+":");
 * System.out.println("\treturn \""+i1+i2+i3+i4+i5+i6+i7+i8+"\";"); }
 * System.out.println("}"); }
 */
/*
 * switch (b & 0xff) { case 0: return "00000000"; case 1: return "00000001";
 * case 2: return "00000010"; case 3: return "00000011"; case 4: return
 * "00000100"; case 5: return "00000101"; case 6: return "00000110"; case 7:
 * return "00000111"; case 8: return "00001000"; case 9: return "00001001"; case
 * 10: return "00001010"; case 11: return "00001011"; case 12: return
 * "00001100"; case 13: return "00001101"; case 14: return "00001110"; case 15:
 * return "00001111"; case 16: return "00010000"; case 17: return "00010001";
 * case 18: return "00010010"; case 19: return "00010011"; case 20: return
 * "00010100"; case 21: return "00010101"; case 22: return "00010110"; case 23:
 * return "00010111"; case 24: return "00011000"; case 25: return "00011001";
 * case 26: return "00011010"; case 27: return "00011011"; case 28: return
 * "00011100"; case 29: return "00011101"; case 30: return "00011110"; case 31:
 * return "00011111"; case 32: return "00100000"; case 33: return "00100001";
 * case 34: return "00100010"; case 35: return "00100011"; case 36: return
 * "00100100"; case 37: return "00100101"; case 38: return "00100110"; case 39:
 * return "00100111"; case 40: return "00101000"; case 41: return "00101001";
 * case 42: return "00101010"; case 43: return "00101011"; case 44: return
 * "00101100"; case 45: return "00101101"; case 46: return "00101110"; case 47:
 * return "00101111"; case 48: return "00110000"; case 49: return "00110001";
 * case 50: return "00110010"; case 51: return "00110011"; case 52: return
 * "00110100"; case 53: return "00110101"; case 54: return "00110110"; case 55:
 * return "00110111"; case 56: return "00111000"; case 57: return "00111001";
 * case 58: return "00111010"; case 59: return "00111011"; case 60: return
 * "00111100"; case 61: return "00111101"; case 62: return "00111110"; case 63:
 * return "00111111"; case 64: return "01000000"; case 65: return "01000001";
 * case 66: return "01000010"; case 67: return "01000011"; case 68: return
 * "01000100"; case 69: return "01000101"; case 70: return "01000110"; case 71:
 * return "01000111"; case 72: return "01001000"; case 73: return "01001001";
 * case 74: return "01001010"; case 75: return "01001011"; case 76: return
 * "01001100"; case 77: return "01001101"; case 78: return "01001110"; case 79:
 * return "01001111"; case 80: return "01010000"; case 81: return "01010001";
 * case 82: return "01010010"; case 83: return "01010011"; case 84: return
 * "01010100"; case 85: return "01010101"; case 86: return "01010110"; case 87:
 * return "01010111"; case 88: return "01011000"; case 89: return "01011001";
 * case 90: return "01011010"; case 91: return "01011011"; case 92: return
 * "01011100"; case 93: return "01011101"; case 94: return "01011110"; case 95:
 * return "01011111"; case 96: return "01100000"; case 97: return "01100001";
 * case 98: return "01100010"; case 99: return "01100011"; case 100: return
 * "01100100"; case 101: return "01100101"; case 102: return "01100110"; case
 * 103: return "01100111"; case 104: return "01101000"; case 105: return
 * "01101001"; case 106: return "01101010"; case 107: return "01101011"; case
 * 108: return "01101100"; case 109: return "01101101"; case 110: return
 * "01101110"; case 111: return "01101111"; case 112: return "01110000"; case
 * 113: return "01110001"; case 114: return "01110010"; case 115: return
 * "01110011"; case 116: return "01110100"; case 117: return "01110101"; case
 * 118: return "01110110"; case 119: return "01110111"; case 120: return
 * "01111000"; case 121: return "01111001"; case 122: return "01111010"; case
 * 123: return "01111011"; case 124: return "01111100"; case 125: return
 * "01111101"; case 126: return "01111110"; case 127: return "01111111"; case
 * 128: return "10000000"; case 129: return "10000001"; case 130: return
 * "10000010"; case 131: return "10000011"; case 132: return "10000100"; case
 * 133: return "10000101"; case 134: return "10000110"; case 135: return
 * "10000111"; case 136: return "10001000"; case 137: return "10001001"; case
 * 138: return "10001010"; case 139: return "10001011"; case 140: return
 * "10001100"; case 141: return "10001101"; case 142: return "10001110"; case
 * 143: return "10001111"; case 144: return "10010000"; case 145: return
 * "10010001"; case 146: return "10010010"; case 147: return "10010011"; case
 * 148: return "10010100"; case 149: return "10010101"; case 150: return
 * "10010110"; case 151: return "10010111"; case 152: return "10011000"; case
 * 153: return "10011001"; case 154: return "10011010"; case 155: return
 * "10011011"; case 156: return "10011100"; case 157: return "10011101"; case
 * 158: return "10011110"; case 159: return "10011111"; case 160: return
 * "10100000"; case 161: return "10100001"; case 162: return "10100010"; case
 * 163: return "10100011"; case 164: return "10100100"; case 165: return
 * "10100101"; case 166: return "10100110"; case 167: return "10100111"; case
 * 168: return "10101000"; case 169: return "10101001"; case 170: return
 * "10101010"; case 171: return "10101011"; case 172: return "10101100"; case
 * 173: return "10101101"; case 174: return "10101110"; case 175: return
 * "10101111"; case 176: return "10110000"; case 177: return "10110001"; case
 * 178: return "10110010"; case 179: return "10110011"; case 180: return
 * "10110100"; case 181: return "10110101"; case 182: return "10110110"; case
 * 183: return "10110111"; case 184: return "10111000"; case 185: return
 * "10111001"; case 186: return "10111010"; case 187: return "10111011"; case
 * 188: return "10111100"; case 189: return "10111101"; case 190: return
 * "10111110"; case 191: return "10111111"; case 192: return "11000000"; case
 * 193: return "11000001"; case 194: return "11000010"; case 195: return
 * "11000011"; case 196: return "11000100"; case 197: return "11000101"; case
 * 198: return "11000110"; case 199: return "11000111"; case 200: return
 * "11001000"; case 201: return "11001001"; case 202: return "11001010"; case
 * 203: return "11001011"; case 204: return "11001100"; case 205: return
 * "11001101"; case 206: return "11001110"; case 207: return "11001111"; case
 * 208: return "11010000"; case 209: return "11010001"; case 210: return
 * "11010010"; case 211: return "11010011"; case 212: return "11010100"; case
 * 213: return "11010101"; case 214: return "11010110"; case 215: return
 * "11010111"; case 216: return "11011000"; case 217: return "11011001"; case
 * 218: return "11011010"; case 219: return "11011011"; case 220: return
 * "11011100"; case 221: return "11011101"; case 222: return "11011110"; case
 * 223: return "11011111"; case 224: return "11100000"; case 225: return
 * "11100001"; case 226: return "11100010"; case 227: return "11100011"; case
 * 228: return "11100100"; case 229: return "11100101"; case 230: return
 * "11100110"; case 231: return "11100111"; case 232: return "11101000"; case
 * 233: return "11101001"; case 234: return "11101010"; case 235: return
 * "11101011"; case 236: return "11101100"; case 237: return "11101101"; case
 * 238: return "11101110"; case 239: return "11101111"; case 240: return
 * "11110000"; case 241: return "11110001"; case 242: return "11110010"; case
 * 243: return "11110011"; case 244: return "11110100"; case 245: return
 * "11110101"; case 246: return "11110110"; case 247: return "11110111"; case
 * 248: return "11111000"; case 249: return "11111001"; case 250: return
 * "11111010"; case 251: return "11111011"; case 252: return "11111100"; case
 * 253: return "11111101"; case 254: return "11111110"; default: return
 * "11111111"; }
 */
