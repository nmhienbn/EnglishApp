package views.controllers.games_ctrl;

import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import views.animations.GameAnimations;
import views.controllers.mainPanel_ctrl;
import views.games.GameNotification;
import views.games.MainQuizz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Quizz_ctrl extends Game_ctrl {
    public static mainPanel_ctrl mainPanelCtrl = null;
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
    StackPane fQ;
    @FXML
    public Text question;
    @FXML
    public AnchorPane fA, fB, fC, fD;
    @FXML
    public Text ansA, ansB, ansC, ansD;
    @FXML
    public HBox titleHBox;
    @FXML
    public Button restartButton;
    @FXML
    public Button exitButton;
    @FXML
    public BorderPane notificationPane;
    @FXML
    public Region dimSc;

    // Word lists
    public static final ArrayList<Quizz> QUESTIONS = new ArrayList<>();

    @FXML
    void initialize() {
        initializeQuizzLists();
        createUI();
        mainQuizz.quizz_ctrl = this;
        setTooltip(restartButton, "Restart");
        setTooltip(exitButton, "Exit");
    }

    @FXML
    public void restart() {
        RotateTransition rotateTransition = GameAnimations.rotateTrans(restartButton, 0, 360 * 3);
        rotateTransition.setOnFinished(ae -> {
            mainQuizz.getRandomWord();
            mainQuizz.createQuestion(question, ansA, ansB, ansC, ansD);
            showStartGame();
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

    @FXML
    public void exitGame() {
        mainPanelCtrl.mainPane.setCenter(mainPanelCtrl.game_tab);
    }

    public void showEndGameWindow(boolean guessed, String winningWord) {
        GameNotification.endGameNotification(guessed, winningWord, this,
                new String[]{"YOUR ANSWER IS CORRECT!", "THE CORRECT ANSWER IS"});
    }

    public void showStartGame() {
        int duration = 500;
        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(
                GameAnimations.fadeTrans(fQ, 0, 1, duration),
                GameAnimations.scaleTrans(fA, 0, 1, duration),
                GameAnimations.scaleTrans(fB, 0, 1, duration),
                GameAnimations.scaleTrans(fC, 0, 1, duration),
                GameAnimations.scaleTrans(fD, 0, 1, duration));
        sequentialTransition.play();
    }
}
