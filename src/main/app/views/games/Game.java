package views.games;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public abstract class Game {

    /**
     * Variables for the grid.
     */
    protected int CUR_ROW = 1;
    protected int CUR_COLUMN = 1;
    protected int MAX_COLUMN = 5;
    protected int MAX_ROW = 6;
    protected String winningWord;

    /**
     * Variables for the keyboard.
     */
    protected final String[][] Letters = {
            {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
            {"A", "S", "D", "F", "G", "H", "J", "K", "L"},
            {"enter", "Z", "X", "C", "V", "B", "N", "M", "backspace"}};

    /**
     * Creates the title for the game
     *
     * @param titleHBox the HBox that will contain the title
     */
    public abstract void createGameTitle(HBox titleHBox);

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
                label.setOnMouseExited(e -> label.setStyle("-fx-border-color: transparent;"));
                if (i == 2 && (j == 0 || j == Letters[i].length - 1)) {
                    label.getStyleClass().add("keyboardTileSymbol");
                } else {
                    label.getStyleClass().add("keyboardTile");
                }
                keyboardRows[i].add(label, j, i + 1);
            }
        }
    }

    protected void onMouseEntered(Label label) {
        label.setStyle("-fx-border-color: BLUE; -fx-border-width: 5; -fx-border-radius: 5;");
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
            Integer r = GridPane.getRowIndex(child);
            Integer c = GridPane.getColumnIndex(child);
            int row = (r == null ? 0 : r);
            int col = (c == null ? 0 : c);
            if (row == searchRow && col == searchColumn && (child instanceof Label)) {
                return (Label) child;
            }
        }
        return null;
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
        if (label != null)
            return label.getText().toLowerCase();
        return null;
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
        if (label != null) {
            label.setText(input.toUpperCase());
        }
    }

    /**
     * Sets the style class of the letter at the specified row and column
     *
     * @param gridPane     the gridPane that contains the letter
     * @param searchRow    the row of the letter
     * @param searchColumn the column of the letter
     * @param styleclass   the style class to be set
     */
    protected void setLabelStyleClass(GridPane gridPane, int searchRow, int searchColumn, String styleclass) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null) {
            label.getStyleClass().add(styleclass);
        }
    }

    /**
     * Clears the style class of the letter at the specified row and column
     *
     * @param gridPane     the gridPane that contains the letter
     * @param searchRow    the row of the letter
     * @param searchColumn the column of the letter
     */
    protected void clearLabelStyleClass(GridPane gridPane, int searchRow, int searchColumn) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null) {
            label.getStyleClass().clear();
        }
    }

    /**
     * Gets the letter at the specified row and column
     *
     * @param gridPane the gridPane that contains the letter
     * @param letter   the letter to be found
     * @return the letter at the specified row and column
     */
    protected Label getLabel(GridPane gridPane, String letter) {
        Label label;
        for (Node child : gridPane.getChildren()) {
            if (child instanceof Label) {
                label = (Label) child;
                if (letter.equalsIgnoreCase(label.getText())) {
                    return label;
                }
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
        StringBuilder input = new StringBuilder();
        for (int j = 1; j <= MAX_COLUMN; j++)
            input.append(getLabelText(gridPane, CUR_ROW, j));
        return input.toString();
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
        } else if (keyEvent.getCode().isLetterKey()) {
            onLetterChosen(gridPane, keyEvent.getText());
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            onEnterChosen(gridPane, keyboardRows);
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
    protected abstract void resetTitle(GridPane gridPane, int row, int col);

    /**
     * A letter is chosen
     *
     * @param gridPane the gridPane that contains the letters
     * @param letter   the letter chosen
     */
    protected abstract void onLetterChosen(GridPane gridPane, String letter);

    /**
     * Backspace button is pressed
     *
     * @param gridPane the gridPane that contains the letters
     */
    protected abstract void onBackspaceChosen(GridPane gridPane);

    /**
     * Enter button is pressed
     *
     * @param gridPane     the gridPane that contains the letters
     * @param keyboardRows the keyboard
     */
    protected abstract void onEnterChosen(GridPane gridPane, GridPane[] keyboardRows);

    /**
     * Gets a random word from the winningWords list
     */
    public abstract void getRandomWord();
}
