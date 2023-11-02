package views.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    }

    private void doTranslate() {
        String totrans = text1.getText();
        String transed = TestAPI.translateEV(totrans);

        text2.setText(transed);
    }

}
