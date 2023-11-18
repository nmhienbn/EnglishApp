package views.controllers.games_ctrl;

import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import views.animations.GameAnimations;
import views.games.GameNotification;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Stream;

public abstract class Game_ctrl {
    public BorderPane notificationPane;
    public Button helpButton;
    public Button restartButton;
    public Button exitButton;
    public Region dimSc;

    abstract public void restart();
    abstract public void showHelp();
    abstract public void exitGame();

    protected void initWords(String path, ArrayList<String> words) {
        InputStream winning_words = getClass().getResourceAsStream(path);
        assert winning_words != null
                : "Could not find " + path + " file in resources folder.";
        Stream<String> winning_words_lines = new BufferedReader(new InputStreamReader(winning_words)).lines();
        winning_words_lines.forEach(words::add);
    }


    protected void setTooltip(Node node, String text) {
        Tooltip tt = new Tooltip(text);
        Tooltip.install(node, tt);
        tt.setShowDelay(new Duration(.1));
        tt.setOnShown(s -> {
            //Get img current bounds on computer screen
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            tt.setX(bounds.getMaxX() - tt.getWidth() / 2);
            tt.setY(bounds.getMaxY() + 5);
        });
//        node.setOnMouseEntered(e -> {
//            node.setStyle("-fx-background-color: #ced4da");
//        });
//        node.setOnMouseExited(e -> {
//            node.setStyle("-fx-background-color: transparent");
//        });
    }

    public abstract void showStartGame();

    public static boolean inHierarchy(Node node, Node potentialHierarchyElement) {
        if (potentialHierarchyElement == null) {
            return false;
        }
        while (node != null) {
            if (node == potentialHierarchyElement) {
                return false;
            }
            node = node.getParent();
        }
        return true;
    }
}
