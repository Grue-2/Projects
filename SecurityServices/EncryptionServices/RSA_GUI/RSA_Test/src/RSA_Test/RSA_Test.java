package RSA_Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

public class RSA_Test {
	private static final int SIZE=2048;
	
	public static void main(String...args) throws Exception{
//		String[] x=generatePair();
//		System.out.println(x[0]+"\n"+x[1]+"\n\n");
//		System.out.println(encryptWithPrivate(x[1],"Base64EncodingSucksButts")+"\n");
//		System.out.println(decryptWithPublic(x[0],encryptWithPrivate(x[1],"Base64EncodingSucksButts")));
		//String publicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvHMbspFj6fVgPGWmFVgNOjxdnu3kTFd/uwAbWQeyvwKkAO4UPxmTny/4fJx+zy2FxXay+Y7FkV8rFdjqrYvxDGKhTsGmBFRxSF+DTYoxjwRfl78gWrTrGPGI8I0hL17N2FppSCLxLQb44sJd8ppLbiugl5NV39RV7jmCOHd6q6joK/8J7EDikKEK90Mho5+DYqy3M4Du0CxJeo5cmCxU861JUKonUFTEEVZU9V4HZQ5ZDLXBNNLyOTMpoKyXj8BCjRPI4Qu4ifPVtiJqeZQo/3QUfCSc49aHN6PI7pluTmyFKP09IrHETi+6Tf25T/KPNeJS8QHMOC5plBLsvfTDlQIDAQAB";
		//String privateKey="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC8cxuykWPp9WA8ZaYVWA06PF2e7eRMV3+7ABtZB7K/AqQA7hQ/GZOfL/h8nH7PLYXFdrL5jsWRXysV2Oqti/EMYqFOwaYEVHFIX4NNijGPBF+XvyBatOsY8YjwjSEvXs3YWmlIIvEtBvjiwl3ymktuK6CXk1Xf1FXuOYI4d3qrqOgr/wnsQOKQoQr3QyGjn4NirLczgO7QLEl6jlyYLFTzrUlQqidQVMQRVlT1XgdlDlkMtcE00vI5MymgrJePwEKNE8jhC7iJ89W2Imp5lCj/dBR8JJzj1oc3o8jumW5ObIUo/T0iscROL7pN/blP8o814lLxAcw4LmmUEuy99MOVAgMBAAECggEBAIXtiJGvokJzkKFb3Fzu5vesTxzUFJvs02oi+Uycm/wD9Gp0yQ7PLorDnOVykuzKfXeIlEmnneFisUwljSMpgXY74kgGd+Y73QaCWt6QiAgMI6PEwUm1qh9gCOJsalkwFzrbhlct/hjyC4+ccwgnf9QcTVVr8GvSi/9x/4oB37x+YiaMjhdD9DYfIpy8y3AmltCqu97lYiC3ioaUuHdq4+wlsM54LuByeUkuNoq86PEJZZHjP4KPSM44Vb5+AOnS1MuoTQg2VWougXs8KY1/7b19NSW7lM8y2dscduP+ZIkSdoGJHpsbkjp8hf28saWvj9cKUhgzx4cZeyjwFK9LHUECgYEA//QmOZky2fpLw4rrf4xFlAgxziUgAQAo6yb9ryXOw9LHmdIdXKvkYMmLqlcG3tT6Oi5CHIKshaK7wInDoTgxFwhI1htOLj02ypw12EcJgIFPrkko2jXkG2q9qT3m51K1ScY+bNQ+OIL+5lnAteN0QVGexAH1MGWfxfR5sPJO3IsCgYEAvHvVW8ensY6TZ/wLFBTKMvDw3Cnlzdsg8XbViqSP5UAm3hS0u2GB7Dr/snvvoVGgcK5jA7rGaqqv2QCj18h04gHc1z5g8zZKp38/H2Ev1wdFoH0dYmlPU4odsTtw9qECP8LHyHTTgXieqk0Ll9t4SzeTzKfIoXqb/nRbbFfURF8CgYEAjDH2EgyI3v6LK0NXdNinb8sBRNHu8r1crFkCEUKbO4WjkOyW6qC4ig87MgVTHRD5Mo8N5UHqL5SQVIjhH9iAgwYlpyjzrL4Y2eewUpOub7XkqCkoeEWWeTIBSsc+R6x04kD1BnVG03tlFVVKChbZIcIMPX55B5ZSaoJbeYFvkrsCgYB6DGHC9YzD78PJsK5nyXKI+/WrSsIQw5UYZk+uYBMBasIASj4hm+kqU6XhKFmqBxYyAOoCrz3uMip+BOkTQC1Xb8dV5YGX248J2N7B7gexYHcYjgup/vEr8iJ58jiaqvWu3ezKA8Q1cSqkshEQuz+wzQM6oWbMEX3TjfrpNBIyVwKBgHl85kymzX/WN8se7a7YeCJV1qjudESRheJb/l6jG3oc1CgzZzQiRPzxbjSHwYb8cJsMDFw/uT3t01GAYFLzeLa7Lvfo4TkoFlURsZ+T494QCLvwcYus2ek78YeuOB3MPp3LBPDIxJ2UiQPuSM3YSicFrHmH/KlGfbSW"+
		//"pd1GDRPG";
		
		//System.out.println(decryptWithPrivate(privateKey,encryptWithPublic(publicKey,"SendMeStuffToDecryptUsingPublicKey")));
		
//		String[] x=generatePair();
//		System.out.println(x[0]+"\n\n"+x[1]);
		
//		String privateKey="MIICXQIBAAKBgQCqS4XOikd2/p7lJKkcQm4y9kTQCa63cMLN4OB+qD1Dh+V11p1Smg4Z2pPBifpzAXyf3HvO9+NlrfJnpHOZe9NnSry0iQngWEBh9WwLEMxFBOcJ8lZcehY9ORmVLI/B+h1kNZDPv0NZMGFa3QviMFGLCZzpkHTf6PeVVtDYiYFTqwIDAQABAoGBAKcOrXYfWtoWEIRTwX4SgQ6HXpwDU51rBf93omM94dO0pdpCrlTRAhJExXwwQju98qbUYbR4o2AlE06vGiQwQAJTiLY327w8XLpuEPtCA21j8bL+qeW0wJKtXFSAMeqKgIH+OO682ntrWkwGVYq68C56J3LIgcDgizHDVWcZsky5AkEAymVrahSpGLzjZoChXspXjpIhr8Wi1DuD+pu2F+X1CcD2otmgOIAs294+TABHYGQhZXJf7VyauJA86EAFakZWRwJBANdloBttFh5L/NItFsW8Lt8QJ/cEa/FNXl2XGvA+Qzcnq4AoADfncXLRydD58+a2Tz5z87wfc7t1iifbykQPtX0CQQCXNUIUlK7oXmX9tGfa64ySZTZpnVvS90vYtPBL1m9FBDa+35vF2mypLtaX7TVmHCifI93q9V5aONhoIzxoc3gNAkBQ/9lult4bdI3VZ7lJkJO7tHKi9Jm2+1ZCMaGiuHdEVXmrzt8OJs3F1Nhk6qnql6JDJQ2CL/2wf+n9RSzR3WptAkBxnpp632G/c71+M1qu42tM2NOYZ19uK8F/rAtQCL/iZiLQi6qfIJVzAWpZUHft6ptzqs/3Ilt1G3Pwz7uLld1W";
//		String message="sVfckUlMm18sM7hJZ/uFzEfLBXTevK/rdPPlD7hQ77Og5f3JBeiFQXuqGtVDGfOPIcLk8y0LsfJmLL6mNebe/GBZuNdyE/e2vdKrYmLlkMXwGEHjfN3ceSfzvlUt/qz/0totGZYxsBTTtHATN9VJMa8c4Mc1hVm7p2AuPJMoY1A=";
//	
//		System.out.println(decryptWithPrivate(privateKey,message));
	}
	public static String[] generatePair() throws NoSuchAlgorithmException{
		KeyPairGenerator keyGen=KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(SIZE);
		KeyPair kp=keyGen.generateKeyPair();
//		System.out.println(kp.getPublic().getFormat());
//		System.out.println(kp.getPrivate().getFormat());
		
		
		//return new String[]{new String(kp.getPublic().toString()),new String(kp.getPrivate().toString())};
		
		
		Encoder e=Base64.getEncoder();
		return new String[]{e.encodeToString(kp.getPublic().getEncoded()),
							e.encodeToString(kp.getPrivate().getEncoded())};
		
	}
	
	
	public static String encryptWithPublic(String publicKey,String inputDataS) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
		//Convert.toBase64String();
		byte[] b=Base64.getDecoder().decode(publicKey.getBytes("utf-8"));
		byte[] inputData=Base64.getDecoder().decode(inputDataS.getBytes("utf-8"));
		PublicKey key=KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(b));
		Cipher cipher=Cipher.getInstance("RSA");
		cipher.init(Cipher.PUBLIC_KEY, key);
		byte[] encryptedBytes=cipher.doFinal(inputData);
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	
	public static String encryptWithPrivate(String privateKey,String inputDataS) throws UnsupportedEncodingException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		byte[] b=Base64.getDecoder().decode(privateKey.getBytes("utf-8"));
		byte[] inputData=Base64.getDecoder().decode(inputDataS.getBytes("utf-8"));
		PrivateKey key=KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(b));
		Cipher cipher=Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encryptedBytes=cipher.doFinal(inputData);
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}
	
	
	public static String decryptWithPrivate(String privateKey,String inputDataS)throws Exception{
		byte[] b=Base64.getDecoder().decode(privateKey.getBytes("utf-8"));
		byte[] inputData=Base64.getDecoder().decode(inputDataS.getBytes("utf-8"));
		PrivateKey key=KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(b));
		Cipher cipher=Cipher.getInstance("RSA");
		cipher.init(Cipher.PRIVATE_KEY, key);
		byte[] decryptedBytes=cipher.doFinal(inputData);
		return Base64.getEncoder().encodeToString(decryptedBytes);
	}
	
	public static String decryptWithPublic(String publicKey,String inputDataS)throws Exception{
		byte[] b=Base64.getDecoder().decode(publicKey.getBytes("utf-8"));
		byte[] inputData=Base64.getDecoder().decode(inputDataS.getBytes("utf-8"));
		PublicKey key=KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(b));
		Cipher cipher=Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] decryptedBytes=cipher.doFinal(inputData);
		return Base64.getEncoder().encodeToString(decryptedBytes);
	}
	
}
