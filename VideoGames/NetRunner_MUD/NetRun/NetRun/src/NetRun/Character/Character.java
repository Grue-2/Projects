package NetRun.Character;

import java.io.Serializable;

import NetRun.Card.Card;
import NetRun.Console.Console;
import NetRun.Program.Program;
import NetRun.Resource.Resource;

public class Character implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int mCredits;
	private Card[] mDeck;
	private Resource[] mResources;
	private Program[] mPrograms;
	private Console[] mConsole;
	
	public Character(int cred,Card[] deck,Resource[] resources,Program[] programs,Console[] console)
	{
		mCredits=cred;mResources=resources;mDeck=deck;mPrograms=programs;mConsole=console;
	}
	public Character()
	{
		mCredits=0;
	}
	
	public int getCredits()
	{
		return mCredits;
	}
	public Card[] getDeck() {
		return mDeck;
	}
	public Resource[] getResources() {
		return mResources;
	}
	public Program[] getPrograms() {
		return mPrograms;
	}
	public Console[] getConsole() {
		return mConsole;
	}
	
	
}
