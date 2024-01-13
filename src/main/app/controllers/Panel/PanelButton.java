package controllers.Panel;

import javaGif.AnimatedGif;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Objects;

public class PanelButton {
    MainPanel_ctrl mainPanelCtrl;

    static final ColorAdjust colorAdjust;

    static {
        colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1);
    }

    public PanelButton(MainPanel_ctrl mainPanelCtrl) {
        this.mainPanelCtrl = mainPanelCtrl;
    }

    void setButton(Button button, String filename, double durationMs) {
        AnimatedGif animation = new AnimatedGif(Objects.requireNonNull(getClass().getResource(filename)).
                toExternalForm(), durationMs);
        animation.getView().setEffect(colorAdjust);
        button.setGraphic(animation.getView());
        button.setOnMousePressed(e -> {
            animation.setCycleCount(1);
            animation.play();
        });
    }

    void setNavButton(Button button, String text) {
        Tooltip tt = new Tooltip(text);
        button.setOnAction(e -> {
            mainPanelCtrl.onButtonPress(button);
        });
        button.setTooltip(tt);
        tt.setShowDelay(new Duration(.1));
        tt.setOnShown(s -> {
            //Get button current bounds on computer screen
            Bounds bounds = button.localToScreen(button.getBoundsInLocal());
            tt.setX(bounds.getMaxX() - 5);
            tt.setY(bounds.getCenterY() - tt.getHeight() / 2);
        });
        tt.getStyleClass().add("navbutton-tooltip");
    }

    void init(Button button, String filename, double durationMs, String tooltipText) {
        setButton(button, filename, durationMs);
        setNavButton(button, tooltipText);
    }
}
