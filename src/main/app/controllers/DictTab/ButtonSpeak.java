package controllers.DictTab;

import models.functions.GoogleTranslate;
import javaGif.AnimatedGif;
import javafx.animation.Animation;
import models.DictFacade;

public class ButtonSpeak {
    MainDictionaryTab_ctrl dictCtrl;

    public ButtonSpeak(MainDictionaryTab_ctrl dictCtrl) {
        this.dictCtrl = dictCtrl;
    }
    void init() {
        var speak_button = dictCtrl.speak_button;
        var wifa_word = dictCtrl.wifa_word;
        
        speak_button.getStyleClass().add("function-button");
        AnimatedGif ani = new AnimatedGif(
                getClass().getResource("/front_end/graphic/icons/speak.gif").
                        toExternalForm(), 500);
        ani.setCycleCount(Animation.INDEFINITE);
        speak_button.setGraphic(ani.getView());
        speak_button.setOnAction(e -> {
            speak_button.setDisable(true);
            GoogleTranslate.ani = ani;
            DictFacade.Dict.speak(wifa_word.getText(), "en");
            speak_button.setDisable(false);
        });
    }
}
