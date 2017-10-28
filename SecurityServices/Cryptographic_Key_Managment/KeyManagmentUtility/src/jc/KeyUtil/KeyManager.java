package jc.KeyUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class KeyManager extends Application{

	private static final String VERSION="v.01";
	public static void main(String...args){Application.launch(args);}
	@Override
	public void start(Stage arg0) throws Exception {
		arg0.setTitle("Key Manager "+VERSION);
		arg0.setScene(new Scene(FXMLLoader.load(getClass().getResource("StartScene.fxml"))));
		arg0.show();
	}

	@Override public void stop() throws FileNotFoundException{
		save();
		System.exit(0);
	}
	
	private void save() throws FileNotFoundException{
		if(StartScene.currentKey!=null){
			PrintWriter out=new PrintWriter(new FileOutputStream(new File("save.txt")));
			out.write(StartScene.currentKey);
			out.close();
		}
		else{
			PrintWriter out=new PrintWriter(new FileOutputStream(new File("save.txt")));
			out.write("");
			out.close();
		}
		deleteEmptyBSF();
	}
	static String load(){
		deleteEmptyBSF();
		try(Scanner in=new Scanner(new File("save.txt"));){
			if(in.hasNext())return in.next();
			else return null;
		}catch(IOException e){
		}
		return null;
		
	}
	private static void deleteEmptyBSF(){
		try(BufferedReader br=new BufferedReader(new FileReader(new File("BinaryStringFile.txt")));){
			if(br.readLine()==null){
				br.close();
				new File("BinaryStringFile.txt").delete();
			}
		}catch(Exception e){}
	}
}
