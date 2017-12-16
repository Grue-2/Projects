package NetRun.Operation;

import java.util.Random;

import NetRun.Game;

public class ShipmentsFromMirrorMorph extends Operation {
	public ShipmentsFromMirrorMorph()
	{
	mOpName="Shipment from Mirrormorph";
	mOpText="The new parts are in.";
	}
	public void operate(Game current)
	{
		System.out.println("Corp Action: "+mOpName+", "+mOpText);
		boolean[][] x=current.getCorpIce();
		Random rng=new Random();
		x[rng.nextInt(5)][rng.nextInt(3)]=true;
		x[rng.nextInt(5)][rng.nextInt(3)]=true;
		current.setCorpIce(x);
		System.out.println("Corp lays 2 down fresh ice.");
	}
	
}
