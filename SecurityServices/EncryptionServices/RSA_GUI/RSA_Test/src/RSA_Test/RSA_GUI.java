package RSA_Test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RSA_GUI extends Application {
	private static final String VERSION="02";
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		arg0.setTitle("RSA Test GUI v."+VERSION);
		arg0.setScene(new Scene(FXMLLoader.load(getClass().getResource("View.fxml"))));
		arg0.show();
	}

}
