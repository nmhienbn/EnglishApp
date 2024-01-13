package models.games.GameKeyBoard.Wordle;

import controllers.Games.GameAnimations;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import static models.games.GameKeyBoard.LabelActions.getLabel;

public class ChangeColor {
    final GameWordle mainWordle;

    public ChangeColor(GameWordle mainWordle) {
        this.mainWordle = mainWordle;
    }

    protected void ChangeRow(GridPane gridPane, int searchRow) {
        var MAX_COLUMN = mainWordle.getMAX_COLUMN();
        var winWord = mainWordle.getWinWord();
        var LetterStyleClass = mainWordle.LetterStyleClass;

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

    protected void ChangeKeyboard(GridPane gridPane, GridPane[] keyboardRows) {
        var curWord = mainWordle.getWordFromCurrentRow(gridPane).toLowerCase();
        var MAX_COLUMN = mainWordle.getMAX_COLUMN();
        var winWord = mainWordle.getWinWord();
        var Letters = mainWordle.Letters;

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

    protected boolean contains(String[] array, String input) {
        for (String s : array) {
            if (s.equalsIgnoreCase(input))
                return true;
        }
        return false;
    }
}
