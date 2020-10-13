package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

import MyExceptions.AnswerDontExists;
import MyExceptions.MoreQuestionsThenExist;
import MyExceptions.QuestionDontExists;
import MyExceptions.TheArrIsFull;
import listeners.SystemEventListener;



public class AdminSystem {// implements manageable {

	final String OPEN_QUESTION = "openQuestions.txt";
	final String QUESTION_AND_ANSWERS = "QuestionAndAnswer.txt";
	final int SIZE_OF_ANS_ARRAY = 10;
	
	private ArrayList<Question> questions;
	
	private Vector<SystemEventListener> listeners;
	

	public AdminSystem() throws FileNotFoundException {
		questions = new ArrayList<Question>();
		listeners= new Vector<SystemEventListener>();
		
		File QuestionAndAnswer = new File("QuestionAndAnswer.txt");
		File openQuestions = new File("openQuestions.txt");
		scaneFromFile(QuestionAndAnswer, openQuestions);
	}

	public String toString() {
		return questions.toString();
	}

	public void addAnswer(int indexQ, Answer a) throws TheArrIsFull {

		if (questions.get(indexQ).getAnswerArr().size() == SIZE_OF_ANS_ARRAY) {
			throw new TheArrIsFull();
		}

		questions.get(indexQ).getAnswerArr().add(a);

	}

	public void addQuestions(String questionText) {
		Question q = new Question(questionText);
		questions.add(q);
		fireAddQuestionEvent(q);
	}

	private void fireAddQuestionEvent(Question q) {
		for (SystemEventListener l : listeners) {
			l.addQuestionsToModelEvent();
		}
		
	}

	public void addOpenQuestion(OpenQuestion openQ) {
		questions.add(openQ);

	}

	public void questionUpdate(int i, String str) throws QuestionDontExists {
		if (i < 0 || i > questions.size()) {
			throw new QuestionDontExists();
		}
		questions.get(i).setStringQuestion(str);
		
		fireQuestionUpdateEvent();

	}

	private void fireQuestionUpdateEvent() {
		for (SystemEventListener l : listeners) {
			l.questionUpdateModelEvent();
		}
		
	}

	public void answerUpdate(int indexQ, int indexA, Answer ans) throws AnswerDontExists, QuestionDontExists {
		if (indexQ < 0 || indexQ > questions.size()) {
			throw new QuestionDontExists();
		}

		if (indexA > questions.get(indexQ).getAnswerArr().size() || indexA < 0) {
			throw new AnswerDontExists();
		}

		questions.get(indexQ).getAnswerArr().set(indexA, ans);
		
		fireAnswerUpdate();
		}

	private void fireAnswerUpdate() {
		for (SystemEventListener l : listeners) {
			l.answerUpdateModelEvent();
		}
		
	}

	public void deleteAnswer(int indexQ, int indexA) throws QuestionDontExists, AnswerDontExists {

		if (indexQ < 0 || indexQ > questions.size()) {
			throw new QuestionDontExists();
		}

		if (indexA > questions.get(indexQ).getAnswerArr().size() || indexA < 0) {
			throw new AnswerDontExists();
		}
		questions.get(indexQ).getAnswerArr().remove(indexA);
		//return;
        fireDeleteAnswerEvent();
	}

	private void fireDeleteAnswerEvent() {
		
		for (SystemEventListener l : listeners) {
			l.deleteAnswerModelEvent();
		}
	}

	public void deleteQuestion(int indexQ) throws QuestionDontExists {

		if (indexQ < 0 || indexQ > questions.size()) {
			throw new QuestionDontExists();
		} else
			questions.remove(indexQ);
		
		fireDeleteQuestionEvent();
	}

	private void fireDeleteQuestionEvent() {
		for (SystemEventListener l : listeners) {
			l.deleteQuestionModelEvent();
		}
		
	}

	public int howMuchOpenQuestions(ArrayList<Question> questions) {
		int counterOpenQ = 0;

		for (int i = 0; i < questions.size(); i++) {
			if (questions.get(i) instanceof OpenQuestion) {
				counterOpenQ++;
			}
		}

		return counterOpenQ;
	}

	public void saveToFile(ArrayList<Question> questions) throws FileNotFoundException {
		
		File f = new File(QUESTION_AND_ANSWERS);
		PrintWriter pw1 = new PrintWriter(f);

		File fOpen = new File(OPEN_QUESTION);
		PrintWriter pw2 = new PrintWriter(fOpen);

		int countOpenQ = howMuchOpenQuestions(questions);
		int counRegularQ = questions.size() - countOpenQ;
		// how much question are
		pw1.println(counRegularQ);
		pw2.println(countOpenQ);

		for (int i = 0; i < questions.size(); i++) {

			if (questions.get(i) instanceof OpenQuestion) {

				pw2.println(questions.get(i).getQuestion());
				pw2.println(questions.get(i).getAnswerArr().get(0).getAnswer());

			}

			else {
				pw1.println(questions.get(i).getQuestion());
				// how many answer are
				pw1.println(questions.get(i).getAnswerArr().size());
				// enter the all answer of this question
				saveAns(questions.get(i).getAnswerArr(), pw1);
			}

		}

		pw1.close();
		pw2.close();
		
		fireSaveToFileEvent();
	}

	private void fireSaveToFileEvent() throws FileNotFoundException {
		for (SystemEventListener l : listeners) {
			l.saveToFileModelEvent();
		}
		
	}

	public void saveAns(ArrayList<Answer> a, PrintWriter p) {
		for (int i = 0; i < a.size(); i++) {
			p.println(a.get(i).getAnswer());
			p.println(a.get(i).isTrue());
		}

	}

	public void scaneFromFile(File QuestionAndAnswer, File openQuestions) throws FileNotFoundException {
		Scanner s1 = new Scanner(QuestionAndAnswer);
		Scanner s2 = new Scanner(openQuestions);

		while (s1.hasNext()) {
			int numOfQues = s1.nextInt();

			for (int i = 0; i < numOfQues; i++) {
				s1.nextLine();
				String q = s1.nextLine();
				Question question = new Question(q);
				addQuestions(question.getQuestion());

				// how much answer are
				int numOfAnswers = s1.nextInt();

				for (int j = 0; j < numOfAnswers; j++) {
					s1.nextLine();// scan the ans + scan ifTrue
					String words = s1.nextLine();
					boolean bol = s1.nextBoolean();
					questions.get(i).answerArr.add(new Answer(words, bol));
				}
			}
		}
		while (s2.hasNext()) {
			int numOfOpenQues = s2.nextInt();
			s2.nextLine();

			for (int i = 0; i < numOfOpenQues; i++) {

				String q = s2.nextLine();
				String wordsAnswer = s2.nextLine();
				OpenQuestion question = new OpenQuestion(q, wordsAnswer);
				addOpenQuestion(question);

			}
		}
		s1.close();
		s2.close();
		
		
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void showAllQuestionAndAnswers() {
		String str = "";
		
		if (questions.size()==0) {
			
			str+= "ther is no question";
			fireShowAllQuestionAndAnswersEvent(str);
			return;
		}
				
		for (int i = 0; i < questions.size(); i++) {
			str += "***** Question number " + i + " *****\n";
			str += questions.get(i).getQuestion() + "\n";
			str += "***** Answers *****\n";

			for (int j = 0; j < questions.get(i).getAnswerArr().size(); j++) {
				str += "(" + j + ") " + questions.get(i).getAnswerArr().get(j).toString() + "\n";
			}
			str += "\n";
		}
		fireShowAllQuestionAndAnswersEvent(str);
		
	}

	private void fireShowAllQuestionAndAnswersEvent(String str) {
		
		for (SystemEventListener l : listeners) {
			l.showAllQuestionAndAnswersModelEvent(str);
		}
		
	}

	public void creatAnExamAutomatic(int numOfQuestions) throws MoreQuestionsThenExist, FileNotFoundException {
	
		if (numOfQuestions > this.getQuestions().size() || numOfQuestions < 0) {
			throw new MoreQuestionsThenExist();
		}

		AutoExam autoExam = new AutoExam(numOfQuestions);

		autoExam.addRandomQuestions(numOfQuestions, this);

		autoExam.saveExam("_A");
		autoExam.saveSoulotion("_A");
		
		fireCreatAutoExamEvent(autoExam.toString());

	}
	
	private void fireCreatAutoExamEvent(String autoExam) throws FileNotFoundException, MoreQuestionsThenExist {
		for (SystemEventListener l : listeners) {
			l.creatAnExamAutomaticModelEvent(autoExam);
		}
		
	}

	public void createAnExamManualy() throws MoreQuestionsThenExist, FileNotFoundException, AnswerDontExists {
		
		
		
		
		fireCreateExamManuallyEvent(this);
		
		
		
		
		/*if (numOfQues > this.getQuestions().size() || numOfQues < 0) {
			throw new MoreQuestionsThenExist();
		}
		
		if (numOfOpenQues > this.howMuchOpenQuestions(this.getQuestions())) {
			throw new MoreQuestionsThenExist();
		}
		
		ManualExam manualEx = new ManualExam(numOfQues);
		// figer out how to do this on gui+on programin
		//everithing else works
		*/
		
	}
	

	
	
	
	private void fireCreateExamManuallyEvent(AdminSystem as) throws FileNotFoundException, MoreQuestionsThenExist, AnswerDontExists {
		
		for (SystemEventListener l : listeners) {
			l.createAnExamManualyModelEvent(as);;
		}
	}

	public void registerListener(SystemEventListener listenerControoler) {
		
		listeners.add(listenerControoler);
	}

	
	
	

	
}
