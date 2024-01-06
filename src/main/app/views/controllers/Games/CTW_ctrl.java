package views.controllers.Games;

import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import views.animations.GameAnimations;
import views.games.GameNotification;
import views.games.GameCatchWord;

import java.util.ArrayList;

public class CTW_ctrl extends GameKeyBoard_ctrl {
    private final GameCatchWord mainCTW = GameCatchWord.getInstance();

    @FXML
    public ImageView ImageAns;
    @FXML
    public GridPane gridPane;
    @FXML
    public GridPane keyboardRow1;
    public GridPane keyboardRow2;
    public GridPane keyboardRow3;
    public GridPane[] keyboardRows;

    // Word lists
    public static final ArrayList<String> winningWords = new ArrayList<>();

    @FXML
    protected void initialize() {
        gameName = "ctw";
        mainCTW.ctw_ctrl = this;
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

    protected void initializeGameData() {
        initWords("/game/ctw_words.txt", winningWords);
        mainCTW.setLevels(winningWords.size());
        mainCTW.getRandomLevel();
    }

    protected void createUI() {
        super.createUI();
        // Create Grid
        mainCTW.createGrid(gridPane, ImageAns);
    }

    public static void showWrongWord() {
        new GameNotification().fadedNotification(GameCatchWord.getInstance().ctw_ctrl.notificationPane, "WRONG WORD!");
    }

    public void showEndGameWindow(boolean guessed, String winningWord) {
        new GameNotification().endGameNotification(guessed, winningWord, this,
                new String[]{"The word you need to catch was", ""});
    }
}
