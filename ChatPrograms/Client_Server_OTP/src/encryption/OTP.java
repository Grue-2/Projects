package encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import key_managment.EntropyProvider;

// TODO: cahnge exception throwing to handle buffer key shifting

public class OTP implements Serializable {

//	public static void main(String... args) throws Exception {
//		OTP test = new OTP();
//
//		String testString = "abcf adfeawf graf423r !4242@#@$";
//		System.out.println(testString);
//
//		testString = test.encrypt(testString);
//		System.out.println("\n" + testString);
//		testString = test.decrypt(testString);
//		System.out.println("\n" + testString);
//	}

	private static void printBits(byte b) {
		for (int i = 0; i < 8; ++i) {
			if (b > 0) {
				System.out.print(b % 2);
				b /= 2;
			} else
				System.out.print(0);
		}
		System.out.print(" ");
	}

	private static final long serialVersionUID = 1L;

	private static OTP OTP_toSave;

	private EntropyProvider providerYou, providerThem;

	public String encrypt(String plainText) throws Exception {
		byte[] cipherBytes = OTP_Algorithms.pad((plainText+"SHA1:"+OTP_Algorithms.sha1(plainText)).getBytes("ISO-8859-1"), providerYou);
		
		cipherBytes = OTP_Algorithms.scramble(cipherBytes, providerYou);

		return new String(cipherBytes, "ISO-8859-1");
	}

	public String decrypt(String cipherText) throws Exception {
		byte[] textBytes = OTP_Algorithms.descramble(cipherText.getBytes("ISO-8859-1"), providerThem);
				
		textBytes = OTP_Algorithms.pad(textBytes, providerThem);
		
		String[] result = new String(textBytes, "ISO-8859-1").split("SHA1:");
		
		if(!result[1].equals(OTP_Algorithms.sha1(result[0])))
			throw new IntegrityException();
		
		return result[0];
	}

	public OTP() throws Exception {
		OTP_toSave = this;

		File otpStateSave = new File("OTP_State.bin");

		providerYou = new EntropyProvider("YourKeyFolder");
		providerThem = new EntropyProvider("TargetKeyFolder");

		// set saving place in key on closing
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				try (ObjectOutputStream saveWriter = new ObjectOutputStream(new FileOutputStream(otpStateSave))) {
					saveWriter.writeObject(OTP.OTP_toSave);

				} catch (IOException e) {
					System.err.println("Failed to save data.");
					e.printStackTrace();
				}
			}
		}));

		// load place in key
		if (otpStateSave.exists()) {
			try (ObjectInputStream saveLoader = new ObjectInputStream(new FileInputStream(otpStateSave))) {
				OTP otp = (OTP) saveLoader.readObject();

				providerYou = otp.providerYou;
				providerThem = otp.providerThem;

			} catch (IOException | ClassNotFoundException e) {
				System.err.println("Failed to load data.");
				e.printStackTrace();
			}
		}
	}
}
