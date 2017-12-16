package Model;

import java.net.Socket;

public class Connection {
	public final Socket socket;
	public final String handle;
	public Connection(Socket socket, String handle){
		this.socket = socket; this.handle = handle;
	}	
}
