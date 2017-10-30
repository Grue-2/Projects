/**
 * @Author Josue Crandall
 * @date 3/22/2017
 * @version .08
 * @file Demo.java
 */

package NetRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import NetRun.Agenda.Agenda;
import NetRun.Card.AccountSiphon;
import NetRun.Card.Card;
import NetRun.Card.Diesel;
import NetRun.Card.Eradicate;
import NetRun.Card.InsideJob;
import NetRun.Card.IveHadWorse;
import NetRun.Card.LawyerUp;
import NetRun.Card.MakersEye;
import NetRun.Card.Notoriety;
import NetRun.Card.Stimhack;
import NetRun.Card.SureGamble;
import NetRun.Card.Vamp;
import NetRun.Character.Character;
import NetRun.Console.BacklogScheduler;
import NetRun.Console.Console;
import NetRun.Console.Desperado;
import NetRun.Console.TheToolBox;
import NetRun.Program.AdSense;
import NetRun.Program.Corroder;
import NetRun.Program.Crawler;
import NetRun.Program.Crypsis;
import NetRun.Program.GordianBlade;
import NetRun.Program.Mimic;
import NetRun.Program.Program;
import NetRun.Program.SneakdoorBeta;
import NetRun.Resource.BankJob;
import NetRun.Resource.DataDealer;
import NetRun.Resource.IceCarver;
import NetRun.Resource.RNDInterface;
import NetRun.Resource.RabbitHole;
import NetRun.Resource.Recycler;
import NetRun.Resource.Resource;
import NetRun.Resource.Wyldside;


public class Demo 
{
	
	public static void main(String[] args)
	{
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("~~      Welcome the prototype version of this netrunner style game.      ~~");
		System.out.println("~(Characters eat "+Game.UPKEEP+" credits in living expenses every one swaps modes)~~");
		System.out.println("~~(if you don't have enough for upkeep your char dies before running)    ~~");
		System.out.println("~~                							                             ~~");
		System.out.println("~~      (GL, report errors to josuecrandall@gmail.com )                  ~~");
		System.out.println("~~                                                                       ~~");
		System.out.println("~~               ( death is permanent )                                  ~~");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		Scanner cs=new Scanner(System.in);
		
	while(true){
		Game currentGame=new Game(cs);
		File save=new File("System.dat");
		if(save.exists()){
		try
		{
			ObjectInputStream fileReader=new ObjectInputStream(new FileInputStream("System.dat"));
			Character[] x=(Character[])fileReader.readObject();
			currentGame.setCredits(x[0].getCredits()-Game.UPKEEP);
			if(currentGame.getCredits()<0)currentGame.setCredits(5);
			else
			{
				
			List<Card> deck=new ArrayList<>();
			for(Card c:x[0].getDeck())deck.add(c);
			
			List<Resource> z=new ArrayList<>();
			for(Resource r:x[0].getResources())z.add(r);
			currentGame.setResource(z);
			
			List<Program> z2=new ArrayList<>();
			for(Program p:x[0].getPrograms())z2.add(p);
			currentGame.setProgram(z2);
			
			currentGame.setConsole(x[0].getConsole()[0]);
			
			int l2=5;while(l2--!=0)currentGame.takeDamage(currentGame);
			int l=5;while(l--!=0)currentGame.drawCard(currentGame,false);
			
			if(currentGame.getConsole().getValue()==1)currentGame.setCorpTurns(currentGame.getCorpTurns()-5);
			
			int numHoles=0;
			for(Resource r:currentGame.getResource())if(r instanceof RabbitHole)numHoles++;
			if(numHoles>3)numHoles=3;
			currentGame.setCorpTurns(currentGame.getCorpTurns()-2*numHoles);
			
			}
			fileReader.close();
		}catch(Exception e){System.out.println("Can't read save file starting with fresh character.");}
		}
		
		
		int response=safeIntegerInput(cs,0,2,"\nEnter 0 for Downtime or 1 for run, or 2 to quit?: ");
		if(response==0)
		{
			while(true)
		  {
			int choice=safeIntegerInput(cs,0,2,"\nDown time activities:\n0 to shop\n1 to work\n2 to stop running for now.\n\nplease enter choice: ");
			if(choice==0)
			{
				int shopPreference=safeIntegerInput(cs,1,4,"\nTo shop for programs enter 1, for assets enter 2, for consoles enter 3, 4 for new abilities(cards): ");
				switch(shopPreference)
				{
					case 1:
						while(true)
						{
							switch(safeIntegerInput(cs,1,8,"You have "+currentGame.getCredits()+"creds to burn\n"
									+ "To purchase a corroder: costs "+Game.PROGRAM_COST+", cheaply breaks barriers enter 1\n"
											+ "to purchase a Gordian Blade: cost "+Game.PROGRAM_COST+", cheaply breaks code gates enter 2\n"
													+ "to purchase a Mimic cost "+Game.PROGRAM_COST+", cheaply breaks weak sentries enter 3\n"
															+ "to purchase a SneakDoor(tm) Beta, cost "+Game.PROGRAM_COST+", can be used instead of a breaker at a 50% chance to avoid ice enter 4\n"
																	+ "To purchase a crypsis cost "+Game.PROGRAM_COST+", a breaker which can break anything but is expensive time and credit wise enter 5.\n"
																			+ "To purchase AdSense for"+Game.PROGRAM_COST/2+", a program which gives you 2 credits per successful hq access(limit 3) enter 6\n"
																			+ "To purchase Crawler for"+Game.PROGRAM_COST*2+", a program which gives you an extra access per time you hit archives in a turn, minus one(limit 1) enter 7\n"
																					+ "or 8 to cancel: "))
							{case 1:
								if(currentGame.getCredits()>=Game.PROGRAM_COST)
								{
									currentGame.setCredits(currentGame.getCredits()-Game.PROGRAM_COST);
									System.out.println("You purchased a Corroder.");
									currentGame.newProgram(new Corroder());
								}else System.out.println("Sorry you don't have enough credits.");
								break;
							case 2:
								if(currentGame.getCredits()>=Game.PROGRAM_COST)
								{
									currentGame.setCredits(currentGame.getCredits()-Game.PROGRAM_COST);
									currentGame.newProgram(new GordianBlade());
									System.out.println("You purchased a Gordian Blade.");
								}else System.out.println("Sorry you don't have enough credits.");
								break;
							case 3:
								if(currentGame.getCredits()>=Game.PROGRAM_COST)
								{
									currentGame.setCredits(currentGame.getCredits()-Game.PROGRAM_COST);
									currentGame.newProgram(new Mimic());
									System.out.println("You purchased a Mimic.");
								}else System.out.println("Sorry you don't have enough credits.");
								break;
							case 4:
								if(currentGame.getCredits()>=Game.PROGRAM_COST)
								{
									currentGame.setCredits(currentGame.getCredits()-Game.PROGRAM_COST);
									currentGame.newProgram(new SneakdoorBeta());
									System.out.println("You purchased a Sneakdoor Beta.");
								}else System.out.println("Sorry you don't have enough credits.");
								break;
							case 5:
								if(currentGame.getCredits()>=Game.PROGRAM_COST)
								{
									currentGame.setCredits(currentGame.getCredits()-Game.PROGRAM_COST);
									currentGame.newProgram(new Crypsis());
									System.out.println("You purchased a Crypsis.");
								}else System.out.println("Sorry you don't have enough credits.");
								break;
							case 6:
								if(currentGame.getCredits()>=Game.PROGRAM_COST/2)
								{
									currentGame.setCredits(currentGame.getCredits()-Game.PROGRAM_COST/2);
									currentGame.newProgram(new AdSense());
									System.out.println("You purchased an AdSense(tm).");
								}else System.out.println("Sorry you don't have enough credits.");
								break;
							case 7:
								if(currentGame.getCredits()>=Game.PROGRAM_COST*2)
								{
									currentGame.setCredits(currentGame.getCredits()-Game.PROGRAM_COST*2);
									currentGame.newProgram(new Crawler());
									System.out.println("You purchased a Crawler.");
								}else System.out.println("Sorry you don't have enough credits.");
								break;
							default:
								System.out.println("Maybe next time.");
								break;
							}
							if(1==safeIntegerInput(cs,0,1,"\nWould you like to purchase another program?(1 for yes, 0 for no)"))continue;
							else break;
						}
						
						break;
					case 2:
						switch(safeIntegerInput(cs,1,8,"You have "+currentGame.getCredits()+"creds to spend\nEnter 1 to purchase a Bank plans, lets you choose to steal credits instead of an agenda from a remote server, cost 10 credits.\n"
													 + "Enter 2 to purchase a access to a data dealer, letting you sell agendas at the end of every corp turn for a random price (can be turned down) costs 100 credits\n"
													 + "Enter 3 to purchase an ice carver which lowers the strength of all ice you come across by 1, costs 500 credits.\n"
													 + "Enter 4 to purchase a rabbit hole, max 3, makes it harder for the corp to figure out who you are. cost 300\n"
													 + "Enter 5 to purchase Wyldside access, lets you draw two cards for a click at the start of each turn(can't be turned down) cost 300.\n"
													 + "Enter 6 to purchase an R&D Interface, makes you acccess another time when you access r&d cost 450.\n"
													 + "Enter 7 to purchase a recycler, increases value of archives accesses cost 300.\n"
													 + "or 8 to cancel. enter choice: "))
						{
						case 1:
							if(currentGame.getCredits()>=10)
							{
								currentGame.setCredits(currentGame.getCredits()-10);
								currentGame.addResource(new BankJob());
								System.out.println("Good luck with those.");
							}else System.out.println("Sorry you don't have enough credits.");
							break;
						case 2:
							if(currentGame.getCredits()>=100)
							{
								currentGame.setCredits(currentGame.getCredits()-100);
								currentGame.addResource(new DataDealer());
								System.out.println("Gavlan wheel, Gavlan deal.");
							}else System.out.println("Sorry you don't have enough credits.");
							break;
						case 3:
							if(currentGame.getCredits()>=500)
							{
								currentGame.setCredits(currentGame.getCredits()-500);
								currentGame.addResource(new IceCarver());
								System.out.println("In the public conciousness. There's a hard line between corp and runner.");
							}else System.out.println("Sorry you don't have enough credits.");
							break;
						case 4:
							if(currentGame.getCredits()>=300)
							{
								currentGame.setCredits(currentGame.getCredits()-300);
								currentGame.addResource(new RabbitHole());
								System.out.println("Wheres this one go Alice?");
							}else System.out.println("Sorry you don't have enough credits.");
							break;
						case 5:	
							if(currentGame.getCredits()>=300)
							{
								currentGame.setCredits(currentGame.getCredits()-300);
								currentGame.addResource(new Wyldside());
								System.out.println("Stay away from 20 if you know what I mean.");
							}else System.out.println("Sorry you don't have enough credits.");
							break;
						case 6:	
							if(currentGame.getCredits()>=450)
							{
								currentGame.setCredits(currentGame.getCredits()-450);
								currentGame.addResource(new RNDInterface());
								System.out.println("Careful nobody finds you with that.");
							}else System.out.println("Sorry you don't have enough credits.");
							break;
						case 7:	
							if(currentGame.getCredits()>=300)
							{
								currentGame.setCredits(currentGame.getCredits()-300);
								currentGame.addResource(new Recycler());
								System.out.println("Make sure to wash up after your done.");
							}else System.out.println("Sorry you don't have enough credits.");
							break;
						default:
							break;
						}
						break;
					case 3:
						switch(safeIntegerInput(cs,1,4,"You have "+currentGame.getCredits()+"creds to fling wildly\nYou may only have 1 console at a time, buying a new one overwrites the old one\nEnter 1 to purchase Desperado: cost 1000, gives you 1 credit on every access\nEnter 2 to purchase The Tool Box: cost 3000, makes it extremely hard for corp to find you\nEnter 3 to purchase a Backlogscheduler, lets you store clicks for later use costs 2000\n or 4 to cancel purchase: "))
						{
						case 1:
							if(currentGame.getCredits()>=1000){
							currentGame.setConsole(new Desperado());
							currentGame.setCredits(currentGame.getCredits()-1000);
							System.out.println("You now have Desperado as your console.");
							}
							break;
						case 2:
							if(currentGame.getCredits()>=3000){
							currentGame.setConsole(new TheToolBox());
							currentGame.setCredits(currentGame.getCredits()-3000);
							System.out.println("You now have The Tool Box as your console.");
							}
							break;
						case 3:
							if(currentGame.getCredits()>=2000){
							currentGame.setConsole(new BacklogScheduler());
							currentGame.setCredits(currentGame.getCredits()-2000);
							System.out.println("You now have The Tool Box as your console.");
							}
							break;
						}
						while(true)
						{	
							if(1==safeIntegerInput(cs,0,1,"\nWould you like to purchase another console?(1 for yes, 0 for no)"))continue;
							else break;
						}
						break;
					default:	
						//cards
						while(true)
						{
								String print1="\nYou have "+currentGame.getCredits()+"creds to sink\nThe cards available are: ";
								print1+="for "+(new Eradicate())+" enter 1 for 300\n";
								print1+="for "+(new SureGamble())+" enter 2 for 300\n";
								print1+="for "+(new Stimhack())+" enter 3 for 300\n";
								print1+="for "+(new Diesel())+" enter 4 for 300\n";
								print1+="for "+(new Vamp())+" enter 5 for 300\n";
								print1+="for "+(new Notoriety())+" enter 6 for 300\n";
								print1+="for "+(new LawyerUp())+" enter 7 for 300\n";
								print1+="for "+(new IveHadWorse())+" enter 8 for 300\n";
								print1+="for "+(new MakersEye())+" enter 9 for 300\n";
								print1+="for "+(new AccountSiphon())+" enter 10 for 300\n";
								print1+="for "+(new InsideJob())+" enter 11 for 300\n";
								print1+="or 12 to cancel.";
								switch(safeIntegerInput(cs,1,12,print1))
								{
								case 1:
									int sumErad=0;
									for(Card c:currentGame.getDeck())if(c instanceof Eradicate)sumErad++;
									if(currentGame.getCredits()>300&&(sumErad<3))
									{
										System.out.println("You can draw Eradicate now.");
										currentGame.setCredits(currentGame.getCredits()-300);
										currentGame.addCardToDeck(new Eradicate());
									}else System.out.println("Sorry you don't have enough credits.(or you already have 3 copies of the card)");
									break;
								case 2:
									int sumGamble=0;
									for(Card c:currentGame.getDeck())if(c instanceof SureGamble)sumGamble++;
									if(currentGame.getCredits()>300&&(sumGamble<3))
									{
										System.out.println("You can draw sure gamble.");
										currentGame.setCredits(currentGame.getCredits()-300);
										currentGame.addCardToDeck(new SureGamble());
									}else System.out.println("Sorry you don't have enough credits.(or you already have 3 copies of the card)");
									break;
								case 3:
									int sumHack=0;
									for(Card c:currentGame.getDeck())if(c instanceof Stimhack)sumHack++;
									if(currentGame.getCredits()>300&&(sumHack<3))
									{
										System.out.println("You can draw Stimhack now.");
										currentGame.setCredits(currentGame.getCredits()-300);
										currentGame.addCardToDeck(new Stimhack());
									}else System.out.println("Sorry you don't have enough credits.(or you already have 3 copies of the card)");
									break;
								case 4:
									int sumDiesel=0;
									for(Card c:currentGame.getDeck())if(c instanceof Diesel)sumDiesel++;
									if(currentGame.getCredits()>300&&(sumDiesel<3))
									{
										System.out.println("You can draw Diesel now.");
										currentGame.setCredits(currentGame.getCredits()-300);
										currentGame.addCardToDeck(new Diesel());
									}else System.out.println("Sorry you don't have enough credits.(or you already have 3 copies of the card)");
									break;
								case 5:
									int sumVamp=0;
									for(Card c:currentGame.getDeck())if(c instanceof Vamp)sumVamp++;
									if(currentGame.getCredits()>300&&(sumVamp<3))
									{
										System.out.println("You can draw Vamp now.");
										currentGame.setCredits(currentGame.getCredits()-300);
										currentGame.addCardToDeck(new Vamp());
									}else System.out.println("Sorry you don't have enough credits.(or you already have 3 copies of the card)");
									break;
								case 6:
									int sumNot=0;
									for(Card c:currentGame.getDeck())if(c instanceof Notoriety)sumNot++;
									if(currentGame.getCredits()>300&&(sumNot<3))
									{
										System.out.println("You can draw Notoriety now.");
										currentGame.setCredits(currentGame.getCredits()-300);
										currentGame.addCardToDeck(new Notoriety());
									}else System.out.println("Sorry you don't have enough credits.(or you already have 3 copies of the card)");
									break;
								case 7:
									int sumLawyer=0;
									for(Card c:currentGame.getDeck())if(c instanceof LawyerUp)sumLawyer++;
									if(currentGame.getCredits()>300&&(sumLawyer<3))
									{
										System.out.println("You can draw Lawyer Up now.");
										currentGame.setCredits(currentGame.getCredits()-300);
										currentGame.addCardToDeck(new LawyerUp());
									}else System.out.println("Sorry you don't have enough credits.(or you already have 3 copies of the card)");
									break;
								case 8:
									int sumWorse=0;
									for(Card c:currentGame.getDeck())if(c instanceof IveHadWorse)sumWorse++;
									if(currentGame.getCredits()>300&&(sumWorse<3))
									{
										System.out.println("You can draw I've Had Worse now.");
										currentGame.setCredits(currentGame.getCredits()-300);
										currentGame.addCardToDeck(new IveHadWorse());
									}else System.out.println("Sorry you don't have enough credits.(or you already have 3 copies of the card)");
									break;
								case 9:
									int sumEye=0;
									for(Card c:currentGame.getDeck())if(c instanceof MakersEye)sumEye++;
									if(currentGame.getCredits()>300&&(sumEye<3))
									{
										System.out.println("You can draw Makers Eye now.");
										currentGame.setCredits(currentGame.getCredits()-300);
										currentGame.addCardToDeck(new MakersEye());
									}else System.out.println("Sorry you don't have enough credits.(or you already have 3 copies of the card)");
									break;
								case 10:
									int sumSiphon=0;
									for(Card c:currentGame.getDeck())if(c instanceof AccountSiphon)sumSiphon++;
									if(currentGame.getCredits()>300&&(sumSiphon<3))
									{
										System.out.println("You can draw Account Siphon now.");
										currentGame.setCredits(currentGame.getCredits()-300);
										currentGame.addCardToDeck(new AccountSiphon());
									}else System.out.println("Sorry you don't have enough credits.(or you already have 3 copies of the card)");
									break;
								case 11:
									int sumJob=0;
									for(Card c:currentGame.getDeck())if(c instanceof InsideJob)sumJob++;
									if(currentGame.getCredits()>300&&(sumJob<3))
									{
										System.out.println("You can draw Inside Job now.");
										currentGame.setCredits(currentGame.getCredits()-300);
										currentGame.addCardToDeck(new InsideJob());
									}else System.out.println("Sorry you don't have enough credits.(or you already have 3 copies of the card)");
									break;
								default:
									System.out.println("Maybe next time.");
									break;
									
								}
						 break;
						}
						break;
				}
				
			}
			else if(choice==1)
			{
				currentGame.work(currentGame);
			}
			else 
			{
				saveGame(currentGame,cs);
				break;
			}
		  }
		
		}
		else if(response==1)
		{
			//int corp=safeIntegerInput(cs,1,4,"Please choose which corp to run against\n1)Jinteki(lots of traps and ways to deal damage)");
			
			while(true)
			{
				boolean end=false;
				System.out.println(currentGame);
				int input=safeIntegerInput(cs,1,6,"Please enter 1 to run, 2 to use a card, 3 to draw a card,\n4 to trade clicks for credits, 5 to end turn, 6 to save and exit: ");
				switch(input)
				{
					case 1:
						int answer=safeIntegerInput(cs,1,6,"\nPlease enter 1 to run archives, 2 to run RND, 3 for HQ, 4 for R1\n5 for R2, or 6 for R3 (you can't run R1-R3 if they don't have ice): ");
							switch(answer)
							{
								case 1:
									System.out.println("");
									currentGame.runArchives(currentGame);
									keepGoing(cs);
									break;
								case 2:
									System.out.println("");
									currentGame.runRND(currentGame);
									keepGoing(cs);
									break;
								case 3:
									System.out.println("");
									currentGame.runHQ(currentGame);
									keepGoing(cs);
									break;
								case 4:
									System.out.println("");
									currentGame.runR1(currentGame);
									keepGoing(cs);
									break;
								case 5:
									System.out.println("");
									currentGame.runR2(currentGame);
									keepGoing(cs);
									break;
								default:
									System.out.println("");
									currentGame.runR3(currentGame);
									keepGoing(cs);
									break;
							}
						break;	
					case 2:
						int awnswer=safeIntegerInput(cs,1,5,"\nPlease enter 1-5 for card in your hand you wish to use: ");
						System.out.println("");
						currentGame.useCard(currentGame,awnswer-1);
						keepGoing(cs);
						break;
					case 3:
						System.out.println("");
						if(currentGame.getClicks()>0){
						if(currentGame.drawCard(currentGame,true))currentGame.spendClick();;
						}else System.out.println("No clicks left to draw.");
						keepGoing(cs);
						break;
					case 4:
						System.out.println("");
						currentGame.dayJob(currentGame);
						keepGoing(cs);
						break;
					case 5:
						System.out.println("");
						currentGame.corpsTurn(currentGame);
						keepGoing(cs);
						break;
					default:
						saveGame(currentGame,cs);
						end=true;
						break;
				}
				if(end)break;
			}
		}
		else break;
		}
		cs.close();
	}
	
	public static void keepGoing(Scanner cs)
	{
		System.out.println("\nPress return to continue.(Really you can type in anything)");
		cs.nextLine();
	}
	public static void saveGame(Game current,Scanner cs)
	{
		try{
			System.out.println("Saving.");
			ObjectOutputStream fileWriter=new ObjectOutputStream(new FileOutputStream("system.dat"));
			List<Agenda> w=current.getAgendaList();
			w.forEach
			(
					a->current.setCredits(current.getCredits()+a.getValue()));
			w.forEach(a->System.out.println("Agenda description: "+a+", credits: "+a.getValue()));
			keepGoing(cs);
			Console[] cn=new Console[1];
			cn[0]=current.getConsole();
			Character fred=new Character(
					current.getCredits(),
					current.getDeck().toArray(new Card[current.getDeck().size()]),
					current.getResource().toArray(new Resource[current.getResource().size()]),
					current.getProgram().toArray(new Program[current.getProgram().size()]),
					cn);
			Character[] fredArray=new Character[1];
			fredArray[0]=fred;
			fileWriter.writeObject(fredArray);
			fileWriter.close();
			
			}catch(IOException e){System.out.println("Demo file write error, exiting.");System.exit(0);}
	}
	public static int safeIntegerInput(Scanner cs,int min,int max,String message)
	{
		int awnswer;
		while(true)
		{
			System.out.print(message);
			try
			{
				awnswer=Integer.parseInt(cs.nextLine());
				if(!(awnswer>=min&&awnswer<=max)){System.out.println("Please enter only "+min+" to "+max+", and only that.");continue;}
			}catch(Exception e){System.out.println("Please enter only "+min+" to "+max+", and only that.");continue;}
		break;
		}
		return awnswer;
	}
}
