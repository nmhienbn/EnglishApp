package controllers.DictTab;

import javafx.scene.control.ListView;
import models.facades.FavouriteFacade;
import models.facades.HistoryFacade;
import models.facades.WordListFacade;

public class SearchBox {
    final MainDictionaryTab_ctrl dictCtrl;

    public SearchBox(MainDictionaryTab_ctrl dictCtrl) {
        this.dictCtrl = dictCtrl;
    }

    void searchBoxAction() {
        ListView<String> word_list_box = dictCtrl.word_list_box;
        if (!word_list_box.getItems().isEmpty()) {
            word_list_box.getSelectionModel().select(0);
            word_list_box.requestFocus();
            word_list_box.getFocusModel().focus(0);
            word_list_box.scrollTo(0);
            on_choose_word(word_list_box.getItems().get(0));
        }
    }

    void on_choose_word(String word) {
        var word_list_box = dictCtrl.word_list_box;
        var favorite_toggle_button = dictCtrl.favorite_toggle_button;
        var SHF_group = dictCtrl.SHF_group;
        var history_button = dictCtrl.history_button;
        var search_box = dictCtrl.search_box;

        if (word == null) return;
        //System.out.println("choose word: " + word);
        favorite_toggle_button.setSelected(FavouriteFacade.check(word));

        new WordInfoArea(dictCtrl).update_word_info_area(word, WordListFacade.getMeaning(word), word_list_box);

        if (SHF_group.getSelectedToggle() != history_button)
            HistoryFacade.add(word);
    }
}
