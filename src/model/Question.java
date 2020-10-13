package model;

import java.util.ArrayList;

public class Question {

	protected String question;
	protected ArrayList<Answer> answerArr;

	public Question(String question) {
		this.question = question;
		this.answerArr = new ArrayList<Answer>();
	}

	public String getQuestion() {
		return question;
	}

	public ArrayList<Answer> getAnswerArr() {
		return answerArr;
	}

	public void setQuestion(Question question) {
		this.question = question.getQuestion();
		this.answerArr = question.getAnswerArr();
	}

	public void setStringQuestion(String str) {
		this.question = str;
	}

	public String toString() {
		String st = "";
		st += "Question:" + question + " answerArr =";
		if (answerArr.size() == 0) {
			st += " null yet";
		} else {
			st += answerArr.toString();
		}
		return st;
	}

}
