package view;

import java.io.FileNotFoundException;
import MyExceptions.AnswerDontExists;
import MyExceptions.MoreQuestionsThenExist;
import listeners.SystemUIEventListener;
import model.AdminSystem;

public interface manageable {

	void addQuestions();

	void questionUpdate();

	void answerUpdate();

	void deleteQuestion();

	void saveToFile() throws FileNotFoundException;

	void addAnswer();

	void showAllQuestionAndAnswers(String str);

	void deleteAnswer();

	void createAnExamManualy(AdminSystem adminSystem) throws FileNotFoundException, MoreQuestionsThenExist, AnswerDontExists;

	void creatAnExamAutomatic(String autoExam) throws MoreQuestionsThenExist, FileNotFoundException;

	void registerListener(SystemUIEventListener manegmentSystemController);

	//void createAnExamManualy(AdminSystem adminSystem);


	

}
