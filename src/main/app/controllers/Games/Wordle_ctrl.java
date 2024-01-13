package controllers.Games;

import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import models.games.GameKeyBoard.Wordle.GameWordle;
import models.games.GameNotification;

import java.util.ArrayList;

public class Wordle_ctrl extends GameKeyBoard_ctrl {

    private GameWordle mainWordle;

    // Word lists
    public ArrayList<String> winningWords;

    @FXML
    protected void initialize() {
        gameName = "wordle";
        mainWordle = new GameWordle();
        mainWordle.setController(this);
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

    @Override
    protected void initializeGameData() {
        winningWords = new ArrayList<>();
        initWords("/game/winning-words.txt", winningWords);
        mainWordle.getRandomLevel();
    }

    @Override
    protected void createUI() {
        super.createUI();
        // Create Grid
        mainWordle.createGrid(gridPane);
    }

    public void showWordNotFound() {
        new GameNotification().fadedNotification(mainWordle.
                getController().notificationPane, "INVALID WORD!");
    }

    public void showEndGameWindow(boolean guessed, String winningWord) {
        new GameNotification().endGameNotification(guessed, winningWord, this,
                new String[]{"YOU WON!", "THE WINNING WORD WAS:"});
    }
}
