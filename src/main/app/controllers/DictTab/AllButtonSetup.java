package controllers.DictTab;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class AllButtonSetup {
    void init_function_button(MainDictionaryTab_ctrl dictCtrl) {
        new ButtonSpeak(dictCtrl).init();
        new ButtonEditWord(dictCtrl).init();
        new ButtonSaveEdit(dictCtrl).init();
        new ButtonRemoveWord(dictCtrl).init();
        new ButtonAddWord(dictCtrl).init();
        new ButtonFavorite(dictCtrl).init();
        new ButtonResetDictionary(dictCtrl).init();
    }
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
