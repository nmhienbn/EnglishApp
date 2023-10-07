package views.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

public class mainDictionaryTab_ctrl {

    @FXML
    private Button search_button;

    @FXML
    private TextField search_box;

    @FXML
    private VBox word_list_box;

    @FXML
    void initialize() {
        assert search_button != null
                : "fx:id=\"seacrh_button\" was not injected: check your FXML file 'main_dictionary.fxml'.";
        assert search_box != null
                : "fx:id=\"search_box\" was not injected: check your FXML file 'main_dictionary.fxml'.";
        assert word_list_box != null
                : "fx:id=\"word_list_box\" was not injected: check your FXML file 'main_dictionary.fxml'.";

        search_button.setId("search_button");
        search_button.setOnAction(e -> {
            submit_search();
        });
        search_box.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER)
                submit_search();
        });
        search_box.setOnKeyTyped(e -> {
            if (e.getCode() != KeyCode.ENTER)
                System.out.println("current word: " + search_box.getText());
        });
    }

    private void submit_search() {
        System.out.println("search: " + search_box.getText());
    }

}
