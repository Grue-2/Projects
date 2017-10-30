package NetRun.Program;

import java.io.Serializable;

import NetRun.Game;
import NetRun.Ice.Ice;
import NetRun.Resource.IceCarver;
import NetRun.Resource.Resource;

abstract public class Program implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String mProgramName;
	protected String mProgramText;
	@Override public String toString()
	{
		return mProgramName+": "+mProgramText;
	}
	abstract public boolean programInteract(Game current,Ice ice);
	
	public boolean programInteractionShell(Game current,Ice ice)
	{
		boolean hasIceCarver=false;
		for(Resource r:current.getResource())if(r instanceof IceCarver)hasIceCarver=true;
		if(hasIceCarver&&(ice.getStrength()>0))ice.setStrength(ice.getStrength()-1);
		return programInteract(current,ice);
	}
	public String getProgramName() {
		return mProgramName;
	}
	public String getProgramText() {
		return mProgramText;
	}

	
	
	
}
