package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FX_Control extends Application {
	public static void main(String[]args){Application.launch(args);}
	
	@Override public void start(Stage stage){
		try {
			stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("Scene.fxml"))));
			stage.setTitle("OTP Gui v.02");
			stage.show();
			
		} catch (IOException e) {e.printStackTrace();}
	}

}
