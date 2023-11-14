package views.controllers;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import views.TestAPI;

public class mainDictionaryTab_ctrl {

    final int MAX_WORD_FIND = 50;

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
        remove_word_button.setOnAction(e -> {
            TestAPI.testRemoveWord("test add word");
        });
    }

    private void update_wordlist() {
        word_list_box.getItems().clear();
        if (!search_box.getText().isEmpty()) {
            ArrayList<String> wordlist = TestAPI.getword(search_box.getText());
            int count = 0;
            for (String word : wordlist) {
                /*
                Button word_button = new Button();
                word_button.prefWidthProperty().bind(word_list_box.widthProperty());
                word_button.maxWidthProperty().bind(word_list_box.widthProperty());
                word_button.setMinHeight(50);
                word_button.setMaxHeight(50);
                word_button.setText(word);
                word_button.setOnAction(e -> {
                    on_choose_word(word);
                });

                word_list_box.getItems().add(word_button);*/
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
        //System.out.println("choose word: " + word);
        wifa_meaning.setText(TestAPI.getWordMeaning(word));
        //System.out.println(TestAPI.getWordMeaning(word));
        wifa_word.setText(word);
    }

}
