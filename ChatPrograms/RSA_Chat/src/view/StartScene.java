package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class StartScene {
	@FXML TextField ip;
	@FXML TextField port;
	@FXML TextField handle;
	@FXML TextField ip_Port;
	@FXML Button host;
	@FXML Button connect;
	
	static Socket connection;
	static boolean asHost;
	static String yourHandle;
	
	public void initialize() throws IOException
	{
		asHost = false;
		
		if(connection != null)
			connection.close();
	}
	
	@FXML public Object host()
	{
		host.setText("Hosting");
		host.setDisable(true);
		
		Task<Void> task = new Task<Void>() {
			@Override
			public Void call()
			{
				// port forwarding solution here is a little sketch
				try(ServerSocket serverSocket=new ServerSocket(0); 
						BufferedReader ipReader = 
								new BufferedReader(new InputStreamReader(new URL("http://checkip.amazonaws.com").openStream())))
				{
					ip_Port.setText("ip-port: "+ipReader.readLine()+" - "+serverSocket.getLocalPort());
					ip_Port.setVisible(true);
						
					connection = serverSocket.accept();
					
				}catch(Exception e){System.err.println("serversocket goof.");
				e.printStackTrace();}

				return null;
			}
		};
		
		task.setOnFailed(e->
		{
			host.setDisable(false);
			ip_Port.setText("Hosting failed.");
		});
		task.setOnCancelled(e->
		{
			host.setDisable(false);
			ip_Port.setText("Hosting canceled.");
		});
		
		task.setOnSucceeded(e->{
			asHost = true;
			yourHandle = handle.getText();
			try {
				RSA_Chat.loadScene("ChatScene.fxml");
			} catch (IOException e1) {
				System.err.println("Scene swap to chat scene failed.");
				e1.printStackTrace();
			}
		});
		
		Thread th =new Thread(task);
		th.setDaemon(true);
		th.start();
		
		return null;
	}
	
	
	@FXML public Object connect() throws NumberFormatException, IOException
	{	
		connection = new Socket();
		connection.connect(new InetSocketAddress(ip.getText(),Integer.parseInt(port.getText())));
		yourHandle = handle.getText();
		RSA_Chat.loadScene("ChatScene.fxml");
		return null;
	}
	
}