package views.animations;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.kordamp.bootstrapfx.BootstrapFX;

// https://stackoverflow.com/questions/28183667/how-i-can-stop-an-animated-gif-in-javafx

public final class GameAnimations {

    public static ScaleTransition scaleTrans(Parent label, double fromScale, double toScale) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), label);
        scaleTransition.fromXProperty().setValue(fromScale);
        scaleTransition.toXProperty().setValue(toScale);
        scaleTransition.fromYProperty().setValue(fromScale);
        scaleTransition.toYProperty().setValue(toScale);
        return scaleTransition;
    }

    public static FadeTransition fadeTrans(Parent label, double fromFade, double toFade, double duration) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration), label);
        fadeTransition.setFromValue(fromFade);
        fadeTransition.setToValue(toFade);
        return fadeTransition;
    }

    public static RotateTransition rotateTrans(Parent node, double fromAngle, double toAngle) {
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1000), node);
        rotateTransition.setFromAngle(fromAngle);
        rotateTransition.setToAngle(toAngle);
        return rotateTransition;
    }
}
