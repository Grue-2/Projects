package edu.orangecoastcollege.cs272.project.view.jcrandall;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import edu.orangecoastcollege.cs272.project.controller.jcrandall.Controller;
import edu.orangecoastcollege.cs272.project.model.jcrandall.ImageQuestion;
import edu.orangecoastcollege.cs272.project.model.jcrandall.Question;
import edu.orangecoastcollege.cs272.project.model.jcrandall.ReferenceInt;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ProjectFX extends Application{
	private Stage stage;
	private Controller controller=Controller.getInstance(); // should this work statically ??
	private final int X_RES=1600,Y_RES=900;
	private boolean sound=true;
	
	public static void main(String[]args){
		Application.launch(args);
	}
	public void start(Stage primaryStage){
		sound=controller.getSound();
		stage=primaryStage;
		primaryStage.setTitle("Quiz Me");
		welcomeScene();
	}
	public void welcomeScene(){
		GridPane menu=new GridPane();
			menu.setVgap(15);
			menu.setHgap(5);
			menu.setPadding(new Insets(50,15,0,300));
		Button start=new Button();
			start.setGraphic(new ImageView(new Image("Images/start.png")));
			menu.add(start,0,0);
			start.setOnAction(e->start());
		Button cont=new Button();
			cont.setGraphic(new ImageView(new Image("Images/continue.png")));
			menu.add(cont,0,1);
			//cont.setOnAction(e->cont());
		Button settings=new Button();
			settings.setGraphic(new ImageView(new Image("Images/settings.png")));
			menu.add(settings,0,2);
			settings.setOnAction(e->settings());
		Button exit=new Button();
			exit.setGraphic(new ImageView(new Image("Images/exit.png")));
			menu.add(exit,0,3);
			exit.setOnAction(e->exit());
		BorderPane pane=new BorderPane();
			pane.setRight(new ImageView(new Image("Images/splash.png")));
			pane.setTop(new ImageView(new Image("Images/welcomeImage.png")));
			pane.setCenter(menu);
			pane.setStyle("-fx-background-color: #ccc7ad;");
		Scene s=new Scene(pane,X_RES,Y_RES);
			stage.setScene(s);
			stage.show();

	}
	public void exitScene(){
		BorderPane pane=new BorderPane();
			pane.setCenter(new ImageView(new Image("Images/endingsplash.png")));
			pane.setStyle("-fx-background-color: #ccc7ad;");
		Scene s=new Scene(pane,X_RES,Y_RES);
			stage.setScene(s);
			stage.show();
		new Timer().schedule(new TimerTask(){@Override public void run(){System.exit(0);}},2000);	
	}
	public void userCreationScene(){
		GridPane menu=new GridPane();
			menu.setVgap(15);
			menu.setHgap(15);
			menu.setPadding(new Insets(50,15,0,300));
		ImageView incomplete=new ImageView(new Image("Images/incomplete.png"));
			menu.add(incomplete, 1, 0);
			incomplete.setVisible(false);
		TextField nameTF=new TextField("");	
			nameTF.setFont(Font.font("Verdana",FontWeight.MEDIUM,40));
			menu.add(new ImageView(new Image("Images/name.png")),0,1);
			menu.add(nameTF, 1, 1);
		TextField userIDTF=new TextField("");
			userIDTF.setFont(Font.font("Verdana",FontWeight.MEDIUM,40));
			menu.add(new ImageView(new Image("Images/userid.png")),0,2);
			menu.add(userIDTF, 1, 2);
		TextField classIDTF=new TextField("");
			classIDTF.setFont(Font.font("Verdana",FontWeight.MEDIUM,40));
			menu.add(new ImageView(new Image("Images/classID.png")),0,3);
			menu.add(classIDTF, 1, 3);
		Button back=new Button();
			back.setGraphic(new ImageView(new Image("Images/Back.png")));
			back.setOnAction(e->welcomeScene());
		Button confirm=new Button();
			confirm.setGraphic(new ImageView(new Image("Images/confirm.png")));
			confirm.setOnAction(e->confirmNewUser(nameTF.getText(),userIDTF.getText(),classIDTF.getText(),incomplete));
		HBox box=new HBox(back,confirm);
			box.setSpacing(Screen.getPrimary().getVisualBounds().getWidth()*.73125);
		BorderPane pane=new BorderPane();
			pane.setTop(new ImageView(new Image("Images/creationBanner.png")));
			pane.setCenter(menu);
			pane.setBottom(box);
			pane.setRight(new ImageView(new Image("Images/mirror.png")));
			pane.setStyle("-fx-background-color: #ccc7ad;");
		Scene s=new Scene(pane,X_RES,Y_RES);
			stage.setScene(s);
			stage.show();
	}
	public void quizMeScene(){
		GridPane menu=new GridPane();
			menu.setVgap(15);
			menu.setHgap(15);
			menu.setPadding(new Insets(50,15,0,300));
		Button takeQuiz=new Button();
			menu.add(takeQuiz,0,0);
			takeQuiz.setGraphic(new ImageView(new Image("Images/quizmeButton.png")));
			takeQuiz.setOnAction(e->questionSelectionScreen());
		Button questionManage=new Button();
			menu.add(questionManage,0,2);
			questionManage.setGraphic(new ImageView(new Image("Images/manageq.png")));
			questionManage.setOnAction(e->manageQuestionScene());
		Button back=new Button();
			back.setGraphic(new ImageView(new Image("Images/Back.png")));
			back.setOnAction(e->welcomeScene());
		BorderPane pane=new BorderPane();
			pane.setTop(new ImageView(new Image("Images/quizme.png")));
			pane.setCenter(menu);
			pane.setRight(new ImageView(new Image("Images/Druid.png")));
			pane.setStyle("-fx-background-color: #ccc7ad;");
			pane.setBottom(back);
		Scene s=new Scene(pane,X_RES,Y_RES);
			stage.setScene(s);
			stage.show();
	}
	public void questionSelectionScreen(){
		ObservableList<Question> questionLst=controller.getQuestionList();
		ObservableList<Question> quizLst=FXCollections.observableArrayList();
		ObservableList<Question> wrongLst=FXCollections.observableArrayList();
		ListView<Question> questionView=new ListView<>(questionLst);
		ListView<Question> quizView=new ListView<>(quizLst);
		GridPane menu=new GridPane();
			menu.setVgap(15);
			menu.setHgap(15);
			menu.setPadding(new Insets(50,15,0,300));
			menu.add(questionView,0,1);
			menu.add(new ImageView(new Image("Images/qpool.png")),0,0);
			menu.add(quizView,1,1);
			menu.add(new ImageView(new Image("Images/currentq.png")),1,0);
		Button rightMove=new Button();
			rightMove.setGraphic(new ImageView(new Image("Images/rightarrow.png")));
			rightMove.setOnAction(e->move(questionView,questionLst,quizLst));
			menu.add(rightMove,1,2);
		Button leftMove=new Button();
			leftMove.setGraphic(new ImageView(new Image("Images/leftarrow.png")));
			leftMove.setOnAction(e->move(quizView,quizLst,questionLst));
			menu.add(leftMove,0,2);
		Button back=new Button();
			back.setGraphic(new ImageView(new Image("Images/Back.png")));
			back.setOnAction(e->quizMeScene());
		Button confirm=new Button();
			confirm.setGraphic(new ImageView(new Image("Images/confirm.png")));
			confirm.setOnAction(e->quizStart(quizLst,wrongLst));
		HBox box=new HBox(back,confirm);
			box.setSpacing(Screen.getPrimary().getVisualBounds().getWidth()*.73125);
		BorderPane pane=new BorderPane();
			pane.setTop(new ImageView(new Image("Images/quizme.png")));
			pane.setCenter(menu);
			pane.setStyle("-fx-background-color: #ccc7ad;");
			pane.setBottom(box);
		Scene s=new Scene(pane,X_RES,Y_RES);
			stage.setScene(s);
			stage.show();
	}
	public void quizStart(ObservableList<Question> lst,ObservableList<Question> wrong){
		Collections.shuffle(lst);
		quizScene(lst,wrong);
	}
	public void quizScene(ObservableList<Question> lst,ObservableList<Question> wrong){
		
		if(lst.size()>0){
			questionScene(lst.get(0),lst,wrong);
		}
		else if(wrong.size()>0){
			lst=wrong;
			wrong=FXCollections.observableArrayList();
			quizStart(lst,wrong);
		}
		else victoryScene();
	}

	public void questionScene(Question q,ObservableList<Question> lst,ObservableList<Question> wrong){
		ReferenceInt ans=new ReferenceInt();
		int numQuestions=q.getNumQ();
		GridPane menu=new GridPane();
			menu.setVgap(15);
			menu.setHgap(15);
			menu.setPadding(new Insets(50,15,0,300));
		Button a=new Button();
			initializeButton(a,menu,0,"Images/A.png",numQuestions,ans);
			a.setOnAction(e->bPress(1,a,ans));
		Button b=new Button();
			initializeButton(b,menu,1,"Images/B.png",numQuestions,ans);
			b.setOnAction(e->bPress(2,b,ans));
		Button c=new Button();
			initializeButton(c,menu,2,"Images/C.png",numQuestions,ans);
			c.setOnAction(e->bPress(3,c,ans));
		Button d=new Button();
			initializeButton(d,menu,3,"Images/D.png",numQuestions,ans);
			d.setOnAction(e->bPress(4,d,ans));
		Button e=new Button();
			initializeButton(e,menu,4,"Images/E.png",numQuestions,ans);
			e.setOnAction(e2->bPress(5,e,ans));
		Button f=new Button();
			initializeButton(f,menu,5,"Images/F.png",numQuestions,ans);
			f.setOnAction(e2->bPress(6,f,ans));
		Button g=new Button();
			initializeButton(g,menu,6,"Images/G.png",numQuestions,ans);
			g.setOnAction(e2->bPress(7,g,ans));
		Button h=new Button();
			initializeButton(h,menu,7,"Images/H.png",numQuestions,ans);
			h.setOnAction(e2->bPress(8,h,ans));
		Button i=new Button();
			initializeButton(i,menu,8,"Images/I.png",numQuestions,ans);
			i.setOnAction(e2->bPress(9,i,ans));
		Button back=new Button();
			back.setGraphic(new ImageView(new Image("Images/backquit.png")));
			back.setOnAction(e2->quizMeScene());
		Button submit=new Button();
			submit.setGraphic(new ImageView(new Image("Images/submit.png")));

		GridPane rightSide=new GridPane();
			rightSide.add(submit,0,0);
			rightSide.setPadding(new Insets(300,100,0,0));
		BorderPane pane=new BorderPane();
			pane.setTop(new ImageView(new Image("Images/quizmesmall.png")));
			pane.setLeft(menu);
			pane.setStyle("-fx-background-color: #ccc7ad;");
			pane.setBottom(back);
			pane.setRight(rightSide);
		// well I mean Billionaire had the back end classes in front end , maybe its fine.
			submit.setOnAction(e2->ansScene(submit,ans,q,pane,lst,wrong));
			setQuestionAnsView(pane,q,false);
		Scene s=new Scene(pane,X_RES,Y_RES);
			stage.setScene(s);
			stage.show();
	}
	private void setQuestionAnsView(BorderPane pane,Question q,boolean a){
		if(q instanceof ImageQuestion){
			try{
				
				ImageView bv=new ImageView(new Image(new File(q.getBody()).toURI().toURL().toExternalForm(),
																(int)(X_RES/2),(int)(.38889*Y_RES),
																false,false)
																);
				ImageView av=new ImageView(new Image(new File(q.getAns()).toURI().toURL().toExternalForm(),
						(int)(X_RES/2),(int)(.1667*Y_RES),
						false,false)
						);
				if(!a)av.setVisible(false);
				GridPane p=new GridPane();
				p.setPadding(new Insets(200,15,0,50));
				p.add(bv, 0, 0);
				p.add(av, 0, 1);
				p.setVgap(10);
				pane.setCenter(p);
			}
			catch(MalformedURLException e2){
				e2.printStackTrace();
			}
		}
		else {
			Label bl=new Label(q.getBody());
			bl.setFont(Font.font("Verdana",FontWeight.EXTRA_BOLD,18));
			Label al=new Label(q.getAns());
			al.setFont(Font.font("Verdana",FontWeight.EXTRA_BOLD,18));
			if(!a)al.setVisible(false);
			GridPane p=new GridPane();
			p.setPadding(new Insets(200,15,0,50));
			p.add(bl, 0, 0);
			p.add(al, 0, 1);
			p.setVgap(10);
			pane.setCenter(p);
		}
		
	}
	
	public void ansScene(Button submit,ReferenceInt ans,Question q,BorderPane pane,ObservableList<Question> lst,ObservableList<Question> wrong){
		
		if(q.getCorrect()==ans.getNum()){
			pane.setTop(new ImageView(new Image("Images/correct.png")));
			setQuestionAnsView(pane,q,true);
			if(sound)new MediaPlayer(new Media(new File("src/Images/smb_coin.mp3").toURI().toString())).play();
			lst.remove(0);
		}
		else{
			pane.setTop(new ImageView(new Image("Images/incorrect.png")));
			setQuestionAnsView(pane,q,true);
			if(sound)new MediaPlayer(new Media(new File("src/Images/smb2_shrink.mp3").toURI().toString())).play();
			wrong.add(lst.get(0));
			System.out.println(lst.size());
			lst.remove(0);
		}
		submit.setGraphic(new ImageView(new Image("Images/continue2.png")));
		submit.setOnAction(e->quizScene(lst,wrong));
	}
	public void initializeButton(Button b,GridPane menu,int row,String imagePath,int numQuestions,ReferenceInt ans){
		menu.add(b,1,row);
		b.setGraphic(new ImageView(new Image("Images/box.png",80,80,false,false)));
		ImageView v=new ImageView(new Image(imagePath,80,80,false,false));
		menu.add(v,0,row);
		if(numQuestions<row+1){
			b.setVisible(false);
			b.setManaged(false);
			v.setVisible(false);
			v.setManaged(false);
		}
	}
	public void  victoryScene(){
		Button cont=new Button();
			cont.setOnAction(e->quizMeScene());
			cont.setGraphic(new ImageView(new Image("Images/continue.png")));
		Button end=new Button();
			end.setOnAction(e->System.exit(0));
			end.setGraphic(new ImageView(new Image("Images/quit.png")));
		VBox box=new VBox(cont,end);
			box.setSpacing(Y_RES*.6);
		BorderPane pane=new BorderPane();
			pane.setTop(new ImageView(new Image("Images/quizmesmall.png")));
			pane.setStyle("-fx-background-color: #ccc7ad;");
			pane.setCenter(new ImageView(new Image("Images/victory.png")));
			pane.setRight(box);
		Scene s=new Scene(pane,X_RES,Y_RES);
			stage.setScene(s);
			stage.show();
		if(sound)new MediaPlayer(new Media(new File("src/Images/smb_stage_clear.mp3").toURI().toString())).play();
		
	}
	
	public void settingsScene(){
		GridPane menu=new GridPane();
			menu.setVgap(15);
			menu.setHgap(15);
			menu.setPadding(new Insets(50,15,0,300));
		ImageView incomplete=new ImageView(new Image("Images/incompleteRes.png"));
			menu.add(incomplete, 1, 0);
			incomplete.setVisible(false);
		Button resetUser=new Button();
			menu.add(resetUser, 0, 0);
			resetUser.setGraphic(new ImageView(new Image("Images/resetUser.png")));
			resetUser.setOnAction(e->deleteUser());
		Button soundBox=new Button();
			if(sound)soundBox.setGraphic(new ImageView(new Image("Images/checkedbox.png")));
			else soundBox.setGraphic(new ImageView(new Image("Images/box.png")));
			soundBox.setOnAction(e->toggleSound(soundBox));
			menu.add(new ImageView(new Image("Images/Sound.png")),0,3);
			menu.add(soundBox, 1, 3);
		Button back=new Button();
			back.setGraphic(new ImageView(new Image("Images/Back.png")));
			back.setOnAction(e->welcomeScene());
		HBox box=new HBox(back);
		BorderPane pane=new BorderPane();
			pane.setTop(new ImageView(new Image("Images/settingsBanner.png")));
			pane.setCenter(menu);
			pane.setBottom(box);
			pane.setStyle("-fx-background-color: #ccc7ad;");
		Scene s=new Scene(pane,X_RES,Y_RES);
			stage.setScene(s);
			stage.show();
	}
	public void manageQuestionScene(){
		ObservableList<Question> questionLst=controller.getQuestionList();
		ListView<Question> questionView=new ListView<>(questionLst);
		GridPane menu=new GridPane();
			menu.setVgap(15);
			menu.setHgap(15);
			menu.setPadding(new Insets(50,15,0,300));
			menu.add(questionView,0,1);
			menu.add(new ImageView(new Image("Images/currentqs.png")),0,0);
		Button addQuestion=new Button();
			addQuestion.setGraphic(new ImageView(new Image("Images/addq.png")));
			addQuestion.setOnAction(e->addQuestionScene());
		Button deleteQuestion=new Button();
			deleteQuestion.setGraphic(new ImageView(new Image("Images/delquestion.png")));
			deleteQuestion.setOnAction(e->deleteQuestion(questionView));
		VBox box=new VBox(addQuestion,deleteQuestion);	
			menu.add(box,1,1);
			box.setSpacing(15);
		Button back=new Button();
			back.setGraphic(new ImageView(new Image("Images/Back.png")));
			back.setOnAction(e->quizMeScene());
		BorderPane pane=new BorderPane();
			pane.setTop(new ImageView(new Image("Images/quizme.png")));
			pane.setCenter(menu);
			pane.setStyle("-fx-background-color: #ccc7ad;");
			pane.setBottom(back);
		Scene s=new Scene(pane,X_RES,Y_RES);
			stage.setScene(s);
			stage.show();
	}
	public void addQuestionScene(){
		GridPane menu=new GridPane();
			menu.setVgap(15);
			menu.setHgap(15);
			menu.setPadding(new Insets(50,15,0,300));
		Button imageQ=new Button();
			menu.add(imageQ,0,0);
			imageQ.setGraphic(new ImageView(new Image("Images/newimageq.png")));
			imageQ.setOnAction(e->addImageQScene());
		Button textQ=new Button();
			menu.add(textQ,0,1);
			textQ.setGraphic(new ImageView(new Image("Images/newtextq.png")));
			textQ.setOnAction(e->addTextQScene());
		Button back=new Button();
			back.setGraphic(new ImageView(new Image("Images/Back.png")));
			back.setOnAction(e->manageQuestionScene());
		BorderPane pane=new BorderPane();
			pane.setTop(new ImageView(new Image("Images/quizme.png")));
			pane.setCenter(menu);
			pane.setStyle("-fx-background-color: #ccc7ad;");
			pane.setBottom(back);
		Scene s=new Scene(pane,X_RES,Y_RES);
			stage.setScene(s);
			stage.show();
	}

	public void addImageQScene(){
		GridPane menu=new GridPane();
			menu.setVgap(15);
			menu.setHgap(15);
			menu.setPadding(new Insets(50,15,0,300));
		ImageView warning=new ImageView(new Image("Images/badformatlarge.png"));
			warning.setVisible(false);
		Button bodyFileButton=new Button();
			bodyFileButton.setGraphic(new ImageView(new Image("Images/bodyimage.png")));
			menu.add(bodyFileButton, 0, 0);
			ImageView bodyView=new ImageView(new Image("Images/box.png"));
			menu.add(bodyView, 1, 0);
			bodyFileButton.setOnAction(e->fileToImage(bodyView,(int)(X_RES/2),(int)(.38889*Y_RES)));
		Button ansFileButton=new Button();
			ansFileButton.setGraphic(new ImageView(new Image("Images/ansimage.png")));
			menu.add(ansFileButton, 0, 1);
			ImageView ansView=new ImageView(new Image("Images/box.png"));
			menu.add(ansView, 1, 1);
			ansFileButton.setOnAction(e->fileToImage(ansView,(int)(X_RES/2),(int)(.1667*Y_RES)));	
		TextField numberQTF=new TextField("");
			numberQTF.setFont(Font.font("Verdana",FontWeight.MEDIUM,16));
			menu.add(numberQTF, 1, 4);
			menu.add(new ImageView(new Image("Images/numchoices.png")),0,4);
		TextField correctAnsTF=new TextField("");
			menu.add(correctAnsTF, 1, 5);
			menu.add(new ImageView(new Image("Images/encodedAns.png")),0,5);
			correctAnsTF.setFont(Font.font("Verdana",FontWeight.MEDIUM,16));
		Button back=new Button();
			back.setGraphic(new ImageView(new Image("Images/Back.png")));
			back.setOnAction(e->addQuestionScene());
		Button confirm=new Button();
			confirm.setGraphic(new ImageView(new Image("Images/confirm.png")));
			confirm.setOnAction(e->confirmImageQuestion(numberQTF.getText(),
														correctAnsTF.getText(),
														bodyView.snapshot(null,null),
														ansView.snapshot(null,null),
														warning
													    ));
		HBox box=new HBox(back,confirm);
			box.setSpacing(Screen.getPrimary().getVisualBounds().getWidth()*.73125);

		BorderPane pane=new BorderPane();
			pane.setTop(new ImageView(new Image("Images/quizmesmall.png")));
			pane.setCenter(menu);
			pane.setBottom(box);
			pane.setRight(warning);
			pane.setStyle("-fx-background-color: #ccc7ad;");
		Scene s=new Scene(pane,X_RES,Y_RES);
			stage.setScene(s);
			stage.show();
	}
	public void addTextQScene(){
		GridPane menu=new GridPane();
			menu.setVgap(15);
			menu.setHgap(15);
			menu.setPadding(new Insets(50,15,0,300));
		ImageView badFormat=new ImageView(new Image("Images/badformat.png"));
			menu.add(badFormat, 0, 0);
			badFormat.setVisible(false);
		ImageView incomplete=new ImageView(new Image("Images/incompletesmall.png"));
			menu.add(incomplete, 1, 0);
			incomplete.setVisible(false);
		TextField nameTF=new TextField("");	
			nameTF.setFont(Font.font("Verdana",FontWeight.MEDIUM,24));
			menu.add(new ImageView(new Image("Images/qname.png")),0,1);
			menu.add(nameTF, 1, 1);
		TextArea questionBodyTA=new TextArea("");
		questionBodyTA.setFont(Font.font("Verdana",FontWeight.MEDIUM,16));
			menu.add(new ImageView(new Image("Images/qbody.png")),0,2);
			menu.add(questionBodyTA, 1, 2);
		TextArea questionAnsTA=new TextArea("");
			questionAnsTA.setFont(Font.font("Verdana",FontWeight.MEDIUM,16));
			menu.add(new ImageView(new Image("Images/qans.png")),0,3);
			menu.add(questionAnsTA, 1, 3);
		TextField numberQTF=new TextField("");
			numberQTF.setFont(Font.font("Verdana",FontWeight.MEDIUM,16));
			menu.add(numberQTF, 1, 4);
			menu.add(new ImageView(new Image("Images/numchoices.png")),0,4);
		TextField correctAnsTF=new TextField("");
			menu.add(correctAnsTF, 1, 5);
			menu.add(new ImageView(new Image("Images/encodedAns.png")),0,5);
			correctAnsTF.setFont(Font.font("Verdana",FontWeight.MEDIUM,16));
		Button back=new Button();
			back.setGraphic(new ImageView(new Image("Images/Back.png")));
			back.setOnAction(e->addQuestionScene());
		Button confirm=new Button();
			confirm.setGraphic(new ImageView(new Image("Images/confirm.png")));
			confirm.setOnAction(e->confirmNewTextQuestion(nameTF.getText(),
												  questionBodyTA.getText(),
												  questionAnsTA.getText(),
												  numberQTF.getText(),
												  correctAnsTF.getText(),
												  badFormat,
												  incomplete));
		HBox box=new HBox(back,confirm);
			box.setSpacing(Screen.getPrimary().getVisualBounds().getWidth()*.73125);
		BorderPane pane=new BorderPane();
			pane.setTop(new ImageView(new Image("Images/quizme.png")));
			pane.setCenter(menu);
			pane.setBottom(box);
			pane.setStyle("-fx-background-color: #ccc7ad;");
		Scene s=new Scene(pane,X_RES,Y_RES);
			stage.setScene(s);
			stage.show();
	}
	private Image scale(Image src,int width,int height){
		ImageView view=new ImageView(src);
		view.setFitWidth(width);
		view.setFitHeight(height);
		return view.snapshot(null,null);
	}
	private void fileToImage(ImageView test,int width,int height){
		FileChooser bodyFile=new FileChooser();
		try{	
		Image newImage=new Image(bodyFile.showOpenDialog(stage).toURI().toURL().toExternalForm());
		test.setImage(scale(newImage,width,height));
		}
		catch(NullPointerException e){
			// Fails silently on cancel, not sure what else do with it yet.
		}
		catch(MalformedURLException e){
			e.printStackTrace();
			
		}
	}
	public void deleteQuestion(ListView<Question> lst){
		try{
		controller.deleteQuestion(lst.getSelectionModel().getSelectedItem());
		manageQuestionScene();
		}
		catch(Exception e){
			//return;
		}
	}
	public void confirmImageQuestion(String num,String correct,Image body,Image ans,ImageView warning){
		try{
			int numQ=Integer.parseInt(num);
			int ansQ=Integer.parseInt(correct);
			controller.addImageQuestion(numQ,ansQ,body,ans);
			manageQuestionScene();
		}
		catch(NumberFormatException e){
			warning.setVisible(true);
		}
	}
	public void confirmNewTextQuestion(String user,String qBody,String qAns,String numQS,String ansNumS,ImageView emptyWarning,ImageView formatWarning){
		try{
			boolean bad;
			if(bad=(user.equals("")||qBody.equals("")||qAns.equals("")||ansNumS.equals("")||numQS.equals(""))){
				emptyWarning.setVisible(true);
			}
			int numQs=Integer.parseInt(numQS);
			int ansNum=Integer.parseInt(ansNumS);
			if(!bad){
				controller.addTextQuestion(numQs,ansNum,qBody.toLowerCase(),qAns.toLowerCase()); // hopefully stops SQL >.>
				manageQuestionScene();
			}
		}
		catch(NumberFormatException e){
			formatWarning.setVisible(true);
		}
	}
	public void confirmNewUser(String user,String id,String clss,ImageView warning){
		if(user.equals("")||id.equals("")||clss.equals("")){
			warning.setVisible(true);
		}
		else{
			controller.setUser(user,id,clss);
			quizMeScene();
		}
	}
	public void deleteUser(){
		controller.deleteUser();
	}
	public void start(){
		if(!controller.hasUser())userCreationScene();
		else quizMeScene();
	}
	//public void cont(){
		//TODO:Implement
	//}
	public void settings(){
		settingsScene();
	}
	public void exit(){
		exitScene();
	}
	public void toggleSound(Button b){
		if(sound){
			sound=false;
			b.setGraphic(new ImageView(new Image("Images/box.png")));
			controller.setSound(false);
		}
		else{
			sound=true;
			b.setGraphic(new ImageView(new Image("Images/checkedbox.png")));
			controller.setSound(true);
		}
	}
	public void move(ListView<Question> lst,ObservableList<Question> from,ObservableList<Question> to){
		Question q=lst.getSelectionModel().getSelectedItem();
		if(q!=null){
			from.remove(q);
			to.add(q);
		}
	}
	private void bPress(int place,Button b,ReferenceInt val)
	{
		int ans=val.getNum();
		if(ans%(int)(Math.pow(10,place))/(int)(Math.pow(10,place-1))==0)
		{
			b.setGraphic(new ImageView(new Image("Images/checkedbox.png",
												 80,
												 80,
												 false,
												 false
												 )));
			ans+=Math.pow(10,place-1);
		}
		else
		{
			b.setGraphic(new ImageView(new Image("Images/box.png",
												 80,
												 80,
												 false,
												 false
												 )));
			ans-=Math.pow(10,place-1);
		}
		val.setNum(ans);
	}
}
