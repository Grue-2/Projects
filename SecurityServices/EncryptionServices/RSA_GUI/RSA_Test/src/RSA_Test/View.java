package RSA_Test;

import java.security.NoSuchAlgorithmException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class View {
	@FXML TextField yourPublic;
	@FXML TextField yourPrivate;
	@FXML TextField theirPublic;
	@FXML Button encryptWithTheirPublic;
	@FXML Button decryptWithTheirPublic;
	@FXML Button encryptWithPublic;
	@FXML Button encryptWithPrivate;
	@FXML Button decryptWithPublic;
	@FXML Button decryptWithPrivate;
	@FXML Button generatePair;
	@FXML TextArea ta0;
	@FXML TextArea ta1;
	@FXML TextArea ta2;
	@FXML TextArea ta3;
	@FXML TextArea ta4;
	@FXML TextArea ta5;
	@FXML public Object encryptWithTheirPublic(){
		try {
			ta0.setText(RSA_Test.encryptWithPublic(theirPublic.getText(),ta0.getText()));
		} catch (Exception e) {
			ta0.setText(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	@FXML public Object decryptWithTheirPublic(){
		try {
			ta1.setText(RSA_Test.decryptWithPublic(theirPublic.getText(),ta1.getText()));
		} catch (Exception e) {
			ta1.setText(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	@FXML public Object encryptWithPublic(){
		try {
			ta2.setText(RSA_Test.encryptWithPublic(yourPublic.getText(),ta2.getText()));
		} catch (Exception e) {
			ta2.setText(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	@FXML public Object encryptWithPrivate(){
		try {
			ta3.setText(RSA_Test.encryptWithPrivate(yourPrivate.getText(),ta3.getText()));
		} catch (Exception e) {
			ta3.setText(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	@FXML public Object decryptWithPublic(){
		try {
			ta4.setText(RSA_Test.decryptWithPublic(yourPublic.getText(),ta4.getText()));
		} catch (Exception e) {
			ta4.setText(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	@FXML public Object decryptWithPrivate(){
		try {
			ta5.setText(RSA_Test.decryptWithPrivate(yourPrivate.getText(),ta5.getText()));
		} catch (Exception e) {
			ta5.setText(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	@FXML public Object generatePair(){
		try {
			String[] x=RSA_Test.generatePair();
			yourPublic.setText(x[0]);
			yourPrivate.setText(x[1]);
		} catch (NoSuchAlgorithmException e) {
			ta5.setText(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
}
