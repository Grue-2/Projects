/*
JC

Untested v 1.0

-Still uses "MessageCompacting" class. Its badly done but
for this purpose shouldn't matter and it is working.
 */

package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RSA_Chat extends Application{
	private static Stage s;
	private static Class<?> c;
	
	public static void main(String[]args){Application.launch(args);}
	
	@Override
	public void start(Stage arg0) throws Exception {
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
