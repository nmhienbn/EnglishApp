package controllers.DictTab;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;
import models.facades.FavouriteFacade;
import models.facades.WordListFacade;

public class ButtonRemoveWord {
    final MainDictionaryTab_ctrl dictCtrl;

    public ButtonRemoveWord(MainDictionaryTab_ctrl dictCtrl) {
        this.dictCtrl = dictCtrl;
    }

    void init() {
        dictCtrl.remove_word_button.setOnAction(e -> try_remove_word());
    }

    void try_remove_word() {
        var wifa_word = dictCtrl.wifa_word;
        
        if (wifa_word.getText() == null && wifa_word.getText().isEmpty()) return;
        if (!WordListFacade.contains(wifa_word.getText())) {
            DictPopup.popup_word_not_exist(wifa_word.getText()).showPopup(dictCtrl);
            return;
        }
        alert_confirm_remove_word(wifa_word.getText());
    }

    void alert_confirm_remove_word(String word) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.getDialogPane().setStyle(
                "-fx-border-color: black;"
                        + "-fx-border-width: 2px;"
        );
        alert.getDialogPane().getStyleClass().add("alert-dialog");
        //alert.setTitle("Remove word ?");
        alert.setHeaderText("Bạn có chắc muốn xóa từ này không ?");
        alert.setContentText(word);

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(type -> {
            if (type == buttonTypeYes) {
                System.out.println("remove word: " + word);
                WordListFacade.remove(word);
                FavouriteFacade.remove(word);
                new UpdateWordList().update_wordlist(dictCtrl);
                DictPopup.popup_word_removed(word).showPopup(dictCtrl);
               new WordInfoArea(dictCtrl).update_word_info_area(null, null, null);
            }
        });
    }
}
