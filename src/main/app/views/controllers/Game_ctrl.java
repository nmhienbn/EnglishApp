package views.controllers;

import javafx.animation.SequentialTransition;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import views.animations.GameAnimations;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Stream;

public abstract class Game_ctrl {
    abstract public void restart();

    protected void initWords(String path, ArrayList<String> words) {
        InputStream winning_words = getClass().getResourceAsStream(path);
        assert winning_words != null
                : "Could not find " + path + " file in resources folder.";
        Stream<String> winning_words_lines = new BufferedReader(new InputStreamReader(winning_words)).lines();
        winning_words_lines.forEach(words::add);
    }


    protected void config_nav_button(Button button) {
        Tooltip tt = button.getTooltip();
        tt.setShowDelay(new Duration(.1));
        tt.setOnShown(s -> {
            //Get button current bounds on computer screen
            Bounds bounds = button.localToScreen(button.getBoundsInLocal());
            button.getTooltip().setX(bounds.getMaxX() - tt.getWidth() / 2);
            button.getTooltip().setY(bounds.getMaxY() + 5);
        });
        tt.getStyleClass().add("navbutton-tooltip");
    }

    public abstract void showStartGame();
}
