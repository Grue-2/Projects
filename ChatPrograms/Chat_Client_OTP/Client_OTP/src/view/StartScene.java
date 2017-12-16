package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class StartScene {
	
	@FXML TextField handle;
	@FXML TextField target;
	@FXML Button connect;
	
	static Socket connection;
	static String yourHandle, yourTarget;
	
	public void initialize(){
		try(ObjectInputStream saveLoader =new ObjectInputStream( new FileInputStream(( new File("save.txt"))))){
			SaveFile save = (SaveFile)saveLoader.readObject();
			
			handle.setText(save.handle);
			target.setText(save.target);
		} catch (IOException e) {
			// Silent fails since one doesn't always have a save file.
		} catch (ClassNotFoundException e) {
		}
	}
	
	@FXML public Object connect() throws NumberFormatException, IOException
	{	
		connection = new Socket();
		connection.connect(new InetSocketAddress(
				InetAddress.getByName(Client.REMOTE_IP), Client.PORT_NUMBER));
		yourHandle = handle.getText();
		yourTarget = target.getText();
		
		try(ObjectOutputStream saveWriter =new ObjectOutputStream( new FileOutputStream(( new File("save.txt"))))){
			saveWriter.writeObject(new SaveFile(yourHandle, yourTarget));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Client.loadScene("ChatScene.fxml");
		return null;
	}
}
