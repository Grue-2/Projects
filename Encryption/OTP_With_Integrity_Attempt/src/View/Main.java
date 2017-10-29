/*
JC

10/28/2017

Don't know why this doesn't work to help OTP integrity.
 */

package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
Uses key material to randomize entry after appending a hash.
 */
public class Main extends Application{
		public static void main(String...args){
			Application.launch(args);
		}

		@Override
		public void start(Stage arg0) throws Exception {
			arg0.setScene(new Scene(FXMLLoader.load(getClass().getResource("View.fxml"))));
			arg0.setTitle("An OTP with integrity.");
			arg0.show();
		}
		
		@Override
		public void stop()
		{
			View._this.save();
		}
		
		

}
