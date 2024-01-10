package controllers.Games;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import controllers.AppControllers;
import controllers.Panel.MainPanel_ctrl;
import models.games.GameNotification;

import static models.games.GameSound.mediaClick;

public abstract class Game_ctrl extends AppControllers {
    protected String gameName;

    @FXML
    public AnchorPane game_sc;
    @FXML
    public BorderPane notificationPane;
    @FXML
    public Button helpButton;
    @FXML
    public HBox titleHBox;
    @FXML
    public Button restartButton;
    @FXML
    public Button exitButton;
    @FXML
    public Region dimSc;

    @FXML
    protected void initialize() {
        initializeGameData();
        createUI();
        new Tooltips().setup(this);
        game_sc.setOnMouseClicked(this::hideNotification);
    }

    protected abstract void initializeGameData();

    protected abstract void createUI();

    protected abstract void hideNotification(MouseEvent e);

    @FXML
    public final void restart() {
        mediaClick.play();
        for (Node node : restartButton.getChildrenUnmodifiable()) {
            if (node != null) {
                RotateTransition rotateTransition = GameAnimations.rotateTrans(node, 0, 360 * 3);
                rotateTransition.setOnFinished(ae -> restartAction());
                rotateTransition.play();
                break;
            }
        }
    }

    @FXML
    public final void showHelp() {
        new GameNotification().showInstruction(this, gameName);
    }

    @FXML
    public final void exitGame() {
        mediaClick.play();
        GameTab_ctrl.mainPanelCtrl.mainPane.setCenter(GameTab_ctrl.mainPanelCtrl.game_tab);
    }

    public abstract void restartAction();

    public abstract void showStartGame();
}
