package ice;

import game.Session;
import javafx.event.Event;
import javafx.event.EventHandler;
import view.Running_Scene;

public class DataPike extends Ice {

	@Override public void encounter() 
	{	
		Running_Scene.setEventPaneBackground(Running_Scene.eventStruct, "dataPikeEvent.png");

		Running_Scene.eventStruct.label[0].setStyle("-fx-text-fill: black");
		Running_Scene.eventStruct.label[0].setText("You walked into a datapike, pricy (costs 2 credits, ends run).");
		
		
		Running_Scene.eventStruct.optSelect[0].setOnMouseClicked(new EventHandler<Event>(){
			@Override public void handle(Event arg0) {
				Running_Scene.playBadButtonSound();
				if(DataPike.decounter1())
					Session.continueRun();
		}});
		
		Running_Scene.eventStruct.eventPane.setManaged(true);
		Running_Scene.eventStruct.optBox[0].setVisible(true);
		Running_Scene.eventStruct.eventPane.setVisible(true);
		

	}

	public static boolean decounter1() {
		if(Session.session.spendCredits(2)){
			Session.consoleLog.append("You hit a datapike losing two credits,\n"
					+"And ending the run.\n");
		}
		else
		{
			Session.consoleLog.append("You hit a datapike it ended the run,\n"
					+"But at least you lost no credits."
					+"\nI mean that's something I guess.\n");
		}
		Running_Scene.eventStruct.label[0].setStyle("-fx-text-fill: white");
		Running_Scene.eventStruct.eventPane.setVisible(false);
		Running_Scene.eventStruct.optBox[0].setVisible(false);
		Running_Scene.eventStruct.eventPane.setManaged(false);
		return false;
	}

}
