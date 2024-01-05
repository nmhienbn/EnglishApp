package views.games;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public abstract class Game {
    protected String winWord;

    /**
     * Creates the title for the game
     *
     * @param titleHBox the HBox that will contain the title
     */
    public abstract void createGameTitle(HBox titleHBox);

    /**
     * Gets a random word from the winningWords list
     */
    public abstract void getRandomLevel();
}
