package MyExceptions;

public class AnswerDontExists extends Exception {

	public AnswerDontExists() {
		super("The index you insert is invalid - answer didnt exist\n");
	}
}
