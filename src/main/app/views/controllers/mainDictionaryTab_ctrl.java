package views.controllers;

import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import views.TestAPI;

public class mainDictionaryTab_ctrl {

    final int MAX_WORD_FIND = 100;

    @FXML
    private Button search_button;

    @FXML
    private TextField search_box;

    @FXML
    private Button speak_button;

    @FXML
    private ListView<String> word_list_box;

    @FXML
    private Button add_word_button;

    @FXML
    private Button remove_word_button;

    @FXML
    private Button edit_word_button;

    //? wifa = word infomation area
    @FXML
    private TextArea wifa_meaning;
    @FXML
    private Label wifa_word;

    boolean is_editing = false;

    @FXML
    void initialize() {
        assert search_button != null
                : "fx:id=\"search_button\" was not injected: check your FXML file 'main_dictionary.fxml'.";
        assert search_box != null
                : "fx:id=\"search_box\" was not injected: check your FXML file 'main_dictionary.fxml'.";
        assert word_list_box != null
                : "fx:id=\"word_list_box\" was not injected: check your FXML file 'main_dictionary.fxml'.";

        init_search_area();
        init_word_information_area();
        init_fuction_button();

    }

    private void init_search_area() {
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
            update_wordlist();
        });

        word_list_box.setOnMouseClicked(e -> {
            on_choose_word(word_list_box.getSelectionModel().getSelectedItem());
        });
    }

    private void init_word_information_area() {
        wifa_meaning.setEditable(false);
    }

    private void init_fuction_button() {
        speak_button.setOnAction(e -> {
            speak_button.setDisable(true);
            TestAPI.SpeakAPI(wifa_word.getText(), "en");
            speak_button.setDisable(false);
        });


        add_word_button.setOnAction(e -> {
            TestAPI.testAddWord("test add word", "test meaning");
        });

        edit_word_button.setOnAction(e -> {
            if (!is_editing) try_start_edit_word();
            else finish_edit_word();
        });

        wifa_meaning.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV) { // focus lost
                //wifa_meaning.requestFocus();
                if (is_editing) finish_edit_word();
            }
        });


        remove_word_button.setOnAction(e -> {
            try_remove_word();
        });
    }

    private void popup_word_not_exist(String word) {
        Popup popup = new Popup();
        popup.setAutoHide(true);
        Label label = new Label("Error: Word does not exists");
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setFont(new Font(25));
        label.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, .3), null, null)));
        label.setPrefSize(400, 100);

        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(event -> popup.hide());
        delay.play();

        popup.getContent().add(label);
        popup.show(wifa_word.getScene().getWindow());
        System.out.println("Word does not exists, WORD: \"" + word + "\"");
    }

    private void try_start_edit_word() {
        String word = wifa_word.getText();
        if (word == null || word.isEmpty()) return;
        if (!TestAPI.dictionaryContainWord(word)) {
            popup_word_not_exist(word);
            return;
        }
        System.out.println("start edit word: " + word);
        is_editing = true;
        wifa_meaning.setEditable(true);
        wifa_meaning.requestFocus();
    }

    private void finish_edit_word() {
        System.out.println("finish edit word: " + wifa_word.getText());
        is_editing = false;
        wifa_meaning.setEditable(false);
        String meaning = wifa_meaning.getText();
        TestAPI.testEditWord(wifa_word.getText(), meaning);
    }

    private void try_remove_word() {
        if (wifa_word.getText() == null && wifa_word.getText().isEmpty()) return;
        if (!TestAPI.dictionaryContainWord(wifa_word.getText())) {
            popup_word_not_exist(wifa_word.getText());
            return;
        }
        System.out.println("remove word: " + wifa_word.getText());
        TestAPI.testRemoveWord(wifa_word.getText());
        update_wordlist();
    }

    private void update_wordlist() {
        word_list_box.getItems().clear();
        if (!search_box.getText().isEmpty()) {
            ArrayList<String> wordlist = TestAPI.getword(search_box.getText());
            int count = 0;
            for (String word : wordlist) {
                word_list_box.getItems().add(word);

                count++;
                if (count >= MAX_WORD_FIND) break;
            }
        }
    }

    private void submit_search() {
        System.out.println("search: " + search_box.getText());
    }

    private void on_choose_word(String word) {
        if (word == null) return;
        //System.out.println("choose word: " + word);
        wifa_meaning.setText(TestAPI.getWordMeaning(word));
        wifa_word.setText(word);
    }
}
