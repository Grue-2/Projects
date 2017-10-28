package chatClientView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ClientScene {
	@FXML private Button exit;
	@FXML private Button confirm;
	@FXML private TextField ip;
	@FXML private TextField port;
	@FXML private TextField name;
	@FXML private Label error;
	

	public void initialize(){
		error.setVisible(false);
		
		
	}
	
	@FXML Object exit(){System.exit(0);return null;}
	@FXML Object confirm(){
		try{
			ChatClientView.clientSocket.connect(new InetSocketAddress(ip.getText(),Integer.parseInt(port.getText())));
			ChatClientView.namerino=name.getText();
			ChatClientView.out=new PrintWriter(ChatClientView.clientSocket.getOutputStream(),true);
			ChatClientView.in=new BufferedReader(new InputStreamReader(ChatClientView.clientSocket.getInputStream()));
			
			new ChatClientView().sceneSwap("Connect to Server","ChatScene.fxml");
			
		}catch(NumberFormatException | IOException e){
			e.printStackTrace();
			error.setVisible(true);
		}
		
		return null;
	}
	
}
