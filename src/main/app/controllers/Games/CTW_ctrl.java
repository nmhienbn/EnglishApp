package controllers.Games;

import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import models.games.GameKeyBoard.CTW.GameCatchWord;
import models.games.GameNotification;

import java.util.ArrayList;

public class CTW_ctrl extends GameKeyBoard_ctrl {
    private GameCatchWord mainCTW;

    @FXML
    public ImageView ImageAns;
    @FXML
    public GridPane gridPane;

    // Word lists
    public ArrayList<String> winningWords;

    @FXML
    protected void initialize() {
        gameName = "ctw";
        mainCTW = new GameCatchWord();
        mainCTW.setController(this);
        gameKeyBoard = mainCTW;
        super.initialize();
    }

    @Override
    public void restartAction() {
        mainCTW.resetGame(ImageAns, gridPane, keyboardRows);
        showStartGame();
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
        parallelTransition1.getChildren().add(GameAnimations.fadeTrans(ImageAns, 0, 1, 1000));
        sequentialTransition.getChildren().add(parallelTransition1);
        sequentialTransition.play();
    }

    @Override
    protected void initializeGameData() {
        winningWords = new ArrayList<>();
        initWords("/game/ctw_words.txt", winningWords);
        mainCTW.setLevels(winningWords.size());
        mainCTW.getRandomLevel();
    }

    @Override
    protected void createUI() {
        super.createUI();
        // Create Grid
        mainCTW.createGrid(gridPane, ImageAns);
    }

    public void showWrongWord() {
        new GameNotification().fadedNotification(mainCTW.getController().notificationPane, "WRONG WORD!");
    }

    public void showEndGameWindow(boolean guessed, String winningWord) {
        new GameNotification().endGameNotification(guessed, winningWord, this,
                new String[]{"The word you need to catch was", ""});
    }
}
