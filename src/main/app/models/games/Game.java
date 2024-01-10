package models.games;

import javafx.scene.layout.HBox;
import controllers.Games.Game_ctrl;

public abstract class Game {
    protected String winWord;

    public Game_ctrl gameCtrl = null;

    public abstract void setController(Game_ctrl gameCtrl);

    public Game_ctrl getController() {
        return gameCtrl;
    }

    public String getWinWord() {
        return winWord;
    }

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