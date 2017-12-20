package key_managment;

import java.io.Serializable;

public class KeyStruct implements Serializable{
	private static final long serialVersionUID = 8552093786973096753L;
	
	public int place;
	public int end;
	public byte[] data;
	public KeyStruct(byte[] data){place = 0; this.data = data; end = data.length - 1;}
}
