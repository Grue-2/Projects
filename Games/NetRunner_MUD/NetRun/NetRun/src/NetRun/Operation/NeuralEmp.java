package NetRun.Operation;

import NetRun.Game;

public class NeuralEmp extends Operation{
	public NeuralEmp()
	{
	mOpName="Neural E.M.P.";
	mOpText="Good for robots. Not for you.";
	}
	public void operate(Game current)
	{
		System.out.println("Corp Action: "+mOpName+", "+mOpText);
		if(current.getCorpTurns()>3)
		{
			System.out.println("Bzzz.(take one damage)");
			current.takeDamage(current);
		}
		else System.out.println("Corp know enough about you to do anything yet.");
	}
}
