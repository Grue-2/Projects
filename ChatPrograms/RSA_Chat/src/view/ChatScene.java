package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import encryption.FrontEndRSA;
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
	private static BufferedReader in;
	private static String targetPub, pub, priv;	
	private static boolean shift;
	
	public void initialize()
	{
		shift = false;
		
		input.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override public void handle(KeyEvent arg0) {
				if(arg0.getCode()==KeyCode.SHIFT)
					shift = false;
		}});
		
		targetPub = pub = priv = null;
		
		try {
			String[] keys = FrontEndRSA.generatePair();
			pub = keys[0];
			priv = keys[1];
		} catch (NoSuchAlgorithmException e) {
			System.err.println("Failed to generate RSA Keys.");
			e.printStackTrace();
		}
		
		try {
			out = new PrintWriter(StartScene.connection.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(StartScene.connection.getInputStream()));
			
			
		} catch (IOException e) {
			System.err.println("Failed to get streams from connection.");
			e.printStackTrace();
		}
		
		InputDaemon belz = new InputDaemon();
		belz.setDaemon(true);
		belz.start();
		
		out.println("#KR");
	}
	
	
	@FXML Object back()
	{
		try {
			RSA_Chat.loadScene("StartScene.fxml");
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
	
	@FXML Object input(KeyEvent e)
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
			if(targetPub != null)
			{
				chatWindow.appendText(StartScene.yourHandle+": "+input.getText()+"\n");
			
				String cipherText = null;
				try {
					cipherText = FrontEndRSA.encryptWithPublic(targetPub, input.getText());
				} catch (InvalidKeyException | UnsupportedEncodingException | InvalidKeySpecException
						| NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
						| BadPaddingException e1) {
					System.err.println("Encrypting failed.");
					e1.printStackTrace();
					return null;
				}
				
				out.println("#M");
				out.println(StartScene.yourHandle);
				out.println(cipherText);
				
				input.setText("");
				e.consume();
			}
			else
				chatWindow.appendText("Haven't established keys.");	
			
		}
		return null;
	}
	
	private class InputDaemon extends Thread{
		public void run(){
			while(true){
				try{
					String result=in.readLine();
					
					if(result.equals("#KR"))
					{
						out.println("#K"+pub);
						
						if(targetPub == null)
							out.println("#KR");
					}
					else if(targetPub == null && result.startsWith("#K"))
						targetPub = result.substring(2);
					else if(result.equals("#M"))
					{
						chatWindow.appendText(in.readLine()+": ");
						
						String message = in.readLine();
						
						try {
							message = FrontEndRSA.decryptWithPrivate(priv, message);
						} catch (InvalidKeyException | UnsupportedEncodingException | InvalidKeySpecException
								| NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
								| BadPaddingException e) {
							System.err.println("Decryption failed.");
							e.printStackTrace();
							chatWindow.appendText("Decryption failed. Message lost.");
							continue;
						}
						
						chatWindow.appendText(message+"\n");
					}
				}catch(Exception e)
				{
					chatWindow.appendText("Connection broken.");
					break;
				}
			}
		}
	}
	
}
