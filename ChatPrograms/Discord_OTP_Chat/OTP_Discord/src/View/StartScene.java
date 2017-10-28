package View;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class StartScene {
	@FXML TextField userF;
	@FXML TextField targetF;
	@FXML TextField yourKey;
	@FXML TextField theirKey;
	@FXML TextField tokenF;
	@FXML Button quit;
	@FXML Button connect;

	static String user,target,token;
	static String keyYou,keyThem;
	static boolean moved=false;
	
	static String ipString,portString;
	
	public void initialize(){
		if(!moved)OTP_Discord_Chat.loadKey();
		if(keyYou!=null)yourKey.setText(keyYou);
		if(keyThem!=null)theirKey.setText(keyThem);
		if(user!=null)userF.setText(user);
		if(target!=null)targetF.setText(target);
		if(token!=null)tokenF.setText(token);
		userF.textProperty().addListener(new ChangeListener(){
			@Override
			public void changed(ObservableValue observable, Object oldval, Object newval) {
				user=(String) newval;
			}});
		targetF.textProperty().addListener(new ChangeListener(){
			@Override
			public void changed(ObservableValue observable, Object oldval, Object newval) {
				target=(String) newval;
			}});
		
		tokenF.textProperty().addListener(new ChangeListener(){
			@Override
			public void changed(ObservableValue observable, Object oldval, Object newval) {
				token=(String) newval;
			}});
		yourKey.textProperty().addListener(new ChangeListener(){
			@Override
			public void changed(ObservableValue observable, Object oldval, Object newval) {
				keyYou=(String) newval;
			}});
		theirKey.textProperty().addListener(new ChangeListener(){
			@Override
			public void changed(ObservableValue observable, Object oldval, Object newval) {
				keyThem=(String) newval;
			}});	
	}
	@FXML public Object quit(){
		OTP_Discord_Chat.save();
		System.exit(0);
		return null;
	}
	@FXML public Object chat() throws NumberFormatException, IOException{
		moved=true;
		OTP_Discord_Chat.loadScene("ChatScene.fxml");
		return null;
	}
	
}
