package views.controllers;

import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import views.File_loader;
import views.animations.GameAnimations.AnimatedGif;

import java.io.IOException;

public class mainPanel_ctrl {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Button home_button;
    @FXML
    private Button main_dictionary_button;
    @FXML
    private Button quizz;
    @FXML
    private Button google_translate_button;
    @FXML
    private Button wordle_button;
    @FXML
    private Button ctw_button;
    @FXML
    private Button logoImg;

    private Button focused_button = null;

    Parent home_tab;
    Parent main_dictionary_tab;
    Parent quizz_tab;
    Parent google_translator_tab;
    Parent wordle_tab;
    Parent ctw_tab;

    WordleTab_ctrl wordleTab_ctrl;

    CTW_ctrl ctwTab_ctrl;

    Quizz_ctrl quizz_ctrl;

    public void setButton(Button button, String filename, double durationMs) {
        AnimatedGif ani = new AnimatedGif(getClass().getResource(filename).toExternalForm(), durationMs);
        button.setGraphic(ani.getView());
        ani.getView().setEffect(colorAdjust);
        button.setOnMousePressed(e -> {
            ani.getView().setEffect(null); // Remove the effect
            ani.playOnce();
        });
    }

    ColorAdjust colorAdjust = new ColorAdjust();

    @FXML
    void initialize() throws IOException {
        colorAdjust.setSaturation(-1);
        AnimatedGif ani = new AnimatedGif(getClass().getResource("/front_end/graphic/icons/logo.gif").
                toExternalForm(), 3000);
        logoImg.setGraphic(ani.getView());
        ani.setCycleCount(Animation.INDEFINITE);
        ani.setAutoReverse(true);
        ani.play();

        home_button.setTooltip(new Tooltip("Home"));
        main_dictionary_button.setTooltip(new Tooltip("Main dictionary"));
        google_translate_button.setTooltip(new Tooltip("Google translator"));
        quizz.setTooltip(new Tooltip("Quizz game"));
        wordle_button.setTooltip(new Tooltip("Wordle game"));
        ctw_button.setTooltip(new Tooltip("Catch the word game"));

        config_nav_button(home_button);
        config_nav_button(main_dictionary_button);
        config_nav_button(google_translate_button);
        config_nav_button(quizz);
        config_nav_button(wordle_button);
        config_nav_button(ctw_button);

        setButton(home_button, "/front_end/graphic/icons/home.gif", 2000);
        setButton(main_dictionary_button, "/front_end/graphic/icons/book.gif", 2000);
        setButton(google_translate_button, "/front_end/graphic/icons/google_translate.gif", 2000);
        setButton(quizz, "/front_end/graphic/icons/quizz.gif", 2000);
        setButton(wordle_button, "/front_end/graphic/icons/wordle.gif", 2000);
        setButton(ctw_button, "/front_end/graphic/icons/ctw.gif", 2000);

        home_tab = File_loader.getInstance().fxml_homeTab();
        main_dictionary_tab = File_loader.getInstance().fxml_mainDictionaryTab();
        google_translator_tab = File_loader.getInstance().fxml_google_translate_Tab();
        quizz_ctrl = new Quizz_ctrl();
        quizz_tab = File_loader.getInstance().fxml_quizz_Tab(quizz_ctrl);
        wordleTab_ctrl = new WordleTab_ctrl();
        wordle_tab = File_loader.getInstance().fxml_wordle_Tab(wordleTab_ctrl);
        ctwTab_ctrl = new CTW_ctrl();
        ctw_tab = File_loader.getInstance().fxml_ctw_Tab(ctwTab_ctrl);

        mainPane.setCenter(home_tab);

    }

    private void config_nav_button(Button button) {
        button.setOnAction(e -> {
            OnButtonPress(button);
        });
        Tooltip tt = button.getTooltip();
        tt.setShowDelay(new Duration(.1));
        tt.setOnShown(s -> {
            //Get button current bounds on computer screen
            Bounds bounds = button.localToScreen(button.getBoundsInLocal());
            button.getTooltip().setX(bounds.getMaxX() - 5);
            button.getTooltip().setY(bounds.getCenterY() - tt.getHeight() / 2);
        });
        tt.getStyleClass().add("navbutton-tooltip");
    }

    private void OnButtonPress(Button button) {
        System.out.println("press: " + button.getText());
        if (focused_button == button) {
            return;
        }

        if (focused_button != null) {
            focused_button.getStyleClass().remove("focused");
            focused_button.getGraphic().setEffect(colorAdjust);
        }
        focused_button = button;
        focused_button.getStyleClass().add("focused");

        if (button == home_button)
            mainPane.setCenter(home_tab);
        else if (button == main_dictionary_button)
            mainPane.setCenter(main_dictionary_tab);
        else if (button == google_translate_button)
            mainPane.setCenter(google_translator_tab);
        else if (button == quizz) {
            mainPane.setCenter(quizz_tab);
            quizz_ctrl.showStartGame();
        }
        else if (button == wordle_button) {
            mainPane.setCenter(wordle_tab);
            wordleTab_ctrl.gridRequestFocus();
            wordleTab_ctrl.showStartGame();
        } else if (button == ctw_button) {
            mainPane.setCenter(ctw_tab);
            ctwTab_ctrl.gridRequestFocus();
            ctwTab_ctrl.showStartGame();
        }
    }

}
