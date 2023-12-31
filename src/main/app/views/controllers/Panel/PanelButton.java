package views.controllers.Panel;

import javaGif.AnimatedGif;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.util.Duration;

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
        AnimatedGif animation = new AnimatedGif(getClass().getResource(filename).toExternalForm(), durationMs);
        animation.setCycleCount(1);
        button.setGraphic(animation.getView());
        animation.getView().setEffect(colorAdjust);
        button.setOnMousePressed(e -> {
            animation.getView().setEffect(null); // Remove the effect
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
