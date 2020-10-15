package listeners;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import MyExceptions.AnswerDontExists;
import MyExceptions.MoreQuestionsThenExist;
import MyExceptions.QuestionDontExists;
import MyExceptions.TheArrIsFull;

public interface SystemUIEventListener {
	
	void addQuestionsToUI(String string);

	void questionUpdateToUI(String QusWording, int indexQ) throws QuestionDontExists;

	void answerUpdateToUI(int indexQ, int indexA, String answerWording , boolean isTrue) throws AnswerDontExists, QuestionDontExists;

	void deleteQuestionToUI(int index) throws QuestionDontExists;

	void saveToFileToUI() throws FileNotFoundException;

	void addAnswerToUI(String answer , boolean isTrue, int indexQ) throws TheArrIsFull;

	//void scaneFromFileToUI() throws FileNotFoundException  ;

	void showAllQuestionAndAnswersToUI();

	void deleteAnswerToUI(int indexQ, int indexA) throws QuestionDontExists, AnswerDontExists;

	void createAnExamManualyToUI(ArrayList<Integer> qnum) throws FileNotFoundException, MoreQuestionsThenExist, AnswerDontExists;

	void creatAnExamAutomaticToUI(int numOfQ) throws MoreQuestionsThenExist, FileNotFoundException;
}
