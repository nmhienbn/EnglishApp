package views.controllers;

import javaGif.AnimatedGif;
import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import views.File_loader;
import views.controllers.games_ctrl.CTW_ctrl;
import views.controllers.games_ctrl.Quizz_ctrl;
import views.controllers.games_ctrl.Wordle_ctrl;

import java.io.File;
import java.io.IOException;

public class MainGame_ctrl extends AppControllers {
    static Media media = new Media(new File(System.getProperty("user.dir") +
            "\\src\\main\\resources\\audio\\click.mp3").toURI().toString());
    static MediaPlayer mediaPlayer = new MediaPlayer(media);
    public static MainPanel_ctrl mainPanelCtrl = null;
    @FXML
    private Button quizz_button;
    @FXML
    private Button wordle_button;
    @FXML
    private Button ctw_button;
    Parent quizz_tab;
    Parent wordle_tab;
    Parent ctw_tab;

    Wordle_ctrl wordle_ctrl;

    CTW_ctrl ctwTab_ctrl;

    Quizz_ctrl quizz_ctrl;

    @FXML
    protected void initialize() throws IOException {
        setButton(quizz_button, "/front_end/graphic/icons/gameTab/quizz.gif", 2000);
        setButton(wordle_button, "/front_end/graphic/icons/gameTab/wordle.gif", 2000);
        setButton(ctw_button, "/front_end/graphic/icons/gameTab/ctw.gif", 2000);

        quizz_ctrl = new Quizz_ctrl();
        quizz_tab = File_loader.getInstance().fxml_loadTab("front_end/fxml/quizz.fxml", quizz_ctrl);
        wordle_ctrl = new Wordle_ctrl();
        wordle_tab = File_loader.getInstance().fxml_loadTab("front_end/fxml/wordle.fxml", wordle_ctrl);
        ctwTab_ctrl = new CTW_ctrl();
        ctw_tab = File_loader.getInstance().fxml_loadTab("front_end/fxml/ctw.fxml", ctwTab_ctrl);
    }

    public void setButton(Button button, String filename, double durationMs) {
        AnimatedGif ani = new AnimatedGif(getClass().getResource(filename).toExternalForm(), durationMs);
        ani.setCycleCount(Animation.INDEFINITE);
        button.setGraphic(ani.getView());
        button.setOnMouseEntered(e -> {
            ani.play();
        });
        button.setOnMouseExited(e -> {
            ani.jumpTo(Duration.ZERO);
            ani.stop();
        });
        button.setOnAction(e -> {
            mediaPlayer.play();
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.stop();
                mediaPlayer.seek(Duration.ZERO);
            });
            OnButtonPress(button);
        });
    }

    private void OnButtonPress(Button button) {
        if (button == quizz_button) {
            mainPanelCtrl.mainPane.setCenter(quizz_tab);
            quizz_ctrl.showStartGame();
        } else if (button == wordle_button) {
            mainPanelCtrl.mainPane.setCenter(wordle_tab);
            wordle_ctrl.gridRequestFocus();
            wordle_ctrl.showStartGame();
        } else if (button == ctw_button) {
            mainPanelCtrl.mainPane.setCenter(ctw_tab);
            ctwTab_ctrl.gridRequestFocus();
            ctwTab_ctrl.showStartGame();
        }
    }
}
