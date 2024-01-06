package views.controllers.GoogleTab;

import controllers.GoogleTranslate;
import javaGif.AnimatedGif;
import javafx.animation.Animation;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import views.DictFacade.DictFacade;

public class ButtonSpeak {
    void init(Button speakButton, TextArea inputText, String lang) {
        AnimatedGif animation = new AnimatedGif(
                getClass().getResource("/front_end/graphic/icons/speak.gif").
                        toExternalForm(), 500);
        animation.setCycleCount(Animation.INDEFINITE);
        speakButton.setGraphic(animation.getView());
        speakButton.setOnAction(e -> {
            if (speakButton.isDisable()) return;
            GoogleTranslate.ani = animation;
            speakButton.setDisable(true);
            DictFacade.Dict.speak(inputText.getText(), lang);
            speakButton.setDisable(false);
        });
    }
}
