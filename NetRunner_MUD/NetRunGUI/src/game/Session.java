package game;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import agenda.Agenda;
import ice.Blank_Ice;
import ice.Empty_Ice;
import ice.Ice;
/*
TODO: Once more things are implemented go over old MUD version
and harvest the writing, it was alot better than I'm doing here.
 */
/* Napkin design

-upkeep 100 credits

-aprox 15 (16 optimal) expected free hits

( consistent)
Archives 10% awful,10% bad, 1/3,2/3, e.v. 5 --> 12-22, 27-39 

 ( high value )
rnd 5% good,2% okay, 8/10, 2/10, e.v. 8 --> 28-228, 20-140

( high stakes )  
HQ 1% giant, 1% big, 1% get bent, e.v. 8 --> 0-2880, 0-320 big, neg 0 - 1600 

 */
import save.PlayerSave;

public class Session {
	public static final long UPKEEP_COST = 50;
	
	public static Session session;
	public static StringBuilder consoleLog;
	
	
	public static SecureRandom rng;
	static{
		try {
			rng = SecureRandom.getInstanceStrong();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	private byte threat, clicks, maxClicks;
	private static byte archivesThreat;
	private static byte rndThreat;
	private static byte hqThreat;
//	private static byte branch1Threat;
//	private static byte branch2Trheat;
	private long credits;
	public static List<Ice> currentIce;
	public static List<Agenda> currentAgendas;
	//private byte remote1, remote2;
	
//	public List<Ice> getCurrentIce()
//	{
//		return currentIce; 
//		// unsafe return, but its large enough to warrant
//		// thinking about it
//	}
	public byte getThreat(){return threat;}
	public byte getClicks(){return clicks;}
	public byte getMaxClicks(){return maxClicks;}
	public long getCredits(){return credits;}
	public void setCredits(long credits){this.credits=credits;}
	public boolean spendCredits(int amt)
	{
		if (amt > 0 && amt < credits)
		{
			credits -= amt;
			return true;
		}
		else
			return false;	
	}
	public void gainCredits(int amt){credits+= amt;}
	public boolean spendClick()
	{
		if(clicks > 0)
		{
			--clicks;
			return true;
		}
		else
			return false;
	}
	public boolean spendDoubleClick()
	{
		if(clicks > 1)
		{
			clicks -= 2;
			return true;
		}
		else
			return false;
	}
	
	private Session()
	{
		consoleLog = new StringBuilder(">> ");
		encounterList = new ArrayList<>(4);
		threat = 0;	
		clicks = 4;
		maxClicks = 4;
		archivesThreat = 0;
		rndThreat = 0;
		hqThreat = 0;
//		branch1Threat = 0;
//		branch2Trheat = 0;
		refillIce();
		currentAgendas = new ArrayList<Agenda>();
		
		// load save
		PlayerSave p = PlayerSave.load();
		if (p.credits < 0){
			setCredits(5);
			PlayerSave.delete();
		}
		else
			setCredits(p.credits);
	}
	
	public synchronized static Session startSession()
	{
		if(session == null)
			session = new Session();
		
		return session;
	}
	
	public boolean endTurn()
	{
		if(clicks < 1){
			// order matters, o.w. 4 runs doesn't proc first ice.
			threat = incrementLimit100(threat);
			refillIce();
			//
			clicks = maxClicks;
			return true;
		}
		else
			return false;
	}
	
	public static byte incrementLimit100(byte base)
	{
		if(base + 1 > 100)
			return base;
		else
			return (byte)(base + 1);
	}
	
	// Ice 0-3 is col 1 , 4-7 col 2 etc..
	/*
		0	4	8 	12	16
		1	5	9	13	17
		2	6	10	14	18
		3	7	11	15	19
	*/
	private void refillIce()
	{
		currentIce = new ArrayList<Ice>(20);
		for(int i = 0; i < 20; ++i)
			currentIce.add(new Empty_Ice());

		refillHelper(archivesThreat + threat, 0);
		refillHelper(rndThreat + threat, 4);
		refillHelper(hqThreat + threat, 8);
	}
	
	private void refillHelper(int total, int iceIndex)
	{
		if(total >= 5)
		{
			currentIce.set(iceIndex++, new Blank_Ice());
			if(total >= 15)
			{
				currentIce.set(iceIndex++, new Blank_Ice());
				if(total >= 30)
				{
					currentIce.set(iceIndex++, new Blank_Ice());
					if(total >= 50)
					{
						currentIce.set(iceIndex, new Blank_Ice());
					}
				}
			}
		}
	}
	
	private static List<Integer> encounterList;
	private static Target target;
	
	public void runArchives()
	{
		// 3 2 1 0
		for(int i = 3; i >= 0; --i)
			encounterList.add(i);
		target = Target.ARCHIVES;
		continueRun();
		
	}
	public static void continueRun(){
		if(encounterList.isEmpty())
		{
			switch(target)
			{
			case ARCHIVES:
				accessArchives();
				break;
			case HQ:
				accessHq();
			default: // rnd
				accessRnd();
				break;
			
			}
		}
		else
			currentIce.get(encounterList.remove(0)).encounter();
	}
	
	public void runRnd()
	{
		// 7 6 5 4
		for(int i = 7; i >= 4; --i)
			encounterList.add(i);
		target = Target.RND;
		continueRun();
	}
	public void runHq()
	{
		// 11 10 9 8 
		for(int i = 11; i >= 8; --i)
			encounterList.add(i);
		target = Target.HQ;
		continueRun();
	}
	
	public static void accessArchives()
	{
		archivesThreat = incrementLimit100(archivesThreat);
		consoleLog.append("Acessing archives:\n");
		
		int result = rng.nextInt(100)+1;
		if(result >= 91)
		{
			currentAgendas.add(new Agenda(27+rng.nextInt(13),
					"Some half decent information. Somebody might want it.",
					"PLACE_HOLDER_TEXT"));
			consoleLog.append("Found something.\n");
		}
		else if (result >= 81)
		{
			currentAgendas.add(new Agenda(12+rng.nextInt(11),
					"Found some rubbish information.",
					"PLACE_HOLDER_TEXT"));
			consoleLog.append("Found stuff.\n");
		}
		else
		{
			consoleLog.append(noLuckSentance());
		}
	}
	public static void accessRnd()
	{
		rndThreat = incrementLimit100(rndThreat);
		consoleLog.append("Acessing RnD:\n");
		
		int result = rng.nextInt(100)+1;
		if(result >= 96)
		{
			currentAgendas.add(new Agenda(28+rng.nextInt(201),
					"A good score.",
					"PLACE_HOLDER_TEXT"));
			consoleLog.append("Ooo good find.\n");
		}
		else if (result >= 94)
		{
			currentAgendas.add(new Agenda(20+rng.nextInt(121),
					"Not a bad find.",
					"PLACE_HOLDER_TEXT"));
			consoleLog.append("Found some okay stuff.\n");
		}
		else
		{
			consoleLog.append(noLuckSentance());
		}
	}
	
	public static void accessHq()
	{
		hqThreat = incrementLimit100(hqThreat);
		consoleLog.append("Acessing HQ:\n");
		
		int result = rng.nextInt(100)+1;
		if(result >= 100)
		{
			currentAgendas.add(new Agenda(rng.nextInt(2881),
					"The motherload.",
					"PLACE_HOLDER_TEXT"));
			consoleLog.append("Jackpot.\n");
		}
		else if (result >= 99)
		{
			currentAgendas.add(new Agenda(rng.nextInt(321),
					"Sad reminder of being close to so much more.",
					"PLACE_HOLDER_TEXT"));
			consoleLog.append("Almost.\n");
		}
		else if (result == 1)
		{
			currentAgendas.add(new Agenda(-rng.nextInt(1601),
					"Huh...",
					"PLACE_HOLDER_TEXT"));
			consoleLog.append("Jackpot.\n");
		}
		else
		{
			consoleLog.append(noLuckSentance());
		}
	}
	
	private static String noLuckSentance()
	{
		switch(rng.nextInt(11))
		{
		case 0:
			return "No dice.";
		case 1:
			return "No luck this time.\n";
		case 2:
			return "Nope.\n";
		case 3:
			return "Nothing here.\n";
		case 4:
			return "Nothing found.\n";
		case 5:
			return "A great many nothings found.\n";
		case 6:
			return "Nuh-uh\n";
		case 7:
			return "Negative\n";
		case 8:
			return "Not this time.\n";
		case 9:
			return "Nothing this time.\n";
		default:
			return "Nothing right now.\n";	
		}
		
	}
	public void unloadAgendas() {
		for(Agenda agenda: currentAgendas)
		{
			System.out.println(agenda.value+" "+agenda.lore);
			credits += agenda.value;
		}
	}
}
