package model;

public class ManualExam extends Exam {

	public ManualExam(int howManyQues) {
		super(howManyQues);

	}

	public void addAnsToQest(int indexQ, Answer ans) {
		super.questionsEx.get(indexQ).getAnswerArr().add(ans);

	}

}
