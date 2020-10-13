package MyExceptions;

public class MoreQuestionsThenExist extends Exception{
	
	public MoreQuestionsThenExist() {
		super("The number of question you want is not exist now.\n");
	}

}
