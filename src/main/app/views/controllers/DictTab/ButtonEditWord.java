package views.controllers.DictTab;

import views.DictFacade.DictFacade;

public class ButtonEditWord {
    MainDictionaryTab_ctrl dictCtrl;

    public ButtonEditWord(MainDictionaryTab_ctrl dictCtrl) {
        this.dictCtrl = dictCtrl;
    }
    void init(){
        var edit_word_button = dictCtrl.edit_word_button;

        edit_word_button.setOnAction(e -> {
            if (!dictCtrl.isEditing()) try_start_edit_word();
        });
    }

    void try_start_edit_word() {
        var edit_word_button = dictCtrl.edit_word_button;
        var remove_word_button = dictCtrl.remove_word_button;
        var save_edit_button = dictCtrl.save_edit_button;
        var wifa_word = dictCtrl.wifa_word;
        var wifa_meaning = dictCtrl.wifa_meaning;
        var wifa_scrollpane = dictCtrl.wifa_scrollpane;
        var wifa_meaning_raw = dictCtrl.wifa_meaning_raw;
        
        String word = wifa_word.getText();
        if (word == null || word.isEmpty()) return;
        if (!DictFacade.Dict.contains(word)) {
            DictPopup.popup_word_not_exist(word).showPopup(dictCtrl);
            return;
        }
        edit_word_button.setDisable(true);
        remove_word_button.setDisable(true);
        save_edit_button.setDisable(false);
        System.out.println("start edit word: " + word);
        dictCtrl.setEditing(true);
        wifa_meaning.setVisible(false);
        wifa_meaning.setDisable(true);
        wifa_scrollpane.setDisable(true);
        wifa_meaning_raw.setDisable(false);
        wifa_meaning_raw.setVisible(true);
        wifa_meaning_raw.setEditable(true);
        wifa_meaning_raw.requestFocus();
    }
}
