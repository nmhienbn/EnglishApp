package views.games;

import edu.princeton.cs.algs4.StdRandom;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import views.animations.GameAnimations;
import views.controllers.games_ctrl.CTW_ctrl;

import java.util.Objects;

import static views.controllers.games_ctrl.CTW_ctrl.showWrongWord;
import static views.controllers.games_ctrl.CTW_ctrl.winningWords;

public class MainCatchWord extends Game {
    protected static MainCatchWord instance = null;
    public CTW_ctrl ctw_ctrl = null;

    /**
     * Variables for the grid.
     */
    protected final int CURRENT_ROW = 1;
    protected int ord_winningWord;
    protected int[] level;
    protected int now_level;


    protected MainCatchWord() {
        // Exists only to defeat instantiation.
    }

    /**
     * Gets the instance of the mainWordle class
     *
     * @return the instance of the mainWordle class
     */
    public static MainCatchWord getInstance() {
        if (instance == null) {
            instance = new MainCatchWord();
        }
        return instance;
    }

    public void setLevels(int cntLevels) {
        level = new int[cntLevels];
        for (int i = 0; i < cntLevels; i++) {
            level[i] = i;
        }
        StdRandom.shuffle(level);
        now_level = 0;
    }

    @Override
    public void createGameTitle(HBox titleHBox) {
        String example = "ctw-example";
        String title = "CATCH THE WORD";
        for (int i = 0; i < title.length(); i++) {
            String letter = String.valueOf(title.charAt(i));
            Label label = new Label(letter);
            if (title.charAt(i) == ' ') {
                label.setVisible(false);
            }
            label.getStyleClass().add(example);
            titleHBox.getChildren().add(label);
        }
    }

    /**
     * Creates the keyboard for the game
     *
     * @param gridPane the gridPane that will contain the keyboard
     */
    public void createGrid(GridPane gridPane, ImageView img) {
        img.setImage(new Image(String.format("/game/ctw/%d.jpg", ord_winningWord),
                300, 300, true, true));

        for (int j = 1; j <= MAX_COLUMN; j++) {
            Label label = new Label();
            label.getStyleClass().add("ctw-default-tile");
            if (winningWord.charAt(j - 1) == ' ') {
                label.setVisible(false);
                label.setText(" ");
            }
            gridPane.add(label, j, 1);
        }
    }

    @Override
    protected void onMouseEntered(Label label) {
        if (ctw_ctrl.notificationPane.isVisible()) {
            return;
        }
        label.toFront();
        label.setStyle("-fx-border-color: BLUE;");
    }

    @Override
    protected void resetTitle(GridPane gridPane, int row, int col) {
        setLabelText(gridPane, row, col, "");
    }

    @Override
    protected void onLetterChosen(GridPane gridPane, String letter) {
        // If the user types a letter while the row is full, nothing changes.
        if (Objects.equals(getLabelText(gridPane, 1, CUR_COLUMN), "")) {
            setLabelText(gridPane, 1, CUR_COLUMN, letter);
            Label label = getLabel(gridPane, 1, CUR_COLUMN);
            GameAnimations.scaleTrans(label, 1, 1.3, 150).play();
            GameAnimations.scaleTrans(label, 1.3, 1, 150).play();
            setLabelStyleClass(gridPane, 1, CUR_COLUMN, "default-tile");
            if (CUR_COLUMN < MAX_COLUMN) {
                CUR_COLUMN++;
                if (winningWord.charAt(CUR_COLUMN - 1) == ' ') {
                    CUR_COLUMN++;
                }
            }
        }
    }

    /**
     * Backspace button is pressed
     *
     * @param gridPane the gridPane that contains the letters
     */
    protected void onBackspaceChosen(GridPane gridPane) {
        if ((CUR_COLUMN == MAX_COLUMN || CUR_COLUMN == 1)
                && !Objects.equals(getLabelText(gridPane, 1, CUR_COLUMN), "")) {
            resetTitle(gridPane, CURRENT_ROW, CUR_COLUMN);
        } else if ((CUR_COLUMN > 1 && CUR_COLUMN < MAX_COLUMN)
                || (CUR_COLUMN == MAX_COLUMN && Objects.equals(getLabelText(gridPane, 1, CUR_COLUMN), ""))) {
            CUR_COLUMN--;
            if (winningWord.charAt(CUR_COLUMN - 1) == ' ') {
                CUR_COLUMN--;
            }
            resetTitle(gridPane, CURRENT_ROW, CUR_COLUMN);
        }
    }

    /**
     * Enter button is pressed
     *
     * @param gridPane     the gridPane that contains the letters
     * @param keyboardRows the keyboard
     */
    protected void onEnterChosen(GridPane gridPane, GridPane[] keyboardRows) {
        if (CUR_COLUMN == MAX_COLUMN) {
            String guess = getWordFromCurrentRow(gridPane).toLowerCase();
            if (guess.equals(winningWord)) {
                ctw_ctrl.showEndGameWindow(true, winningWord);
            } else {
                showWrongWord();
            }
        }
    }

    /**
     * Gets a random word from the winningWords list
     */
    public void getRandomWord() {
        now_level++;
        if (now_level >= level.length) {
            now_level = 0;
            StdRandom.shuffle(level);
        }
        ord_winningWord = level[now_level];
        winningWord = winningWords.get(ord_winningWord);
        MAX_COLUMN = winningWord.length();
    }

    /**
     * Restarts the game
     *
     * @param gridPane     the gridPane that contains the letters
     * @param keyboardRows the keyboard
     */
    public void resetGame(ImageView img, GridPane gridPane, GridPane[] keyboardRows) {
        getRandomWord();
        gridPane.getChildren().removeIf(Objects::nonNull);
        createGrid(gridPane, img);
        createKeyBoard(keyboardRows, gridPane);

        CUR_COLUMN = 1;
        ctw_ctrl.gridRequestFocus();
    }
}
