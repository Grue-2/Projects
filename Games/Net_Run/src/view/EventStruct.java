package view;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class EventStruct {
	public StackPane eventPane;
	public HBox optBox[];
	public ImageView optSelect[];
	public Label label[];
	
	public EventStruct(StackPane eventPane, HBox option[], ImageView[] optSelect, Label label[]){
		this.eventPane=eventPane;optBox=option;this.optSelect=optSelect;this.label=label;
	}
	
}
