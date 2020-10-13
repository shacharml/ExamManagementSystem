package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
//import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Exam {

	protected ArrayList<Question> questionsEx;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd__HH_mm");

	final String TXT = ".txt";
	final String EXAM = "exam_";
	final String SOULOTION = "soulotion_";
	
	public Exam(int howMany) {
		questionsEx = new ArrayList<Question>(howMany);
	}

	public String getDtf() {
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	public void addNoTrueAnswer(int indexQ, boolean isTrue) {
		questionsEx.get(indexQ).answerArr.add(new Answer("No True Answers ", isTrue));
	}

	public void addQuestion(String question) {
		questionsEx.add(new Question(question));

	}

	public ArrayList<Question> getQuestionsEx() {
		return questionsEx;
	}

	public void setQuestionsEx(ArrayList<Question> questionsEx) {
		this.questionsEx = questionsEx;
	}

	public void saveExam(String st) throws FileNotFoundException {

		String nameFile = EXAM + getDtf() + st + TXT;
		File f = new File(nameFile);
		PrintWriter printW = new PrintWriter(f);

		for (int i = 0; i < questionsEx.size(); i++) {
			printW.println("***question number " + (i + 1) + " ***");

			if (questionsEx.get(i) instanceof OpenQuestion) {
				printW.println(questionsEx.get(i).getQuestion());
				printW.println("\n\n\n\n\n");
			} else {
				printW.println(questionsEx.get(i).getQuestion());
				saveAnswersToExam(questionsEx.get(i).getAnswerArr(), printW);
				printW.println();
			}

		}
		printW.close();
	}

	private void saveAnswersToExam(ArrayList<Answer> answerArr, PrintWriter pr) {
		for (int i = 0; i < answerArr.size(); i++) {
			pr.println((i + 1) + ")" + answerArr.get(i).getAnswer());
		}

	}

	public void saveSoulotion(String st) throws FileNotFoundException {
		String nameFile = SOULOTION + getDtf() + st + TXT;
		File f = new File(nameFile);
		PrintWriter pw = new PrintWriter(f);

		for (int i = 0; i < questionsEx.size(); i++) {
			pw.println("***question number " + (i + 1) + " ***");
			pw.println(questionsEx.get(i).getQuestion() + "\n");
			pw.println("***the answers***");

			for (int j = 0; j < questionsEx.get(i).getAnswerArr().size(); j++) {
				pw.println((j + 1) + ")" + questionsEx.get(i).getAnswerArr().get(j).toString());
			}
			pw.println();
		}
		pw.close();
	}

	@Override
	public String toString() {
          String str = "Exam "+ getDtf()+"\n\\n";
		
		if (questionsEx.size()==0) {
			
			str+= "ther is no question";
			
			return null;
		}
				
		for (int i = 0; i < questionsEx.size(); i++) {
			str += "***** Question number " + i + " *****\n";
			str += questionsEx.get(i).getQuestion() + "\n";
			str += "***** Answers *****\n";

			for (int j = 0; j < questionsEx.get(i).getAnswerArr().size(); j++) {
				str += "(" + j + ") " + questionsEx.get(i).getAnswerArr().get(j).toString() + "\n";
			}
			str += "\n";
		}

		return  str ;
	}

	public void addOpenQuestion(String question, String answer) {
		questionsEx.add(new OpenQuestion(question, answer));
		
	}


}
