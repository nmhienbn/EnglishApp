package views.controllers;

import java.io.*;

import views.File_loader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    private Button focused_button = null;

    Parent home_tab;
    Parent main_dictionary_tab;
    Parent user_dictionary_tab;
    Parent goodle_translator_tab;

    @FXML
    void initialize() throws IOException {
        config_nav_button(home_button);
        config_nav_button(main_dictionary_button);
        config_nav_button(user_dictionary_button);
        config_nav_button(google_translate_button);

        home_tab = File_loader.getInstance().fxml_homeTab();
        main_dictionary_tab = File_loader.getInstance().fxml_mainDictionaryTab();

        goodle_translator_tab = File_loader.getInstance().fxml_google_translate_Tab();

        mainPane.setCenter(home_tab);

    }

    private void config_nav_button(Button button) {
        button.setOnAction(e -> {
            OnButtonPress(button);
        });
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
            mainPane.setCenter(goodle_translator_tab);
    }

}
