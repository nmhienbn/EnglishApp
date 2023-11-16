package views.controllers.games_ctrl;

import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import views.File_loader;
import views.animations.GameAnimations;
import views.controllers.mainPanel_ctrl;

import java.io.IOException;

public class MainGame_ctrl {
    public static mainPanel_ctrl mainPanelCtrl = null;
    @FXML
    private Button quizz_button;
    @FXML
    private Button wordle_button;
    @FXML
    private Button ctw_button;
    Parent quizz_tab;
    Parent wordle_tab;
    Parent ctw_tab;

    WordleTab_ctrl wordleTab_ctrl;

    CTW_ctrl ctwTab_ctrl;

    Quizz_ctrl quizz_ctrl;

    @FXML
    void initialize() throws IOException {
        setButton(quizz_button, "/front_end/graphic/icons/quizz.gif", 2000);
        setButton(wordle_button, "/front_end/graphic/icons/wordle.gif", 2000);
        setButton(ctw_button, "/front_end/graphic/icons/ctw.gif", 2000);

        quizz_ctrl = new Quizz_ctrl();
        quizz_tab = File_loader.getInstance().fxml_quizz_Tab(quizz_ctrl);
        wordleTab_ctrl = new WordleTab_ctrl();
        wordle_tab = File_loader.getInstance().fxml_wordle_Tab(wordleTab_ctrl);
        ctwTab_ctrl = new CTW_ctrl();
        ctw_tab = File_loader.getInstance().fxml_ctw_Tab(ctwTab_ctrl);
    }

    public void setButton(Button button, String filename, double durationMs) {
        GameAnimations.AnimatedGif ani = new GameAnimations.AnimatedGif(getClass().getResource(filename).toExternalForm(), durationMs);
        ani.setCycleCount(Animation.INDEFINITE);
        button.setGraphic(ani.getView());
        button.setOnMouseEntered(e -> {
            ani.play();
        });
        button.setOnMouseExited(e -> {
            ani.stop();
        });
        button.setOnAction(e -> {
            OnButtonPress(button);
        });
    }

    private void OnButtonPress(Button button) {
        if (button == quizz_button) {
            mainPanelCtrl.mainPane.setCenter(quizz_tab);
            quizz_ctrl.showStartGame();
        } else if (button == wordle_button) {
            mainPanelCtrl.mainPane.setCenter(wordle_tab);
            wordleTab_ctrl.gridRequestFocus();
            wordleTab_ctrl.showStartGame();
        } else if (button == ctw_button) {
            mainPanelCtrl.mainPane.setCenter(ctw_tab);
            ctwTab_ctrl.gridRequestFocus();
            ctwTab_ctrl.showStartGame();
        }
    }
}
