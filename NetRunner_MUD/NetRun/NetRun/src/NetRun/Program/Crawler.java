package NetRun.Program;

import NetRun.Game;
import NetRun.Ice.Ice;

public class Crawler extends Program {
	private static final long serialVersionUID = 1L;
	public Crawler()
	{
		mProgramName="Crawler.";
		mProgramText="Its extra legs help find all sorts of useful goodies when digging through the heap.(Hes a bit loud though)";
	}
	public boolean programInteract(Game current,Ice ice)
	{
			System.out.println("Crawler doesn't really like ice. Sadly he can't do much about it.");
			return false;	
	}

}
