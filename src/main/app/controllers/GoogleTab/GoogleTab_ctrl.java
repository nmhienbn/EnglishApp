package controllers.GoogleTab;

import controllers.AppControllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class GoogleTab_ctrl extends AppControllers {
    @FXML
    Label lang_label1;

    @FXML
    Label lang_label2;

    @FXML
    Button speak_button1;

    @FXML
    Button speak_button2;

    @FXML
    Button swap_lang_button;

    @FXML
    TextArea textToTranslate;

    @FXML
    TextArea translatedText;

    String lang1 = "en";
    String lang2 = "vi";
    long prevTime = 0;
    boolean isTranslated = false;

    @FXML
    protected void initialize() {
        //assert textToTranslate != null : "fx:id=\"textToTranslate\" was not injected: check your FXML file 'google_tab.fxml'.";
        //assert translatedText != null : "fx:id=\"translatedText\" was not injected: check your FXML file 'google_tab.fxml'.";
        //assert translate_button != null : "fx:id=\"translate_button\" was not injected: check your FXML file 'google_tab.fxml'.";

        translatedText.setEditable(false);
        setLang_label();
        new AutoTranslate(this).init();
        new ButtonSwapLang(this).init();
        new ButtonSpeak().init(speak_button1, textToTranslate, lang1);
        new ButtonSpeak().init(speak_button2, translatedText, lang2);
    }

    void setLang_label() {
        if (lang1.equals("vi")) lang_label1.setText("Tiếng Việt");
        if (lang1.equals("en")) lang_label1.setText("English");
        if (lang2.equals("vi")) lang_label2.setText("Tiếng Việt");
        if (lang2.equals("en")) lang_label2.setText("English");
    }
}