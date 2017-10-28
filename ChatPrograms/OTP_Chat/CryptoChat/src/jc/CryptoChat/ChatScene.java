package jc.CryptoChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ChatScene {
	@FXML TextArea chatWindow;
	@FXML TextField input;
	@FXML Button back;
	@FXML Button clear;
	@FXML Button check;
	
	static PrintWriter out;
	static BufferedReader in;

	
	
	public void initialize() throws IOException{
		input.setOnKeyPressed(e->input(e));
		if(StartScene.asServer){
			out=new PrintWriter(StartScene.client.getOutputStream(),true);
			in=new BufferedReader(new InputStreamReader(StartScene.client.getInputStream()));
			
		}
		else{
			out=new PrintWriter(StartScene.server.getOutputStream(),true);
			in=new BufferedReader(new InputStreamReader(StartScene.server.getInputStream()));
		}
		InputDaemon id=new InputDaemon();
		id.setDaemon(true);id.start();
		
	}
	private class InputDaemon extends Thread{
		public void run(){
			while(StartScene.server!=null||StartScene.client!=null){ //StartScene.moved <-- the timing on these things is really awkard
				synchronized(in){
					try {
						
						StringBuilder result=new StringBuilder(in.readLine());
						String[] output=decrypt(in.readLine(),StartScene.keyThem);
						StartScene.keyThem=output[1];
						result.append(output[0]);
						chatWindow.appendText(result.toString()+"\n");
						
					} catch (IOException e) {e.printStackTrace();}
				}
			}
		}
	}

	@FXML Object back() throws IOException{
		StartScene.clearStatics();
		CryptoChat.loadScene("StartScene.fxml");
		return null;
	}
	@FXML Object clear(){
		chatWindow.setText("Chat cleared.\n");
		return null;
	}
	@FXML Object check(){
		chatWindow.appendText("\nRemaining key-you: "+StartScene.keyYou.length()+
							"\nRemaining key-them: "+StartScene.keyThem.length()+"\n\n");
		return null;
	}
	private Object input(KeyEvent e){
		if(e.getCode()==KeyCode.ENTER){
			chatWindow.appendText(StartScene.handlez+": "+input.getText()+"\n");
			out.println(StartScene.handlez+": ");
			String[] result=encrypt(input.getText(),StartScene.keyYou);
			StartScene.keyYou=result[1];
			out.println(result[0]);
			input.setText("");
		}
		return null;
	}
	
	private String[] decrypt(String ct,String key){
		String result=convertShortFormatToBinaryString(ct);
		int amount=result.length();
		result=pad(key,result);
		return new String[]{convertBinaryStringToText(result),
							key.substring(amount)};
	}
	
	private static String[] encrypt(String pt,String key){
		String result=convertTextToBinary(pt);
		int amount=result.length();
		result=pad(key,result);
		return new String[]{binaryStringToShortFormat(result),
							key.substring(amount)};	
	}
	
	private static String convertBinaryStringToText(String text){
		StringBuilder result=new StringBuilder();
		for(int i=0,len=text.length();i<len;i+=5){
			switch(text.substring(i,i+5).toLowerCase()){
				case "00000":
					result.append("a");
					break;
				case "00001":
					result.append("b");
					break;
				case "00010":
					result.append("c");
					break;
				case "00011":
					result.append("d");
					break;
				case "00100":
					result.append("e");
					break;
				case "00101":
					result.append("f");
					break;
				case "00110":
					result.append("g");
					break;
				case "00111":
					result.append("h");
					break;
				case "01000":
					result.append("i");
					break;
				case "01001":
					result.append("j");
					break;
				case "01010":
					result.append("k");
					break;
				case "01011":
					result.append("l");
					break;
				case "01100":
					result.append("m");
					break;
				case "01101":
					result.append("n");
					break;
				case "01110":
					result.append("o");
					break;
				case "01111":
					result.append("p");
					break;
				case "10000":
					result.append("q");
					break;
				case "10001":
					result.append("r");
					break;
				case "10010":
					result.append("s");
					break;
				case "10011":
					result.append("t");
					break;
				case "10100":
					result.append("u");
					break;
				case "10101":
					result.append("v");
					break;
				case "10110":
					result.append("w");
					break;
				case "10111":
					result.append("x");
					break;
				case "11000":
					result.append("y");
					break;
				case "11001":
					result.append("z");
					break;
				case "11010":
					result.append(" ");
					break;
				case "11011":
					result.append(".");
					break;
				case "11100":
					result.append(",");
					break;
			}
		}
		return result.toString();
	}
	
	private static String convertShortFormatToBinaryString(String shortFormatString){
		StringBuilder result=new StringBuilder();
		for(int i=0,len=shortFormatString.length();i<len;i++){
			switch(shortFormatString.substring(i,i+1)){
				case "0":
					result.append("00000");
					break;
				case "1":
					result.append("00001");
					break;
				case "2":
					result.append("00010");
					break;
				case "3":
					result.append("00011");
					break;
				case "4":
					result.append("00100");
					break;
				case "5":
					result.append("00101");
					break;
				case "6":
					result.append("00110");
					break;
				case "7":
					result.append("00111");
					break;
				case "8":
					result.append("01000");
					break;
				case "9":
					result.append("01001");
					break;
				case "a":
					result.append("01010");
					break;
				case "b":
					result.append("01011");
					break;
				case "c":
					result.append("01100");
					break;
				case "d":
					result.append("01101");
					break;
				case "e":
					result.append("01110");
					break;
				case "f":
					result.append("01111");
					break;
				case "g":
					result.append("10000");
					break;
				case "h":
					result.append("10001");
					break;
				case "i":
					result.append("10010");
					break;
				case "j":
					result.append("10011");
					break;
				case "k":
					result.append("10100");
					break;
				case "l":
					result.append("10101");
					break;
				case "m":
					result.append("10110");
					break;
				case "n":
					result.append("10111");
					break;
				case "o":
					result.append("11000");
					break;
				case "p":
					result.append("11001");
					break;
				case "q":
					result.append("11010");
					break;
				case "r":
					result.append("11011");
					break;
				case "s":
					result.append("11100");
					break;
				case "t":
					result.append("11101");
					break;
				case "u":
					result.append("11110");
					break;
				case "v":
					result.append("11111");
					break;
			}
		}
		return result.toString();
	}
	
	private static String binaryStringToShortFormat(String binaryString){
		StringBuilder result=new StringBuilder();
		for(int i=0,len=binaryString.length();i<len;i+=5){
			switch(binaryString.substring(i,i+5)){
				case "00000":
					result.append("0");
					break;
				case "00001":
					result.append("1");
					break;
				case "00010":
					result.append("2");
					break;
				case "00011":
					result.append("3");
					break;
				case "00100":
					result.append("4");
					break;
				case "00101":
					result.append("5");
					break;
				case "00110":
					result.append("6");
					break;
				case "00111":
					result.append("7");
					break;
				case "01000":
					result.append("8");
					break;
				case "01001":
					result.append("9");
					break;
				case "01010":
					result.append("a");
					break;
				case "01011":
					result.append("b");
					break;
				case "01100":
					result.append("c");
					break;
				case "01101":
					result.append("d");
					break;
				case "01110":
					result.append("e");
					break;
				case "01111":
					result.append("f");
					break;
				case "10000":
					result.append("g");
					break;
				case "10001":
					result.append("h");
					break;
				case "10010":
					result.append("i");
					break;
				case "10011":
					result.append("j");
					break;
				case "10100":
					result.append("k");
					break;
				case "10101":
					result.append("l");
					break;
				case "10110":
					result.append("m");
					break;
				case "10111":
					result.append("n");
					break;
				case "11000":
					result.append("o");
					break;
				case "11001":
					result.append("p");
					break;
				case "11010":
					result.append("q");
					break;
				case "11011":
					result.append("r");
					break;
				case "11100":
					result.append("s");
					break;
				case "11101":
					result.append("t");
					break;
				case "11110":
					result.append("u");
					break;
				case "11111":
					result.append("v");
					break;
			}
		}
		return result.toString();
	}
	private static String pad(String key,String text){
		StringBuilder result=new StringBuilder();
		for(int i=0,len=text.length();i<len;i++){
			if(key.substring(i,i+1).equals(text.substring(i,i+1)))result.append("0");
			else result.append("1");
		}
		return result.toString();
	}
	private static String convertTextToBinary(String text){
		StringBuilder result=new StringBuilder();
		for(int i=0,len=text.length();i<len;i++){
			switch(text.substring(i,i+1).toLowerCase()){
				case "a":
					result.append("00000");
					break;
				case "b":
					result.append("00001");
					break;
				case "c":
					result.append("00010");
					break;
				case "d":
					result.append("00011");
					break;
				case "e":
					result.append("00100");
					break;
				case "f":
					result.append("00101");
					break;
				case "g":
					result.append("00110");
					break;
				case "h":
					result.append("00111");
					break;
				case "i":
					result.append("01000");
					break;
				case "j":
					result.append("01001");
					break;
				case "k":
					result.append("01010");
					break;
				case "l":
					result.append("01011");
					break;
				case "m":
					result.append("01100");
					break;
				case "n":
					result.append("01101");
					break;
				case "o":
					result.append("01110");
					break;
				case "p":
					result.append("01111");
					break;
				case "q":
					result.append("10000");
					break;
				case "r":
					result.append("10001");
					break;
				case "s":
					result.append("10010");
					break;
				case "t":
					result.append("10011");
					break;
				case "u":
					result.append("10100");
					break;
				case "v":
					result.append("10101");
					break;
				case "w":
					result.append("10110");
					break;
				case "x":
					result.append("10111");
					break;
				case "y":
					result.append("11000");
					break;
				case "z":
					result.append("11001");
					break;
				case " ":
					result.append("11010");
					break;
				case ".":
					result.append("11011");
					break;
				case ",":
					result.append("11100");
					break;
			}
		}
		return result.toString();
	}
	
}

/*
 * 
 * 	private class UpdateDaemon  extends Thread{
		public void run(){
			while(StartScene.moved==true){
				
			}
		}
	}
 * 
 * 	if(StartScene.asServer){
			clientOut=new PrintWriter(StartScene.client.getOutputStream(),true);
			clientIn=new BufferedReader(new InputStreamReader(StartScene.client.getInputStream()));
			
			String ip=clientIn.readLine();
			String port=clientIn.readLine();
	
			StartScene.server=new Socket();
			StartScene.server.connect(new InetSocketAddress(ip,Integer.parseInt(port)));
			
			serverOut=new PrintWriter(StartScene.server.getOutputStream(),true);
			serverIn=new BufferedReader(new InputStreamReader(StartScene.client.getInputStream()));
			
		}
		else{
			serverOut=new PrintWriter(StartScene.server.getOutputStream(),true);
			serverIn=new BufferedReader(new InputStreamReader(StartScene.client.getInputStream()));
			
			HostConnection hc=new HostConnection();
			hc.setDaemon(true);
			hc.start();
			CryptoChat.pause();
			
			serverOut.println(StartScene.ipString);
			serverOut.println(StartScene.portString);
			CryptoChat.pause();
			
			clientOut=new PrintWriter(StartScene.client.getOutputStream(),true);
			clientIn=new BufferedReader(new InputStreamReader(StartScene.client.getInputStream()));
		}
		
		UpdateDaemon ud=new UpdateDaemon();
		ud.setDaemon(true);
		ud.start();
 * 
 * 
 * 
 * 
 */
