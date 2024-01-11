package controllers.DictTab;

import javafx.scene.control.*;
import models.DictFacade;

import java.util.ArrayList;

public class UpdateWordList {
    void update_wordlist(MainDictionaryTab_ctrl dictCtrl) {
        var MAX_WORD_FIND = 100;
        var search_box = dictCtrl.search_box;
        var result_list = dictCtrl.word_list_box.getItems();
        var SHF_group = dictCtrl.SHF_group;
        var search_button = dictCtrl.search_button;
        var favorite_button = dictCtrl.favorite_button;
        var history_button = dictCtrl.history_button;

        result_list.clear();
        ArrayList<String> wordlist = new ArrayList<>();

        if (!search_box.getText().isEmpty())
            wordlist = DictFacade.Dict.getWordsWithPrefix(search_box.getText());
        int count = 0;
        var selectedToggle = SHF_group.getSelectedToggle();

        if (selectedToggle == search_button) {
            for (String word : wordlist) {
                result_list.add(word);
                count++;
                if (count >= MAX_WORD_FIND) break;
            }
        } else if (selectedToggle == favorite_button) {
            for (String word : DictFacade.Favourite.getAll()) {
                result_list.add(word);
                count++;
                if (count >= MAX_WORD_FIND) break;
            }
        } else if (selectedToggle == history_button) {
            for (String word : DictFacade.History.toIterable()) {
                if (word.startsWith(search_box.getText())) {
                    result_list.add(word);
                    count++;
                    if (count >= MAX_WORD_FIND) break;
                }
            }
        }
    }
}
