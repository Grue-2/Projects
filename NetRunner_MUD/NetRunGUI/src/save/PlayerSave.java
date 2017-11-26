package save;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import game.Session;

public class PlayerSave implements Serializable{
	private static final long serialVersionUID = 5960542406097471224L;
	
	public long credits;
	
	public static void save(long credits) throws FileNotFoundException, IOException
	{
		PlayerSave temp = new PlayerSave();
		temp.credits=credits;
		
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("save.ser")));
		out.writeObject(temp);
		out.close();
	}
	public static PlayerSave load()
	{
		PlayerSave temp =new PlayerSave();
		temp.credits = 5;
		
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("save.ser"))))
		{
			temp = (PlayerSave)in.readObject();
			temp.credits -= Session.UPKEEP_COST;
			
		} catch (Exception e){}
		return temp;
	}

	public static void delete()
	{
		new File("save.ser").delete();
	}
	
}
