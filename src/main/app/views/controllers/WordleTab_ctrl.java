package views.controllers;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;
import views.animations.GameAnimations;
import views.wordle.GameNotification;
import views.wordle.MainWordle;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Stream;

public class WordleTab_ctrl {

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
        rotateTransition.setOnFinished(ae ->
                mainWordle.resetGame(gridPane, keyboardRows));
        rotateTransition.play();
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

    private void config_nav_button(Button button) {
        Tooltip tt = button.getTooltip();
        tt.setShowDelay(new Duration(.1));
        tt.setOnShown(s -> {
            //Get button current bounds on computer screen
            Bounds bounds = button.localToScreen(button.getBoundsInLocal());
            button.getTooltip().setX(bounds.getMaxX() - tt.getWidth() / 2);
            button.getTooltip().setY(bounds.getMaxY() + 5);
        });
        tt.getStyleClass().add("navbutton-tooltip");
    }

    public static void showWordNotFound() {
        GameNotification.notFoundWordNotification(MainWordle.getInstance().wordleTab_ctrl.notificationPane);
    }

    private void initWords(String path, ArrayList<String> words) {
        InputStream winning_words = getClass().getResourceAsStream(path);
        assert winning_words != null
                : "Could not find " + path + " file in resources folder.";
        Stream<String> winning_words_lines = new BufferedReader(new InputStreamReader(winning_words)).lines();
        winning_words_lines.forEach(words::add);
    }

    private void initializeWordLists() {
        initWords("/game/winning-words.txt", winningWords);
        mainWordle.getRandomWord();
    }

    public void showEndGameWindow(boolean guessed, String winningWord) {
        GameNotification.endGameNotification(guessed, winningWord, notificationPane);
    }
}
