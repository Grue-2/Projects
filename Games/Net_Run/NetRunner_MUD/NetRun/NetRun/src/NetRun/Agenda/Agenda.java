package NetRun.Agenda;

import java.util.Random;

public class Agenda {
	private int mValue;
	private String mDescription;
	
	public Agenda(int n,boolean big)
	{
		mValue=n;
		if(big)mDescription=randomDescription();
		else mDescription="Mostly trivial, but hey its worth something.... maybe";
	}
	public Agenda(int n,boolean big,boolean special)
	{
		mValue=n;
		if(big)mDescription=randomSpecialDescription();
		else mDescription="Mostly trivial, but hey its worth something.";
	}
	public Agenda(int n,String description)
	{
		mValue=n;
		mDescription=description;
	}
	public int getValue()
	{
		return mValue;
	}
	
	@Override public String toString()
	{
		return mDescription;
	}
	
	private String randomDescription()
	{
		Random rng=new Random();
		switch(rng.nextInt(6))
		{
		case 0:
			return "RQ Is a state of the art tool for optimizing I-O psych procedures for workers.-brochure";
		case 1:
			return "Illixr has managed to to lower the cost of workers tremendously in the last two years-Worried emails from competitors.";
		case 2:
			return "Yes I know your hubsand is important to you, but we can't give out employee information. You know that.-HR";
		case 3:
			return "Red, please cancel my 4'oclock and compile the reports before I get back-Managment";
		case 4:
			return "0020130011000055550000002CFFF0000007f037f03-";
		case 5:
			return "We can't have another accident like that. With all due respect Madam do you have any idea how much thats going to cost us?-concerned investor";
		default:	
			return "Yea its weird, big ass place like that and nobody wants nothing-Smuggler on Illiyxr";
		}
	}
	private String randomSpecialDescription()
	{
		Random rng=new Random();
		switch(rng.nextInt(3))
		{
		case 0:
			return "Shes becoming less responsive, lets try a hard reboot-engineer email";
		case 1:
			return "Illixr sales to Saeder&Krupp indus. include packaged meat. It's crazy how branched out corporatations are. Long as they're doing well I guess.-employee email";
		default:	
			return "Is anybody else even really still out there?-UNKNOWN";
		}
	}
	
}
