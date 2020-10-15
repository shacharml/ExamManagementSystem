package listeners;

import java.io.FileNotFoundException;
import MyExceptions.AnswerDontExists;
import MyExceptions.MoreQuestionsThenExist;


public interface SystemEventListener {
	
	void addQuestionsToModelEvent();

	void questionUpdateModelEvent();

	void answerUpdateModelEvent();

	void deleteQuestionModelEvent();

	void saveToFileModelEvent() throws FileNotFoundException;

	void addAnswerModelEvent();

	//void scaneFromFileModelEvent() throws FileNotFoundException ;

	void showAllQuestionAndAnswersModelEvent(String str);

	void deleteAnswerModelEvent();

	void createAnExamManualyModelEvent() throws FileNotFoundException, MoreQuestionsThenExist, AnswerDontExists;

	void creatAnExamAutomaticModelEvent(String autoExam) throws MoreQuestionsThenExist, FileNotFoundException;
}
