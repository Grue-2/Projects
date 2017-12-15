package key_managment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;


public class EntropyProvider implements Serializable{
	private static final long serialVersionUID = 6541781276889569915L;
	
	private KeyStruct key;
	private String folder;
	
	public EntropyProvider(String folder)
	{
		this.folder = folder;
		
		File saveFile = new File(folder+"/key.bin");
		
		// set saving place in key on closing
		Thread saveThread = new Thread(new Runnable(){@Override public void run() {
			try(ObjectOutputStream saveWriter = new ObjectOutputStream(new FileOutputStream(saveFile)))
			{
				saveWriter.writeObject(key);
				
			}catch (IOException e) {
				System.err.println("Failed to save data.");
				e.printStackTrace();
			}
		}});
		Runtime.getRuntime().addShutdownHook(saveThread);
		
		// load place in key
		if (saveFile.exists()){
			try(ObjectInputStream saveLoader = new ObjectInputStream(new FileInputStream(saveFile)))
			{
				key = (KeyStruct)saveLoader.readObject();
				
			}catch (IOException | ClassNotFoundException e) {
				System.err.println("Failed to load data.");
				e.printStackTrace();
			}
		}
		else // or start a new place
			eatKeyFile();
	}
	
	public synchronized byte provide(boolean front) throws Exception
	{
		byte result;
		
		
		if(front){	
			if (key.place <= key.end)
				result = key.data[key.place++];
			else
			{
				eatKeyFile();
				
				result = key.data[key.place++];
			}
		}
		else{
			if (key.end >= key.place)
				result = key.data[key.end--];
			else
			{
				eatKeyFile();
				
				result = key.data[key.end--];
			}
		}
		
		return result;
	}
	
	private void eatKeyFile()
	{
		try {
			File[] files = new File(folder+"/.").listFiles();Arrays.sort(files);
			
			File entropySource = null;
			for (File f : files) {
				if (f.toString().contains("KeyFile") && !f.toString().contains("AsBinaryFile")) {
					entropySource = f;
					break;
				}
			}
			
			AES.decrypt(entropySource.toString(), folder+"/keyFileAsBinaryFile.bin");

			if(!Randomness_Test.testSuite(new File(folder+"/keyFileAsBinaryFile.bin").getAbsolutePath()))
				throw new Exception("Randomness test failed.");
			
			entropySource.delete();
			
			key = new KeyStruct(Files.readAllBytes(Paths.get(folder+"/keyFileAsBinaryFile.bin")));
		} catch (Exception e) {
			System.err.println("Could not read key file.");
			e.printStackTrace();
		}
	}
	
	
}
