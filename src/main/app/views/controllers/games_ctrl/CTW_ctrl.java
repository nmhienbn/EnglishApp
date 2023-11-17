package views.controllers.games_ctrl;

import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import views.animations.GameAnimations;
import views.controllers.mainPanel_ctrl;
import views.games.GameNotification;
import views.games.MainCatchWord;

import java.util.ArrayList;

public class CTW_ctrl extends Game_ctrl {
    public static mainPanel_ctrl mainPanelCtrl = null;
    private final MainCatchWord mainCTW = MainCatchWord.getInstance();

    @FXML
    public AnchorPane game_sc;
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
    public ImageView helpButton;
    @FXML
    public HBox titleHBox;
    @FXML
    public ImageView restartButton;
    @FXML
    public ImageView exitButton;
    @FXML
    public BorderPane notificationPane;
    @FXML
    public Region dimSc;

    // Word lists
    public static final ArrayList<String> winningWords = new ArrayList<>();

    @FXML
    void initialize() {
        initializeWordLists();
        createUI();
        mainCTW.ctw_ctrl = this;
        GameNotification.ctw_ctrl = this;
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
        mainCTW.onKeyPressed(gridPane, keyboardRows, keyEvent);
    }

    @FXML
    public void showHelp() {
        if (notificationPane.isVisible()) {
            dimSc.setVisible(false);
            notificationPane.setVisible(false);
            gridRequestFocus();
        } else {
            GameNotification.instructionCTW(notificationPane);
            dimSc.toFront();
            dimSc.setVisible(true);
            notificationPane.toFront();
            notificationPane.setVisible(true);
        }
    }

    @FXML
    public void restart() {
        RotateTransition rotateTransition = GameAnimations.rotateTrans(restartButton, 0, 360 * 3);
        rotateTransition.setOnFinished(ae -> {
            mainCTW.resetGame(ImageAns, gridPane, keyboardRows);
            showStartGame();
        });
        rotateTransition.play();
        gridRequestFocus();
    }

    @FXML
    public void exitGame() {
        mainPanelCtrl.mainPane.setCenter(mainPanelCtrl.game_tab);
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
        GameNotification.endGameNotification(guessed, winningWord, this,
                new String[]{"The word you need to catch was", ""});
    }
}
