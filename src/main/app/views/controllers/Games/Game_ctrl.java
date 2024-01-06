package views.controllers.Games;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import views.animations.GameAnimations;
import views.controllers.AppControllers;
import views.controllers.Panel.MainPanel_ctrl;
import views.games.GameNotification;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Stream;

import static views.games.GameSound.mediaClick;

public abstract class Game_ctrl extends AppControllers {
    public static MainPanel_ctrl mainPanelCtrl = null;
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
        mainPanelCtrl.mainPane.setCenter(mainPanelCtrl.game_tab);
    }

    public abstract void restartAction();

    public abstract void showStartGame();
}
