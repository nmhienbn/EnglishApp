package models.games.GameKeyBoard;

import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import controllers.Games.Game_ctrl;

public class MouseActionsOnKeyboard {
    Game_ctrl gameCtrl = null;

    public MouseActionsOnKeyboard(Game_ctrl gameCtrl) {
        this.gameCtrl = gameCtrl;
    }

    protected final void onMouseEntered(Label label) {
        if (gameCtrl.notificationPane.isVisible()) {
            return;
        }
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.4);
        label.setEffect(colorAdjust);
    }

    protected final void onMouseExited(Label label) {
        if (gameCtrl.notificationPane.isVisible()) {
            return;
        }
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0);
        label.setEffect(colorAdjust);
    }
}
