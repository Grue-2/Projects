package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import encryption.IntegrityException;
import encryption.OTP;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ChatScene {
	@FXML TextArea chatWindow;
	@FXML TextArea input;
	@FXML Button back;
	@FXML Button clear;
	
	private static PrintWriter out;
	private static Scanner in;
	private static boolean shift;
	
	private static OTP otp;
	
	public void initialize() throws Exception
	{
		otp = new OTP();
		
		shift = false;
		
		input.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override public void handle(KeyEvent arg0) {
				if(arg0.getCode()==KeyCode.SHIFT)
					shift = false;
		}});
		
		try {
			out = new PrintWriter(StartScene.connection.getOutputStream(), true);
			in = new Scanner(StartScene.connection.getInputStream());
			in.useDelimiter("#END");
		} catch (IOException e) {
			System.err.println("Failed to get streams from connection.");
			e.printStackTrace();
		}
		
		InputDaemon belz = new InputDaemon();
		belz.setDaemon(true);
		belz.start();
		
		out.println(StartScene.yourHandle);
		out.println(StartScene.yourTarget);
	}
	
	
	@FXML Object back()
	{
		try {
			Client.loadScene("StartScene.fxml");
		} catch (IOException e) {
			System.err.println("Failed to load start scene from chat scene.");
			e.printStackTrace();
		}
		return null;
	}
	@FXML Object clear(){
		chatWindow.setText("Chat cleared.\n");
		return null;
	}
	
	@FXML Object input(KeyEvent e) throws Exception
	{
		if (shift)
		{
			if (e.getCode()==KeyCode.ENTER)
				input.appendText("\n");
		}
		else if(e.getCode()==KeyCode.SHIFT)
			shift = true;
		else if(e.getCode()==KeyCode.ENTER && !input.getText().equals(""))
		{
			//TODO: #END by chance in message glitch ?
			// Would unalign keys.
			
			out.println(otp.encrypt(input.getText())+"#END");
			
			chatWindow.appendText(StartScene.yourHandle+": "+input.getText()+"\n");
			
			input.setText("");
			e.consume();
			}
			
		return null;
		
	}
	
	private class InputDaemon extends Thread{
		public void run(){
			while(true){
					try {
						chatWindow.appendText(in.nextLine()+": "+otp.decrypt(in.next())+"\n");
						in.nextLine();
					} catch(IntegrityException e){
						chatWindow.appendText("\nMessage integrity problem has arisen.\n"
											+ "Either keys are out of sync,\n"
											+ "the connection is faltering or"
											+ "messages have been tampered with."+"\n");
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
	}
	
}
