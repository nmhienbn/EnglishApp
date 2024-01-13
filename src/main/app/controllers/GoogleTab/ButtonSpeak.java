package controllers.GoogleTab;

import javaGif.AnimatedGif;
import javafx.animation.Animation;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import models.facades.WordListFacade;
import models.functions.GoogleTranslate;

import java.util.Objects;

public class ButtonSpeak {
    void init(Button speakButton, TextArea inputText, String lang) {
        AnimatedGif animation = new AnimatedGif(
                Objects.requireNonNull(getClass().getResource("/front_end/graphic/icons/speak.gif")).
                        toExternalForm(), 500);
        animation.setCycleCount(Animation.INDEFINITE);
        speakButton.setGraphic(animation.getView());
        speakButton.setOnAction(e -> {
            if (speakButton.isDisable()) return;
            GoogleTranslate.ani = animation;
            speakButton.setDisable(true);
            WordListFacade.speak(inputText.getText(), lang);
            speakButton.setDisable(false);
        });
    }
}
