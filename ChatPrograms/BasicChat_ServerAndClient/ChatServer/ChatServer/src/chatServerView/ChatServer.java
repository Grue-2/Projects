package chatServerView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatServer extends Application{
	public static void main(String[] args){
		Application.launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		arg0.setScene(new Scene(FXMLLoader.load(getClass().getResource("ServerScene.fxml"))));
		arg0.show();
	}
	
}
