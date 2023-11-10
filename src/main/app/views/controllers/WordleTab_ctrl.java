package views.controllers;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import javafx.util.Duration;
import views.animations.GameAnimations;
import views.wordle.MainWordle;
import views.wordle.ScoreWindow;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Stream;

import static java.lang.System.exit;

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
    public BorderPane helpPane;
    @FXML
    public Polygon triangle;

    /* Word lists */
    public static final ArrayList<String> winningWords = new ArrayList<>();
    public static final ArrayList<String> dictionaryWords = new ArrayList<>();

    private static Stage stageReference;

    public void createUI() {
        createGrid();
        createKeyboard();
        createTitleHBox();
    }

    public void createGrid() {
        mainWordle.createGrid(gridPane);
    }

    public void createKeyboard() {
        keyboardRows = new GridPane[3];
        keyboardRows[0] = keyboardRow1;
        keyboardRows[1] = keyboardRow2;
        keyboardRows[2] = keyboardRow3;
        mainWordle.createKeyBoard(keyboardRows, gridPane);
    }

    public void gridRequestFocus() {
        gridPane.requestFocus();
    }

    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        mainWordle.onKeyPressed(gridPane, keyboardRows, keyEvent);
    }

    public void getRandomWord() {
        mainWordle.getRandomWord();
    }

    @FXML
    public void showHelp() {
        if (helpPane.isVisible()) {
            helpPane.setVisible(false);
            triangle.setVisible(false);
            gridRequestFocus();
        } else {
            ScoreWindow.createHelpWindow(helpPane);
            helpPane.setVisible(true);
            triangle.setVisible(true);
        }
    }

    public void createTitleHBox() {
        mainWordle.createGameTitle(titleHBox);
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

    @FXML
    public void restart() {
        RotateTransition rotateTransition = GameAnimations.rotateTrans(restartButton, 0, 360 * 3);
        rotateTransition.setOnFinished(ae ->
                mainWordle.resetGame(gridPane, keyboardRows));
        rotateTransition.play();
    }

    @FXML
    void initialize() {
        initializeWordLists();
        createUI();
        getRandomWord();
        mainWordle.wordleTab_ctrl = this;
        ScoreWindow.wordleTab_ctrl = this;
        helpButton.setTooltip(new Tooltip("Instructions"));
        helpButton.setStyle("-fx-background-image: url(/game/images/help.png); " +
                "-fx-background-size: 40 40;-fx-background-radius: 50%");
        config_nav_button(helpButton);
        restartButton.setTooltip(new Tooltip("Restart"));
        restartButton.setStyle("-fx-background-image: url(/game/images/restart.png); " +
                "-fx-background-size: 40 40;-fx-background-radius: 50%");
        config_nav_button(restartButton);
    }

    public static void showToast() {
        ScoreWindow.showNotFoundWord(MainWordle.getInstance().wordleTab_ctrl.helpPane);
    }

    private void initWords(String path, ArrayList<String> words) {
        InputStream winning_words = getClass().getResourceAsStream(path);
        assert winning_words != null
                : "Could not find " + path + " file in resources folder.";
        Stream<String> winning_words_lines = new BufferedReader(new InputStreamReader(winning_words)).lines();
        winning_words_lines.forEach(words::add);
    }

    public void initializeWordLists() {
        initWords("/game/winning-words.txt", winningWords);
        initWords("/game/dictionary.txt", dictionaryWords);
    }

    public void showEndGameWindow(boolean guessed, String winningWord) {
        ScoreWindow.displayEndGame(guessed, winningWord, helpPane);
    }
}
