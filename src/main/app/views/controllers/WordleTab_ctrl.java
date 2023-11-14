package views.controllers;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Polygon;
import views.animations.GameAnimations;
import views.games.GameNotification;
import views.games.MainWordle;

import java.util.ArrayList;

public class WordleTab_ctrl extends Game_ctrl {

    private final MainWordle mainWordle = MainWordle.getInstance();

    @FXML
    public GridPane gridPane;
    @FXML
    public GridPane keyboardRow1;
    public GridPane keyboardRow2;
    public GridPane keyboardRow3;
    public GridPane[] keyboardRows;
    @FXML
    public Button helpButton;
    @FXML
    public HBox titleHBox;
    @FXML
    public Button restartButton;

    @FXML
    public BorderPane notificationPane;
    @FXML
    public Polygon triangle;

    // Word lists
    public static final ArrayList<String> winningWords = new ArrayList<>();

    @FXML
    void initialize() {
        initializeWordLists();
        createUI();
        mainWordle.wordleTab_ctrl = this;
        GameNotification.wordleTab_ctrl = this;
        helpButton.setTooltip(new Tooltip("Instructions"));
        helpButton.setStyle("-fx-background-image: url(/game/images/help.png); " +
                "-fx-background-size: 40 40;-fx-background-radius: 50%");
        config_nav_button(helpButton);
        restartButton.setTooltip(new Tooltip("Restart"));
        restartButton.setStyle("-fx-background-image: url(/game/images/restart.png); " +
                "-fx-background-size: 40 40;-fx-background-radius: 50%");
        config_nav_button(restartButton);
    }

    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        mainWordle.onKeyPressed(gridPane, keyboardRows, keyEvent);
    }

    @FXML
    public void showHelp() {
        if (notificationPane.isVisible()) {
            notificationPane.setVisible(false);
            triangle.setVisible(false);
            gridRequestFocus();
        } else {
            GameNotification.instructionNotification(notificationPane);
            notificationPane.setVisible(true);
            triangle.setVisible(true);
        }
    }

    @FXML
    public void restart() {
        RotateTransition rotateTransition = GameAnimations.rotateTrans(restartButton, 0, 360 * 3);
        rotateTransition.setOnFinished(ae -> {
            showStartGame();
            mainWordle.resetGame(gridPane, keyboardRows);
        });
        rotateTransition.play();
    }

    @Override
    public void showStartGame() {
        SequentialTransition sequentialTransition = new SequentialTransition();
        ParallelTransition parallelTransition1 = new ParallelTransition();
        for (Node node : gridPane.getChildren()) {
            if (node != null) {
                parallelTransition1.getChildren().add(GameAnimations.scaleTrans(node, 0, 1, 1000));
            }
        }
        sequentialTransition.getChildren().add(parallelTransition1);
        sequentialTransition.play();
    }

    private void createUI() {
        // Create Grid
        mainWordle.createGrid(gridPane);

        // Create Keyboard
        keyboardRows = new GridPane[3];
        keyboardRows[0] = keyboardRow1;
        keyboardRows[1] = keyboardRow2;
        keyboardRows[2] = keyboardRow3;
        mainWordle.createKeyBoard(keyboardRows, gridPane);

        // Create Game Title
        mainWordle.createGameTitle(titleHBox);
    }

    public void gridRequestFocus() {
        gridPane.requestFocus();
    }

    public static void showWordNotFound() {
        GameNotification.showNotification(MainWordle.getInstance().wordleTab_ctrl.notificationPane, "INVALID WORD!");
    }

    private void initializeWordLists() {
        initWords("/game/winning-words.txt", winningWords);
        mainWordle.getRandomWord();
    }

    public void showEndGameWindow(boolean guessed, String winningWord) {
        GameNotification.endGameNotification(guessed, winningWord, notificationPane, this,
                new String[]{"YOU WON!", "THE WINNING WORD WAS:"});
    }
}
