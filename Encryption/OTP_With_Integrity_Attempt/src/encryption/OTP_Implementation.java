// 10/28/2017 -JC

package encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
You encrypt using key, and decrypt using otherKey.
 */
public class OTP_Implementation {

	public static String sha1(String input, boolean lowercase) throws NoSuchAlgorithmException {
		if (lowercase)
			input = input.toLowerCase();
		byte[] result = MessageDigest.getInstance("SHA1").digest(input.getBytes());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < result.length; ++i) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	public OTP_Implementation(String newKey, String newOtherKey) {
		if (newKey != null)
			key = new StringBuilder(newKey);
		if (newOtherKey != null)
			otherKey = new StringBuilder(newOtherKey);
	}

	public void setKey(String newKey) {
		key = new StringBuilder(newKey);
	}

	public void setOtherKey(String otherNewKey) {
		otherKey = new StringBuilder(otherNewKey);
	}

	public String getKey() {
		return key.toString();
	}

	public String getOtherKey() {
		return otherKey.toString();
	}

	public String encrypt(String plainText) throws OTP_Encryption_Exception {
		try {
			return binaryStringToTextString(encryptBinaryString(textStringToBinaryString(plainText)));
		} catch (StringIndexOutOfBoundsException e) {
			System.err.println("You ran out of key to encrypt with.");

			throw new OTP_Encryption_Exception("You ran out of key to encrypy with.");
		}
	}

	public String decrypt(String plainText) throws OTP_Encryption_Exception {
		try {
			return binaryStringToTextString(decryptBinaryString(textStringToBinaryString(plainText)));
		} catch (StringIndexOutOfBoundsException e) {
			System.err.println("You ran out of key to decrypt with.");

			throw new OTP_Encryption_Exception("You ran out of key to decrypt with.");
		}
	}

	private StringBuilder key, otherKey;

	private static final char alphabet[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ' ', '.', ',', '"', '\'', ':', ';', '?', '1', '2',
			'3', '4', '5', '6', '7', '8', '9', '0', '!', '#', '(', ')', '-', '+', '<', '_', '=', '>', '[', ']', '{',
			'}', '@', '$', '%', '^', '&', '*', };
	private static final String binaryStringAlphabet[] = { "000000", "000001", "000010", "000011", "000100", "000101",
			"000110", "000111", "001000", "001001", "001010", "001011", "001100", "001101", "001110", "001111",
			"010000", "010001", "010010", "010011", "010100", "010101", "010110", "010111", "011000", "011001",
			"011010", "011011", "011100", "011101", "011110", "011111", "100000", "100001", "100010", "100011",
			"100100", "100101", "100110", "100111", "101000", "101001", "101010", "101011", "101100", "101101",
			"101110", "101111", "110000", "110001", "110010", "110011", "110100", "110101", "110110", "110111",
			"111000", "111001", "111010", "111011", "111100", "111101", "111110", "111111", };

	// does input checking
	private static StringBuilder textStringToBinaryString(String plainText) {
		plainText = plainText.toLowerCase();

		StringBuilder binaryString = new StringBuilder();

		for (int i = 0, length = plainText.length(); i < length; ++i) {
			char temp = plainText.charAt(i);
			boolean found = false;

			for (int j = 0; j < alphabet.length; ++j)
				if (alphabet[j] == temp) {
					binaryString.append(binaryStringAlphabet[j]);
					found = true;
				}

			if (!found) {
				binaryString.append(binaryStringAlphabet[27]);
				// 27 is empty space character in array;
				System.err.println(temp);
				System.err.println("Bad character input. Replacing char with an empty space.");
			}
		}

		return binaryString;
	}

	private StringBuilder encryptBinaryString(StringBuilder plainText) {
		StringBuilder cipherText = new StringBuilder();
		int binaryStringSize = plainText.length();

		for (int i = 0; i < binaryStringSize; ++i)
			cipherText.append(plainText.charAt(i) == key.charAt(i) ? "0" : "1");

		key.delete(0, binaryStringSize);

		return cipherText;
	}

	private StringBuilder decryptBinaryString(StringBuilder cipherText) {
		StringBuilder plainText = new StringBuilder();
		int binaryStringSize = cipherText.length();

		for (int i = 0; i < binaryStringSize; ++i)
			plainText.append(cipherText.charAt(i) == otherKey.charAt(i) ? "0" : "1");

		otherKey.delete(0, binaryStringSize);

		return plainText;
	}

	private static String binaryStringToTextString(StringBuilder binaryText) {
		// Assumes binaryText is a binary string with length%6 == 0.
		StringBuilder readableString = new StringBuilder();

		// 6 is the encoding frame size
		for (int i = 0, length = binaryText.length(); i < length; i += 6) {
			String temp = binaryText.substring(i, i + 6);

			for (int j = 0; j < binaryStringAlphabet.length; ++j)
				if (binaryStringAlphabet[j].equals(temp))
					readableString.append(alphabet[j]);

		}

		return readableString.toString();
	}

	public String padScramble(String text) throws OTP_Encryption_Exception {
		try {
			StringBuilder result = new StringBuilder(text);

			cryptScramble(result, key);

			return result.toString();
		} catch (Exception e) {
			System.err.println("You ran out of key to scramble with.");

			throw new OTP_Encryption_Exception("You ran out of key to scramble with.");
		}
	}

	public String padDescramble(String text) throws OTP_Encryption_Exception {
		try {
			StringBuilder result = new StringBuilder(text);

			cryptDeScramble(result, otherKey);

			return result.toString();
		} catch (Exception e) {
			System.err.println("You ran out of key to descramble with.");

			throw new OTP_Encryption_Exception("You ran out of key to descramble with.");
		}
	}

	private static void cryptDeScramble(StringBuilder m, StringBuilder k) {
		int len = m.length();
		char[] result = new char[len];
		boolean[] filled = new boolean[len];
		for (int i = 0; i < len; ++i) {
			int x = getNumberBetween0AndLengthMinusOne(len - i, k);
			for (int z = 0; z <= x; z++)
				if (filled[z] == true)
					x++;
			result[x] = m.charAt(i);
			filled[x] = true;
		}
		m.setLength(0);
		m.insert(0, String.valueOf(result));
	}

	private static void cryptScramble(StringBuilder m, StringBuilder k) {
		int len = m.length();
		StringBuilder result = new StringBuilder("");
		for (int i = 0; i < len; i++) {
			int hold = getNumberBetween0AndLengthMinusOne(m.length(), k);
			result.append(m.charAt(hold));
			m.deleteCharAt(hold);
		}
		m.insert(0, result);
	}

	private static int getNumberBetween0AndLengthMinusOne(int length, StringBuilder key) {
		key.reverse();
		int keyUse = -1, result;
		do {
			result = 0;
			int i = 1;
			int len = length;
			while (len > 0) {
				if (key.charAt(++keyUse) == '1')
					result += i;
				len /= 2;
				i *= 2;
			}
		} while (result > length - 1);
		key.delete(0, keyUse);
		key.reverse();
		return result;
	}

}