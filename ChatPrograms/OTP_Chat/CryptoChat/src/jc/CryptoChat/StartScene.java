package jc.CryptoChat;

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
	@FXML TextField ip;
	@FXML TextField port;
	@FXML TextField yourKey;
	@FXML TextField theirKey;
	@FXML TextField handle;
	@FXML TextField ip_Port;
	@FXML Button quit;
	@FXML Button host;
	@FXML Button connect;

	static String handlez;
	static String ipPortString;
	static String keyYou,keyThem;
	static Socket client,server;
	static boolean asServer;
	static boolean moved=false;
	
	static String ipString,portString;
	
	public void initialize(){
		CryptoChat.loadKey();
		if(keyYou!=null)yourKey.setText(keyYou);
		if(keyThem!=null)theirKey.setText(keyThem);
		if(handlez!=null)handle.setText(handlez);
		handle.textProperty().addListener(new ChangeListener(){
			@Override
			public void changed(ObservableValue observable, Object oldval, Object newval) {
				handlez=(String) newval;
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
	
	static void clearStatics() throws IOException{
		ipPortString=null;
		ChatScene.out.close();//ChatScene.clientOut.close();
		ChatScene.in.close();//ChatScene.serverOut.close();
		client.close();server.close();
		client=null;server=null;
		moved=false;
	}
	
	static void setClient() throws IOException{
		asServer=true;
		if(!moved){CryptoChat.loadScene("ChatScene.fxml");}
		moved=true;
	}
	
	@FXML public Object quit(){
		CryptoChat.save();
		System.exit(0);
		return null;
	}
	
	@FXML public Object host(){
		host.setText("Hosting");
		
		Task hosting=new Task(){
			@Override
			protected Object call() throws Exception {
				try(ServerSocket ss=new ServerSocket(0)){
					StartScene.ipPortString="ip-port: "+InetAddress.getLocalHost().getHostAddress()+" - "+ss.getLocalPort();
					StartScene.ipString=InetAddress.getLocalHost().getHostAddress();
					StartScene.portString=String.valueOf(ss.getLocalPort());
					client=(ss.accept());	
				} catch (IOException e) {e.printStackTrace();}
				return null;
			}
		};
		hosting.setOnSucceeded(e->{
			try {
				setClient();
			} catch (IOException e1) {e1.printStackTrace();}
		});
		Thread thing=new Thread(hosting);
		thing.setDaemon(true);
		thing.start();

		CryptoChat.pause();
		ip_Port.setText(ipPortString);
		ip_Port.setVisible(true);
		return null;
	}
	
	@FXML public Object Connect() throws NumberFormatException, IOException{
		server=new Socket();
		server.connect(new InetSocketAddress(ip.getText(),Integer.parseInt(port.getText())));
		asServer=false;
		moved=true;
		CryptoChat.loadScene("ChatScene.fxml");
		return null;
	}
	
}
