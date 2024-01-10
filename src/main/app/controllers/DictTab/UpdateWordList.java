package controllers.DictTab;

import javafx.scene.control.*;
import models.DictFacade;

import java.util.ArrayList;

public class UpdateWordList {
    void update_wordlist(MainDictionaryTab_ctrl dictCtrl) {
        final int MAX_WORD_FIND = 100;
        TextField search_box = dictCtrl.search_box;
        ListView<String> word_list_box = dictCtrl.word_list_box;
        ToggleGroup SHF_group = dictCtrl.SHF_group;
        ToggleButton search_button = dictCtrl.search_button;
        ToggleButton favorite_button = dictCtrl.favorite_button;
        ToggleButton history_button = dictCtrl.history_button;

        word_list_box.getItems().clear();
        ArrayList<String> wordlist = new ArrayList<>();

        if (!search_box.getText().isEmpty())
            wordlist = DictFacade.Dict.getWordsWithPrefix(search_box.getText());
        int count = 0;

        if (SHF_group.getSelectedToggle() == search_button) {
            for (String word : wordlist) {
                word_list_box.getItems().add(word);
                count++;
                if (count >= MAX_WORD_FIND) break;
            }
        } else if (SHF_group.getSelectedToggle() == favorite_button) {
            for (String word : wordlist) {
                if (DictFacade.Favourite.check(word)) {
                    word_list_box.getItems().add(word);
                    count++;
                    if (count >= MAX_WORD_FIND) break;
                }
            }
        } else if (SHF_group.getSelectedToggle() == history_button) {
            for (String word : DictFacade.History.toIterable()) {
                if (word.startsWith(search_box.getText())) {
                    word_list_box.getItems().add(word);
                    count++;
                    if (count >= MAX_WORD_FIND) break;
                }
            }
        }
    }
}
