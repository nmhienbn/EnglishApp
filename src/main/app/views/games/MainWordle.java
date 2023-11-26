package views.games;

import edu.princeton.cs.algs4.StdRandom;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import views.animations.GameAnimations;
import views.controllers.games_ctrl.Wordle_ctrl;

import java.util.Collections;
import java.util.Objects;

import static views.controllers.games_ctrl.Wordle_ctrl.showWordNotFound;
import static views.controllers.games_ctrl.Wordle_ctrl.winningWords;

public class MainWordle extends Game {
    protected static MainWordle instance = null;
    public Wordle_ctrl wordle_ctrl = null;
    protected final String[] LetterStyleClass = {"correct-letter", "valid-letter", "absent-letter"};


    protected MainWordle() {
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

    @Override
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
                label.getStyleClass().add("default-tile");
                gridPane.add(label, j, i);
            }
        }
    }

    @Override
    protected void onMouseEntered(Label label) {
        if (wordle_ctrl.notificationPane.isVisible()) {
            return;
        }
        super.onMouseEntered(label);
    }

    @Override
    protected void onMouseExited(Label label) {
        if (wordle_ctrl.notificationPane.isVisible()) {
            return;
        }
        super.onMouseExited(label);
    }

    /**
     * Updates the row colors
     *
     * @param gridPane  the gridPane that contains the letters
     * @param searchRow the row to be updated
     */
    protected void updateRowColors(GridPane gridPane, int searchRow) {
        SequentialTransition effects = new SequentialTransition();
        for (int i = 1; i <= MAX_COLUMN; i++) {
            Label label = getLabel(gridPane, searchRow, i);
            if (label == null) {
                continue;
            }
            String styleClass;
            String curChar = String.valueOf(label.getText().charAt(0)).toLowerCase();
            if (String.valueOf(winWord.charAt(i - 1)).equalsIgnoreCase(curChar)) {
                styleClass = LetterStyleClass[0];
            } else if (winWord.contains(curChar)) {
                styleClass = LetterStyleClass[1];
            } else {
                styleClass = LetterStyleClass[2];
            }

            // Effects
            FadeTransition fadeTransition;
            ScaleTransition scaleTransition;

            // Fade Out
            fadeTransition = GameAnimations.fadeTrans(label, 1, 0.2, 100);
            fadeTransition.setOnFinished(e -> label.getStyleClass().setAll(styleClass));
            scaleTransition = GameAnimations.scaleTrans(label, 1, 1.2, 150);
            effects.getChildren().add(new ParallelTransition(fadeTransition, scaleTransition));

            // Fade In
            fadeTransition = GameAnimations.fadeTrans(label, 0.2, 1, 100);
            scaleTransition = GameAnimations.scaleTrans(label, 1.2, 1, 150);
            effects.getChildren().add(new ParallelTransition(fadeTransition, scaleTransition));
        }
        effects.play();
    }

    /**
     * Updates the keyboard colors
     *
     * @param gridPane     the gridPane that contains the letters
     * @param keyboardRows the keyboard
     */
    protected void updateKeyboardColors(GridPane gridPane, GridPane[] keyboardRows) {
        String curWord = getWordFromCurrentRow(gridPane).toLowerCase();
        for (int i = 0; i < MAX_COLUMN; i++) {
            Label keyboardLabel = new Label();
            String curChar = String.valueOf(curWord.charAt(i));
            String winChar = String.valueOf(winWord.charAt(i));

            for (int j = 0; j < Letters.length; j++) {
                if (contains(Letters[j], curChar)) {
                    keyboardLabel = getLabel(keyboardRows[j], curChar);
                    break;
                }
            }

            String styleClass = "keyboard";
            if (curChar.equals(winChar))
                styleClass += "Correct";
            else if (winWord.contains(curChar))
                styleClass += "Valid";
            else
                styleClass += "Absent";
            styleClass += "Color";
            if (keyboardLabel != null) {
                keyboardLabel.getStyleClass().setAll(styleClass);
            }
        }
    }

    /**
     * Checks if the array contains the input
     *
     * @param array the array to be checked
     * @param input the input to be checked
     * @return true if the array contains the input, false otherwise
     */
    protected boolean contains(String[] array, String input) {
        for (String s : array) {
            if (s.equalsIgnoreCase(input))
                return true;
        }
        return false;
    }

    @Override
    protected void resetTile(GridPane gridPane, int row, int col) {
        setLabelText(gridPane, row, col, "");
        setDefaultTile(gridPane, row, col);
    }

    @Override
    protected void onLetterChosen(GridPane gridPane, String letter) {
        // If the user types a letter while the row is full, nothing changes.
        if (Objects.equals(getLabelText(gridPane, CUR_ROW, CUR_COLUMN), "")) {
            super.onLetterChosen(gridPane, letter);
            setLabelText(gridPane, CUR_ROW, CUR_COLUMN, letter);
            Label label = getLabel(gridPane, CUR_ROW, CUR_COLUMN);
            GameAnimations.scaleTrans(label, 1, 1.2, 150).play();
            GameAnimations.scaleTrans(label, 1.2, 1, 150).play();
            if (CUR_COLUMN < MAX_COLUMN)
                CUR_COLUMN++;
        }
    }

    @Override
    protected void onBackspaceChosen(GridPane gridPane) {
        boolean cmp = Objects.equals(getLabelText(gridPane, CUR_ROW, CUR_COLUMN), "");
        super.onBackspaceChosen(gridPane);
        if (CUR_COLUMN == 1 || !cmp) {
            resetTile(gridPane, CUR_ROW, CUR_COLUMN);
        } else {
            resetTile(gridPane, CUR_ROW, --CUR_COLUMN);
        }
    }

    @Override
    protected void onEnterChosen(GridPane gridPane, GridPane[] keyboardRows) {
        super.onEnterChosen(gridPane, keyboardRows);
        if (CUR_ROW <= MAX_ROW && CUR_COLUMN == MAX_COLUMN) {
            String guess = getWordFromCurrentRow(gridPane).toLowerCase();
            if (guess.equals(winWord)) {
                updateRowColors(gridPane, CUR_ROW);
                updateKeyboardColors(gridPane, keyboardRows);
                wordle_ctrl.showEndGameWindow(true, winWord);
            } else if (isValidGuess(guess)) {
                updateRowColors(gridPane, CUR_ROW);
                updateKeyboardColors(gridPane, keyboardRows);
                if (CUR_ROW == MAX_ROW) {
                    wordle_ctrl.showEndGameWindow(false, winWord);
                } else {
                    CUR_ROW++;
                    CUR_COLUMN = 1;
                }
            } else {
                showWordNotFound();
            }
        }
    }

    @Override
    public void getRandomLevel() {
        winWord = winningWords.get(StdRandom.uniformInt(winningWords.size()));
    }

    /**
     * Checks if the guess is a valid word
     *
     * @param guess the guess to be checked
     * @return true if the guess is a valid word, false otherwise
     */
    protected boolean isValidGuess(String guess) {
        return Collections.binarySearch(winningWords, guess) >= 0;
    }

    /**
     * Restarts the game
     *
     * @param gridPane     the gridPane that contains the letters
     * @param keyboardRows the keyboard
     */
    public void resetGame(GridPane gridPane, GridPane[] keyboardRows) {
        getRandomLevel();

        for (GridPane keyboardRow : keyboardRows) {
            for (Node child : keyboardRow.getChildren()) {
                if (child instanceof Label label) {
                    if (label.getText().equals("enter") || label.getText().equals("backspace")) {
                        continue;
                    }
                    label.getStyleClass().setAll("keyboardTile");
                }
            }
        }

        for (Node child : gridPane.getChildren()) {
            if (child instanceof Label label) {
                label.setText("");
                label.getStyleClass().setAll("default-tile");
            }
        }

        CUR_COLUMN = 1;
        CUR_ROW = 1;
        wordle_ctrl.gridRequestFocus();
    }
}
