/*
JC

10/28/2017 - clean up for github update

Github update
*/
package jc.CryptoDiscordChat;

import java.io.IOException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class StartScene {
	@FXML
	TextField userF;
	@FXML
	TextField targetF;
	@FXML
	TextField tokenF;
	@FXML
	Button quit;
	@FXML
	Button connect;

	static String user, target, token;
	static boolean moved = false;

	public void initialize() {
		if (!moved)
			CryptoChatChatRSA.loadKey();

		if (user != null)
			userF.setText(user);

		if (target != null)
			targetF.setText(target);

		if (token != null)
			tokenF.setText(token);

		userF.textProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldval, Object newval) {
				user = (String) newval;
			}
		});
		targetF.textProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldval, Object newval) {
				target = (String) newval;
			}
		});

		tokenF.textProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<?> observable, Object oldval, Object newval) {
				token = (String) newval;
			}
		});
	}

	@FXML
	public Object quit() {
		CryptoChatChatRSA.save();
		System.exit(0);
		return null;
	}

	@FXML
	public Object chat() throws NumberFormatException, IOException {
		moved = true;
		CryptoChatChatRSA.loadScene("ChatScene.fxml");
		return null;
	}

}
