package views.controllers.Games;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class Tooltips {
    void setup(Game_ctrl gameCtrl) {
        setTooltip(gameCtrl.helpButton, "Instructions");
        setTooltip(gameCtrl.restartButton, "Restart");
        setTooltip(gameCtrl.exitButton, "Exit");
    }

    private void setTooltip(Node node, String text) {
        Tooltip tt = new Tooltip(text);
        Tooltip.install(node, tt);
        tt.setShowDelay(new Duration(.1));
        tt.setOnShown(s -> {
            //Get img current bounds on computer screen
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            tt.setX(bounds.getMaxX() - tt.getWidth() / 2);
            tt.setY(bounds.getMaxY() + 5);
        });
    }
}
