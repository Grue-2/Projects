package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import Model.Connection;

public class Chat_Server {
	private static final int PORT_NUMBER = 55555;

	private static List<Connection> connections = new LinkedList<Connection>();

	public static void main(String... args) throws IOException {
		while (true) {
			if (connections.size() < 2) {
				try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
						BufferedReader ipReader = new BufferedReader(
								new InputStreamReader(new URL("http://checkip.amazonaws.com").openStream()));) {
					
					System.out.println("Ready on");
					System.out.println("Port: " + serverSocket.getLocalPort());
					System.out.println("remote IP: " + ipReader.readLine()+"\n");

					Socket chatSocket = serverSocket.accept();

					Thread chatThread = new Thread(new Runnable() {
						public void run() {
							try {
								serverChatProtocol1(chatSocket);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
					chatThread.start();
				}
			}
		}
	}

	private static void serverChatProtocol1(Socket soc) throws IOException {
		try (Scanner in = new Scanner(soc.getInputStream());
				PrintWriter out = new PrintWriter(soc.getOutputStream(), true)) {

			// get handle & target
			String handle = in.nextLine(), target = in.nextLine();
			
			Connection currentConnection = new Connection(soc, handle);

			connections.add(currentConnection);
			
			// catchup messages
			try{
				Scanner backlogReader = new Scanner(new File(handle + ".txt"));
				backlogReader.useDelimiter("#END");
				while (backlogReader.hasNext())
					out.println(backlogReader.next()+"#END");
				
				backlogReader.close();
				new File(handle + ".txt").delete();
			}catch(Exception e){
				// fail silently, not always messages to send
			}
			
			// keepup messages // check for message overflow and shut down
			// server	
			in.useDelimiter("#END");
			while (in.hasNext()) {
				String message = in.next();in.nextLine();

				System.out.println("Got message from: "+handle+
						"\nMessage: "+message+
						"\nForwarding to: "+target+"\n");
				
				PrintWriter targetOut = null;
				for(Connection c : connections)
				 if(c.handle.equals(target)){
					 targetOut = new PrintWriter(c.socket.getOutputStream(), true);
					 break;
				 }
				if(targetOut == null){
					targetOut = new PrintWriter(new FileOutputStream (new File(target+".txt"), true));
				
					targetOut.append(handle+"\n"
								   + message
								   + "#END");
					targetOut.flush();
				}
				else{
					targetOut.println(handle+"\n"
							   + message
							   + "#END");
				}
				
			}
			
			connections.remove(currentConnection);
		}
	}

}
