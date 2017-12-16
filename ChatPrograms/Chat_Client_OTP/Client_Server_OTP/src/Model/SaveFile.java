package Model;

import java.io.Serializable;

public class SaveFile implements Serializable{

	private static final long serialVersionUID = 1L;

	public final String handle, target;
	
	public SaveFile(String handle, String target){
		this.handle = handle; this.target = target;
	}
	
}
