package jcrandall.dnd;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import jcrandall.dnd.Monster.Kobold;
import jcrandall.dnd.Monster.Monster;
/**
 * 
		//TODO:Game over's color is wrong
 *
 */
public class DemoDnD extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}
	//images
	Image over=new Image("Images/gameover.png");
	Image cont=new Image("Images/continue.png");
	Image unknown=new Image("Images/unknown.png");
	Image fog=new Image("Images/FogOfWar.png");
	Image blank=new Image("Images/blank.png");
	Image fighter=new Image("Images/fighter.png");
	Image startImage=new Image("Images/startButtonImage.png");
	Image exitImage=new Image("Images/exitButtonImage.png");
	Image startScreenSplash=new Image("Images/startscreensplash.png");
	Image selectSplashScreen=new Image("Images/selectcreensplash.png");
	Image rip=new Image("Images/rip.png");
	private int i=1;
	private static List<Character> mCharsInCombat=new ArrayList<Character>();private List<Monster> mMonsters=new ArrayList<Monster>();
	private static Party currentParty=new Party();ImageView p1=new ImageView(unknown);ImageView p2=new ImageView(unknown);ImageView p3=new ImageView(unknown);ImageView p4=new ImageView(unknown);
	private Stage s;
	private Scene csScene;private Scene storyScene;private Scene combatScene;private GridPane combatPane;private BorderPane combatUI;private GridPane combatWords;
	private static Scene gameOverScene;
	private Button a1=new Button();private Button a2=new Button();private Button a3=new Button();private Button a4=new Button();
	private Label aOut=new Label();
	private List<PC> mercs=new ArrayList<>();
	private Button char1=new Button();private Label char1description=new Label();
	private Button char2=new Button();private Label char2description=new Label();
	private Button char3=new Button();private Label char3description=new Label();
	
	@Override
	public void start(Stage stage) throws Exception {
		//param prep
		s=stage;
		char1description.setWrapText(true);char2description.setWrapText(true);char3description.setWrapText(true);
		aOut.setVisible(false);aOut.setManaged(false);
		//startScene
		GridPane startMenu=new GridPane();
		startMenu.setStyle("-fx-background-color: #ccc7ad;");
		startMenu.setMaxSize(1000,600);
		startMenu.setPadding(new Insets(200,0,0,300));
		startMenu.add(new ImageView(startScreenSplash),0,0);
		Button start=new Button();
		start.setPadding(new Insets(0));
		start.setGraphic(new ImageView(startImage));
		startMenu.add(start,0,1);
		start.setOnAction(e->changeScene(csScene));
		Button exit=new Button();
		exit.setOnAction(e->System.exit(0));
		exit.setPadding(new Insets(0));
		exit.setGraphic(new ImageView(exitImage));
		startMenu.add(exit,0,2);
		Scene startScene=new Scene(startMenu,1600,900);
		//Character Selection
		BorderPane charSelect=new BorderPane();
		charSelect.setStyle("-fx-background-color: #ccc7ad;");
		Scene otherScene=new Scene(charSelect,1600,900);
		csScene=otherScene;
		ImageView charSelectSplash=new ImageView(selectSplashScreen);
		charSelect.setTop(charSelectSplash);
		BorderPane.setAlignment(charSelectSplash,Pos.CENTER);
		GridPane charSel=new GridPane();
		charSel.setHgap(100);charSel.setVgap(10);
		charSel.add(char1description,0,1);charSel.add(char1, 0, 0);
		charSel.add(char2description,1,1);charSel.add(char2, 1, 0);
		charSel.add(char3description,2,1);charSel.add(char3, 2, 0);
		charSelect.setCenter(charSel);
		charSel.setPadding(new Insets(100,0,0,300));
		charSelectGenerateChars();
		char1.setOnAction(e->addMerc(0,i));char2.setOnAction(e->addMerc(1,i));char3.setOnAction(e->addMerc(2,i));
		GridPane current=new GridPane();current.add(p1, 0,1);current.add(p2, 0,2);current.add(p3, 0,3);current.add(p4, 0,4);current.add(new Label("Current party:"), 0,0);
		charSelect.setRight(current);
		current.setPadding(new Insets(50,100,0,100));
		//Story Scene
		BorderPane storyPane=new BorderPane();
		storyScene=new Scene(storyPane,1600,900);
		storyPane.setStyle("-fx-background-color: #ccc7ad;");
		Button skipStory=new Button("You are ambushed by kobolds who get infinite free rounds.\n		Click to watch players die.");
		storyPane.setCenter(skipStory);skipStory.setOnAction(e->changeScene(combatScene));
		//combat Scene
		combatUI=new BorderPane();
		combatUI.setStyle("-fx-background-color: #ccc7ad;");
		combatPane=new GridPane();
		combatUI.setCenter(combatPane);
		combatPane.setStyle("-fx-background-color: #ccc7ad;");
		combatScene=new Scene(combatUI,1600,900);
		//Game over scene
		BorderPane gameOverPane=new BorderPane();
		gameOverPane.setStyle("-fx-background-color: #ccc7ad;");
		gameOverScene=new Scene(gameOverPane,1600,900);
		Button exitButton=new Button();
		gameOverPane.setCenter(exitButton);
		exitButton.setGraphic(new ImageView(over));
		exitButton.setOnAction(c->System.exit(0));
		//Display
		stage.setScene(startScene);
		stage.setTitle("RPG-attempt-jc");
		stage.setFullScreen(true);
		stage.show();
		
	}
	
	private void rollInitiative() // currently adds d6+2 kobolds and puts everyone into a specific place
	{
		i=0;
		for(int i=0;i<currentParty.getReferenceToParty().size();i++)currentParty.getReferenceToParty().get(i).setPos(new int[]{4+i*2,0});
		for(PC p:currentParty.getReferenceToParty())mCharsInCombat.add(p);
		for(int i=0,len=dX(6)+2;i<len;i++){mMonsters.add(new Kobold());}
		for(int i=0;i<mMonsters.size();i++){mMonsters.get(i).setPos(new int[]{0+i/6,i%6});}
		for(Monster m:mMonsters)mCharsInCombat.add(m);
		for(Character c:mCharsInCombat)if(c!=null)c.setInit(dX(20)+(c.getScores()[2]-10)/2);
		Collections.sort(mCharsInCombat);Collections.reverse(mCharsInCombat);
	}
	
	public static int dX(int x)
	{
		Random rng=new Random();
		return rng.nextInt(x)+1;
	}
	
	private void combatSceneRefresh()
	{
		// visual
		combatPane=new GridPane();
		for(int i=0;i<13;i++)for(int k=0;k<7;k++)
		{
			boolean hasPlayer=false;
			for(PC p:currentParty.getReferenceToParty())if(p.getPos()[0]==i&&p.getPos()[1]==k)
			{
				if(p.getHP()>0)combatPane.add(new ImageView(p.getPortrait()),i,k);
				else combatPane.add(new ImageView(rip),i,k);
				hasPlayer=true;
			}
			if(!hasPlayer)
			{
				boolean hasMonster=false;
				for(Monster m:mMonsters)if(m.getPos()[0]==i&&m.getPos()[1]==k){combatPane.add(new ImageView(m.getPortrait()), i, k);hasMonster=true;}
				if(!hasMonster)combatPane.add(new ImageView(fog),i,k);
			}
		}
		combatUI.setCenter(combatPane);
		// initiative
		combatWords=new GridPane();
		combatWords.setPadding(new Insets(100,100,0,10));
		combatWords.setVgap(100);
		StringBuilder str=new StringBuilder();
		str.append("Initiative Order:\nCurrent turn-> ");
		for(Character c:mCharsInCombat)str.append("\t"+c.getName()+"'s initiative: "+c.getInit()+"\n");
		Label local=new Label(str.toString());
		combatWords.add(local,0,0);
		StringBuilder str2=new StringBuilder();
		str2.append("Stats:\n");
		for(Character c:mCharsInCombat)if(c instanceof PC)str2.append("\n"+c.getName()+"\nhp: "+c.getHP()+"\nac: "+c.getAC()+"\n");
		Label local2=new Label(str2.toString());combatWords.add(local2,0,1);		
		combatUI.setRight(combatWords);
		
		//if(mCharsInCombat.get(0)instanceof Monster)
		//{
			a2.setVisible(false);a2.setManaged(false);a3.setVisible(false);a3.setManaged(false);a4.setVisible(false);a4.setManaged(false);
			a1.setGraphic(new ImageView(cont));a1.setOnAction(c->monsterTurn());
		//}
		
		//TODO: add text box for rolls to the side of button pane // change kobold attack to print something to text box
		HBox options=new HBox(a1,a2,a3,a4,aOut);
		options.setSpacing(10);
		options.setAlignment(Pos.CENTER);
		options.setPadding(new Insets(20,100,90,0));
		combatUI.setBottom(options);
		
		boolean noPCsAlive=true;
		for(PC p:currentParty.getReferenceToParty())if(p.getHP()>0)noPCsAlive=false;
		if(noPCsAlive)changeScene(gameOverScene);
		
	}
	public static boolean spaceIsEmpty(int x,int y)
	{
		for(Character c:mCharsInCombat)if(c.getPos()[0]==x&&c.getPos()[1]==y)return false;
		return true;
	}
	public static PC randomPCTarget()
	{
		while(true)
		{
		Random rng=new Random();
		int i=rng.nextInt((currentParty.getReferenceToParty().size()));
		if(currentParty.getReferenceToParty().get(i).getHP()>0)return currentParty.getReferenceToParty().get(i);
		
		boolean noPCsAlive=true;
		for(PC p:currentParty.getReferenceToParty())if(p.getHP()>0)noPCsAlive=false;
		if(noPCsAlive)return null;
		}
	}
	
	
	private void monsterTurn()
	{
		Character m=mCharsInCombat.get(0);
		m.move();
		String cText=m.attack();
		if(!cText.equals("")){aOut.setVisible(true);aOut.setManaged(true);aOut.setText(cText);}
		else {aOut.setVisible(false);aOut.setManaged(false);}
		mCharsInCombat.add(m);mCharsInCombat.remove(0);
		combatSceneRefresh();
	}
	private void addMerc(int n,int partyMember)
	{
		currentParty.addPlayer(mercs.get(n));
		switch(partyMember)
		{
		case 1:
			p1.setImage(mercs.get(n).getPortrait());
			break;
		case 2:
			p2.setImage(mercs.get(n).getPortrait());
			break;
		case 3:
			p3.setImage(mercs.get(n).getPortrait());
			break;
		default:
			p4.setImage(mercs.get(n).getPortrait());
		}
		i++;
		if(i==5)changeScene(storyScene);
		charSelectGenerateChars();
	}
	
	private void charSelectGenerateChars()
	{
		mercs=new ArrayList<PC>();
		mercs.add(PC.makeRandomPC());char1.setGraphic(new ImageView(mercs.get(0).getPortrait()));char1description.setText(mercs.get(0).toString());
		mercs.add(PC.makeRandomPC());char2.setGraphic(new ImageView(mercs.get(1).getPortrait()));char2description.setText(mercs.get(1).toString());
		mercs.add(PC.makeRandomPC());char3.setGraphic(new ImageView(mercs.get(2).getPortrait()));char3description.setText(mercs.get(2).toString());
	}
	
	private void changeScene(Scene scene)
	{
		if(scene==combatScene){rollInitiative();combatSceneRefresh();}
		
		s.setScene(scene);
	}

}
