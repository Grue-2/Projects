package PlantSelector;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	private static final double VERSION=.01;
	public static void main(String...args){
		Application.launch(args);
	}
	@Override
	public void start(Stage arg0) throws Exception {
		arg0.setTitle("Plant Selector v."+VERSION);
		arg0.setScene(new Scene(FXMLLoader.load(getClass().getResource("View.fxml"))));
		arg0.show();
		
	}
	
}
