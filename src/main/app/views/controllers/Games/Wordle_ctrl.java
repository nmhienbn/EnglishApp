package views.controllers.Games;

import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import views.animations.GameAnimations;
import views.controllers.Panel.MainPanel_ctrl;
import views.games.GameNotification;
import views.games.GameWordle;

import java.util.ArrayList;

public class Wordle_ctrl extends GameKeyBoard_ctrl {
    public static MainPanel_ctrl mainPanelCtrl = null;

    private final GameWordle mainWordle = GameWordle.getInstance();

    // Word lists
    public static final ArrayList<String> winningWords = new ArrayList<>();

    @FXML
    protected void initialize() {
        gameName = "wordle";
        mainWordle.wordle_ctrl = this;
        gameKeyBoard = mainWordle;
        super.initialize();
    }

    @Override
    public void restartAction() {
        showStartGame();
        mainWordle.resetGame(gridPane, keyboardRows);
        gridRequestFocus();
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

    protected void initializeGameData() {
        initWords("/game/winning-words.txt", winningWords);
        mainWordle.getRandomLevel();
    }

    protected void createUI() {
        super.createUI();
        // Create Grid
        mainWordle.createGrid(gridPane);
    }

    public static void showWordNotFound() {
        new GameNotification().fadedNotification(GameWordle.getInstance().wordle_ctrl.notificationPane, "INVALID WORD!");
    }

    public void showEndGameWindow(boolean guessed, String winningWord) {
        new GameNotification().endGameNotification(guessed, winningWord, this,
                new String[]{"YOU WON!", "THE WINNING WORD WAS:"});
    }
}
