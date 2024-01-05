package views.games;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public abstract class GameWithKeyBoard extends Game {
    static Media keyboardPress = new Media(new File(System.getProperty("user.dir") +
            "\\src\\main\\resources\\audio\\keyboard.mp3").toURI().toString());
    static MediaPlayer keyboardPressPlayer = new MediaPlayer(keyboardPress);

    /**
     * Variables for the grid.
     */
    protected int CUR_ROW = 1;
    protected int CUR_COLUMN = 1;
    protected int MAX_COLUMN = 5;
    protected int MAX_ROW = 6;

    /**
     * Variables for the keyboard.
     */
    protected final String[][] Letters = {
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
                label.setOnMouseEntered(e -> onMouseEntered(label));
                label.setOnMouseExited(e -> onMouseExited(label));
                String styleClass = "keyboardTile";
                if (i == 2 && (j == 0 || j == Letters[i].length - 1)) {
                    styleClass += "Symbol";
                }
                label.getStyleClass().add(styleClass);
                keyboardRows[i].add(label, j, i + 1);
            }
        }
    }

    protected void onMouseEntered(Label label) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0.4);
        label.setEffect(colorAdjust);
    }

    protected void onMouseExited(Label label) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0);
        label.setEffect(colorAdjust);
    }

    /**
     * Gets the letter at the specified row and column
     *
     * @param gridPane     the gridPane that contains the letter
     * @param searchRow    the row of the letter
     * @param searchColumn the column of the letter
     * @return the letter at the specified row and column
     */
    protected Label getLabel(GridPane gridPane, int searchRow, int searchColumn) {
        for (Node child : gridPane.getChildren()) {
            if (Integer.valueOf(searchRow).equals(GridPane.getRowIndex(child))
                    && Integer.valueOf(searchColumn).equals(GridPane.getColumnIndex(child))
                    && child instanceof Label label) {
                return label;
            }
        }
        return null;
    }

    /**
     * Sets the style class of the letter at the specified row and column
     *
     * @param gridPane     the gridPane that contains the letter
     * @param searchRow    the row of the letter
     * @param searchColumn the column of the letter
     */
    protected void setDefaultTile(GridPane gridPane, int searchRow, int searchColumn) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label == null) {
            return;
        }
        label.getStyleClass().setAll("default-tile");
    }

    /**
     * Gets the letter at the specified row and column
     *
     * @param gridPane     the gridPane that contains the letter
     * @param searchRow    the row of the letter
     * @param searchColumn the column of the letter
     * @return the letter at the specified row and column
     */
    protected String getLabelText(GridPane gridPane, int searchRow, int searchColumn) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label == null) {
            return null;
        }
        return label.getText().toLowerCase();
    }

    /**
     * Sets the letter at the specified row and column
     *
     * @param gridPane     the gridPane that contains the letter
     * @param searchRow    the row of the letter
     * @param searchColumn the column of the letter
     * @param input        the letter to be set
     */
    protected void setLabelText(GridPane gridPane, int searchRow, int searchColumn, String input) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label == null) {
            return;
        }
        label.setText(input.toUpperCase());
    }

    /**
     * Gets the letter at the specified row and column
     *
     * @param gridPane the gridPane that contains the letter
     * @param letter   the letter to be found
     * @return the letter at the specified row and column
     */
    protected Label getLabel(GridPane gridPane, String letter) {
        for (Node child : gridPane.getChildren()) {
            if (child instanceof Label label && letter.equalsIgnoreCase(label.getText())) {
                return label;
            }
        }
        return null;
    }

    /**
     * Gets the word from the current row.
     *
     * @param gridPane the gridPane that contains the letters
     * @return the word from the current row
     */
    protected String getWordFromCurrentRow(GridPane gridPane) {
        StringBuilder res = new StringBuilder();
        for (int j = 1; j <= MAX_COLUMN; j++)
            res.append(getLabelText(gridPane, CUR_ROW, j));
        return res.toString();
    }

    /**
     * Event a key is pressed
     *
     * @param gridPane     the gridPane that contains the letters
     * @param keyboardRows the keyboard
     * @param keyEvent     the key pressed
     */
    public void onKeyPressed(GridPane gridPane, GridPane[] keyboardRows, KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
            onBackspaceChosen(gridPane);
        } else if (keyEvent.getCode() == KeyCode.ENTER) {
            onEnterChosen(gridPane, keyboardRows);
        } else if (keyEvent.getCode().isLetterKey()) {
            onLetterChosen(gridPane, keyEvent.getText());
        }
    }

    /**
     * A letter is clicked
     *
     * @param gridPane     the gridPane that contains the letters
     * @param keyboardRows the keyboard
     * @param letter       the letter clicked
     */
    protected void onLetterClicked(GridPane gridPane, GridPane[] keyboardRows, String letter) {
        if (letter.equals("backspace")) {
            onBackspaceChosen(gridPane);
        } else if (letter.equals("enter")) {
            onEnterChosen(gridPane, keyboardRows);
        } else {
            onLetterChosen(gridPane, letter);
        }
    }

    /**
     * Resets the letter at the specified row and column
     *
     * @param gridPane the gridPane that contains the letters
     * @param row      the row of the letter
     * @param col      the column of the letter
     */
    protected void resetTile(GridPane gridPane, int row, int col) {
    }

    /**
     * A letter is chosen
     *
     * @param gridPane the gridPane that contains the letters
     * @param letter   the letter chosen
     */
    protected void onLetterChosen(GridPane gridPane, String letter) {
        keyboardPressPlayer.seek(keyboardPressPlayer.getStartTime());
        keyboardPressPlayer.play();
    }

    /**
     * Backspace button is pressed
     *
     * @param gridPane the gridPane that contains the letters
     */
    protected void onBackspaceChosen(GridPane gridPane) {
        keyboardPressPlayer.seek(keyboardPressPlayer.getStartTime());
        keyboardPressPlayer.play();
    }

    /**
     * Enter button is pressed
     *
     * @param gridPane     the gridPane that contains the letters
     * @param keyboardRows the keyboard
     */
    protected void onEnterChosen(GridPane gridPane, GridPane[] keyboardRows) {
        keyboardPressPlayer.seek(keyboardPressPlayer.getStartTime());
        keyboardPressPlayer.play();
    }
}
