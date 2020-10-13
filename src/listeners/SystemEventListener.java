package listeners;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import MyExceptions.AnswerDontExists;
import MyExceptions.MoreQuestionsThenExist;
import model.AdminSystem;
import model.Question;

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

	void createAnExamManualyModelEvent(AdminSystem adminSystem) throws FileNotFoundException, MoreQuestionsThenExist, AnswerDontExists;

	void creatAnExamAutomaticModelEvent(String autoExam) throws MoreQuestionsThenExist, FileNotFoundException;

	


}
