package jcrandall.dnd;

import java.util.ArrayList;
import java.util.List;

public class Party 
{
	private List<PC> mParty;
	
	
	public Party()
	{
		mParty=new ArrayList<PC>();
	}
	
	public void addPlayer(PC p)
	{
		mParty.add(p);
	}
	
	public List<PC> getReferenceToParty(){return mParty;}
	
}
