package views.controllers.DictTab;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SearchArea {
    void init(MainDictionaryTab_ctrl dictCtrl) {
        TextField search_box = dictCtrl.search_box;
        ListView<String> word_list_box = dictCtrl.word_list_box;
        search_box.setOnAction(e -> {
            new SearchBox(dictCtrl).searchBoxAction();
        });
        search_box.setOnKeyTyped(e -> {
            System.out.println("current word: " + search_box.getText());
            new UpdateWordList().update_wordlist(dictCtrl);
        });

        word_list_box.setOnMouseClicked(e -> {
            new SearchBox(dictCtrl).on_choose_word(word_list_box.getSelectionModel().getSelectedItem());
        });
        word_list_box.getFocusModel().focusedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (word_list_box.isFocused() && newVal != null) {
                new SearchBox(dictCtrl).on_choose_word(newVal);
            }
        });
    }


}
