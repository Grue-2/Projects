package view;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Client extends Application{
	static final String REMOTE_IP = "96.41.48.205";
	static final int PORT_NUMBER = 55555;
	
	private static Stage s;
	private static Class<?> c;
	
//	@Override public void stop()
//	{
//		// no more saving on stop needed
//	}
	
	public static void main(String[]args){Application.launch(args);}
	
	@Override public void start(Stage arg0) throws Exception {
		arg0.setTitle("RSA Chat.");
		c=getClass();
		s=arg0;
		loadScene("StartScene.fxml");
	}
	
	public static void loadScene(String fxmlScenePath) throws IOException{
		s.setScene(new Scene(FXMLLoader.load(c.getResource(fxmlScenePath))));
		s.show();
	}
}
