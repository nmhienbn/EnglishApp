package views.controllers.DictTab;

import views.TestAPI;

public class EditWord {
    MainDictionaryTab_ctrl dictCtrl;

    public EditWord(MainDictionaryTab_ctrl dictCtrl) {
        this.dictCtrl = dictCtrl;
    }

    void setup() {
        var wifa_meaning_raw = dictCtrl.wifa_meaning_raw;

        wifa_meaning_raw.focusedProperty().addListener((ov, oldV, newV) -> {
            if (!newV)  // focus lost
                if (dictCtrl.isEditing())
                    finish_edit_word();
        });
    }

    void finish_edit_word() {
        var wifa_word = dictCtrl.wifa_word;
        var wifa_meaning_raw = dictCtrl.wifa_meaning_raw;
        var wifa_meaning = dictCtrl.wifa_meaning;
        var wifa_scrollpane = dictCtrl.wifa_scrollpane;
        var edit_word_button = dictCtrl.edit_word_button;
        var remove_word_button = dictCtrl.remove_word_button;
        var save_edit_button = dictCtrl.save_edit_button;


        edit_word_button.setDisable(false);
        remove_word_button.setDisable(false);
        save_edit_button.setDisable(true);
        System.out.println("finish edit word: " + wifa_word.getText());
        dictCtrl.setEditing(false);
        wifa_meaning_raw.setEditable(false);
        wifa_meaning_raw.setVisible(false);
        wifa_meaning_raw.setDisable(true);
        wifa_meaning.setVisible(true);
        wifa_meaning.setDisable(false);
        wifa_scrollpane.setDisable(false);
        String meaning = wifa_meaning_raw.getText();
        TestAPI.testEditWord(wifa_word.getText(), meaning);
        new WordInfoArea(dictCtrl).update_word_info_area(wifa_word.getText(), meaning, null);
        DictPopup.popup_word_updated(wifa_word.getText()).showPopup(dictCtrl);
    }
}
