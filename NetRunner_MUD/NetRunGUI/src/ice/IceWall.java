package ice;

import game.Session;
import javafx.event.Event;
import javafx.event.EventHandler;
import view.Running_Scene;

public class IceWall extends Ice {

	@Override public void encounter() 
	{	
		Running_Scene.setEventPaneBackground(Running_Scene.eventStruct, "iceWallEvent.png");
		
		Running_Scene.eventStruct.label[0].setText("Thud. you hit an iceWall. Select to end run.");
		
		Running_Scene.eventStruct.optSelect[0].setOnMouseClicked(new EventHandler<Event>(){
			@Override public void handle(Event arg0) {
				Running_Scene.playBadButtonSound();
				if(IceWall.decounter1())
					Session.continueRun();
		}});
		Running_Scene.eventStruct.eventPane.setManaged(true);
		Running_Scene.eventStruct.optBox[0].setVisible(true);
		Running_Scene.eventStruct.eventPane.setVisible(true);
	}
	
	public static boolean decounter1()
	{
		Session.consoleLog.append("Thud. You hit an IceWall. Run ended.");
		Running_Scene.eventStruct.eventPane.setVisible(false);
		Running_Scene.eventStruct.optBox[0].setVisible(false);
		Running_Scene.eventStruct.eventPane.setManaged(false);
		return false;
	}
	
}
