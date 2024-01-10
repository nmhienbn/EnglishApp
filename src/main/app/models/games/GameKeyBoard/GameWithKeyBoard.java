package models.games.GameKeyBoard;

import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import models.games.Game;
import models.games.GameSound;

import java.io.File;

import static models.games.GameKeyBoard.LabelActions.getLabel;
import static models.games.GameKeyBoard.LabelActions.getLabelText;
import static models.games.GameSound.mediaKeyboardPress;

public abstract class GameWithKeyBoard extends Game {
    /**
     * Variables for the grid.
     */
    protected int CUR_ROW = 1;
    protected int CUR_COLUMN = 1;
    protected int MAX_COLUMN = 5;
    protected int MAX_ROW = 6;

    public int getMAX_COLUMN() {
        return MAX_COLUMN;
    }

    /**
     * Variables for the keyboard.
     */
    public final String[][] Letters = {
            {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
            {"A", "S", "D", "F", "G", "H", "J", "K", "L"},
            {"enter", "Z", "X", "C", "V", "B", "N", "M", "backspace"}};

    /**
     * Creates the keyboard for the game
     *
     * @param keyboardRows the gridPanes that will contain the keyboard
     */
    public void createKeyBoard(GridPane[] keyboardRows, GridPane gridPane) {
        for (int i = 0; i < Letters.length; i++) {
            for (int j = 0; j < Letters[i].length; j++) {
                Label label = new Label(Letters[i][j]);
                label.setOnMouseClicked(e -> onLetterClicked(gridPane, keyboardRows, label.getText()));
                label.setOnMouseEntered(e -> new MouseActionsOnKeyboard(this.getController()).onMouseEntered(label));
                label.setOnMouseExited(e -> new MouseActionsOnKeyboard(this.getController()).onMouseExited(label));
                String styleClass = "keyboardTile";
                if (i == 2 && (j == 0 || j == Letters[i].length - 1)) {
                    styleClass += "Symbol";
                }
                label.getStyleClass().add(styleClass);
                keyboardRows[i].add(label, j, i + 1);
            }
        }
    }

    protected abstract void resetTile(GridPane gridPane, int row, int col);

    public final String getWordFromCurrentRow(GridPane gridPane) {
        StringBuilder res = new StringBuilder();
        for (int j = 1; j <= MAX_COLUMN; j++)
            res.append(getLabelText(gridPane, CUR_ROW, j));
        return res.toString();
    }

    public final void onKeyPressed(GridPane gridPane, GridPane[] keyboardRows, KeyEvent keyEvent) {
        mediaKeyboardPress.play();
        if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
            onBackspaceChosen(gridPane);
        } else if (keyEvent.getCode() == KeyCode.ENTER) {
            onEnterChosen(gridPane, keyboardRows);
        } else if (keyEvent.getCode().isLetterKey()) {
            onLetterChosen(gridPane, keyEvent.getText());
        }
    }

    protected final void onLetterClicked(GridPane gridPane, GridPane[] keyboardRows, String letter) {
        mediaKeyboardPress.play();
        if (letter.equals("backspace")) {
            onBackspaceChosen(gridPane);
        } else if (letter.equals("enter")) {
            onEnterChosen(gridPane, keyboardRows);
        } else {
            onLetterChosen(gridPane, letter);
        }
    }

    protected abstract void onLetterChosen(GridPane gridPane, String letter);

    protected abstract void onBackspaceChosen(GridPane gridPane);

    protected abstract void onEnterChosen(GridPane gridPane, GridPane[] keyboardRows);
}
