package controllers.DictTab;

public class ButtonSaveEdit {
    MainDictionaryTab_ctrl dictCtrl;

    public ButtonSaveEdit(MainDictionaryTab_ctrl dictCtrl) {
        this.dictCtrl = dictCtrl;
    }
    void init() {
        dictCtrl.save_edit_button.setDisable(true);
    }
}
