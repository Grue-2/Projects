/*
JC

10/29/2017

No-func version. - clean
 */

package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	static Stage arg0;
	@Override
	public void start(Stage arg0) throws Exception {
		Main.arg0=arg0;
		arg0.setTitle("Statically unencrypted message into pictures.");
		arg0.setScene(new Scene(FXMLLoader.load(getClass().getResource("View.fxml"))));
		arg0.show();
	}
	public static void main(String...args){Application.launch(args);}
	
}
