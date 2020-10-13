package view;
/*
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import MyExceptions.AnswerDontExists;
import MyExceptions.MoreQuestionsThenExist;
import MyExceptions.QuestionDontExists;
import MyExceptions.TheArrIsFull;
import listeners.SystemUIEventListener;
import model.Answer;
import model.AutoExam;
import model.AdminSystem;
import model.ManualExam;
import model.OpenQuestion;
import model.Question;

public class CounsolUI implements manageable {

	Scanner scan = new Scanner(System.in);
	AdminSystem manegSys = new AdminSystem();
	int indexQ, indexA;
	ArrayList<SystemUIEventListener> listeners;
	
	final  String NO_ANSWER_IS_TRUE = "no answer is true";
	final String TWO_TRUE_ANS = "ther is two true answers";

	@Override
	public void addQuestions() {
		System.out.println("Press 1 for add regular question \nPress 2 for add Open question");
		int chose = scan.nextInt();
		switch (chose) {
		case 1:
			scan.nextLine();
			System.out.println("Enter a new question: ");
			Question q =new Question(scan.nextLine());
			manegSys.addQuestions(q.getQuestion());
			break;

		case 2:
			scan.nextLine();
			String question, answer;
			System.out.println("Enter a new question: ");
			question = scan.nextLine();
			System.out.println("Enter the answer: ");
			answer = scan.nextLine();
			manegSys.addOpenQuestion(new OpenQuestion(question, answer));
			break;
		}

	}

	@Override
	public void questionUpdate() {
		System.out.println(manegSys.showAllQuestionAndAnswers());
		System.out.println("Enter the index of question you want to update :");
		indexQ = scan.nextInt();
		System.out.println("Enter the words to update:");
		scan.nextLine();
		try {
			manegSys.questionUpdate(indexQ, scan.nextLine());
		} catch (QuestionDontExists e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void answerUpdate() {
		System.out.println(manegSys.showAllQuestionAndAnswers());
		System.out.println("Enter the index of question :");
		indexQ = scan.nextInt();
		System.out.println("Enter the index of answer to update :");
		indexA = scan.nextInt();
		scan.nextLine();
		System.out.println("Enter the words to update and then true/fales : ");
		String worsAns = scan.nextLine();
		boolean bol = scan.nextBoolean();
		try {
			manegSys.answerUpdate(indexQ, indexA, new Answer(worsAns, bol));
		} catch (QuestionDontExists e) {
			System.out.println(e.getMessage());
		} catch (AnswerDontExists e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void deleteQuestion() {
		System.out.println(manegSys.showAllQuestionAndAnswers());

		System.out.println("Enter the index of question you want to delete :");
		indexQ = scan.nextInt();

		try {
			manegSys.deleteQuestion(indexQ);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void deleteAnswer() {
		System.out.println(manegSys.showAllQuestionAndAnswers());
		System.out.println("Enter the index of question you want to update :");
		indexQ = scan.nextInt();
		System.out.println("Enter the index of answer to update :");
		indexA = scan.nextInt();

		try {
			manegSys.deleteAnswer(indexQ, indexA);
		} catch (AnswerDontExists e) {
			System.out.println(e.getMessage());
		} catch (QuestionDontExists e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void addAnswer() {
		System.out.println(manegSys.showAllQuestionAndAnswers());

		System.out.println("Enter the index of question you want to add answer :");
		indexQ = scan.nextInt();

		if (manegSys.getQuestions().get(indexQ) instanceof OpenQuestion) {
			System.out.println("Cannot add more answer to Open Quesion");
			return;
		} else {

			scan.nextLine();
			System.out.println("Enter the answer words: ");
			String words = scan.nextLine();
			System.out.println("and true/fales: ");
			boolean bol = scan.nextBoolean();

			try {
				manegSys.addAnswer(indexQ, new Answer(words, bol));
			} catch (TheArrIsFull e) {

				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public void saveToFile() throws FileNotFoundException {
		manegSys.saveToFile(manegSys.getQuestions());
		System.out.println(" GOODBYE :) ");
	}

	@Override
	public void scaneFromFile(File f, File fOpen) throws FileNotFoundException {

		manegSys.scaneFromFile(f, fOpen);
		//System.out.println("All Scans");
	}

	@Override
	public void showAllQuestionAndAnswers() {
		return manegSys.showAllQuestionAndAnswers();
	}

	@Override
	public void createAnExamManualy() throws MoreQuestionsThenExist, FileNotFoundException, AnswerDontExists {

		System.out.println("How much Question you want to choose? ");
		int numOfQues = scan.nextInt();

		if (numOfQues > manegSys.getQuestions().size() || numOfQues < 0) {
			throw new MoreQuestionsThenExist();
		}

		System.out.println("How much from them Open Question you want to choose? ");
		int numOfOpenQues = scan.nextInt();

		if (numOfOpenQues > manegSys.howMuchOpenQuestions(manegSys.getQuestions())) {
			throw new MoreQuestionsThenExist();
		}

		ManualExam manualEx = new ManualExam(numOfQues);
		System.out.println(showAllQuestionAndAnswers());

		for (int i = 0; i < numOfQues - numOfOpenQues; i++) {
			System.out.println("Enter the index of the regular question you want: ");
			int indexQ = scan.nextInt();

			if (manegSys.getQuestions().get(indexQ) instanceof OpenQuestion) {
				System.out.println("This is an Open Question");
				i--;
				continue;

			}

			manualEx.addQuestion(manegSys.getQuestions().get(indexQ).getQuestion());

			int trueCounter = 0, uWantToCountinu = 0;

			while (uWantToCountinu != (-1)) {

				System.out.println("Wich answer you want yo this question ? ");
				int indexA = scan.nextInt();

				if (indexA > manegSys.getQuestions().get(indexQ).getAnswerArr().size() || indexA < 0) {
					throw new AnswerDontExists();
				}

				manualEx.addAnsToQest(i, manegSys.getQuestions().get(indexQ).getAnswerArr().get(indexA));

				if (manegSys.getQuestions().get(indexQ).getAnswerArr().get(indexA).isTrue()) {
					trueCounter++;
				}
				System.out.println("You want to add more ans? -1 :no || 0 :yes");
				uWantToCountinu = scan.nextInt();
			}

			if (trueCounter == 0) {
				manualEx.addAnsToQest(i, new Answer(NO_ANSWER_IS_TRUE, true));
				manualEx.addAnsToQest(i, new Answer(TWO_TRUE_ANS, false));
			} else if (trueCounter >= 2) {
				manualEx.addAnsToQest(i, new Answer(NO_ANSWER_IS_TRUE, false));
				manualEx.addAnsToQest(i, new Answer(TWO_TRUE_ANS, true));
			} else {
				manualEx.addAnsToQest(i, new Answer(NO_ANSWER_IS_TRUE, false));
				manualEx.addAnsToQest(i, new Answer(TWO_TRUE_ANS, false));
			}

		}

		for (int i = 0; i < numOfOpenQues; i++) {
			System.out.println("add the index of open question you want to add");
			indexQ = scan.nextInt();
			if (manegSys.getQuestions().get(indexQ) instanceof OpenQuestion) {
				manualEx.addOpenQuestion(manegSys.getQuestions().get(indexQ).getQuestion(),
						manegSys.getQuestions().get(indexQ).getAnswerArr().get(0).getAnswer());
			} else {
				i--;
				continue;
			}

		}

		manualEx.saveExam("_M");
		manualEx.saveSoulotion("_M");
	}

	public void creatAnExamAutomatic() throws MoreQuestionsThenExist, FileNotFoundException {
		System.out.println("Enter how much questions you want? ");
		int numOfQuestions = scan.nextInt();

		if (numOfQuestions > manegSys.getQuestions().size() || numOfQuestions < 0) {
			throw new MoreQuestionsThenExist();
		}

		AutoExam autoExam = new AutoExam(numOfQuestions);

		autoExam.addRandomQuestions(numOfQuestions, manegSys);

		autoExam.saveExam("_A");
		autoExam.saveSoulotion("_A");

	}

	
}*/
