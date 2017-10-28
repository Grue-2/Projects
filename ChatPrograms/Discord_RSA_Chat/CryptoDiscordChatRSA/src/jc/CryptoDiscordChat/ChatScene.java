package jc.CryptoDiscordChat;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.login.LoginException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jc.RSA.FrontEndRSA;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ChatScene {
	private static final String COMMAND_PHRASE = "#D:";
	private static final int C_PHRASE_LENGTH = COMMAND_PHRASE.length();

	@FXML
	TextArea chatWindow;
	@FXML
	TextField input;
	@FXML
	Button back;
	@FXML
	Button clear;
	@FXML
	Button check;
	@FXML
	Button establishKey;
	@FXML
	CheckBox deleteMessages;

	public static JDA jda;

	private static boolean gotKey = false, deleteStuff = false;
	private static String theirPublicKey, yourPublicKey, yourPrivateKey;

	// TODO:: Add auto key request silent, and response to it

	public void initialize() throws IOException, LoginException, IllegalArgumentException, InterruptedException,
			RateLimitedException, NoSuchAlgorithmException {
		String[] keys = FrontEndRSA.generatePair();
		yourPublicKey = keys[0];
		yourPrivateKey = keys[1];
		theirPublicKey = "None recieved yet.";
		//
		input.setOnKeyPressed(e -> {
			try {
				input(e);
			} catch (InvalidKeyException | UnsupportedEncodingException | InvalidKeySpecException
					| NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
					| BadPaddingException e1) {
				e1.printStackTrace();
			}
		});
		JDA jda2 = new JDABuilder(AccountType.CLIENT).setToken(StartScene.token).buildBlocking();
		jda = jda2;
		jda.setAutoReconnect(true);
		jda.addEventListener(new MessageDecryptor());
		// send key
		establishKey(false);
	}

	private void establishKey(boolean loud) {
		if (loud)
			chatWindow.appendText("\nPublic Key sent.\n\n");
		User target = jda.asClient().getFriendsByName(StartScene.target, true).get(0).getUser();
		PrivateChannel x = target.openPrivateChannel().complete();
		Message y = x.sendMessage("#K:" + yourPublicKey).complete();
		if (deleteStuff || !loud) {
			y.editMessage("scrambledmymessageplease").queue();
			y.delete().queue();
		}
	}

	public Object deleteMessages() {
		deleteStuff = !deleteStuff;
		return null;
	}

	public Object establishKey() {
		establishKey(true);
		return null;
	}

	private class MessageDecryptor extends ListenerAdapter {
		@Override
		public void onPrivateMessageReceived(PrivateMessageReceivedEvent e) {
			System.out.println("got message: " + e.getMessage().getContent());
			String message = e.getMessage().getContent();
			if (e.getMessage().getAuthor().getName().equals(StartScene.target) && message.startsWith(COMMAND_PHRASE)) {
				message = message.substring(C_PHRASE_LENGTH);
				try {
					message = FrontEndRSA.decryptWithPrivate(yourPrivateKey, message);
				} catch (InvalidKeyException | UnsupportedEncodingException | InvalidKeySpecException
						| NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
						| BadPaddingException e1) {
					e1.printStackTrace();
				}

				chatWindow.appendText(StartScene.target + ": " + message + "\n");
			} else if (e.getMessage().getAuthor().getName().equals(StartScene.target) && message.startsWith("#K:")) {
				theirPublicKey = e.getMessage().getContent().substring(3);
				gotKey = true;
			}

		}
	}

	@FXML
	Object back() throws IOException {
		CryptoChatChatRSA.loadScene("StartScene.fxml");
		return null;
	}

	@FXML
	Object clear() {
		chatWindow.setText("Chat cleared.\n");
		return null;
	}

	@FXML
	Object check() {
		chatWindow.appendText("\nYour public key: " + yourPublicKey + "\nYour private key: " + yourPrivateKey
				+ "\nTarget's public key: " + theirPublicKey + "\n\n");
		return null;
	}

	private Object input(KeyEvent e) throws InvalidKeyException, UnsupportedEncodingException, InvalidKeySpecException,
			NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		if (e.getCode() == KeyCode.ENTER) {
			if (gotKey) {
				String message = input.getText();
				chatWindow.appendText(StartScene.user + ": " + message + "\n");
				message = FrontEndRSA.encryptWithPublic(theirPublicKey, message);
				input.setText("");
				User target = jda.asClient().getFriendsByName(StartScene.target, true).get(0).getUser();
				PrivateChannel x = target.openPrivateChannel().complete();
				Message y = x.sendMessage(COMMAND_PHRASE + message).complete();
				if (deleteStuff) {
					y.editMessage("scrambledmymessageplease").queue();
					y.delete().queue();
				}

			} else {
				chatWindow.appendText("Can't chat till they send you their key.\n");
				input.setText("");
			}
		}
		return null;
	}

}
