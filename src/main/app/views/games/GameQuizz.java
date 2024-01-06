package views.games;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import views.animations.GameAnimations;
import views.controllers.Games.Quizz_ctrl;

import static views.controllers.Games.Quizz_ctrl.QUESTIONS;

public class GameQuizz extends Game {
    protected static GameQuizz instance = null;
    public Quizz_ctrl quizz_ctrl = null;


    protected GameQuizz() {
        // Exists only to defeat instantiation.
    }

    /**
     * Gets the instance of the mainWordle class
     *
     * @return the instance of the mainWordle class
     */
    public static GameQuizz getInstance() {
        if (instance == null) {
            instance = new GameQuizz();
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

    private boolean isScaled = false;

    /**
     * Creates the keyboard for the game
     *
     * @param gridPane the gridPane that will contain the keyboard
     */
    public void createGrid(GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof AnchorPane anchorPane) {
                anchorPane.setOnMouseEntered(event -> {
                    if (!quizz_ctrl.notificationPane.isVisible() && !isScaled) {
                        anchorPane.toFront();
                        ScaleTransition scaleTransition = GameAnimations.scaleTrans(anchorPane, 1, 1.2, 150);
                        scaleTransition.play();
                        isScaled = true;
                    }
                });
                anchorPane.setOnMouseExited(event -> {
                    if (isScaled) {
                        isScaled = false;
                        anchorPane.toFront();
                        ScaleTransition scaleTransition = GameAnimations.scaleTrans(anchorPane, 1.2, 1, 150);
                        scaleTransition.play();
                    }

                });
                anchorPane.setOnMouseClicked(event -> onClickedChoice(anchorPane));
            }
        }
    }

    public void resetGrid(GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof AnchorPane anchorPane) {
                anchorPane.setOnMouseEntered(null);
                anchorPane.setOnMouseExited(null);
                anchorPane.setOnMouseClicked(null);
            }
        }
    }

    public void getRandomLevel() {
        now_level++;
        if (now_level >= QUESTIONS.size()) {
            now_level = 0;
        }
        Quizz_ctrl.Quizz quizz = QUESTIONS.get(now_level);
        question = quizz.question;
        choices = quizz.choices;
        answer = quizz.answer;
    }

    private void onClickedChoice(AnchorPane anchorPane) {
        for (Node node : anchorPane.getChildren()) {
            if (node instanceof Label label) {
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
