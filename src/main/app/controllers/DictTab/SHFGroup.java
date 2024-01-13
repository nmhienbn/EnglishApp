package controllers.DictTab;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;

public class SHFGroup {
    final MainDictionaryTab_ctrl dictCtrl;

    public SHFGroup(MainDictionaryTab_ctrl dictCtrl) {
        this.dictCtrl = dictCtrl;
    }

    void setup() {
        var SHF_group = dictCtrl.SHF_group;
        var search_button = dictCtrl.search_button;
        var favorite_button = dictCtrl.favorite_button;
        var history_button = dictCtrl.history_button;
        var add_word_button = dictCtrl.add_word_button;
        var search_box = dictCtrl.search_box;
        var result_list = dictCtrl.word_list_box.getItems();

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
                    search_box.setText("");
                    new UpdateWordList().update_wordlist(dictCtrl);
                    if (t2 != search_button) {
                        disallow_add_word(add_word_button);
                    } else {
                        allow_add_word(add_word_button);
                    }
                }
            }
        });
    }

    void allow_add_word(Button add_word_button) {
        add_word_button.setDisable(false);
    }

    void disallow_add_word(Button add_word_button) {
        add_word_button.setDisable(true);
    }
}
