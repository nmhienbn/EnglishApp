package views.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import views.TestAPI;

public class GoogleTab_ctrl {

    @FXML
    private TextArea text1;

    @FXML
    private TextArea text2;

    @FXML
    private Button translate_button;

    @FXML
    private Button swap_lang_button;

    @FXML
    private Label test_label;

    private String lang1 = "en";
    private String lang2 = "vi";

    @FXML
    void initialize() {
        //assert text1 != null : "fx:id=\"text1\" was not injected: check your FXML file 'google_tab.fxml'.";
        //assert text2 != null : "fx:id=\"text2\" was not injected: check your FXML file 'google_tab.fxml'.";
        //assert translate_button != null : "fx:id=\"translate_button\" was not injected: check your FXML file 'google_tab.fxml'.";

        translate_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                doTranslate();
            }
        });

        text2.setEditable(false);

        test_label.setText(lang1 + "->" + lang2);
        swap_lang_button.setOnAction(e -> {
            String temp = lang1;
            lang1 = lang2;
            lang2 = temp;
            test_label.setText(lang1 + "->" + lang2);
        });
    }

    private void doTranslate() {
        String transed = TestAPI.TranslateAPI(text1.getText(), lang1, lang2);
        text2.setText(transed);
    }

}
