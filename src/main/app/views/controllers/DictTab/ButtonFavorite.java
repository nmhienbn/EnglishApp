package views.controllers.DictTab;

import views.TestAPI;

public class ButtonFavorite {
    MainDictionaryTab_ctrl dictCtrl;

    public ButtonFavorite(MainDictionaryTab_ctrl dictCtrl) {
        this.dictCtrl = dictCtrl;
    }

    void init() {
        dictCtrl.favorite_toggle_button.setOnAction(e -> toggle_favorite());
    }

    void toggle_favorite() {
        var wifa_word = dictCtrl.wifa_word;
        var favorite_toggle_button = dictCtrl.favorite_toggle_button;
        
        System.out.println("action toggle favorite called");
        String word = wifa_word.getText();
        if (favorite_toggle_button.isSelected()) {
            if (word == null || word.isEmpty() || !TestAPI.dictionaryContainWord(word)) {
                favorite_toggle_button.setSelected(false);
                return;
            }
            TestAPI.addFavoriteWord(word);
        } else {
            TestAPI.removeFavoriteWord(word);
        }
    }
}
