package jc.CryptoChat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CryptoChat extends Application{
	private static Stage s;
	private static Class c;
	public static void main(String...args){Application.launch(args);}
	@Override public void start(Stage arg0) throws Exception {
		arg0.setTitle("CryptoChat");
		c=getClass();
		s=arg0;
		loadScene("StartScene.fxml");
	}
	public static void loadScene(String scene) throws IOException{
		s.setScene(new Scene(FXMLLoader.load(c.getResource(scene))));
		s.show();
	}
	
	static void pause(){
		try {		// kind of a gross work around to ordering of threads
			Thread.sleep(200);
		} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	@Override public void stop(){
		save();
	}
	
	static void save(){
		File youKeyFile=new File("ObviouslyNotYouKey.txt");
		File themKeyFile=new File("ObviouslyNotThemKey.txt");
		File handleFile=new File("Handle.txt");
		try(PrintWriter pwYou=new PrintWriter(youKeyFile);
			PrintWriter pwThem=new PrintWriter(themKeyFile);
			PrintWriter handleWriter=new PrintWriter(handleFile);){
				pwYou.println(StartScene.keyYou);
				pwThem.println(StartScene.keyThem);
				handleWriter.println(StartScene.handlez);
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
	}
	
	static void loadKey(){
		File youKeyFile=new File("ObviouslyNotYouKey.txt");
		File themKeyFile=new File("ObviouslyNotThemKey.txt");
		File handleFile=new File("ObviouslyNotBlitzCrank.txt");
		try(Scanner readYou=new Scanner(youKeyFile);
			Scanner readThem=new Scanner(themKeyFile);
			Scanner handleReader=new Scanner(handleFile);){
				StringBuilder sb=new StringBuilder();
				while(readYou.hasNextLine())sb.append(readYou.nextLine());
				StartScene.keyYou=sb.toString();sb.setLength(0);
				while(readThem.hasNextLine())sb.append(readThem.nextLine());
				StartScene.keyThem=sb.toString();sb.setLength(0);
				while(handleReader.hasNextLine())sb.append(handleReader.nextLine());
				StartScene.handlez=sb.toString();
		} catch (FileNotFoundException e){}
	}
	
}
