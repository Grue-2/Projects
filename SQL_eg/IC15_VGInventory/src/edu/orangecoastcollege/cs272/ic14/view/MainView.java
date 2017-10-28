package edu.orangecoastcollege.cs272.ic14.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainView extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {		
		ViewNavigator.setStage(primaryStage);
		primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("SignInScene.fxml"))));
		primaryStage.show();
		//ViewNavigator.loadScene("Welcome to in.vent.ory", ViewNavigator.SIGN_IN_SCENE);
	}
	
	public static void main(String[] args) {
		launch(args);

	}

}
