package models.games.GameKeyBoard.Wordle;

import controllers.Games.GameAnimations;
import controllers.Games.Game_ctrl;
import controllers.Games.Wordle_ctrl;
import edu.princeton.cs.algs4.StdRandom;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import models.games.GameKeyBoard.GameWithKeyBoard;
import models.games.GameKeyBoard.LabelActions;

import java.util.Collections;
import java.util.Objects;

public class GameWordle extends GameWithKeyBoard {
    protected Wordle_ctrl wordleCtrl = null;
    protected final String[] LetterStyleClass = {"correct-letter", "valid-letter", "absent-letter"};

    @Override
    public void setController(Game_ctrl gameCtrl) {
        this.gameCtrl = gameCtrl;
        wordleCtrl = (Wordle_ctrl) gameCtrl;
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
    protected void resetTile(GridPane gridPane, int row, int col) {
        LabelActions.setLabelText(gridPane, row, col, "");
        LabelActions.setDefaultTile(gridPane, row, col);
    }

    @Override
    protected void onLetterChosen(GridPane gridPane, String letter) {
        // If the user types a letter while the row is full, nothing changes.
        if (Objects.equals(LabelActions.getLabelText(gridPane, CUR_ROW, CUR_COLUMN), "")) {
            LabelActions.setLabelText(gridPane, CUR_ROW, CUR_COLUMN, letter);
            Label label = LabelActions.getLabel(gridPane, CUR_ROW, CUR_COLUMN);
            GameAnimations.scaleTrans(label, 1, 1.2, 150).play();
            GameAnimations.scaleTrans(label, 1.2, 1, 150).play();
            if (CUR_COLUMN < MAX_COLUMN)
                CUR_COLUMN++;
        }
    }

    @Override
    protected void onBackspaceChosen(GridPane gridPane) {
        boolean cmp = Objects.equals(LabelActions.getLabelText(gridPane, CUR_ROW, CUR_COLUMN), "");
        if (CUR_COLUMN == 1 || !cmp) {
            resetTile(gridPane, CUR_ROW, CUR_COLUMN);
        } else {
            resetTile(gridPane, CUR_ROW, --CUR_COLUMN);
        }
    }

    @Override
    protected void onEnterChosen(GridPane gridPane, GridPane[] keyboardRows) {
        if (CUR_ROW <= MAX_ROW && CUR_COLUMN == MAX_COLUMN) {
            String guess = getWordFromCurrentRow(gridPane).toLowerCase();
            if (guess.equals(winWord)) {
                new ChangeColor(this).ChangeRow(gridPane, CUR_ROW);
                new ChangeColor(this).ChangeKeyboard(gridPane, keyboardRows);
                wordleCtrl.showEndGameWindow(true, winWord);
            } else if (isValidGuess(guess)) {
                new ChangeColor(this).ChangeRow(gridPane, CUR_ROW);
                new ChangeColor(this).ChangeKeyboard(gridPane, keyboardRows);
                if (CUR_ROW == MAX_ROW) {
                    wordleCtrl.showEndGameWindow(false, winWord);
                } else {
                    CUR_ROW++;
                    CUR_COLUMN = 1;
                }
            } else {
                wordleCtrl.showWordNotFound();
            }
        }
    }

    @Override
    public void getRandomLevel() {
        winWord = wordleCtrl.winningWords.get(StdRandom.uniformInt(wordleCtrl.winningWords.size()));
    }

    /**
     * Checks if the guess is a valid word
     *
     * @param guess the guess to be checked
     * @return true if the guess is a valid word, false otherwise
     */
    protected boolean isValidGuess(String guess) {
        return Collections.binarySearch(wordleCtrl.winningWords, guess) >= 0;
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
        wordleCtrl.gridRequestFocus();
    }
}
