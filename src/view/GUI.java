package view;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JOptionPane;
import MyExceptions.AnswerDontExists;
import MyExceptions.MoreQuestionsThenExist;
import MyExceptions.QuestionDontExists;
import MyExceptions.TheArrIsFull;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import listeners.SystemUIEventListener;
import model.Exam;

public class GUI implements manageable {

	private Vector<SystemUIEventListener> allListeners = new Vector<SystemUIEventListener>();
	private Stage qandaStage = new Stage();
	private Label questionAndAnswersLabel = new Label();
	private Button btnPrintAllQandA = new Button("print all question and answers");
	//private Exam manExam;

	public GUI(Stage theStage) throws FileNotFoundException {

		theStage.setTitle("-- Mainu --");
		VBox vbRoot = new VBox();
		vbRoot.setSpacing(10);
		vbRoot.setPadding(new Insets(10));
		vbRoot.setAlignment(Pos.TOP_LEFT);

		Button btnCloseAndSave = new Button("close and save all changes");
		ToggleGroup toggleGroup = new ToggleGroup();
		RadioButton rdoAddAQuestion = new RadioButton("Add new Question ");
		RadioButton rdoAddAnswerToQ = new RadioButton("Add new Answer to an existing Question ");
		RadioButton rdoUpdateQuestion = new RadioButton("Update an existing question wording ");
		RadioButton rdoUpdateAnswer = new RadioButton("Update the wording of an existing answer ");
		RadioButton rdoDeleteAnsFronQ = new RadioButton("Delete an answer from a question ");
		RadioButton rdoDeleteQuestion = new RadioButton("Deleting the question with all its answers ");
		RadioButton rdoCreatManuualyExam = new RadioButton("Creat Manuualy Exam ");
		RadioButton rdoCreatAutomaticExam = new RadioButton("Creat Automatic Exam ");

		rdoAddAQuestion.setToggleGroup(toggleGroup);
		rdoAddAnswerToQ.setToggleGroup(toggleGroup);
		rdoUpdateQuestion.setToggleGroup(toggleGroup);
		rdoUpdateAnswer.setToggleGroup(toggleGroup);
		rdoDeleteAnsFronQ.setToggleGroup(toggleGroup);
		rdoDeleteQuestion.setToggleGroup(toggleGroup);
		rdoCreatManuualyExam.setToggleGroup(toggleGroup);
		rdoCreatAutomaticExam.setToggleGroup(toggleGroup);

		btnPrintAllQandA.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				for (SystemUIEventListener l : allListeners) {
					l.showAllQuestionAndAnswersToUI();
				}
			}

		});

		btnCloseAndSave.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent action) {
				for (SystemUIEventListener l : allListeners) {
					try {
						l.saveToFileToUI();
					} catch (FileNotFoundException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
						e.printStackTrace();
					}
				}

				theStage.close();
			}
		});

		rdoAddAQuestion.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent action) {

				Stage addQStage = new Stage();
				addQStage.setTitle("Add Question");

				// VBox vbAddQ = new VBox();
				GridPane gpAddQ = new GridPane();
				gpAddQ.setPadding(new Insets(10));
				gpAddQ.setHgap(10);
				gpAddQ.setVgap(10);

				Label lblQuestion = new Label("Enter Question Wording: ");
				TextField tfNewQ = new TextField();

				Button btnAddQ = new Button("Add Question");
				btnAddQ.setBackground(
						new Background(new BackgroundFill(Color.BLUEVIOLET, CornerRadii.EMPTY, Insets.EMPTY)));

				btnAddQ.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent action) {

						for (SystemUIEventListener l : allListeners)
							l.addQuestionsToUI(tfNewQ.getText());

						addQStage.close();
					}

				});

				gpAddQ.add(lblQuestion, 1, 0);
				gpAddQ.add(tfNewQ, 2, 0);
				gpAddQ.add(btnAddQ, 2, 1);

				addQStage.setScene(new Scene(gpAddQ));
				addQStage.show();

			}
		});

		rdoAddAnswerToQ.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent action) {
				Stage AddAnsStage = new Stage();
				AddAnsStage.setTitle("Add Answer to existing Question");

				GridPane gpAddAns = new GridPane();
				gpAddAns.setPadding(new Insets(10));
				gpAddAns.setHgap(10);
				gpAddAns.setVgap(10);

				Label lbChoosQ = new Label();
				lbChoosQ.setText("Choose the question index you want to add answer to :");
				TextField tfQIndex = new TextField();

				Label lblAnswer = new Label("Enter Answer Wording: ");
				TextField tfNewA = new TextField();

				ComboBox<Boolean> cmTF = new ComboBox<Boolean>();
				cmTF.setPromptText("choose if the answer is T/F :");
				cmTF.getItems().addAll(true, false);

				Button btnAddAns = new Button("Add answer !");
				btnAddAns.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent action) {
						for (SystemUIEventListener l : allListeners) {
							try {

								l.addAnswerToUI(tfNewA.getText(), cmTF.getValue(),
										Integer.parseInt(tfQIndex.getText()));

							} catch (NumberFormatException e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							} catch (TheArrIsFull e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
						}
						AddAnsStage.close();
					}
				});

				gpAddAns.add(lbChoosQ, 1, 0);
				gpAddAns.add(tfQIndex, 2, 0);
				gpAddAns.add(lblAnswer, 1, 1);
				gpAddAns.add(tfNewA, 2, 1);
				gpAddAns.add(cmTF, 1, 2);
				gpAddAns.add(btnAddAns, 2, 3);
				AddAnsStage.setScene(new Scene(gpAddAns));
				AddAnsStage.show();

			}
		});

		rdoUpdateQuestion.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent action) {
				Stage UpdateQStage = new Stage();
				UpdateQStage.setTitle("Update existing Question wording");

				GridPane gpUpdateQuestion = new GridPane();
				gpUpdateQuestion.setPadding(new Insets(10));
				gpUpdateQuestion.setHgap(10);
				gpUpdateQuestion.setVgap(10);

				Label lbChoosQ = new Label();
				lbChoosQ.setText("Choose the question index you want to update :");
				TextField tfQIndex = new TextField();

				Label lblQUpdate = new Label("Enter Question Update Wording: ");
				TextField tfNewQ = new TextField();

				Button btnUpdateQ = new Button("Update !");
				btnUpdateQ.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent action) {
						for (SystemUIEventListener l : allListeners) {

							try {
								l.questionUpdateToUI(tfNewQ.getText(), Integer.parseInt(tfQIndex.getText()));
							} catch (NumberFormatException | QuestionDontExists e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
						}

						UpdateQStage.close();

					}
				});

				gpUpdateQuestion.add(lbChoosQ, 1, 0);
				gpUpdateQuestion.add(tfQIndex, 2, 0);
				gpUpdateQuestion.add(lblQUpdate, 1, 1);
				gpUpdateQuestion.add(tfNewQ, 2, 1);
				gpUpdateQuestion.add(btnUpdateQ, 2, 2);

				UpdateQStage.setScene(new Scene(gpUpdateQuestion));
				UpdateQStage.show();

			}
		});

		rdoUpdateAnswer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent action) {
				Stage UpdateAStage = new Stage();
				UpdateAStage.setTitle("Update existing Answer wording");

				GridPane gpUpdateAns = new GridPane();
				gpUpdateAns.setPadding(new Insets(10));
				gpUpdateAns.setHgap(10);
				gpUpdateAns.setVgap(10);

				Label lbChoosQ = new Label();
				lbChoosQ.setText("Choose the question index you want to update her answer :");
				TextField tfQIndex = new TextField();

				Label lbChoosA = new Label();
				lbChoosA.setText("Choose the answer index you want to update:");
				TextField tfAIndex = new TextField();

				Label lblAUpdate = new Label("Enter Answer Update Wording: ");
				TextField tfQuestion = new TextField();

				ComboBox<Boolean> cmTF = new ComboBox<Boolean>();
				cmTF.setPromptText("choose if the answer is T/F :");
				cmTF.getItems().addAll(true, false);

				Button btnUpdateA = new Button("Update Answer!");
				btnUpdateA.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						for (SystemUIEventListener l : allListeners)
							try {
								l.answerUpdateToUI(Integer.parseInt(tfQIndex.getText()),
										Integer.parseInt(tfAIndex.getText()), tfQuestion.getText(), cmTF.getValue());
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
						UpdateAStage.close();
					}
				});

				gpUpdateAns.add(lbChoosQ, 1, 0);
				gpUpdateAns.add(tfQIndex, 2, 0);
				gpUpdateAns.add(lbChoosA, 1, 1);
				gpUpdateAns.add(tfAIndex, 2, 1);
				gpUpdateAns.add(lblAUpdate, 1, 2);
				gpUpdateAns.add(tfQuestion, 2, 2);
				gpUpdateAns.add(cmTF, 1, 3);
				gpUpdateAns.add(btnUpdateA, 2, 4);

				UpdateAStage.setScene(new Scene(gpUpdateAns));
				UpdateAStage.show();
			}
		});

		rdoDeleteAnsFronQ.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent action) {
				Stage deleteAStage = new Stage();
				deleteAStage.setTitle("Delete Answer ");

				GridPane gpDeleteAns = new GridPane();
				gpDeleteAns.setPadding(new Insets(10));
				gpDeleteAns.setHgap(10);
				gpDeleteAns.setVgap(10);

				Label lbChoosQ = new Label();
				lbChoosQ.setText("Choose the question index you want to delete her answer :");
				TextField tfQIndex = new TextField();

				Label lbChoosA = new Label();
				lbChoosA.setText("Choose the answer index you want to delete:");
				TextField tfAIndex = new TextField();

				Button btndeleteA = new Button("delete Answer!");
				btndeleteA.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent action) {
						for (SystemUIEventListener l : allListeners)
							try {
								l.deleteAnswerToUI(Integer.parseInt(tfQIndex.getText()),
										Integer.parseInt(tfAIndex.getText()));
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							}

						deleteAStage.close();
					}
				});

				gpDeleteAns.add(lbChoosQ, 1, 0);
				gpDeleteAns.add(tfQIndex, 2, 0);
				gpDeleteAns.add(lbChoosA, 1, 1);
				gpDeleteAns.add(tfAIndex, 2, 1);
				gpDeleteAns.add(btndeleteA, 2, 2);
				deleteAStage.setScene(new Scene(gpDeleteAns));
				deleteAStage.show();

			}
		});

		rdoDeleteQuestion.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent action) {
				Stage deleteQStage = new Stage();
				deleteQStage.setTitle("Update existing Answer wording");

				GridPane gpDleteQ = new GridPane();
				gpDleteQ.setPadding(new Insets(10));
				gpDleteQ.setHgap(10);
				gpDleteQ.setVgap(10);

				Label lbChoosQ = new Label();
				lbChoosQ.setText("Choose the question index you want to Delete :");
				TextField tfQIndex = new TextField();

				Button btnDeleteQ = new Button("delete Answer!");
				btnDeleteQ.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent action) {
						for (SystemUIEventListener l : allListeners)
							try {
								l.deleteQuestionToUI(Integer.parseInt(tfQIndex.getText()));
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							}
						deleteQStage.close();
					}
				});

				gpDleteQ.add(lbChoosQ, 1, 0);
				gpDleteQ.add(tfQIndex, 2, 0);
				gpDleteQ.add(btnDeleteQ, 2, 1);

				deleteQStage.setScene(new Scene(gpDleteQ));
				deleteQStage.show();

			}
		});

		rdoCreatManuualyExam.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				Stage creatExamManStage = new Stage();
				creatExamManStage.setTitle("Creat exam manually : ");

				GridPane gpCreatExam = new GridPane();
				gpCreatExam.setPadding(new Insets(10));
				gpCreatExam.setHgap(10);
				gpCreatExam.setVgap(10);

				Label lbIndexesQ = new Label();
				lbIndexesQ.setText("Enter the questions indexes you want in the exam (1,5,7,) :");
				TextField tfIndexesQ = new TextField();
				tfIndexesQ.setMaxWidth(100);

				Button btnCreate = new Button("create");
				btnCreate.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {

						ArrayList<String> QStringNum = new ArrayList<String>(
								Arrays.asList(tfIndexesQ.getText().split(",")));

						ArrayList<Integer> Qnum = new ArrayList<Integer>();

						for (int i = 0; i < QStringNum.size(); i++) {
							Qnum.add(Integer.parseInt(QStringNum.get(i)));

						}

						for (SystemUIEventListener l : allListeners)
							try {
								creatExamManStage.close();
								l.createAnExamManualyToUI(Qnum);
								
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							}

					}
				});
				;

				gpCreatExam.add(lbIndexesQ, 1, 0);
				gpCreatExam.add(tfIndexesQ, 2, 0);
				gpCreatExam.add(btnCreate, 2, 1);

				creatExamManStage.setScene(new Scene(gpCreatExam));
				creatExamManStage.show();

			}
		});

		rdoCreatAutomaticExam.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent action) {
				Stage createAutoExamStage = new Stage();
				createAutoExamStage.setTitle("Creat Automatic Exam");

				GridPane gpAutoExam = new GridPane();
				gpAutoExam.setPadding(new Insets(10));
				gpAutoExam.setHgap(10);
				gpAutoExam.setVgap(10);

				Label lbHowManyQ = new Label();
				lbHowManyQ.setText("Choose how many question you want in the exam :");
				TextField tfHowMQ = new TextField();

				Button btnCreat = new Button("Creat Exam!");
				btnCreat.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent action) {
						for (SystemUIEventListener l : allListeners)
							try {
								l.creatAnExamAutomaticToUI(Integer.parseInt(tfHowMQ.getText()));
							} catch (Exception e) {
								JOptionPane.showMessageDialog(null, e.getMessage());
							}

						createAutoExamStage.close();

					}
				});

				gpAutoExam.add(lbHowManyQ, 1, 0);
				gpAutoExam.add(tfHowMQ, 2, 0);
				gpAutoExam.add(btnCreat, 2, 1);

				createAutoExamStage.setScene(new Scene(gpAutoExam));
				createAutoExamStage.show();
			}
		});

		vbRoot.getChildren().addAll(btnPrintAllQandA, rdoAddAQuestion, rdoAddAnswerToQ, rdoUpdateQuestion,
				rdoUpdateAnswer, rdoDeleteAnsFronQ, rdoDeleteQuestion, rdoCreatManuualyExam, rdoCreatAutomaticExam,
				btnCloseAndSave);

		
		vbRoot.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, null, null)));
		theStage.setScene(new Scene(vbRoot, 400, 350));
		theStage.show();
	}

	@Override
	public void addQuestions() {
		// JOptionPane.showMessageDialog(null, "the question added sucssesfully");
		qandaStage.close();
		btnPrintAllQandA.fire();
	}

	@Override
	public void questionUpdate() {
		qandaStage.close();
		btnPrintAllQandA.fire();

	}

	@Override
	public void answerUpdate() {
		qandaStage.close();
		btnPrintAllQandA.fire();

	}

	@Override
	public void deleteQuestion() {
		JOptionPane.showMessageDialog(null, "the question deleted sucssesfully");
		qandaStage.close();
		btnPrintAllQandA.fire();
	}

	@Override
	public void saveToFile() throws FileNotFoundException {
		JOptionPane.showMessageDialog(null, "all Save into a new file");
		qandaStage.close();
		btnPrintAllQandA.fire();

	}

	@Override
	public void addAnswer() {
		qandaStage.close();
		btnPrintAllQandA.fire();

	}

	@Override
	public void showAllQuestionAndAnswers(String QandA) {

		questionAndAnswersLabel.setText(QandA);
		// open the "File" allQandA in a new tab

		qandaStage.setTitle("All Question And answers");

		VBox vBox = new VBox();
		vBox.setPadding(new Insets(10));
		ScrollPane sp = new ScrollPane();
		sp.setContent(vBox);

		vBox.getChildren().add(questionAndAnswersLabel);
		qandaStage.setScene(new Scene(sp, 500, 550));
		qandaStage.show();

	}

	@Override
	public void deleteAnswer() {
		qandaStage.close();
		btnPrintAllQandA.fire();

	}

	@Override
	public void createAnExamManualy() throws FileNotFoundException, MoreQuestionsThenExist, AnswerDontExists {

		JOptionPane.showMessageDialog(null, "The exam were created");
	}

	/*
	 * Stage creatExamManStage = new Stage();
	 * creatExamManStage.setTitle("Creat exam manually : ");
	 * 
	 * GridPane gpCreatExam = new GridPane(); gpCreatExam.setPadding(new
	 * Insets(10)); gpCreatExam.setHgap(10); gpCreatExam.setVgap(10);
	 * 
	 * Label lbHowManyQ = new Label();
	 * lbHowManyQ.setText("Choose how many question in the exam :"); TextField
	 * tfHowManyQ = new TextField();
	 * 
	 * 
	 * Label lbHowManyOpenQ = new Label(); lbHowManyOpenQ.
	 * setText("Choose how many from them open question in the exam :"); TextField
	 * tfHowManyOpenQ = new TextField();
	 * 
	 * 
	 * 
	 * Button btnChoose = new Button("Choose !");
	 * 
	 * 
	 * gpCreatExam.add(lbHowManyQ, 1, 0); gpCreatExam.add(tfHowManyQ, 2, 0);
	 * gpCreatExam.add(lbHowManyOpenQ, 1, 1); gpCreatExam.add(tfHowManyOpenQ, 2, 1);
	 * //gpCreatExam.add(lbChoosQ, 1, 2); //gpCreatExam.add(tfQIndex, 2, 2);
	 * gpCreatExam.add(btnChoose, 2, 3);
	 * 
	 * creatExamManStage.setScene(new Scene(gpCreatExam)); creatExamManStage.show();
	 * 
	 * 
	 * 
	 * btnChoose.setOnAction(new EventHandler<ActionEvent>() {
	 * 
	 * @Override public void handle(ActionEvent arg0) {
	 * 
	 * 
	 * manExam= new ManualExam(Integer.parseInt(tfHowManyQ.getText()));
	 * 
	 * 
	 * Label lbChoosQ = new Label();
	 * lbChoosQ.setText("Choose the question index you want to Add to the exam :");
	 * TextField tfQIndex = new TextField();
	 * 
	 * Stage chooseAnsStage = new Stage();
	 * chooseAnsStage.setTitle("Choose answers to this Question : ");
	 * 
	 * GridPane gpAns = new GridPane();
	 * 
	 * Label lbChoosA = new Label();
	 * lbChoosA.setText("Choose the Answer index you want to Add to this Question :"
	 * ); TextField tfAIndex = new TextField();
	 * 
	 * ComboBox<Boolean> cmTF = new ComboBox<Boolean>();
	 * cmTF.setPromptText("you want to choose another answers? T/F :");
	 * cmTF.getItems().addAll(true, false);
	 * 
	 * Button btnSend = new Button("send !");
	 * 
	 * gpCreatExam.add(lbChoosQ, 1, 0); gpCreatExam.add(tfQIndex, 2, 0);
	 * gpAns.add(lbChoosA, 1, 1); gpAns.add(tfAIndex, 2, 1); gpAns.add(cmTF, 1, 3);
	 * gpAns.add(btnSend, 2, 4);
	 * 
	 * chooseAnsStage.setScene(new Scene(gpAns)); chooseAnsStage.show();
	 * creatExamManStage.close();
	 * 
	 * btnSend.setOnAction(new EventHandler<ActionEvent>() {
	 * 
	 * @Override public void handle(ActionEvent arg0) {
	 * 
	 * 
	 * if (!cmTF.getValue()) { chooseAnsStage.close(); } else {
	 * chooseAnsStage.close(); btnChoose.fire();
	 * 
	 * } }
	 * 
	 * });
	 * 
	 * } });
	 * 
	 * }
	 */

	@Override
	public void creatAnExamAutomatic(String autoExam) throws MoreQuestionsThenExist, FileNotFoundException {
		Stage secondStage = new Stage();
		secondStage.setTitle("Automatic Exam");

		VBox vBox = new VBox();
		vBox.setPadding(new Insets(10));
		ScrollPane sp = new ScrollPane();
		sp.setContent(vBox);

		Label lbExam = new Label();
		lbExam.setText(autoExam);

		vBox.getChildren().add(lbExam);
		secondStage.setScene(new Scene(sp, 500, 550));
		secondStage.show();

	}

	@Override
	public void registerListener(SystemUIEventListener manegmentSystemController) {
		this.allListeners.add(manegmentSystemController);

	}
}
