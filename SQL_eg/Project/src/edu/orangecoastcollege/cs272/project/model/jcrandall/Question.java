package edu.orangecoastcollege.cs272.project.model.jcrandall;

abstract public class Question {
	private int mId;
	private int mNumberOfQuestion;
	private int mCorrectAnswer;
	
	protected Question(int key,int number,int correct){
		mId=key;
		mNumberOfQuestion=number;
		mCorrectAnswer=correct;
	}
	public int getID(){
		return mId;
	}
	public int getNumQ(){
		return mNumberOfQuestion;
	}
	public int getCorrect(){
		return mCorrectAnswer;
	}
	public abstract String getBody();
	public abstract String getAns();

}
