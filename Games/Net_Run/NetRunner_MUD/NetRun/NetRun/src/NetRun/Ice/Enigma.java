package NetRun.Ice;

import NetRun.Game;

public class Enigma extends Ice {
	public Enigma()
	{
		mStrength=2;
		mType=1;
		mSubType="none.";
		mName="Enigma";
		mDescription="Now where did I leave my keys...(type codeGate)";
	}
	
	@Override public boolean triggerIce(Game current)
	{
		if(current.getClicks()>0){
			current.setClicks(current.getClicks()-1);
			System.out.println("What was I doing?(-1click");
		}
		return false;
	}
}
