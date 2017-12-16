package view;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.Session;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;

public class StartScene {
	@FXML AnchorPane anchor;
	@FXML BorderPane borderPane;
	@FXML Button start;
	
	public void initialize() throws IOException
	{
		Image image = SwingFXUtils.toFXImage(ImageIO.read(new File("background.gif")), null);
		BackgroundSize backgroundSize = new BackgroundSize(1600,900,true,true,true,false);
		BackgroundImage backgroundImage = new BackgroundImage(
				image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.CENTER, backgroundSize);
		Background background = new Background(backgroundImage);
		anchor.setBackground(background);
		
		ImageView startImage = new ImageView();
		startImage.setImage(new Image(new File("startButton.png").toURI().toString()));
		start.setGraphic(startImage);
		//start.setStyle("-fx-background-image: url('../../startButtong.png')");
	}
	
	@FXML public Object start() throws IOException
	{
		Session.startSession();
		Main.loadScene("Running_Scene.fxml");
		return null;
	}
	
	@FXML public Object mousePressStart()
	{
		ImageView startImage = new ImageView();
		startImage.setImage(new Image(new File("startButtonPressed.png").toURI().toString()));
		start.setGraphic(startImage);
		return null;
	}
	
	@FXML public Object mouseReleaseStart()
	{
		ImageView startImage = new ImageView();
		startImage.setImage(new Image(new File("startButton.png").toURI().toString()));
		start.setGraphic(startImage);
		return null;
	}
}