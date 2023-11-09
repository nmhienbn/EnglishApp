package views.wordle;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.kordamp.bootstrapfx.BootstrapFX;

// https://stackoverflow.com/questions/28183667/how-i-can-stop-an-animated-gif-in-javafx

public final class GameAnimations {

    public static void showNotFoundWord(Stage ownerStage) {
        Stage stage = new Stage();
        stage.initOwner(ownerStage);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);

        Text text = new Text("Not in word list");
        text.setFill(Color.WHITE);
        text.getStyleClass().setAll("h4");

        BorderPane root = new BorderPane();
        root.setCenter(text);
        root.setStyle("-fx-background-radius: 5; " +
                "-fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 10;");
        root.setOpacity(0);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        stage.setScene(scene);
        stage.show();

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(750), root);
        fadeTransition.setFromValue(0.75);
        fadeTransition.setToValue(1);
        fadeTransition.setOnFinished(e -> stage.close());
        fadeTransition.play();
    }

    public static ScaleTransition scaleTrans(Label label, double fromScale, double toScale) {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), label);
        scaleTransition.fromXProperty().setValue(fromScale);
        scaleTransition.toXProperty().setValue(toScale);
        scaleTransition.fromYProperty().setValue(fromScale);
        scaleTransition.toYProperty().setValue(toScale);
        return scaleTransition;
    }

    public static FadeTransition fadeTrans(Label label, double fromFade, double toFade) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(150), label);
        fadeTransition.setFromValue(fromFade);
        fadeTransition.setToValue(toFade);
        return fadeTransition;
    }

    public static RotateTransition rotateTrans(Node node, double fromAngle, double toAngle) {
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1500), node);
        rotateTransition.setFromAngle(fromAngle);
        rotateTransition.setToAngle(toAngle);
        return rotateTransition;
    }
}
