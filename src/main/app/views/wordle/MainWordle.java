package views.wordle;

import edu.princeton.cs.algs4.StdRandom;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import views.File_loader;
import views.animations.GameAnimations;
import views.controllers.WordleTab_ctrl;

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.System.exit;
import static views.controllers.WordleTab_ctrl.*;

public class MainWordle {
    private static MainWordle instance = null;
    public WordleTab_ctrl wordleTab_ctrl = null;
    private final String[] LetterStyleClass = {"correct-letter", "present-letter", "wrong-letter"};

    /**
     * Variables for the grid.
     */
    private int CURRENT_ROW = 1;
    private int CURRENT_COLUMN = 1;
    private final int MAX_COLUMN = 5;
    private final int MAX_ROW = 6;
    private String winningWord;

    /**
     * Variables for the keyboard.
     */
    private final String[][] Letters = {
            {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
            {"A", "S", "D", "F", "G", "H", "J", "K", "L"},
            {"enter", "Z", "X", "C", "V", "B", "N", "M", "backspace"}};


    private MainWordle() {
        // Exists only to defeat instantiation.
    }

    /**
     * Gets the instance of the mainWordle class
     *
     * @return the instance of the mainWordle class
     */
    public static MainWordle getInstance() {
        if (instance == null) {
            instance = new MainWordle();
        }
        return instance;
    }

    /**
     * Creates the title "WORDLE" for the game
     *
     * @param titleHBox the HBox that will contain the title
     */
    public void createGameTitle(HBox titleHBox) {
        String example = "-example";
        String title = "WORDLE";
        int[] style = {0, 2, 1, 0, 1, 2};
        for (int i = 0; i < title.length(); i++) {
            String letter = String.valueOf(title.charAt(i));
            Label label = new Label(letter);
            label.getStyleClass().add(LetterStyleClass[style[i]] + example);
            titleHBox.getChildren().add(label);
        }
    }

    /**
     * Creates the keyboard for the game
     *
     * @param gridPane the gridPane that will contain the keyboard
     */
    public void createGrid(GridPane gridPane) {
        for (int i = 1; i <= MAX_ROW; i++) {
            for (int j = 1; j <= MAX_COLUMN; j++) {
                Label label = new Label();
                label.getStyleClass().add("grid-pane");
                gridPane.add(label, j, i);
            }
        }
    }

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
                label.setOnMouseEntered(e -> label.setStyle("-fx-border-color: BLUE; -fx-border-width: 5;"));
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

    /**
     * Sets the letter at the specified row and column
     *
     * @param gridPane     the gridPane that contains the letter
     * @param searchRow    the row of the letter
     * @param searchColumn the column of the letter
     * @param input        the letter to be set
     */
    private void setLabelText(GridPane gridPane, int searchRow, int searchColumn, String input) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null) {
            label.setText(input.toUpperCase());
        }
    }

    /**
     * Gets the letter at the specified row and column
     *
     * @param gridPane     the gridPane that contains the letter
     * @param searchRow    the row of the letter
     * @param searchColumn the column of the letter
     * @return the letter at the specified row and column
     */
    private Label getLabel(GridPane gridPane, int searchRow, int searchColumn) {
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
     * @param gridPane the gridPane that contains the letter
     * @param letter   the letter to be found
     * @return the letter at the specified row and column
     */
    private Label getLabel(GridPane gridPane, String letter) {
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
     * Gets the letter at the specified row and column
     *
     * @param gridPane     the gridPane that contains the letter
     * @param searchRow    the row of the letter
     * @param searchColumn the column of the letter
     * @return the letter at the specified row and column
     */
    private String getLabelText(GridPane gridPane, int searchRow, int searchColumn) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null)
            return label.getText().toLowerCase();
        return null;
    }

    /**
     * Sets the style class of the letter at the specified row and column
     *
     * @param gridPane     the gridPane that contains the letter
     * @param searchRow    the row of the letter
     * @param searchColumn the column of the letter
     * @param styleclass   the style class to be set
     */
    private void setLabelStyleClass(GridPane gridPane, int searchRow, int searchColumn, String styleclass) {
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
    private void clearLabelStyleClass(GridPane gridPane, int searchRow, int searchColumn) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label != null) {
            label.getStyleClass().clear();
        }
    }

    private void updateRowColors(GridPane gridPane, int searchRow) {
        SequentialTransition effects = new SequentialTransition();
        for (int i = 1; i <= MAX_COLUMN; i++) {
            Label label = getLabel(gridPane, searchRow, i);
            String styleClass;
            if (label != null) {
                String currentCharacter = String.valueOf(label.getText().charAt(0)).toLowerCase();
                if (String.valueOf(winningWord.charAt(i - 1)).equalsIgnoreCase(currentCharacter)) {
                    styleClass = "correct-letter";
                } else if (winningWord.contains(currentCharacter)) {
                    styleClass = "present-letter";
                } else {
                    styleClass = "wrong-letter";
                }

                // Effects
                FadeTransition fadeTransition;
                ScaleTransition scaleTransition;

                // Fade Out
                fadeTransition = GameAnimations.fadeTrans(label, 1, 0.2, 150);
                fadeTransition.setOnFinished(e -> {
                    label.getStyleClass().clear();
                    label.getStyleClass().setAll(styleClass);
                });
                scaleTransition = GameAnimations.scaleTrans(label, 1, 1.2);
                effects.getChildren().add(new ParallelTransition(fadeTransition, scaleTransition));

                // Fade In
                fadeTransition = GameAnimations.fadeTrans(label, 0.2, 1, 150);
                scaleTransition = GameAnimations.scaleTrans(label, 1.2, 1);
                effects.getChildren().add(new ParallelTransition(fadeTransition, scaleTransition));
            }
        }
        effects.play();
    }

    private void updateKeyboardColors(GridPane gridPane, GridPane[] keyboardRows) {
        String currentWord = getWordFromCurrentRow(gridPane).toLowerCase();
        for (int i = 1; i <= MAX_COLUMN; i++) {
            Label keyboardLabel = new Label();
            String currentCharacter = String.valueOf(currentWord.charAt(i - 1));
            String winningCharacter = String.valueOf(winningWord.charAt(i - 1));

            for (int j = 0; j < Letters.length; j++) {
                if (contains(Letters[j], currentCharacter)) {
                    keyboardLabel = getLabel(keyboardRows[j], currentCharacter);
                    break;
                }
            }

            String styleClass;
            if (currentCharacter.equals(winningCharacter))
                styleClass = "keyboardCorrectColor";
            else if (winningWord.contains(currentCharacter))
                styleClass = "keyboardPresentColor";
            else
                styleClass = "keyboardWrongColor";
            if (keyboardLabel != null) {
                keyboardLabel.getStyleClass().clear();
                keyboardLabel.getStyleClass().add(styleClass);
            }
        }
    }


    private String getWordFromCurrentRow(GridPane gridPane) {
        StringBuilder input = new StringBuilder();
        for (int j = 1; j <= MAX_COLUMN; j++)
            input.append(getLabelText(gridPane, CURRENT_ROW, j));
        return input.toString();
    }

    private boolean contains(String[] array, String input) {
        for (String s : array) {
            if (s.equalsIgnoreCase(input))
                return true;
        }
        return false;
    }

    public void onKeyPressed(GridPane gridPane, GridPane[] keyboardRows, KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.BACK_SPACE) {
            onBackspacePressed(gridPane);
        } else if (keyEvent.getCode().isLetterKey()) {
            onLetterPressed(gridPane, keyEvent);
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            onEnterPressed(gridPane, keyboardRows);
        }
    }

    private void resetTitle(GridPane gridPane, int row, int col) {
        setLabelText(gridPane, row, col, "");
        clearLabelStyleClass(gridPane, row, col);
        setLabelStyleClass(gridPane, row, col, "default-tile");
    }

    private void onBackspacePressed(GridPane gridPane) {
        if ((CURRENT_COLUMN == MAX_COLUMN || CURRENT_COLUMN == 1)
                && !Objects.equals(getLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN), "")) {
            resetTitle(gridPane, CURRENT_ROW, CURRENT_COLUMN);
        } else if ((CURRENT_COLUMN > 1 && CURRENT_COLUMN < MAX_COLUMN)
                || (CURRENT_COLUMN == MAX_COLUMN && Objects.equals(getLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN), ""))) {
            CURRENT_COLUMN--;
            resetTitle(gridPane, CURRENT_ROW, CURRENT_COLUMN);
        }
    }

    private void onLetterPressed(GridPane gridPane, KeyEvent keyEvent) {
        // this is to make it so that when the user types a letter but the row is full
        // it doesn't change the last letter instead
        if (Objects.equals(getLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN), "")) {
            onLetterChosen(gridPane, keyEvent.getText());
        }
    }

    private void onLetterChosen(GridPane gridPane, String letter) {
        // this is to make it so that when the user types a letter but the row is full
        // it doesn't change the last letter instead
        if (Objects.equals(getLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN), "")) {
            setLabelText(gridPane, CURRENT_ROW, CURRENT_COLUMN, letter);
            Label label = getLabel(gridPane, CURRENT_ROW, CURRENT_COLUMN);
            GameAnimations.scaleTrans(label, 1, 1.1).play();
            GameAnimations.scaleTrans(label, 1.1, 1).play();
            setLabelStyleClass(gridPane, CURRENT_ROW, CURRENT_COLUMN, "tile-with-letter");
            if (CURRENT_COLUMN < MAX_COLUMN)
                CURRENT_COLUMN++;
        }
    }

    private void onLetterClicked(GridPane gridPane, GridPane[] keyboardRows, String letter) {
        if (letter.equals("backspace")) {
            onBackspacePressed(gridPane);
        } else if (letter.equals("enter")) {
            onEnterPressed(gridPane, keyboardRows);
        } else {
            onLetterChosen(gridPane, letter);
        }
    }

    private void onEnterPressed(GridPane gridPane, GridPane[] keyboardRows) {
        if (CURRENT_ROW <= MAX_ROW && CURRENT_COLUMN == MAX_COLUMN) {
            String guess = getWordFromCurrentRow(gridPane).toLowerCase();
            if (guess.equals(winningWord)) {
                updateRowColors(gridPane, CURRENT_ROW);
                updateKeyboardColors(gridPane, keyboardRows);
                wordleTab_ctrl.showEndGameWindow(true, winningWord);
            } else if (isValidGuess(guess)) {
                updateRowColors(gridPane, CURRENT_ROW);
                updateKeyboardColors(gridPane, keyboardRows);
                if (CURRENT_ROW == MAX_ROW) {
                    wordleTab_ctrl.showEndGameWindow(false, winningWord);
                }
                CURRENT_ROW++;
                CURRENT_COLUMN = 1;
            } else {
                showToast();
            }
        }
    }

    public void getRandomWord() {
        winningWord = winningWords.get(StdRandom.uniformInt(winningWords.size()));
    }

    private boolean isValidGuess(String guess) {
        return binarySearch(winningWords, guess) || binarySearch(dictionaryWords, guess);
    }

    public void resetGame(GridPane gridPane, GridPane[] keyboardRows) {
        getRandomWord();
        Label label;
        for (Node child : gridPane.getChildren()) {
            if (child instanceof Label) {
                label = (Label) child;
                label.getStyleClass().clear();
                label.setText("");
                label.getStyleClass().add("default-tile");
            }
        }

        for (GridPane keyboardRow : keyboardRows) {
            for (Node child : keyboardRow.getChildren()) {
                if (child instanceof Label) {
                    label = (Label) child;
                    if (label.getText().equals("enter") || label.getText().equals("backspace")) {
                        continue;
                    }
                    label.getStyleClass().clear();
                    label.getStyleClass().add("keyboardTile");
                }
            }
        }

        CURRENT_COLUMN = 1;
        CURRENT_ROW = 1;
        wordleTab_ctrl.gridRequestFocus();
    }

    private boolean binarySearch(ArrayList<String> list, String string) {
        int low = 0, high = list.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = string.compareTo(list.get(mid));
            if (comparison == 0) return true;
            if (comparison > 0) low = mid + 1;
            else high = mid - 1;
        }
        return false;
    }

}
