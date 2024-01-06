package views.controllers.GoogleTab;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class AutoTranslate {
    GoogleTab_ctrl googleTabCtrl;

    public AutoTranslate(GoogleTab_ctrl GoogleTabCtrl) {
        this.googleTabCtrl = GoogleTabCtrl;
    }

    void init() {
        var translateTimeline = new Timeline(new KeyFrame(
                Duration.seconds(1), event -> new Translate(googleTabCtrl).doTranslate()));
        translateTimeline.setCycleCount(1);

        googleTabCtrl.textToTranslate.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    translateTimeline.stop();
                    translateTimeline.playFromStart();
                });
    }
}
