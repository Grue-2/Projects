package NetRun.Ice;

import NetRun.Game;

public class HungryHungryHippo extends Ice {

	public HungryHungryHippo()
	{
		mStrength=1;
		mType=1;
		mSubType="Eater";
		mName="Hungry hungry hippo";
		mDescription="Omnomnomnom.(type codegate)";
	}
	
	@Override public boolean triggerIce(Game current)
	{
		current.destroyResource();
		System.out.println("Hungry hippo eats a resource if you have one. Then ends the run.");
		return false;
	}
}
