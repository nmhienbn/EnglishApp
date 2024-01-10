package controllers.DictTab;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;
import models.DictFacade;

public class ButtonResetDictionary {
    MainDictionaryTab_ctrl dictCtrl;

    public ButtonResetDictionary(MainDictionaryTab_ctrl dictCtrl) {
        this.dictCtrl = dictCtrl;
    }

    void init() {
        dictCtrl.reset_dictionary_button.setOnAction(e -> reset_dictionary());
    }

    void reset_dictionary() {
        alert_confirm_reset();
    }

    void alert_confirm_reset() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UNDECORATED);
        alert.getDialogPane().setStyle(
                "-fx-border-color: black;"
                        + "-fx-border-width: 2px;"
        );
        alert.getDialogPane().getStyleClass().add("alert-dialog");
        //alert.setTitle("Remove word ?");
        alert.setHeaderText("Bạn có chắc muốn khởi tạo lại từ điển không ?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        alert.showAndWait().ifPresent(type -> {
            if (type == buttonTypeYes) {
                DictFacade.resetDictionaryData();
                new UpdateWordList().update_wordlist(dictCtrl);
                new WordInfoArea(dictCtrl).update_word_info_area(null, null, null);
            }
        });
    }
}
