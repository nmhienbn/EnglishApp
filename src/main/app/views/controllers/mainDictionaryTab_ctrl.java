package views.controllers;

import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private ToggleButton search_button;

    @FXML
    private ToggleButton favorite_button;

    @FXML
    private ToggleButton history_button;

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

    @FXML
    private Button save_edit_button;

    @FXML
    private ToggleButton favorite_toggle_button;

    //? wifa = word infomation area
    @FXML
    private TextArea wifa_meaning;
    @FXML
    private Label wifa_word;

    private boolean is_editing = false;

    private String current_word = null;
    private String current_meaning = null;

    //? search, history, favorite
    private ToggleGroup SHF_group = new ToggleGroup();


    @FXML
    void initialize() {
        init_search_area();
        init_word_information_area();
        init_fuction_button();
        init_toggle_button();
    }

    private void init_search_area() {
        search_box.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER)
                on_choose_word(word_list_box.getItems().get(0));
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

        edit_word_button.setOnAction(e -> {
            if (!is_editing) try_start_edit_word();
        });
        save_edit_button.setDisable(true);

        wifa_meaning.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV)  // focus lost
                if (is_editing) finish_edit_word();
        });

        remove_word_button.setOnAction(e -> try_remove_word());

        add_word_button.setOnAction(e -> try_add_word());

        favorite_toggle_button.setOnAction(e -> toggle_favorite());
    }

    private void init_toggle_button() {
        search_button.setToggleGroup(SHF_group);
        favorite_button.setToggleGroup(SHF_group);
        history_button.setToggleGroup(SHF_group);

        search_button.setSelected(true);
        SHF_group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> o, Toggle t1, Toggle t2) {
                if (t2 == null)
                    t1.setSelected(true);
                else if (t1 != null && t1 != t2) {
                    update_wordlist();

                    if (t2 != search_button) {
                        disallow_add_word();
                    } else {
                        allow_add_word();
                    }
                }
            }
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

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> popup.hide());
        delay.play();

        popup.getContent().add(label);
        popup.show(wifa_word.getScene().getWindow());
        System.out.println("Word does not exists, WORD: \"" + word + "\"");
    }

    private void popup_exist(String word) {
        Popup popup = new Popup();
        popup.setAutoHide(true);
        Label label = new Label("Error: Word exists\n\"" + word + "\"");
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setFont(new Font(25));
        label.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, .3), null, null)));
        label.setPrefSize(400, 100);

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> popup.hide());
        delay.play();

        popup.getContent().add(label);
        popup.show(wifa_word.getScene().getWindow());
        System.out.println("Word exists, WORD: \"" + word + "\"");
    }

    private void popup_word_updated(String word) {
        Popup popup = new Popup();
        popup.setAutoHide(true);
        Label label = new Label("Word's meaning updated\n\"" + word + "\"");
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setFont(new Font(25));
        label.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, .3), null, null)));
        label.setPrefSize(400, 100);

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> popup.hide());
        delay.play();

        popup.getContent().add(label);
        popup.show(wifa_word.getScene().getWindow());
    }

    private void popup_word_added(String word) {
        Popup popup = new Popup();
        popup.setAutoHide(true);
        Label label = new Label("Added word to dictionary\n\"" + word + "\"");
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setFont(new Font(25));
        label.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, .3), null, null)));
        label.setPrefSize(400, 100);

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> popup.hide());
        delay.play();

        popup.getContent().add(label);
        popup.show(wifa_word.getScene().getWindow());
    }

    private void try_start_edit_word() {
        String word = wifa_word.getText();
        if (word == null || word.isEmpty()) return;
        if (!TestAPI.dictionaryContainWord(word)) {
            popup_word_not_exist(word);
            return;
        }
        edit_word_button.setDisable(true);
        remove_word_button.setDisable(true);
        save_edit_button.setDisable(false);
        System.out.println("start edit word: " + word);
        is_editing = true;
        wifa_meaning.setEditable(true);
        wifa_meaning.requestFocus();
    }

    private void finish_edit_word() {
        edit_word_button.setDisable(false);
        remove_word_button.setDisable(false);
        save_edit_button.setDisable(true);
        System.out.println("finish edit word: " + wifa_word.getText());
        is_editing = false;
        wifa_meaning.setEditable(false);
        String meaning = wifa_meaning.getText();
        TestAPI.testEditWord(wifa_word.getText(), meaning);
        popup_word_updated(wifa_word.getText());
    }

    private void try_remove_word() {
        if (wifa_word.getText() == null && wifa_word.getText().isEmpty()) return;
        if (!TestAPI.dictionaryContainWord(wifa_word.getText())) {
            popup_word_not_exist(wifa_word.getText());
            return;
        }
        System.out.println("remove word: " + wifa_word.getText());
        TestAPI.testRemoveWord(wifa_word.getText());
        TestAPI.removeFavoriteWord(wifa_word.getText());
        update_wordlist();

    }

    private void allow_add_word() {
        add_word_button.setDisable(false);
    }

    private void disallow_add_word() {
        add_word_button.setDisable(true);
    }

    private void try_add_word() {
        String word = search_box.getText();
        if (word == null || word.isEmpty()) return;
        if (TestAPI.dictionaryContainWord(word)) {
            popup_exist(word);
            return;
        }
        TestAPI.testAddWord(word, "");
        popup_word_added(word);
        update_wordlist();
    }

    private void toggle_favorite() {
        System.out.println("action toggle favorite called");
        String word = wifa_word.getText();
        if (favorite_toggle_button.isSelected()) {
            if (word == null || word.isEmpty() || !TestAPI.dictionaryContainWord(word)) {
                favorite_toggle_button.setSelected(false);
                return;
            }
            TestAPI.addFavoriteWord(word);
        } else {
            TestAPI.removeFavoriteWord(word);
        }
    }

    private void update_wordlist() {
        word_list_box.getItems().clear();
        ArrayList<String> wordlist = new ArrayList<>();

        if (!search_box.getText().isEmpty())
            wordlist = TestAPI.getword(search_box.getText());
        int count = 0;

        if (SHF_group.getSelectedToggle() == search_button) {
            for (String word : wordlist) {
                word_list_box.getItems().add(word);
                count++;
                if (count >= MAX_WORD_FIND) break;
            }
        } else if (SHF_group.getSelectedToggle() == favorite_button) {
            for (String word : wordlist) {
                if (TestAPI.isFavoriteWord(word)) {
                    word_list_box.getItems().add(word);
                    count++;
                    if (count >= MAX_WORD_FIND) break;
                }
            }
        } else if (SHF_group.getSelectedToggle() == history_button) {
            for (String word : TestAPI.SearchHistory()) {
                if (word.startsWith(search_box.getText())) {
                    word_list_box.getItems().add(word);
                    count++;
                    if (count >= MAX_WORD_FIND) break;
                }
            }

        }
    }

    private void on_choose_word(String word) {
        if (word == null) return;
        //System.out.println("choose word: " + word);
        if (TestAPI.isFavoriteWord(word))
            favorite_toggle_button.setSelected(true);
        else
            favorite_toggle_button.setSelected(false);
        wifa_meaning.setText(TestAPI.getWordMeaning(word));
        wifa_word.setText(word);
        if (SHF_group.getSelectedToggle() != history_button)
            TestAPI.addSearchHistory(word);
    }
}
