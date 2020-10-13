package model;

public class Answer {
	private String answer;
	private boolean isTrue;

	public Answer(String answer, boolean isTrue) {
		this.answer = answer;
		this.isTrue = isTrue;
	}

	public String toString() {
		return answer + ", isTrue=" + isTrue;
	}

	public String getAnswer() {
		return answer;
	}

	public boolean isTrue() {
		return isTrue;
	}

	public void setTrue(boolean isTrue) {
		this.isTrue = isTrue;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
