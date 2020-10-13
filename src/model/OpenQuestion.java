package model;

import java.util.ArrayList;

public class OpenQuestion extends Question {

	public OpenQuestion(String question, String answerStr) {
		super(question);
		Answer newA = new Answer(answerStr, true);
		answerArr = new ArrayList<Answer>(1);
		answerArr.add(newA);
	}

}
