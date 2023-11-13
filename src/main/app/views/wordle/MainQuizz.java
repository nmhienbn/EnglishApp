package views.wordle;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import views.animations.GameAnimations;
import views.controllers.Quizz_ctrl;

import static views.controllers.Quizz_ctrl.QUESTIONS;

public class MainQuizz {
    protected static MainQuizz instance = null;
    public Quizz_ctrl quizz_ctrl = null;


    protected MainQuizz() {
        // Exists only to defeat instantiation.
    }

    /**
     * Gets the instance of the mainWordle class
     *
     * @return the instance of the mainWordle class
     */
    public static MainQuizz getInstance() {
        if (instance == null) {
            instance = new MainQuizz();
        }
        return instance;
    }

    private String question;
    private String[] choices;
    private String answer;
    private int now_level = 0;

    public void createGameTitle(HBox titleHBox) {
        Label label = new Label("QUIZZ");
        label.getStyleClass().add("default-tile");
        titleHBox.getChildren().add(label);
    }

    /**
     * Creates the keyboard for the game
     *
     * @param gridPane the gridPane that will contain the keyboard
     */
    public void createGrid(GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof BorderPane borderPane) {
                borderPane.setOnMouseEntered(event -> {
                    borderPane.toFront();
                    ScaleTransition scaleTransition = GameAnimations.scaleTrans(borderPane, 1, 1.15);
                    scaleTransition.play();
                });
                borderPane.setOnMouseExited(event -> {
                    borderPane.toFront();
                    ScaleTransition scaleTransition = GameAnimations.scaleTrans(borderPane, 1.15, 1);
                    scaleTransition.play();
                });
                borderPane.setOnMouseClicked(event -> onClickedChoice(borderPane));
            }
        }
    }

    public void getRandomWord() {
        now_level++;
        if (now_level >= QUESTIONS.size()) {
            now_level = 0;
        }
        Quizz_ctrl.Quizz quizz = QUESTIONS.get(now_level);
        question = quizz.question;
        choices = quizz.choices;
        answer = quizz.answer;
    }

    private void onClickedChoice(BorderPane borderPane) {
        if (borderPane.getLeft() instanceof StackPane stackPane) {
            if (stackPane.getChildren().get(0) instanceof Label label) {
                quizz_ctrl.showEndGameWindow(label.getText().equals(answer), answer);
            }
        }
    }

    public void createQuestion(Text question, Text ansA, Text ansB, Text ansC, Text ansD) {
        question.setText(this.question);
        ansA.setText(choices[0]);
        ansB.setText(choices[1]);
        ansC.setText(choices[2]);
        ansD.setText(choices[3]);
    }
}
