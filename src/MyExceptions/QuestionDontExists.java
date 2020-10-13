package MyExceptions;

public class QuestionDontExists extends Exception{

	 public QuestionDontExists() {
		super("The index you insert is invalid - question didnt exist\n");
	}
}
