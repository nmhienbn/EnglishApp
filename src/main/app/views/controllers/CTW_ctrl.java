package views.controllers;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Polygon;
import views.animations.GameAnimations;
import views.wordle.GameNotification;
import views.wordle.MainCatchWord;

import java.util.ArrayList;

public class CTW_ctrl extends Game_ctrl{
    private final MainCatchWord mainCTW = MainCatchWord.getInstance();

    @FXML
    public ImageView ImageAns;
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
        mainCTW.ctw_ctrl = this;
        GameNotification.ctw_ctrl = this;
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
        mainCTW.onKeyPressed(gridPane, keyboardRows, keyEvent);
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
                mainCTW.resetGame(ImageAns, gridPane, keyboardRows));
        rotateTransition.play();
        gridRequestFocus();
    }

    private void createUI() {
        // Create Game Title
        mainCTW.createGameTitle(titleHBox);

        // Create Grid
        mainCTW.createGrid(gridPane, ImageAns);

        // Create Keyboard
        keyboardRows = new GridPane[3];
        keyboardRows[0] = keyboardRow1;
        keyboardRows[1] = keyboardRow2;
        keyboardRows[2] = keyboardRow3;
        mainCTW.createKeyBoard(keyboardRows, gridPane);
    }

    public void gridRequestFocus() {
        gridPane.requestFocus();
    }

    public static void showWrongWord() {
        GameNotification.showNotification(MainCatchWord.getInstance().ctw_ctrl.notificationPane, "WRONG WORD!");
    }

    private void initializeWordLists() {
        initWords("/game/ctw_words.txt", winningWords);
        mainCTW.setLevels(winningWords.size());
        mainCTW.getRandomWord();
    }

    public void showEndGameWindow(boolean guessed, String winningWord) {
        GameNotification.endGameNotification(guessed, winningWord, notificationPane, this,
                new String[]{"               You won! \n The word you need to catch was", ""});
    }
}
