// 10/22/2017 -JC

package View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OTP_Discord_Chat extends Application{
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
		System.exit(0);
	}
	
	static void save(){
		File youKeyFile=new File("ObviouslyNotYouKey.txt");
		File themKeyFile=new File("ObviouslyNotThemKey.txt");
		File userFile=new File("userfile.txt");
		File targetFile=new File("targetfile.txt");
		File tokenFile=new File("tokenfile.txt");
		try(PrintWriter pwYou=new PrintWriter(youKeyFile);
			PrintWriter pwThem=new PrintWriter(themKeyFile);
			PrintWriter userWriter=new PrintWriter(userFile);
			PrintWriter targetWriter=new PrintWriter(targetFile);
			PrintWriter tokenWriter=new PrintWriter(tokenFile);
			){
				pwYou.println(StartScene.keyYou);
				pwThem.println(StartScene.keyThem);
				userWriter.println(StartScene.user);
				targetWriter.println(StartScene.target);
				tokenWriter.println(StartScene.token);
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
	}
	
	static void loadKey(){
		File youKeyFile=new File("ObviouslyNotYouKey.txt");
		File themKeyFile=new File("ObviouslyNotThemKey.txt");
		File userFile=new File("userfile.txt");
		File targetFile=new File("targetfile.txt");
		File tokenFile=new File("tokenfile.txt");
		try(Scanner readYou=new Scanner(youKeyFile);
			Scanner readThem=new Scanner(themKeyFile);
			Scanner userReader=new Scanner(userFile);
			Scanner targetReader=new Scanner(targetFile);
			Scanner tokenReader=new Scanner(tokenFile);
			){
				StringBuilder sb=new StringBuilder();
				while(readYou.hasNextLine())sb.append(readYou.nextLine());
				StartScene.keyYou=sb.toString();sb.setLength(0);
				while(readThem.hasNextLine())sb.append(readThem.nextLine());
				StartScene.keyThem=sb.toString();sb.setLength(0);
				while(userReader.hasNextLine())sb.append(userReader.nextLine());
				StartScene.user=sb.toString();sb.setLength(0);
				while(targetReader.hasNextLine())sb.append(targetReader.nextLine());
				StartScene.target=sb.toString();sb.setLength(0);
				while(tokenReader.hasNextLine())sb.append(tokenReader.nextLine());
				StartScene.token=sb.toString();
		} catch (FileNotFoundException e){}
	}
	
}
