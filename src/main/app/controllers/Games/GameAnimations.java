package controllers.Games;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

// https://stackoverflow.com/questions/28183667/how-i-can-stop-an-animated-gif-in-javafx

public final class GameAnimations {

    public static ScaleTransition scaleTrans(Node node, double fromScale, double toScale, double duration) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(duration), node);
        scaleTransition.fromXProperty().setValue(fromScale);
        scaleTransition.toXProperty().setValue(toScale);
        scaleTransition.fromYProperty().setValue(fromScale);
        scaleTransition.toYProperty().setValue(toScale);
        return scaleTransition;
    }

    public static FadeTransition fadeTrans(Node node, double fromFade, double toFade, double duration) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration), node);
        fadeTransition.setFromValue(fromFade);
        fadeTransition.setToValue(toFade);
        return fadeTransition;
    }

    public static RotateTransition rotateTrans(Node node, double fromAngle, double toAngle) {
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1000), node);
        rotateTransition.setFromAngle(fromAngle);
        rotateTransition.setToAngle(toAngle);
        return rotateTransition;
    }
}
