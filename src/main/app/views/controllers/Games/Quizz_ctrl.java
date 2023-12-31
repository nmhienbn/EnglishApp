package views.controllers.Games;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import views.animations.GameAnimations;
import views.controllers.Panel.MainPanel_ctrl;
import views.games.GameNotification;
import views.games.GameQuizz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static views.controllers.Games.NodeIntersect.inHierarchy;

public class Quizz_ctrl extends Game_ctrl {
    public static MainPanel_ctrl mainPanelCtrl = null;

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

    private final GameQuizz mainQuizz = GameQuizz.getInstance();

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

    SequentialTransition sequentialTransition;

    // Word lists
    public static final ArrayList<Quizz> QUESTIONS = new ArrayList<>();

    @FXML
    protected void initialize() {
        gameName = "quizz";
        mainQuizz.quizz_ctrl = this;
        super.initialize();
    }

    @Override
    public void restartAction() {
        mainQuizz.getRandomLevel();
        mainQuizz.createQuestion(question, ansA, ansB, ansC, ansD);
        showStartGame();
    }

    protected void createUI() {
        // Create Question
        mainQuizz.createQuestion(question, ansA, ansB, ansC, ansD);

        // Create Game Title
        mainQuizz.createGameTitle(titleHBox);
    }

    protected void initializeGameData() {
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

        mainQuizz.getRandomLevel();
    }

    protected void hideNotification(MouseEvent e) {
        if (notificationPane.isVisible() &&
                inHierarchy(e.getPickResult().getIntersectedNode(), notificationPane) &&
                inHierarchy(e.getPickResult().getIntersectedNode(), fA) &&
                inHierarchy(e.getPickResult().getIntersectedNode(), fB) &&
                inHierarchy(e.getPickResult().getIntersectedNode(), fC) &&
                inHierarchy(e.getPickResult().getIntersectedNode(), fD)) {
            new GameNotification().hideNotification(dimSc, notificationPane);
        }
    }

    public void showEndGameWindow(boolean guessed, String winningWord) {
        new GameNotification().endGameNotification(guessed, winningWord, this,
                new String[]{"YOUR ANSWER IS CORRECT!", "THE CORRECT ANSWER IS"});
    }

    public void showStartGame() {
        mainQuizz.resetGrid(choices);
        ParallelTransition parallelTransition = new ParallelTransition();
        int duration = 200;
        if (sequentialTransition != null) {
            sequentialTransition.stop();
            parallelTransition.getChildren().addAll(
                    GameAnimations.fadeTrans(fQ, 1, 0, duration),
                    GameAnimations.scaleTrans(fA, 1, 0, duration),
                    GameAnimations.scaleTrans(fB, 1, 0, duration),
                    GameAnimations.scaleTrans(fC, 1, 0, duration),
                    GameAnimations.scaleTrans(fD, 1, 0, duration));
        }
        sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().add(parallelTransition);
        duration = 400;
        sequentialTransition.getChildren().addAll(
                GameAnimations.fadeTrans(fQ, 0, 1, duration),
                GameAnimations.scaleTrans(fA, 0, 1, duration),
                GameAnimations.scaleTrans(fB, 0, 1, duration),
                GameAnimations.scaleTrans(fC, 0, 1, duration),
                GameAnimations.scaleTrans(fD, 0, 1, duration));
        sequentialTransition.play();
        sequentialTransition.setOnFinished(event -> {
            mainQuizz.createGrid(choices);
        });
    }
}
