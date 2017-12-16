package NetRun.Ice;

import NetRun.Game;

public class Archer extends Ice {

	public Archer()
	{
		mStrength=6;
		mType=2;
		mSubType="Destroyer";
		mName="Archer";
		mDescription="Next time, read the terms of service. (type sentry)";
	}
	
	@Override public boolean triggerIce(Game current)
	{
		System.out.println("Ryu ga waga teki wo kurau!(Destroys up to two programs, corp knows more about you, ends the run)");
		current.setCorpTurns(current.getCorpTurns()+4);
		int i=2;while(current.getProgram().size()>0&&i-->0)current.destroyProgram();
		return false;
	}
}
