package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import game.Session;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import save.PlayerSave;

public class Main extends Application{
	private static final String VERSION = ".01";
	static Stage _stage;
	static Class<?> _class;
	public static Scene _scene;
	
	public static void main(String[]args){Application.launch(args);}

	public void start(Stage stage) throws Exception {
		stage.setTitle("Net_Run v "+VERSION);
		_class=getClass();
		_stage=stage;
		playMusic();
		loadScene("StartScene.fxml");
	}
	
	public static void loadScene(String fxmlScenePath) throws IOException{
		_scene = new Scene(FXMLLoader.load(_class.getResource(fxmlScenePath)));
		_stage.setScene(_scene);
		_stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		_stage.setFullScreen(true);
		_stage.show();
	}
	
	public static void playMusic()
	{
		 MediaPlayer a =new MediaPlayer(
				 new Media(new File("GoldPile.mp3").toURI().toString()));
		 a.setOnEndOfMedia(new Runnable() {
		       public void run() {
		         a.seek(Duration.ZERO);
		       }
		   });
		  a.play();
	}
	
	@Override public void stop() throws FileNotFoundException, IOException
	{
		Session.session.unloadAgendas();
		PlayerSave.save(Session.session.getCredits());
	}
	

}
