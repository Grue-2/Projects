/*
JC

10/28/2017

Commenting update for github I guess.
 */
package jc.RSA;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class FrontEndRSA {
	private final static  SecureRandom rng;
	static{rng = new SecureRandom();}
	private static final int SIZE = 2048;

	/**
	 * Returns String key's in array with [0] being public and [1] being private
	 * key.
	 */
	public static String[] generatePair() throws NoSuchAlgorithmException {
		// create key pair
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(SIZE, rng);
		
		KeyPair kp = keyGen.generateKeyPair();
		
		Encoder e = Base64.getEncoder();
		
		return new String[] { e.encodeToString(kp.getPublic().getEncoded()),
				e.encodeToString(kp.getPrivate().getEncoded()) };
	}

	public static String encryptWithPublic(String publicKey, String message)
			throws UnsupportedEncodingException, InvalidKeySpecException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		message = MessageCompacting.encodeMessageToLegalBase64(message + "             ");
		byte[] b = Base64.getDecoder().decode(publicKey.getBytes("utf-8"));
		byte[] pt = Base64.getDecoder().decode(message.getBytes("utf-8"));
		PublicKey key = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(b));
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.PUBLIC_KEY, key);
		byte[] ct = cipher.doFinal(pt);
		return Base64.getEncoder().encodeToString(ct);
	}

	public static String decryptWithPrivate(String privateKey, String cipherText)
			throws UnsupportedEncodingException, InvalidKeySpecException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] b = Base64.getDecoder().decode(privateKey.getBytes("utf-8"));
		byte[] ct = Base64.getDecoder().decode(cipherText.getBytes("utf-8"));
		PrivateKey key = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(b));
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.PRIVATE_KEY, key);
		byte[] pt = cipher.doFinal(ct);
		String result = Base64.getEncoder().encodeToString(pt).replaceAll("=", "");
		return MessageCompacting.decodeMessageFromLegalBase64(result);
	}
}
