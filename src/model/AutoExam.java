package model;

import java.util.ArrayList;
import java.util.Random;

public class AutoExam extends Exam {

	Random rand = new Random();
	final int NUM_OF_ANSWERS = 4;

	public AutoExam(int howMany) {
		super(howMany);

	}

	public ArrayList<Integer> arrCreatForRandQuestions(ArrayList<Question> allQuestionss) {
		ArrayList<Integer> newArrRand = new ArrayList<Integer>(allQuestionss.size());
		for (int i = 0; i < allQuestionss.size(); i++) {
			newArrRand.add(i);
		}
		return newArrRand;
	}

	public ArrayList<Integer> arrCreatForRandAnswers(ArrayList<Answer> allAnswers) {
		ArrayList<Integer> newArrRand = new ArrayList<Integer>(allAnswers.size());
		for (int i = 0; i < allAnswers.size(); i++) {
			newArrRand.add(i);
		}
		return newArrRand;
	}

	// ** need to do that the random number not choose twitch the same number **//
	// i create an array who do the ransoms for me :)

	public void addRandomQuestions(int numOfQ, AdminSystem manSys) {

		ArrayList<Integer> randArr = arrCreatForRandQuestions(manSys.getQuestions());
		// {0,1,2,3,4,....}

		for (int i = 0; i < numOfQ; i++) {
			int randQ = rand.nextInt(randArr.size());// choos random question

			if (manSys.getQuestions().get(randArr.get(randQ)) instanceof OpenQuestion) {
				questionsEx.add(new OpenQuestion(manSys.getQuestions().get(randArr.get(randQ)).question,
						manSys.getQuestions().get(randArr.get(randQ)).answerArr.get(0).getAnswer()));
			} else
				questionsEx.add(new Question(manSys.getQuestions().get(randArr.get(randQ)).question));// insert
																										// ques(rand)->
																										// inTo quesEx

			if (!(manSys.getQuestions().get(randArr.get(randQ)) instanceof OpenQuestion)) {

				addRandomAns(manSys.getQuestions().get(randArr.get(randQ)).answerArr, i);
			}

			randArr.remove(randQ);
		}
	}

	// if we have a true ans in the ansArr of this question
	// and only left in the other answers only a true answers
	// dont insert any answer any more--> cant have 4 answers

	public boolean therIsOnlyTrueLeft(ArrayList<Answer> allAns, ArrayList<Integer> randAns) {

		int CounterTrue = 0;
		for (int i = 0; i < randAns.size(); i++) {
			if (allAns.get(randAns.get(i)).isTrue()) {
				CounterTrue++;
			}

		}

		if (CounterTrue == randAns.size() && CounterTrue == allAns.size()) {
			return true;
		}

		return false;

	}

	public void addRandomAns(ArrayList<Answer> allAns, int indexQ) {

		int counterIsTrue = 0;

		ArrayList<Integer> randAnswerArr = arrCreatForRandAnswers(allAns);
		// open Question

		for (int i = 0; i < NUM_OF_ANSWERS; i++) {

			int randA = rand.nextInt(randAnswerArr.size());

			if (allAns.get(randAnswerArr.get(randA)).isTrue()) {
				counterIsTrue++;

				if (counterIsTrue == 1) {
					questionsEx.get(indexQ).answerArr.add(allAns.get(randAnswerArr.get(randA)));

					// add the true answer
				} else if (counterIsTrue > 1) {

					if (therIsOnlyTrueLeft(allAns, randAnswerArr)) {
						break;
					}

					i++;
					continue;
				}
			} else {
				questionsEx.get(indexQ).answerArr.add(allAns.get(randAnswerArr.get(randA)));
			}

			randAnswerArr.remove(randA);
		}

		if (counterIsTrue == 0) {
			addNoTrueAnswer(indexQ, true);
		} else {
			addNoTrueAnswer(indexQ, false);
		}

	}

	
}
