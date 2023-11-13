package views.controllers;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import views.animations.GameAnimations;
import views.wordle.GameNotification;
import views.wordle.MainQuizz;

import java.io.*;
import java.util.ArrayList;

public class Quizz_ctrl extends Game_ctrl {
    public static class Quizz {
        public String question;
        public String[] choices;
        public String answer;

        public Quizz(String question, String[] choices, String answer) {
            this.question = question;
            this.choices = choices;
            this.answer = answer;
        }
    }

    private final MainQuizz mainQuizz = MainQuizz.getInstance();

    @FXML
    public GridPane choices;
    @FXML
    public Text question;
    @FXML
    public Text ansA, ansB, ansC, ansD;
    @FXML
    public HBox titleHBox;
    @FXML
    public Button restartButton;

    @FXML
    public BorderPane notificationPane;
    @FXML
    public Polygon triangle;

    // Word lists
    public static final ArrayList<Quizz> QUESTIONS = new ArrayList<>();

    @FXML
    void initialize() {
        initializeQuizzLists();
        createUI();
        mainQuizz.quizz_ctrl = this;
        restartButton.setTooltip(new Tooltip("Another Question"));
        restartButton.setStyle("-fx-background-image: url(/game/images/restart.png); " +
                "-fx-background-size: 40 40;-fx-background-radius: 50%");
        config_nav_button(restartButton);
    }

    @FXML
    public void restart() {
        RotateTransition rotateTransition = GameAnimations.rotateTrans(restartButton, 0, 360 * 3);
        rotateTransition.setOnFinished(ae -> {
            mainQuizz.getRandomWord();
            mainQuizz.createQuestion(question, ansA, ansB, ansC, ansD);
        });
        rotateTransition.play();
    }

    private void createUI() {
        // Create Question
        mainQuizz.createQuestion(question, ansA, ansB, ansC, ansD);

        // Create Grid
        mainQuizz.createGrid(choices);

        // Create Game Title
        mainQuizz.createGameTitle(titleHBox);
    }

    private void initializeQuizzLists() {
        try (InputStream inputStream = getClass().getResourceAsStream("/game/quizz.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String question;
            while ((question = reader.readLine()) != null) {
                if (question.isEmpty()) {
                    continue;
                }
                String[] choices = new String[4];
                for (int i = 0; i < 4; i++) {
                    choices[i] = reader.readLine();
                }
                String answer = reader.readLine();
                QUESTIONS.add(new Quizz(question, choices, answer));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mainQuizz.getRandomWord();
    }

    public void showEndGameWindow(boolean guessed, String winningWord) {
        GameNotification.endGameNotification(guessed, winningWord, notificationPane, this,
                new String[]{"YOUR ANSWER IS CORRECT!", "THE CORRECT ANSWER IS"});
    }
}
