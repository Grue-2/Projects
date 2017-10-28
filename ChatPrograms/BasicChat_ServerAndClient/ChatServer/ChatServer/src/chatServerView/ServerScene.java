package chatServerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ServerScene {
	@FXML private TextArea log;
	@FXML private TextField info;
	@FXML private Button clear;
	
	public void initialize(){
		log.setText("");
		acceptConnections ac=new acceptConnections();
		ac.setDaemon(true);
		ac.start();
	}
	
	private class acceptConnections extends Thread{
		public void run(){
			try(ServerSocket ss=new ServerSocket(0)){
				info.setText(InetAddress.getLocalHost().getHostAddress()+" - "+ss.getLocalPort());
				
				while(true){
					Socket clientSocket=ss.accept();
					clientConnection cc=new clientConnection(clientSocket);
					cc.setDaemon(true);
					cc.start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class clientConnection extends Thread{
		private volatile Socket cs;
		public clientConnection(Socket cs){
			this.cs=cs;
		}
		
		public void run(){
			try(PrintWriter out=new PrintWriter(cs.getOutputStream(),true);
				BufferedReader in=new BufferedReader(new InputStreamReader(cs.getInputStream()));
				){

				
				updater u=new updater(cs,out);
				u.setDaemon(true);u.start();
				
				while(!cs.isClosed()){
					log.appendText(in.readLine()+"\n");
				}
				
			}catch(RuntimeException | IOException e){e.printStackTrace();}
		}
		
		
	}
	
	private class updater extends Thread{
		private Socket cs;
		private PrintWriter out;
		updater(Socket cs,PrintWriter out){
			this.cs=cs;
			this.out=out;
		}
		public void run(){
			while(!cs.isClosed()){
				synchronized(out){
					try {
						out.println(log.getText());
						out.println("-set");
						out.wait(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
	
	@FXML Object clear(){
		log.setText("Chat cleared manually by server Admin.\n");
		return null;
	}
	
}
