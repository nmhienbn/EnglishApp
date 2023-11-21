package views.controllers.games_ctrl;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import views.animations.GameAnimations;
import views.controllers.MainPanel_ctrl;
import views.games.GameNotification;
import views.games.MainWordle;

import java.util.ArrayList;

public class Wordle_ctrl extends Game_ctrl {
    public static MainPanel_ctrl mainPanelCtrl = null;

    private final MainWordle mainWordle = MainWordle.getInstance();

    @FXML
    public AnchorPane game_sc;
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
    public Button exitButton;
    @FXML
    public BorderPane notificationPane;
    @FXML
    public Region dimSc;

    // Word lists
    public static final ArrayList<String> winningWords = new ArrayList<>();

    @FXML
    protected void initialize() {
        initializeWordLists();
        createUI();
        mainWordle.wordle_ctrl = this;
        GameNotification.wordle_ctrl = this;
        setTooltip(helpButton, "Instructions");
        setTooltip(restartButton, "Restart");
        setTooltip(exitButton, "Exit");
        game_sc.setOnMouseClicked(e -> {
            if (notificationPane.isVisible() &&
                    inHierarchy(e.getPickResult().getIntersectedNode(), notificationPane) &&
                    inHierarchy(e.getPickResult().getIntersectedNode(), helpButton)) {
                dimSc.setVisible(false);
                notificationPane.setVisible(false);
                notificationPane.setTranslateX(0);
                notificationPane.setTranslateY(0);
                notificationPane.setOnMousePressed(null);
                notificationPane.setOnMouseDragged(null);
                gridRequestFocus();
            }
        });
    }

    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        mainWordle.onKeyPressed(gridPane, keyboardRows, keyEvent);
    }

    @FXML
    public void showHelp() {
        super.showHelp();
        if (notificationPane.isVisible()) {
            dimSc.setVisible(false);
            notificationPane.setVisible(false);
            gridRequestFocus();
        } else {
            GameNotification.instructionWordle(notificationPane);
            dimSc.toFront();
            dimSc.setVisible(true);
            notificationPane.toFront();
            notificationPane.setVisible(true);
        }
    }

    @FXML
    public void restart() {
        super.restart();
        for (Node node : restartButton.getChildrenUnmodifiable()) {
            if (node != null) {
                RotateTransition rotateTransition = GameAnimations.rotateTrans(node, 0, 360 * 3);
                rotateTransition.setOnFinished(ae -> {
                    showStartGame();
                    mainWordle.resetGame(gridPane, keyboardRows);
                });
                rotateTransition.play();
                gridRequestFocus();
                break;
            }
        }
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

    @FXML
    public void exitGame() {
        super.exitGame();
        mainPanelCtrl.mainPane.setCenter(mainPanelCtrl.game_tab);
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
        GameNotification.showNotification(MainWordle.getInstance().wordle_ctrl.notificationPane, "INVALID WORD!");
    }

    private void initializeWordLists() {
        initWords("/game/winning-words.txt", winningWords);
        mainWordle.getRandomWord();
    }

    public void showEndGameWindow(boolean guessed, String winningWord) {
        GameNotification.endGameNotification(guessed, winningWord, this,
                new String[]{"YOU WON!", "THE WINNING WORD WAS:"});
    }
}
