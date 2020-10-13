package controller;


import java.io.FileNotFoundException;
import MyExceptions.AnswerDontExists;
import MyExceptions.MoreQuestionsThenExist;
import MyExceptions.QuestionDontExists;
import MyExceptions.TheArrIsFull;
import listeners.SystemEventListener;
import listeners.SystemUIEventListener;
import model.AdminSystem;
import model.Answer;
import view.manageable;

public class ManegmentSystemController implements SystemUIEventListener, SystemEventListener {

	private AdminSystem adminSystem;
	private manageable systemView;
	
	public ManegmentSystemController(AdminSystem adminSystem, manageable systemInterface) {
		
		this.adminSystem = adminSystem;
		this.systemView = systemInterface;
		
		adminSystem.registerListener(this);
		systemView.registerListener(this);
	}

	@Override
	public void addQuestionsToModelEvent() {
		systemView.addQuestions();
		
	}

	@Override
	public void questionUpdateModelEvent() {
		systemView.questionUpdate();
		
	}

	@Override
	public void answerUpdateModelEvent() {
		systemView.answerUpdate();
		
	}

	@Override
	public void deleteQuestionModelEvent() {
		systemView.deleteQuestion();
		
	}

	@Override
	public void saveToFileModelEvent() throws FileNotFoundException {
		systemView.saveToFile();
		
	}

	@Override
	public void addAnswerModelEvent() {
		systemView.addAnswer();
		
	}

	@Override
	public void showAllQuestionAndAnswersModelEvent(String str) {
		
		systemView.showAllQuestionAndAnswers(str);
		
	}

	@Override
	public void deleteAnswerModelEvent() {
		systemView.deleteAnswer();
		
	}

	@Override
	public void createAnExamManualyModelEvent(AdminSystem adminSystem) throws FileNotFoundException, MoreQuestionsThenExist, AnswerDontExists {
		systemView.createAnExamManualy(adminSystem);
		
	}

	@Override
	public void creatAnExamAutomaticModelEvent(String autoExam) throws MoreQuestionsThenExist, FileNotFoundException {
		systemView.creatAnExamAutomatic(autoExam);
		
	}

	@Override
	public void questionUpdateToUI(String QWording, int indexQ) throws QuestionDontExists {
		
		adminSystem.questionUpdate(indexQ, QWording);	
	}

	@Override
	public void answerUpdateToUI(int indexQ, int indexA, String answerWording , boolean isTrue) throws AnswerDontExists, QuestionDontExists {
		Answer ans = new Answer(answerWording, isTrue);
		adminSystem.answerUpdate(indexQ, indexA, ans);
		
	}

	@Override
	public void saveToFileToUI() throws FileNotFoundException {
		
		adminSystem.saveToFile(adminSystem.getQuestions());
		
	}

	@Override
	public void addAnswerToUI(String answer , boolean isTrue, int indexQ) throws TheArrIsFull {
		
		Answer a = new Answer(answer, isTrue);
		adminSystem.addAnswer(indexQ, a);
		
		
	}

	@Override
	public void showAllQuestionAndAnswersToUI() {

		 adminSystem.showAllQuestionAndAnswers();
	}

	@Override
	public void deleteAnswerToUI(int indexQ, int indexA) throws QuestionDontExists, AnswerDontExists {
		adminSystem.deleteAnswer(indexQ, indexA);	
	}

	@Override
	public void createAnExamManualyToUI() throws FileNotFoundException, MoreQuestionsThenExist, AnswerDontExists {
		adminSystem.createAnExamManualy();
		
	}

	@Override
	public void creatAnExamAutomaticToUI(int numOfQ) throws MoreQuestionsThenExist, FileNotFoundException {
		adminSystem.creatAnExamAutomatic(numOfQ);
		
	}

	@Override
	public void addQuestionsToUI(String string) {
		adminSystem.addQuestions(string);
		
	}

	@Override
	public void deleteQuestionToUI(int index) throws QuestionDontExists {
		adminSystem.deleteQuestion(index);
		
	}

}
