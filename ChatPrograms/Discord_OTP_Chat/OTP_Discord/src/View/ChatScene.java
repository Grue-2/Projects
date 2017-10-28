// 10/22/2017 - JC

package View;

import java.io.IOException;

import javax.security.auth.login.LoginException;

import encryption.OTP_Encryption_Exception;
import encryption.OTP_Implementation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	private static final String COMMAND_PHRASE="#D:";
	private static final int C_PHRASE_LENGTH=COMMAND_PHRASE.length();
	
	@FXML TextArea chatWindow;
	@FXML TextField input;
	@FXML Button back;
	@FXML Button clear;
	@FXML Button check;

	public static JDA jda;
	
	private static OTP_Implementation encryptor;
	
	public void initialize() throws IOException, LoginException, IllegalArgumentException, InterruptedException, RateLimitedException{
		encryptor = new OTP_Implementation(StartScene.keyYou,StartScene.keyThem);
				
		input.setOnKeyPressed(e->input(e));
		JDA jda2=new JDABuilder(AccountType.CLIENT).setToken(StartScene.token).buildBlocking();
		jda=jda2;
		jda.setAutoReconnect(true);
		jda.addEventListener(new MessageDecryptor());
	}
	
	private class MessageDecryptor extends ListenerAdapter{
		@Override public void onPrivateMessageReceived(PrivateMessageReceivedEvent e){
			String message=e.getMessage().getContent();
			if(e.getMessage().getAuthor().getName().equals(StartScene.target)&&message.startsWith(COMMAND_PHRASE)){
				message=message.substring(C_PHRASE_LENGTH);
				String result;
				try {
					result = encryptor.decrypt(message);
					StartScene.keyThem = encryptor.getOtherKey();
					chatWindow.appendText(StartScene.target+": "+result+"\n");
				} catch (OTP_Encryption_Exception e1) {
					System.err.println(e1.getMessage());
					e1.printStackTrace();
				}
				
			}
		}
	}
	
	@FXML Object back() throws IOException{
		OTP_Discord_Chat.loadScene("StartScene.fxml");
		return null;
	}
	@FXML Object clear(){
		chatWindow.setText("Chat cleared.\n");
		return null;
	}
	@FXML Object check(){
		chatWindow.appendText("\nRemaining key-you: "+StartScene.keyYou.length()+
							"\nRemaining key-them: "+StartScene.keyThem.length()+"\n\n");
		return null;
	}
	private Object input(KeyEvent e){
		if(e.getCode()==KeyCode.ENTER){
			String message=input.getText();
			chatWindow.appendText(StartScene.user+": "+message+"\n");
			String result;
			try {
				result = encryptor.encrypt(message);
				StartScene.keyYou=encryptor.getKey();
				input.setText("");
				User target=jda.asClient().getFriendsByName(StartScene.target,true).get(0).getUser();
				PrivateChannel x=target.openPrivateChannel().complete();
				Message y=x.sendMessage(COMMAND_PHRASE+result).complete();
				y.editMessage("scrambledmymessageplease").queue();
				y.delete().queue();
			} catch (OTP_Encryption_Exception e1) {
				System.err.println(e1.getMessage());
				e1.printStackTrace();
			}
		}
		return null;
	}
}
