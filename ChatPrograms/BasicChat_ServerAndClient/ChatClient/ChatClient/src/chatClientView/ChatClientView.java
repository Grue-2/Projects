package chatClientView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatClientView extends Application{
	final static String version="v.01";
	static Socket clientSocket=new Socket();
	static String namerino="Default";
	static PrintWriter out;
	static BufferedReader in;
	
	private static Stage arg0;
	public static void main(String[] args){
		Application.launch(args);
	}

	@Override
	public void start(Stage arg1) throws Exception {
		arg0=arg1;
		sceneSwap("Connect to Server","ClientScene.fxml");
	}
	
	void sceneSwap(String tittle,String fxmlSrc){
		try {
			arg0.setScene(new Scene(FXMLLoader.load(getClass().getResource(fxmlSrc))));
			arg0.setTitle(tittle);
			arg0.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("here");
			e.printStackTrace();
		}

	}
	
	
}
