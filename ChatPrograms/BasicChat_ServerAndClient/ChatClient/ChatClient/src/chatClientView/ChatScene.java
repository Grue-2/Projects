package chatClientView;

import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ChatScene {
	@FXML volatile private TextArea log;
	@FXML private TextField input;
	
	public void initialize(){
		input.setOnKeyPressed(e->input(e));
		log.setText("");
		updateDaemon ud=new updateDaemon();
		ud.setDaemon(true);
		ud.start();
		
	}
	
	private class updateDaemon extends Thread{
		
		public void run(){
			StringBuilder sb=new StringBuilder();
			while(true){
				try{
					String y=ChatClientView.in.readLine();
					if(y.equals("-set")){
						log.setText(sb.toString());
						sb.setLength(0);
						log.setScrollTop(Double.MAX_VALUE);
					}
					else 
						sb.append(y+"\n");
					
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
		}
		
	}
	
	private Object input(KeyEvent e){
		if(e.getCode()==KeyCode.ENTER){
			ChatClientView.out.println(ChatClientView.namerino+": "+input.getText());
			input.clear();
		}
		return null;
	}
	
}
