package controllers.Games;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import models.games.GameNotification;
import models.games.GameKeyBoard.GameWithKeyBoard;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.stream.Stream;

import static controllers.Games.NodeIntersect.inHierarchy;

public abstract class GameKeyBoard_ctrl extends Game_ctrl {
    @FXML
    public GridPane gridPane;
    GameWithKeyBoard gameKeyBoard;
    @FXML
    public GridPane keyboardRow1;
    @FXML
    public GridPane keyboardRow2;
    @FXML
    public GridPane keyboardRow3;
    public GridPane[] keyboardRows;

    public void gridRequestFocus() {
        gridPane.requestFocus();
    }

    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        gameKeyBoard.onKeyPressed(gridPane, keyboardRows, keyEvent);
    }

    protected void createUI() {
        // Create Game Title
        gameKeyBoard.createGameTitle(titleHBox);

        // Create Keyboard
        keyboardRows = new GridPane[3];
        keyboardRows[0] = keyboardRow1;
        keyboardRows[1] = keyboardRow2;
        keyboardRows[2] = keyboardRow3;
        gameKeyBoard.createKeyBoard(keyboardRows, gridPane);
    }

    @Override
    protected void hideNotification(MouseEvent e) {
        if (notificationPane.isVisible() &&
                inHierarchy(e.getPickResult().getIntersectedNode(), notificationPane)) {
            new GameNotification().hideNotification(dimSc, notificationPane);
            gridRequestFocus();
        }
    }

    protected void initWords(String path, ArrayList<String> words) {
        InputStream winning_words = getClass().getResourceAsStream(path);
        assert winning_words != null
                : "Could not find " + path + " file in resources folder.";
        Stream<String> winning_words_lines = new BufferedReader(new InputStreamReader(winning_words)).lines();
        winning_words_lines.forEach(words::add);
    }
}
