package view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import agenda.Agenda;
import game.Session;
import ice.Empty_Ice;
import ice.Ice;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Running_Scene {
	private final int INFO_DISPLAY_DELAY = 150;
	private final int BUTTON_PRESS_DELAY = 250;
	private boolean refractory = false;
	
	@FXML StackPane eventPane;
	@FXML HBox optBox1;@FXML ImageView optSelect1;@FXML Label optDescription1;
	@FXML HBox optBox2;@FXML ImageView optSelect2;@FXML Label optDescription2;
	@FXML HBox optBox3;@FXML ImageView optSelect3;@FXML Label optDescription3;
	@FXML HBox optBox4;@FXML ImageView optSelect4;@FXML Label optDescription4;
	@FXML HBox optBox5;@FXML ImageView optSelect5;@FXML Label optDescription5;
	public static EventStruct eventStruct;
	
	
	@FXML AnchorPane anchor;
	@FXML HBox hbox;
	@FXML GridPane corpGrid;
	@FXML GridPane runnerGrid;
	@FXML ImageView corpCell11;
	@FXML ImageView corpCell12;
	@FXML ImageView corpCell13;
	@FXML ImageView corpCell14;
	@FXML ImageView corpCell15;
	@FXML ImageView corpCell21;
	@FXML ImageView corpCell22;
	@FXML ImageView corpCell23;
	@FXML ImageView corpCell24;
	@FXML ImageView corpCell25;
	@FXML ImageView corpCell31;
	@FXML ImageView corpCell32;
	@FXML ImageView corpCell33;
	@FXML ImageView corpCell34;
	@FXML ImageView corpCell35;
	@FXML ImageView corpCell41;
	@FXML ImageView corpCell42;
	@FXML ImageView corpCell43;
	@FXML ImageView corpCell44;
	@FXML ImageView corpCell45;
	@FXML ImageView corpCell51;
	@FXML ImageView corpCell52;
	@FXML ImageView corpCell53;
	@FXML ImageView corpCell54;
	@FXML ImageView corpCell55;
	
	@FXML TextArea console;
	@FXML TextArea balance;
	
	@FXML ImageView grip1;
	@FXML ImageView grip2;
	@FXML ImageView grip3;
	@FXML ImageView grip4;
	@FXML ImageView grip5;
	@FXML ImageView endTurn;
	
	@FXML ImageView info;
	
	private static Image emptyServer,emptyGrip,endImage,
		archives, rnd, hq, archivesInfo,
		rndInfo, hqInfo, endInfo;
	{
		try{
				emptyServer = SwingFXUtils.toFXImage(ImageIO.read(new File("server.png")), null);
				emptyGrip = SwingFXUtils.toFXImage(ImageIO.read(new File("emptyGrip.png")), null);
				endImage = SwingFXUtils.toFXImage(ImageIO.read(new File("endTurn.png")), null);
				archives = SwingFXUtils.toFXImage(ImageIO.read(new File("archives.png")), null);
				rnd = SwingFXUtils.toFXImage(ImageIO.read(new File("rnd.png")), null); 
			    hq = SwingFXUtils.toFXImage(ImageIO.read(new File("hq.png")), null);
			    archivesInfo = SwingFXUtils.toFXImage(ImageIO.read(new File("archiveInfo.png")), null);
			    rndInfo = SwingFXUtils.toFXImage(ImageIO.read(new File("rndInfo.png")), null);
			    hqInfo =  SwingFXUtils.toFXImage(ImageIO.read(new File("hqInfo.png")), null);
			    endInfo = SwingFXUtils.toFXImage(ImageIO.read(new File("endInfo.png")), null);
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static List<ImageView> mainServerCells, allIceCells, allGripCells;
	
	private boolean displayingInfo;
	
	private static MediaPlayer goodButtonSound, badButtonSound;
	static
	{
		goodButtonSound =new MediaPlayer(
				 new Media(new File("buttonGood.mp3").toURI().toString()));
		badButtonSound = new MediaPlayer(
				 new Media(new File("buttonBad.mp3").toURI().toString()));
	}
	
	private static Background background;
	static
	{
		Image image;
		try {
			image = SwingFXUtils.toFXImage(ImageIO.read(new File("Room.png")), null);
			BackgroundSize backgroundSize = new BackgroundSize(1600,900,true,true,true,false);
			BackgroundImage backgroundImage = new BackgroundImage(
					image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
					BackgroundPosition.CENTER, backgroundSize);
			background = new Background(backgroundImage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initialize() throws IOException
	{
		//eventPane.setManaged(false);
		
		// initialize major lists
		eventStruct = new EventStruct(eventPane,
				new HBox[]{optBox1,optBox2,optBox3,optBox4,optBox5},
				new ImageView[]{optSelect1,optSelect2,
						optSelect3,optSelect4,optSelect5},
				new Label[]{optDescription1,optDescription2,
						optDescription3,optDescription4,optDescription5});
		
    	mainServerCells = new ArrayList<ImageView>(3);
    	mainServerCells.add(corpCell11);
    	mainServerCells.add(corpCell12);
    	mainServerCells.add(corpCell13);
    	allIceCells = new ArrayList<ImageView>(20);
    	allIceCells.add(corpCell21);allIceCells.add(corpCell31);
    	allIceCells.add(corpCell41);allIceCells.add(corpCell51);
    	allIceCells.add(corpCell22);allIceCells.add(corpCell32);
    	allIceCells.add(corpCell42);allIceCells.add(corpCell52);
    	allIceCells.add(corpCell23);allIceCells.add(corpCell33);
    	allIceCells.add(corpCell43);allIceCells.add(corpCell53);
    	allIceCells.add(corpCell24);allIceCells.add(corpCell34);
    	allIceCells.add(corpCell44);allIceCells.add(corpCell45);
    	allIceCells.add(corpCell25);allIceCells.add(corpCell35);
    	allIceCells.add(corpCell54);allIceCells.add(corpCell55);

    	allGripCells = new ArrayList<ImageView>(5);
    	allGripCells.add(grip1);allGripCells.add(grip2);allGripCells.add(grip3);
    	allGripCells.add(grip4);allGripCells.add(grip5);
		

		//setup graphics
		anchor.setBackground(background);
		
		// setting server images
		corpCell11.setImage(archives);corpCell12.setImage(rnd);
		corpCell13.setImage(hq);corpCell14.setImage(emptyServer);
		corpCell15.setImage(emptyServer);
		
		for(ImageView view : allIceCells)
			view.setImage(Empty_Ice.imageImg);
		
		for(ImageView view: allGripCells)
			view.setImage(emptyGrip);
		
		endTurn.setImage(endImage);
		
		updateGUI();
	}

	@FXML public Object endTurn()
	{
		if(buttonIsRefractory())
			return null;
		
		if(Session.session.endTurn())
		{
			playGoodButtonSound();
			updateGUI();
		}
		return null;
	}
	
	// Archives
	@FXML public Object corpCell11()
	{
		if(buttonIsRefractory())
			return null;
		
		if(Session.session.spendClick())
		{
			playGoodButtonSound();
			Session.session.runArchives();
			console.setText(Session.consoleLog.toString());
			updateGUI();
		}
		return null;
	}
	@FXML public Object displayCorpCell11Info()
	{
		displayingInfo = true;
		
		Task<Void> task = new Task<Void>()
		{
			@Override
			protected Void call() throws Exception {
				try {
					Thread.sleep(INFO_DISPLAY_DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				info.setImage(archivesInfo);
				return null;
			}
		};
		
		new Thread(task).start();
		
		return null;
	}
	// R&D
	@FXML public Object corpCell12()
	{
		if(buttonIsRefractory())
			return null;
		
		if(Session.session.spendClick())
		{
			playGoodButtonSound();
			Session.session.runRnd();
			console.setText(Session.consoleLog.toString());
			updateGUI();
		}
		return null;
	}
	@FXML public Object displayCorpCell12Info()
	{
		displayingInfo = true;
		
		Task<Void> task = new Task<Void>()
		{
			@Override
			protected Void call() throws Exception {
				try {
					Thread.sleep(INFO_DISPLAY_DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				info.setImage(rndInfo);
				return null;
			}
		};
		
		new Thread(task).start();
		
		return null;
	}
	// HQ
	@FXML public Object corpCell13()
	{
		if(buttonIsRefractory())
			return null;
		
		if(Session.session.spendClick())
		{
			playGoodButtonSound();
			Session.session.runHq();
			console.setText(Session.consoleLog.toString());
			updateGUI();
		}
		return null;
	}
	@FXML public Object displayCorpCell13Info()
	{
		displayingInfo = true;
		
		Task<Void> task = new Task<Void>()
		{
			@Override
			protected Void call() throws Exception {
				try {
					Thread.sleep(INFO_DISPLAY_DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				info.setImage(hqInfo);
				return null;
			}
		};
		
		new Thread(task).start();
		
		return null;
	}
	
	@FXML public Object displayEndInfo()
	{
		displayingInfo = true;
		
		Task<Void> task = new Task<Void>()
		{
			@Override
			protected Void call() throws Exception {
				try {
					Thread.sleep(INFO_DISPLAY_DELAY);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				info.setImage(endInfo);
				return null;
			}
		};
		
		new Thread(task).start();

		return null;
	}
	
	@FXML public Object stopInfoDisplay()
	{	
		displayingInfo = false;

		Task<Void> task = new Task<Void>()
		{
			@Override
			protected Void call() throws Exception {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(!displayingInfo)
					info.setImage(null);
				return null;
			}
			
		};
		
		new Thread(task).start();
		
		return null;
	}
	
	
	@FXML public Object displayCorpCell21()
	{
		displayIceInfo(0);
		return null;
	}
	@FXML public Object displayCorpCell31()
	{
		displayIceInfo(1);
		return null;
	}
	@FXML public Object displayCorpCell41()
	{
		displayIceInfo(2);
		return null;
	}
	@FXML public Object displayCorpCell51()
	{
		displayIceInfo(3);
		return null;
	}
	@FXML public Object displayCorpCell22()
	{
		displayIceInfo(4);
		return null;
	}
	@FXML public Object displayCorpCell32()
	{
		displayIceInfo(5);
		return null;
	}
	@FXML public Object displayCorpCell42()
	{
		displayIceInfo(6);
		return null;
	}
	@FXML public Object displayCorpCell52()
	{
		displayIceInfo(7);
		return null;
	}
	@FXML public Object displayCorpCell23()
	{
		displayIceInfo(8);
		return null;
	}
	@FXML public Object displayCorpCell33()
	{
		displayIceInfo(9);
		return null;
	}
	@FXML public Object displayCorpCell43()
	{
		displayIceInfo(10);
		return null;
	}
	@FXML public Object displayCorpCell53()
	{
		displayIceInfo(11);
		return null;
	}

	private void updateGUI()
	{
		// Ice updates
		for(int i = 0; i < 20; ++i)
			updateIce(Session.currentIce.get(i), allIceCells.get(i));
		
		// Click based updates
		if(Session.session.getClicks() == 0)
		{
			endTurn.setStyle("-fx-opacity: 1");
			for(ImageView view: mainServerCells)
				view.setStyle("-fx-opacity: .5");
		}
		else
		{
			endTurn.setStyle("-fx-opacity: .5");
			for(ImageView view: mainServerCells)
				view.setStyle("-fx-opacity: 1");
		}
		
		{ // update BalanceSheet
		StringBuilder clicks = new StringBuilder(), 
				agendas = new StringBuilder();
		for(int i = 0,post = Session.session.getMaxClicks() - Session.session.getClicks();i < post ; ++i)
			clicks.append("♢");
		for(int i = 0, post = Session.session.getClicks(); i < post; ++i)
			clicks.append("♦");
		if(Session.currentAgendas.size() > 0)
		{
			agendas.append("\nScores : \n");
			for(Agenda a: Session.currentAgendas)
				agendas.append(a).append("\n");
		}
		
		balance.setText("Balance Sheet :\n"
				+clicks+" (clicks)\n"
				+Session.session.getCredits()+" (credits)\n"
				+agendas
				);
		}
	}
	public static void playGoodButtonSound()
	{
		goodButtonSound.play();
		goodButtonSound.seek(Duration.ZERO);
	}
	public static void playBadButtonSound()
	{
		badButtonSound.play();
		badButtonSound.seek(Duration.ZERO);
	}
	
	private void updateIce(Ice ice, ImageView corpCell)
	{
		if(ice instanceof Empty_Ice)
			corpCell.setStyle("-fx-opacity: .5");
		else
			corpCell.setStyle("-fx-opacity: 1");
		if(corpCell.getImage() != ice.image)
			corpCell.setImage(ice.image);
	}
	
	// ice display functions
	private void displayIceInfo(int n)
	{
		if(!(Session.currentIce.get(n) instanceof Empty_Ice))
		{
			displayingInfo = true;
			
			Task<Void> task = new Task<Void>()
			{
				@Override
				protected Void call() throws Exception {
					try {
						Thread.sleep(INFO_DISPLAY_DELAY);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					info.setImage(Session.currentIce.get(n).info);
					return null;
				}
			};
			
			new Thread(task).start();
		}
	}

	private boolean buttonIsRefractory()
	{
		if (refractory)
			return true;
		else
		{
			refractory = true;
			
			Task<Void> task = new Task<Void>()
			{
				@Override
				protected Void call() throws Exception {
					try {
						Thread.sleep(BUTTON_PRESS_DELAY);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					refractory = false;
					return null;
				}
			};
			new Thread(task).start();
			return false;
		}
	}
	
	public static void setEventPaneBackground(EventStruct eventStruct, String imagePath)
	{
		Image image;
		try {
			image = SwingFXUtils.toFXImage(ImageIO.read(new File(imagePath)), null);
			BackgroundSize backgroundSize = new BackgroundSize(1600,900,true,true,true,false);
			BackgroundImage backgroundImage = new BackgroundImage(
					image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
					BackgroundPosition.CENTER, backgroundSize);
			Background background = new Background(backgroundImage);
			
			eventStruct.eventPane.setBackground(background);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}


/*
Platform.runLater(new Runnable(){// implement method});
 
		// Done in scene builder
//		Main._scene.getStylesheets().add(
//				this.getClass().getResource("crt.css").toExternalForm());
*/