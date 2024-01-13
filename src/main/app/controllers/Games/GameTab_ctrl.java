package controllers.Games;

import controllers.AppControllers;
import controllers.Panel.ChangeTab;
import javaGif.AnimatedGif;
import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.util.Objects;

import static models.games.GameSound.mediaClick;

public class GameTab_ctrl extends AppControllers {
    public static BorderPane mainPane;
    @FXML
    private Button quizz_button;
    @FXML
    private Button wordle_button;
    @FXML
    private Button ctw_button;

    @FXML
    protected void initialize() {
        setButton(quizz_button, "/front_end/graphic/icons/gameTab/quizz.gif", 2000);
        setButton(wordle_button, "/front_end/graphic/icons/gameTab/wordle.gif", 2000);
        setButton(ctw_button, "/front_end/graphic/icons/gameTab/ctw.gif", 2000);
    }

    public void setButton(Button button, String filename, double durationMs) {
        ImageView img = new ImageView(new AnimatedGif(Objects.requireNonNull(getClass().getResource(filename)).
                toExternalForm(), durationMs).getView().getImage());
        button.setGraphic(img);
        button.setOnMouseEntered(e -> {
            AnimatedGif animation = new AnimatedGif(Objects.requireNonNull(getClass().getResource(filename)).
                    toExternalForm(), durationMs);
            button.setGraphic(animation.getView());
            animation.setCycleCount(Animation.INDEFINITE);
            animation.play();
        });
        button.setOnMouseExited(e -> {
            button.setGraphic(img);
        });
        button.setOnAction(e -> {
            mediaClick.play();
            OnButtonPress(button);
        });
    }

    private void OnButtonPress(Button button) {
        if (button == quizz_button) {
            Quizz_ctrl quizz_ctrl = new ChangeTab().QuizzTab(mainPane);
            quizz_ctrl.showStartGame();
        } else if (button == wordle_button) {
            Wordle_ctrl wordle_ctrl = new ChangeTab().WordleTab(mainPane);
            wordle_ctrl.gridRequestFocus();
            wordle_ctrl.showStartGame();
        } else if (button == ctw_button) {
            CTW_ctrl ctwTab_ctrl = new ChangeTab().CTWTab(mainPane);
            ctwTab_ctrl.gridRequestFocus();
            ctwTab_ctrl.showStartGame();
        }
    }
}
