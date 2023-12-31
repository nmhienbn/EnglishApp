package views.controllers.DictTab;

import views.DictFacade.DictFacade;

public class ButtonAddWord {
    MainDictionaryTab_ctrl dictCtrl;

    public ButtonAddWord(MainDictionaryTab_ctrl dictCtrl) {
        this.dictCtrl = dictCtrl;
    }

    void init() {
        dictCtrl.add_word_button.setOnAction(e -> try_add_word());
    }

    void try_add_word() {
        var search_box = dictCtrl.search_box;
        var wifa_word = dictCtrl.wifa_word;

        String word = search_box.getText();
        if (word == null || word.isEmpty()) return;
        if (DictFacade.Dict.contains(word)) {
            DictPopup.popup_exist(word).showPopup(dictCtrl);
            return;
        }

        boolean success = DictFacade.Dict.add(word, "");
        if (success) {
            DictPopup.popup_word_added(word).showPopup(dictCtrl);
            new UpdateWordList().update_wordlist(dictCtrl);
            new SearchBox(dictCtrl).on_choose_word(word);
        } else {
            DictPopup.popup_word_not_added(word).showPopup(dictCtrl);
        }
    }
}
