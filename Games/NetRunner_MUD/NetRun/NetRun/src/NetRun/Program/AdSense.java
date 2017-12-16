package NetRun.Program;

import NetRun.Game;
import NetRun.Ice.Ice;

public class AdSense extends Program{
	private static final long serialVersionUID = 1L;
	public AdSense()
	{
		mProgramName="Ad sense.";
		mProgramText="Useless on ice. But gives you 2 credits on successful HQ accesses.(max Ad senses 2)";
	}
	public boolean programInteract(Game current,Ice ice)
	{
			System.out.println("Adsense is pretty darn useless on ice.");
			return false;	
	}
	
}
