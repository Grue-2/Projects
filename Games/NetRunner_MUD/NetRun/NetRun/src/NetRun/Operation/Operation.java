package NetRun.Operation;

import java.util.Random;

import NetRun.Game;

abstract public class Operation 
{
	protected String mOpName;
	protected String mOpText;
	private static final int OPERATION_POOL=11;
	
	static public void randomOperation(Game current)
	{
		Random rng=new Random();
		switch(rng.nextInt(OPERATION_POOL))
		{
		case 0:
			new BioticLabor().operate(current);
			break;
		case 1:
			new ClosedAccounts().operate(current);
			break;
		case 2:
			new ScorchedEarth().operate(current);
			break;
		case 3:
			new GhostBranch().operate(current);
			break;
		case 4:
			new SEASource().operate(current);
			break;
		case 5:
			new HedgeFund().operate(current);
			break;
		case 6:
			new ShipmentsFromMirrorMorph().operate(current);
			break;
		case 7:
			new CorporateRetreat().operate(current);
			break;
		case 8:
			new HellionBetaTest().operate(current);
			break;
		case 9:
			new NeuralEmp().operate(current);
			break;
		case 10:
			new CerebralCast().operate(current);
		default:
			new Investigation().operate(current);
			break;
		}
	}
	
}
