/*
JC

11/01/2017

@version GitHub-date
 */

package jc.KeyUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public interface AES {
	// public static void main(String...args) throws NoSuchAlgorithmException,
	// FileNotFoundException, IOException, InvalidKeyException,
	// ClassNotFoundException, BadPaddingException
	// {
	// //encrypt("KeyFile2", "KeyFile2c");
	// //encrypt("KeyFile1", "KeyFile1c");
	// }

	public static void generateAESKeyFile() throws NoSuchAlgorithmException, FileNotFoundException, IOException {
		SecureRandom rng = new SecureRandom();
		rng.nextBytes(new byte[20]);
		KeyGenerator gen = KeyGenerator.getInstance("AES");
		gen.init(128, rng);
		SecretKey key = gen.generateKey();
		ObjectOutputStream writer = new ObjectOutputStream(new java.io.FileOutputStream(new File("AESKey.bin")));
		writer.writeObject(key);
		writer.close();
	}

	public static SecretKey getAESKey() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream reader = new ObjectInputStream(new java.io.FileInputStream(new File("AESKey.bin")));
		Object key = reader.readObject();
		reader.close();
		return (SecretKey) key;
	}

	public static void encrypt(String inputFileName, String outputFileName)
			throws NoSuchAlgorithmException, InvalidKeyException, FileNotFoundException, ClassNotFoundException,
			IOException, javax.crypto.NoSuchPaddingException, javax.crypto.IllegalBlockSizeException,
			BadPaddingException, java.security.InvalidAlgorithmParameterException {
		Cipher c = Cipher.getInstance("AES");
		c.init(1, getAESKey());
		byte[] b_ct = c.doFinal(Files.readAllBytes(Paths.get(inputFileName, new String[0])));
		Files.write(Paths.get(outputFileName, new String[0]), b_ct, new OpenOption[0]);
		new File(inputFileName).delete();
	}

	public static void decrypt(String inputFileName, String outputFileName)
			throws IOException, InvalidKeyException, ClassNotFoundException, NoSuchAlgorithmException,
			javax.crypto.NoSuchPaddingException, javax.crypto.IllegalBlockSizeException, BadPaddingException,
			java.security.InvalidAlgorithmParameterException {
		byte[] b_ct = Files.readAllBytes(Paths.get(inputFileName, new String[0]));
		Cipher c = Cipher.getInstance("AES");
		c.init(2, getAESKey());
		byte[] b_pt = c.doFinal(b_ct);
		Files.write(Paths.get(outputFileName, new String[0]), b_pt, new OpenOption[0]);
		new File(inputFileName).delete();
	}
}