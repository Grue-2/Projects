/*
JC

October 28, 2017

Fixing and documenting Tic-Tac-Toe AI for GitHub.
 */

package ticTacToe;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


/*
	Gui to play with AI.

	Can change aiGame flag to false to play against
	completely random moves.
 */
public class TicTacToeGame extends Application {
	static final boolean AI_GAME = true;
	
	public static void main(String... args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		this.arg0 = arg0;
		this._class = getClass();
		arg0.setTitle("Tic Tac Toe AI Tester");
		startAGame();
		arg0.show();
	}

	private static Class _class;
	private static Stage arg0;

	public static void startAGame() {
		try {
			arg0.setScene(new Scene(FXMLLoader.load(_class.getResource("View.fxml"))));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
