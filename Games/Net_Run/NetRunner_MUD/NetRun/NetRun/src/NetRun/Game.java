package NetRun;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import NetRun.Agenda.Agenda;
import NetRun.Card.AccountSiphon;
import NetRun.Card.Card;
import NetRun.Card.EasyMark;
import NetRun.Card.Empty;
import NetRun.Card.InsideJob;
import NetRun.Card.IveHadWorse;
import NetRun.Card.MakersEye;
import NetRun.Character.Character;
import NetRun.Ice.Ice;
import NetRun.Operation.Operation;
import NetRun.Program.AdSense;
import NetRun.Program.Crawler;
import NetRun.Program.Program;
import NetRun.Resource.BankJob;
import NetRun.Resource.DataDealer;
import NetRun.Resource.RNDInterface;
import NetRun.Resource.Recycler;
import NetRun.Resource.Resource;
import NetRun.Console.BacklogScheduler;
import NetRun.Console.Console;
import NetRun.Resource.Wyldside;

public class Game 
{
	/**
	 * design is aprox 3 to break ice
	 */
	
	
	static String test;
	
	private int mCredits;
	private int mClicks;
	private Card[] mHand;
	private boolean[][] mCorpIce;
	private List<Resource> mResource;
	private List<Program> mProgram;
	private List<Agenda> mAgenda;
	private Console mConsole;
	private int[] mHitList;
	private int mCorpTurns;
	public Scanner mInputRead;
	private List<Card> mDeck;
	private boolean mVamped;
	private int cTurns;
	private int mCrawl;
	public static final int CARD_POOL=11; // counting empty
	public static final int REASOURCE_POOL=6;
	public static final int PROGRAM_POOL=5;
	public static final int CONSOLE_POOL=3;
	public static final int AGENDA_VALUE=25;
	public static final int UPKEEP=50;
	public static final int PROGRAM_COST=275;
	
	public Game(Scanner cs)
	{
		mCredits=5;
		mClicks=4;
		mHand=new Card[5];
		int i=5;while(i--!=0){mHand[i]=Card.drawBaseCard();}
		mCorpIce=new boolean[5][6];
		mResource=new ArrayList<>();
		mProgram=new ArrayList<>();
		mAgenda=new ArrayList<>();
		mConsole=new Console();
		mHitList=new int[6];
		mCorpTurns=0;
		mInputRead=cs;
		int[]x=new int[CARD_POOL];
		for(int k=0;k<4;k++)x[k]=1;
		mDeck=new ArrayList<>();
		mDeck.add(new AccountSiphon());
		mDeck.add(new EasyMark());
		mDeck.add(new MakersEye());
		mDeck.add(new InsideJob());
		mVamped=false;
		mCrawl=0;
		
	}
	
	public void cClick(int n)
	{
		cTurns+=n;
	}
	
	public void vamped()
	{
		mVamped=true;
	}
	
	public void addCardToDeck(Card card)
	{
		mDeck.add(card);
	}
	
	public List<Card> getDeck()
	{
		return mDeck;
	}
	public void newProgram(Program program)
	{
		mProgram.add(program);
	}
	public void destroyProgram()
	{
		if(mProgram.size()>0)
		{
			Random rng =new Random();
			int choice=rng.nextInt(mResource.size());
			System.out.println(mProgram.get(choice)+" is destroyed.");mProgram.remove(choice);
		}
	}
	
	public void destroyResource()
	{
		if(mResource.size()>0){
		Random rng =new Random();
		int choice=rng.nextInt(mResource.size());
		System.out.println(mResource.get(choice)+" is destroyed.");mResource.remove(choice);
		}
	}
	
	
	
	public void addResource(Resource resource)
	{
		mResource.add(resource);
	}
	
	
	public List<Resource> getResource() {
		return mResource;
	}


	public void setResource(List<Resource> resource) {
		mResource = resource;
	}


	public List<Program> getProgram() {
		return mProgram;
	}


	public void setProgram(List<Program> program) {
		mProgram = program;
	}


	public Console getConsole() {
		return mConsole;
	}


	public void setConsole(Console console) {
		mConsole = console;
	}

	public List<Agenda> getAgendaList()
	{
		return mAgenda;
	}
	public void hitServer(int n)
	{
		mHitList[n]++;
	}
	public void addAgenda(int n)
	{
		Random rng=new Random();
		mAgenda.add(new Agenda(rng.nextInt(n*AGENDA_VALUE),true));
	}
	public void loseAgenda()
	{
		mAgenda.remove(mAgenda.size()-1);
	}
	
	public void setCorpTurns(int n)
	{
		mCorpTurns=n;
	}
	
	@Override public String toString()
	{
		StringBuilder x=new StringBuilder();
		x.append("\n__________________________________________________________________________________________"
				+ "\n[Archive][  RND  ][  HQ   ][  R1   ][   R2  ][   R3  ]\n");
		int i=5;while(i--!=0)x.append("[   "+(mCorpIce[4-i][0]?"X":" ")+"   ][   "+(mCorpIce[4-i][1]?"X":" ")+"   ][   "+(mCorpIce[4-i][2]?"X":" ")+"   ][   "+(mCorpIce[4-i][3]?"X":" ")+"   ][   "+(mCorpIce[4-i][4]?"X":" ")+"   ][   "+(mCorpIce[4-i][5]?"X":" ")+"   ]\n");
		x.append("\n");
		int place=1;
		for(Program p:mProgram){x.append(place+")"+p.toString()+"\n");place++;}
		x.append("\nCredits: "+mCredits+", Clicks remaining: "+mClicks+"/4 console: "+mConsole.getName()+"\n\n");
		for(Resource a:mResource)x.append(a.toString()+"\n");
		x.append("\nHand:\n");
		place=1;
		for(Card c:mHand){x.append(place+")"+c.toString()+"\n");place++;}
		x.append("\nAgenda count for downtime(Not representative of credit total): "+mAgenda.size()+"\n");
		return x.toString();
	}
	
	public void runArchives(Game current){
		if(current.getClicks()>0)
		{
		mHitList[0]++;	
		spendClick();
		boolean victory=true;
		int i=5;while(i--!=0&&victory){if(mCorpIce[i][0])victory=Ice.encounterIce(current);}
		if(victory)
		{
			accessArchives(current);
		}else System.out.println("Run ended.");
		}
		else System.out.println("Your out of time for today.");
		
	}
	public void accessArchives(Game current)
	{
		boolean hasCrawler=false;
		for(Program p:mProgram)if(p instanceof Crawler)hasCrawler=true;
		for(int i=0;i<mCrawl+1;i++){
		int value=110;
		for(Resource r:mResource)if(r instanceof Recycler)value+=55;
		if(value>220)value=220;
		Random rng=new Random();
		int result=rng.nextInt(100);
		if(result>97){mAgenda.add(new Agenda(rng.nextInt((value/10)*AGENDA_VALUE),true));System.out.println("You Scored an over agenda for downtime.");}
		else if(result>71){mAgenda.add(new Agenda(rng.nextInt((value/30)*AGENDA_VALUE),false));System.out.println("You Scored a left over agenda for downtime.");}
		else if(result<1){tGTBT(1);}
		else if(result<2){snare(current);}
		else if(result<3){System.out.println("Ow. sharp junk data.(take one damage)");takeDamage(current);}
		else System.out.println("No luck finding anything in this heap.");
		if(mConsole.getValue()==0)incrementCredits(current);
		if(hasCrawler){current.hitServer(0);System.out.println("Scutle scutle scutle says the crawler.(bit of noise made)");}
		}
		if(hasCrawler){mCrawl++;}
	}
	
	public void incrementCredits(Game current)
	{
		current.setCredits(current.getCredits()+1);
	}
	
	public void runRND(Game current)
	{
		if(current.getClicks()>0)
		{
	    mHitList[1]++;
		spendClick();
		boolean victory=true;
		int i=5;while(i--!=0&&victory){if(mCorpIce[i][1])victory=Ice.encounterIce(current);}
		if(victory)
		{
			accessRND(current);
		}else System.out.println("Run ended.");
		}
		else System.out.println("Your out of time for today.");
	}
	
	public void accessRND(Game current)
	{
		int access=1;
		for(Resource r:mResource)if(r instanceof RNDInterface)access++; // 2 interfaces proved to be too strong
		if(access>2)access=2;
		for(int i=0;i<access;i++)RNDsingleHit(current);
		if(mConsole.getValue()==0)incrementCredits(current);
	}
	
	public void RNDsingleHit(Game current)
	{
		Random rng=new Random();
		int result;
		result=rng.nextInt(100);
		if(result>90){mAgenda.add(new Agenda(rng.nextInt(12*AGENDA_VALUE),true));System.out.println("You Scored an agenda for downtime.");}
		else if(result<2){tGTBT(2);}
		else if(result<3){snare(current);}
		else System.out.println("Guess they diddn't leave anything intresting laying around.");
	}
	
	
	public void runHQ(Game current)
	{
		if(current.getClicks()>0)
		{
		mHitList[2]++;
		spendClick();
		boolean victory=true;
		int i=5;while(i--!=0&&victory){if(mCorpIce[i][2])victory=Ice.encounterIce(current);}
		if(victory)
		{
			accessHQ(current);
		}}
		else System.out.println("Your out of time for today.");
	}
	
	public void accessHQ(Game current)
	{
		Random rng=new Random();
		int numAds=0;
		for(Program p:mProgram)if(p instanceof AdSense)numAds++;
		if(numAds>2)numAds=2;
		mCredits+=2*numAds;
		if(numAds>0)System.out.println("Gained "+2*numAds+" credits in adrev.");
		int result=rng.nextInt(100);
		if(result>95){mAgenda.add(new Agenda(rng.nextInt(25*AGENDA_VALUE),true));System.out.println("You Scored an agenda for downtime.");}
		else if(result==1){tGTBT(3);}
		else if(result<2){snare(current);}
		else System.out.println("Sigh, just more empty useless meeting logs.");
		if(mConsole.getValue()==0)incrementCredits(current);
	}
	
	
	
	public void runR1(Game current)
	{
		if(current.getClicks()>0&&(mCorpIce[0][3]==true))
		{
		mHitList[3]++;
		spendClick();
		boolean victory=true;
		int i=5;while(i--!=0&&victory){if(mCorpIce[i][3])victory=Ice.encounterIce(current);}
		if(victory)
		{
			accessRemote(current);
		}else System.out.println("Run ended.");
		}
		else System.out.println("Your out of time for today or R1 is empty.");
	}
	public void accessRemote(Game current)
	{
		boolean hasBankJob=false;
		int location=-1;
		for(Resource r:mResource)if(r instanceof BankJob){hasBankJob=true;location=mResource.indexOf(r);}
		if(hasBankJob)
		{
			switch(Demo.safeIntegerInput(mInputRead,0,1,"If you would like to use up bank job instead of accessing please enter 0, 1 otherwise: "))
			{
			case 0:
				System.out.println("This better be worth it.");
				Random rnng=new Random();
				int loot=rnng.nextInt(50);
				System.out.println("The haul this time was "+loot+" credits.");
				current.setCredits(current.getCredits()+loot);
				mResource.remove(location);
				return;
			}
		}
		Random rng=new Random();
		int result=rng.nextInt(100);
		if (result>91){mAgenda.add(new Agenda(rng.nextInt(35*AGENDA_VALUE),true));System.out.println("You Scored a serious agenda for downtime.");}
		else if(result>70){mAgenda.add(new Agenda(rng.nextInt(3*AGENDA_VALUE),false));System.out.println("You Scored an unrelated agenda for downtime.");}
		else if(result==1){tGTBT(5);}
		else if(result<3){snare(current);}
		else System.out.println("Meh, nothing here of worth.");
		if(mConsole.getValue()==0)incrementCredits(current);
	}
	public void runR2(Game current)
	{
		if(current.getClicks()>0&&(mCorpIce[0][4]==true))
		{
		mHitList[4]++;
		spendClick();
		boolean victory=true;
		int i=5;while(i--!=0&&victory){if(mCorpIce[i][4])victory=Ice.encounterIce(current);}
		if(victory)
		{
			accessRemote(current);
		}else System.out.println("Run ended.");}
		else System.out.println("Your out of time for today or R2 is empty.");
	}
	public void runR3(Game current)
	{
		if(current.getClicks()>0&&(mCorpIce[0][5]==true))
		{
		mHitList[5]++;
		spendClick();
		boolean victory=true;
		int i=5;while(i--!=0&&victory){if(mCorpIce[i][5])victory=Ice.encounterIce(current);}
		if(victory)
		{
			accessRemote(current);
		}else System.out.println("Run ended.");}
		else System.out.println("Your out of time for today or R3 is empty.");
	}
	public void useCard(Game current,int n)
	{
		
		if(mHand[n] instanceof Empty)System.out.println("No card there.");
		else 
		{
			Card hold=mHand[n];
			mHand[n]=new Empty();
			if(!hold.useCard(current))mHand[n]=hold; // card shouldn't be there while effect is going off
		}
	}
	public boolean drawCard(Game current,boolean loud)
	{
		// removed click requirement 
		if(mHand[0] instanceof Empty)
		{
			mHand[0]=Card.drawCard(current,loud);
			return true;
		}
		else if(mHand[1] instanceof Empty)
		{
			mHand[1]=Card.drawCard(current,loud);
			return true;
		}
		else if(mHand[2] instanceof Empty)
		{
			mHand[2]=Card.drawCard(current,loud);
			return true;
		}
		else if(mHand[3] instanceof Empty)
		{
			mHand[3]=Card.drawCard(current,loud);
			return true;
		}
		else if(mHand[4] instanceof Empty)
		{
			mHand[4]=Card.drawCard(current,loud);
			return true;
		}
		else {System.out.println("Hand is full.");return false;}
	}
	public void takeDamage(Game current)
	{
		/*
		Random rng=new Random();
		switch(rng.nextInt(5)){
		case 0:
		System.out.println("Ow.");
		case 1:
			System.out.println("Owchie.");
		case 2:
			System.out.println("Youchie.");
		case 3:
			System.out.println("Ouch.");
		default:
			System.out.println("Yow.");
		}
		*/
		boolean worse=false;
		if(!(mHand[0] instanceof Empty))
		{
			if(mHand[0]instanceof IveHadWorse)worse=true;
			mHand[0]=(new Empty());
			if(worse)worseProc(current);
		}
		else if(!(mHand[1] instanceof Empty))
		{
			if(mHand[0]instanceof IveHadWorse)worse=true;
			mHand[1]=(new Empty());
			if(worse)worseProc(current);
		}
		else if(!(mHand[2] instanceof Empty))
		{
			if(mHand[0]instanceof IveHadWorse)worse=true;
			mHand[2]=(new Empty());
			if(worse)worseProc(current);
		}
		else if(!(mHand[3] instanceof Empty))
		{
			if(mHand[0]instanceof IveHadWorse)worse=true;
			mHand[3]=(new Empty());
			if(worse)worseProc(current);
		}
		else if(!(mHand[4] instanceof Empty))
		{
			if(mHand[0]instanceof IveHadWorse)worse=true;
			mHand[4]=(new Empty());
			if(worse)worseProc(current);
		}
		else current.killPlayer();
	}
	
	public void worseProc(Game current)
	{
		System.out.println("Thats nothing. I once bathed with your mother. Uphill. Both ways.(Draw 3 cards)");
		current.drawCard(current,true);
		current.drawCard(current,true);
		current.drawCard(current,true);
	}
	
	public void dayJob(Game current)
	{
		if(current.getClicks()>0)
		{
			spendClick();
			work(current);
		}
		else System.out.println("Your out of time for today.");
	}
	public void corpsTurn(Game current)
	{	
		int j=3;while(j--!=0){int j2=5;while(j2--!=0)mCorpIce[j2][5-j]=false;}
		final int[] hitThresholds={3,7,11,15,19};int k=5;while(k--!=0){int i=3;while(i--!=0)if(mHitList[2-i]>hitThresholds[4-k])mCorpIce[4-k][2-i]=true;}
		
		final int[] turnThresholds={5,8,12,16,20};
		for(int layers=0;layers<5;layers++)for(int lanes=0;lanes<3;lanes++)if(mCorpTurns>turnThresholds[layers])mCorpIce[layers][lanes]=true;
		
		Random rng=new Random();
		if(!mVamped){
		cTurns=2;
		while((cTurns--)>0)
		{
			switch(rng.nextInt(5))
			{
			case 0:
				mCorpIce[rng.nextInt(5)][rng.nextInt(3)]=true;
				System.out.println("Corp Action: more ice for the ice gods.");
				break;
			case 1:case 2:	
				Operation.randomOperation(current);
				break;
			default:
				System.out.println("Corp Action: remote servers for super secret, completely unimportant things.");
				int lane=rng.nextInt(3)+3;
				mCorpIce[0][lane]=true;
				if(mCorpTurns+mHitList[lane]>3)mCorpIce[1][lane]=true;
				if(mCorpTurns+mHitList[lane]>7)mCorpIce[2][lane]=true;
				if(mCorpTurns+mHitList[lane]>15)mCorpIce[3][lane]=true;
				if(mCorpTurns+mHitList[lane]>20)mCorpIce[4][lane]=true;
				break;
			}
		}
			mCorpTurns++;
		}
			boolean wyld=false;
			boolean hasDealer=false;
			
			for(Resource r:mResource)
			{
				if(r instanceof Wyldside)wyld=true;
				else if(r instanceof DataDealer)hasDealer=true;
			}
			boolean hasCrawler=false;
			for(Program p:mProgram)
			{
				if(p instanceof Crawler)hasCrawler=true;
			}
			if(hasCrawler)mCrawl=0;
			if (wyld)
			{
				mClicks=3;
				System.out.println("Best place to go whne you want to get your mind out of the gutter and take it inside.(wlydside-> draw 2 cards for a click can't be avoided)");
				drawCard(current,true);
				drawCard(current,true);
			}
			else
			{
				mClicks=4;
			}
			
			if(mConsole instanceof BacklogScheduler)
			{
				BacklogScheduler currentC=(BacklogScheduler)mConsole;
				boolean cont=true;
				while(cont&&mClicks>0&&currentC.getClicks()<4)
				{
					switch(Demo.safeIntegerInput(current.mInputRead,1,2,"BacklogScheduler: enter 0 to store a click, 1 to use all stored clicks for this turn, and 2 to: "))
					{
					case 0:
						currentC.storeClick(current);
						System.out.println("You now have "+currentC.getClicks()+" stored. And "+mClicks+" left for the turn");
						break;
					case 1:
						currentC.unsealClicks(current);
						System.out.println("You now have "+currentC.getClicks()+" stored. And "+mClicks+" left for the turn");
						cont=false;
						break;
					default:
						cont=false;
						break;
					}
				}
			}
			
			if(hasDealer&&(mAgenda.size()>0))
			{
				Random rng2=new Random();
				int price=rng2.nextInt(500);
				switch(Demo.safeIntegerInput(mInputRead,0,1,"Data Dealer: Would you like to sell the last agenda you got for "+price+" Credits?(enter 0 for yes, 1 for no): "))
				{
				case 0:
					mAgenda.remove(mAgenda.size()-1);
					System.out.println("Good deal. +"+price+" credits.");
				default:
					System.out.println("Another time then.");
				}
			}
			
			
		

	}

	public int getCorpTurns()
	{
		return mCorpTurns;
	}
	
	public int getCredits() {
		return mCredits;
	}


	public void setCredits(int credits) {
		mCredits = credits;
	}


	public int getClicks() {
		return mClicks;
	}


	public void setClicks(int clicks) {
		mClicks = clicks;
	}


	public Card[] getHand() {
		return mHand;
	}


	public void setHand(Card[] hand) {
		mHand = hand;
	}


	public boolean[][] getCorpIce() {
		return mCorpIce;
	}


	public void setCorpIce(boolean[][] corpIce) {
		mCorpIce = corpIce;
	}
	
	public void spendClick()
	{
		mClicks-=1;
	}
	public void work(Game current)
	{
		System.out.println("Working... 8:00AM\n");
		try{
		TimeUnit.SECONDS.sleep(3);
		System.out.println("Working still... 11:00AM\n");
		TimeUnit.SECONDS.sleep(3);
		System.out.println("Still working... 2:00PM\n");
		TimeUnit.SECONDS.sleep(3);
		System.out.println("5:00PM thats better, and an entire credit. sweet.(This ironically spends 1/4 of a day it just feels like more :D   your welcome)\n");
		current.setCredits(current.getCredits()+1);
		}catch(Exception e){System.out.println("I don't know wtf this does, gl fixing it.-- sleep error");System.exit(0);}
	}
	public void killPlayer()
	{
		try{
			System.out.println("You died.");
			ObjectOutputStream fileWriter=new ObjectOutputStream(new FileOutputStream("system.dat"));
			Character[] dead={new Character()};
			fileWriter.writeObject(dead);
			fileWriter.close();
			System.exit(0);
			}catch(Exception e){System.out.println("whoops file write error.");System.exit(0);}
	}
	public void snare(Game current)
	{
		
		System.out.println("Oh shit you tripped a snare.");
		if(mCredits>=4)
		{
			switch(Demo.safeIntegerInput(mInputRead,1,2,"Would you like to pay 4 credits to avoid taking 3 damage and some heat?(1 for yes, 2 for no)"))
			{
			case 1:
				System.out.println("That should smooth things out.");
				break;
			default:	
				snared(current);
			break;
		}
		}else snared(current);
		
	}
	private void snared(Game current)
	{
		System.out.println("Ouch. ( take 3 damage & corps knows a little bit more about you)");
		takeDamage(current);
		takeDamage(current);
		takeDamage(current);
		mCorpTurns+=3;
	}
	public void tGTBT(int n)
	{
		System.out.println("You scored a giant super important agenda! (not a trap at all)");
		Random rng=new Random();
		int value=rng.nextInt(n*AGENDA_VALUE);
		mAgenda.add(new Agenda(-(value),"It was too good to be true. You had your account drained for "));
	}
}
