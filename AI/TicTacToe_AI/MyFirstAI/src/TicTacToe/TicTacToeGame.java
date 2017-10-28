package TicTacToe;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TicTacToeGame extends Application{
	public static int iterations=1000;
	

	
	public static void main(String...args){Application.launch(args);}
	@Override
	public void start(Stage arg0) throws Exception {
		this.arg0=arg0;
		this._class=getClass();
		arg0.setTitle("Tic Tac Toe AI Tester");
		startAGame();
		arg0.show();
	}
	private static Class _class;
	private static Stage arg0;
	
	public static void startAGame(){
		try {
			arg0.setScene(new Scene(FXMLLoader.load(_class.getResource("View.fxml"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	


}
