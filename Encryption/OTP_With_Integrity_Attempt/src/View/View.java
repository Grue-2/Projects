/*
JC

10/28/2017

github'date
 */

package View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import encryption.OTP_Encryption_Exception;
import encryption.OTP_Implementation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class View {
	@FXML
	Button encrypt;
	@FXML
	Button decrypt;
	@FXML
	public TextField keyYou;
	@FXML
	public TextField keyThem;
	@FXML
	TextField eText;
	@FXML
	TextField dText;

	static View _this;

	public void initialize() {
		_this = this;

		try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(new File("save.ser")))) {
			String savedKeys[] = (String[]) reader.readObject();

			if (savedKeys[0] != null)
				keyYou.setText(savedKeys[0]);
			if (savedKeys[1] != null)
				keyThem.setText(savedKeys[1]);

		} catch (Exception e) {
			// Silent fail when there's no save file.
		}

	}

	void save() {
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(new File("save.ser")))) {
			writer.writeObject(new String[] { keyYou.getText(), keyThem.getText() });
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public Object encrypt() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String text = eText.getText();
		if (!"".equals(text)){
			OTP_Implementation encrypter = new OTP_Implementation(keyYou.getText(), null);
			
			try {
				String result = encrypter.encrypt(text + "###" + OTP_Implementation.sha1(text, true));
				result = encrypter.padScramble(result);;
				
				eText.setText(result);
	
				keyYou.setText(encrypter.getKey());
	
			} catch (OTP_Encryption_Exception e) {
				eText.setText("Whoops encryption failed for some reason.\n" + e.getStackTrace());
			}
		}
		
		return null;
	}

	@FXML
	public Object decrypt() throws NoSuchAlgorithmException, InvalidKeySpecException {
		OTP_Implementation decrypter = new OTP_Implementation(null, keyThem.getText());

		try {
			String result = decrypter.padDescramble(dText.getText());
			result = decrypter.decrypt(result);
			
			String results[] = result.split("###");
			
			if (OTP_Implementation.sha1(results[0], false).equals(results[1]))
				dText.setText(results[0]);
			else
				dText.setText("Message integrity not valid.");

			keyThem.setText(decrypter.getOtherKey());
		} catch (OTP_Encryption_Exception e) {
			dText.setText("Whoops decryption failed for some reason.\n" + e.getStackTrace());
			
		} catch (Exception e){
			dText.setText("Message integrity not valid.");
		}
		
		return null;
	}
}
