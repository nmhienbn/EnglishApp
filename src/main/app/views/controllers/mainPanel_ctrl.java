package views.controllers;

import java.io.*;

import javafx.geometry.Bounds;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import views.animations.AnimatedGif;
import views.File_loader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class mainPanel_ctrl {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Button home_button;
    @FXML
    private Button main_dictionary_button;
    @FXML
    private Button user_dictionary_button;
    @FXML
    private Button google_translate_button;
    @FXML
    private Button wordle_button;

    private Button focused_button = null;

    Parent home_tab;
    Parent main_dictionary_tab;
    Parent user_dictionary_tab;
    Parent google_translator_tab;
    Parent wordle_tab;

    WordleTab_ctrl wordleTab_ctrl;

    public void setButton(Button button, String filename, double durationMs) {
        AnimatedGif ani = new AnimatedGif(getClass().getResource(filename).toExternalForm(), durationMs);
        button.setGraphic(ani.getView());
        button.setOnMousePressed(e -> {
            ani.playOnce();
        });
    }


    @FXML
    void initialize() throws IOException {

        home_button.setTooltip(new Tooltip("Home"));
        main_dictionary_button.setTooltip(new Tooltip("Main dictionary"));
        user_dictionary_button.setTooltip(new Tooltip("User's dictionary"));
        google_translate_button.setTooltip(new Tooltip("Google translator"));
        wordle_button.setTooltip(new Tooltip("Wordle game"));

        config_nav_button(home_button);
        config_nav_button(main_dictionary_button);
        config_nav_button(user_dictionary_button);
        config_nav_button(google_translate_button);
        config_nav_button(wordle_button);

        setButton(home_button, "/front_end/graphic/icons/home.gif", 1000);
        setButton(google_translate_button, "/front_end/graphic/icons/google_translate.gif", 1000);
        setButton(wordle_button, "/front_end/graphic/icons/wordle.gif", 1000);
        setButton(main_dictionary_button, "/front_end/graphic/icons/book.gif", 1000);
        setButton(user_dictionary_button, "/front_end/graphic/icons/book.gif", 1000);

        home_tab = File_loader.getInstance().fxml_homeTab();
        main_dictionary_tab = File_loader.getInstance().fxml_mainDictionaryTab();
        google_translator_tab = File_loader.getInstance().fxml_google_translate_Tab();
        wordleTab_ctrl = new WordleTab_ctrl();
        wordle_tab = File_loader.getInstance().fxml_wordle_Tab(wordleTab_ctrl);

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

        if (focused_button != null) focused_button.getStyleClass().remove("focused");
        focused_button = button;
        focused_button.getStyleClass().add("focused");

        if (button == home_button)
            mainPane.setCenter(home_tab);
        if (button == main_dictionary_button)
            mainPane.setCenter(main_dictionary_tab);
        if (button == user_dictionary_button)
            mainPane.setCenter(user_dictionary_tab);
        if (button == google_translate_button)
            mainPane.setCenter(google_translator_tab);
        if (button == wordle_button) {
            mainPane.setCenter(wordle_tab);
            wordleTab_ctrl.gridRequestFocus();
        }
    }

}
