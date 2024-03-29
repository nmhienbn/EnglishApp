package controllers.Panel;

import javaGif.AnimatedGif;
import javafx.animation.Animation;
import javafx.scene.control.Button;

import java.util.Objects;

public class Logo {
    void init(Button logo) {
        AnimatedGif ani = new AnimatedGif(Objects.requireNonNull(getClass().getResource("/front_end/graphic/icons/mainPanel/logo.gif")).
                toExternalForm(), 5000);
        logo.setGraphic(ani.getView());
        ani.setCycleCount(Animation.INDEFINITE);
        ani.setAutoReverse(true);
        ani.play();
    }
}
