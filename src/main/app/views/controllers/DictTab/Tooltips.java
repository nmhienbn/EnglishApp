package views.controllers.DictTab;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class Tooltips {
    void setup_all_tooltips(ButtonBase[] buttons) {
        for (ButtonBase button : buttons) {
            setup_tooltip(button.getTooltip(), button);
        }
    }

    void setup_tooltip(Tooltip tt, Node node) {
        if (node == null) {
            System.out.println("ERROR: tooltip owner node is null");
            return;
        }
        tt.setShowDelay(new Duration(.1));
        tt.setOnShown(s -> {
            //Get button current bounds on computer screen
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            tt.setX(bounds.getCenterX() - tt.getWidth() / 2);
            tt.setY(bounds.getMaxY() - 1);
        });
    }
}
