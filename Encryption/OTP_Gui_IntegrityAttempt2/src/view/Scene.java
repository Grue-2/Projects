package view;

import java.text.Normalizer;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Database;
import model.DatabaseData;
import service.EncryptionService;
import service.StringPair;

public class Scene {
	@FXML private TextField keyYou;
	@FXML private TextField keyThem;
	@FXML private TextField plainText;
	@FXML private TextField cipherText;
	@FXML private Button encrypt;
	@FXML private Button decrypt;
	
	private static Database database;
	
	public void initialize() throws Exception {
		DatabaseData databaseData = Database.getDataBaseInformation();
		keyYou.setText(databaseData.getKeyYou());
		keyThem.setText(databaseData.getKeyThem());
		database = databaseData.getDatabase();
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){@Override public void run() {
			database.saveState(keyYou.getText(), keyThem.getText());
		}}));
	}
	
	public Object encrypt() {
		try {
			StringPair result = EncryptionService.encrypt(plainText.getText(), keyThem.getText());
			plainText.setText(Base64.getEncoder().encodeToString(result.first.getBytes("ISO-8859-1")));
			keyThem.setText(result.second);
		} catch (Exception e) {
			plainText.setText("Error decrypting message! "+plainText.getText());
			e.printStackTrace();
		}
		return null;
	}
	public Object decrypt() {
		try {
			StringPair result = EncryptionService.decrypt(
					new String(Base64.getDecoder().decode(cipherText.getText().getBytes("ISO-8859-1")), "ISO-8859-1"),
					keyYou.getText());
			cipherText.setText(result.first);
			keyYou.setText(result.second);
		} catch (Exception e) {
			cipherText.setText("Error decrypting message! "+cipherText.getText());
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	private String toHex(String input) {
		byte[] inputArray = input.getBytes();
		StringBuilder temp = new StringBuilder();
		for(byte b: inputArray)
			temp.append(Integer.toHexString(b));
		return temp.toString();
	}
	private String fromHex(String input) {
		byte[] result = new byte[input.length()/2];
		
		for(int i = 0, len = input.length(); i < len; i+=2)
			result[i/2] = (byte)(Integer.parseInt(input.substring(i,i+2), 16) & 0xff);

		return new String(result);
	}*/
	
}
