package views.controllers;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

public class WordleTab_ctrl {

    private final MainWordle mainWordle = MainWordle.getInstance();

    @FXML
    public GridPane gridPane;
    @FXML
    public GridPane keyboardRow1;
    public GridPane keyboardRow2;
    public GridPane keyboardRow3;
    public GridPane[] keyboardRows;
    @FXML
    public ImageView helpIcon;
    @FXML
    public HBox titleHBox;
    @FXML
    public ImageView restartIcon;
    /* Word lists */
    public static final ArrayList<String> winningWords = new ArrayList<>();
    public static final ArrayList<String> dictionaryWords = new ArrayList<>();

    private static Stage stageReference;

    public void createUI() {
        createGrid();
        createKeyboard();
        createTitleHBox();
    }

    public void createGrid() {
        mainWordle.createGrid(gridPane);
    }

    public void createKeyboard() {
        keyboardRows = new GridPane[3];
        keyboardRows[0] = keyboardRow1;
        keyboardRows[1] = keyboardRow2;
        keyboardRows[2] = keyboardRow3;
        mainWordle.createKeyBoard(keyboardRows, gridPane);
    }

    public void gridRequestFocus() {
        gridPane.requestFocus();
    }

    @FXML
    public void onKeyPressed(KeyEvent keyEvent) {
        mainWordle.onKeyPressed(gridPane, keyboardRows, keyEvent);
    }

    public void getRandomWord() {
        mainWordle.getRandomWord();
    }

    @FXML
    public void showHelp() {
        displayHelpWindow();
    }

    public void createTitleHBox() {
        mainWordle.createGameTitle(titleHBox);
    }

    @FXML
    public void restart() {
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(500), restartIcon);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        rotateTransition.setOnFinished(ae ->
                mainWordle.resetGame(gridPane, keyboardRows));
        rotateTransition.play();
    }

    @FXML
    void initialize() {
        initializeWordLists();
        createUI();
        getRandomWord();
        helpIcon.setImage(new Image(Objects.requireNonNull(getClass().
                getResourceAsStream("/game/images/help.png"))));
        restartIcon.setImage(new Image(Objects.requireNonNull(getClass().
                getResourceAsStream("/game/images/icons8-restart-40.png"))));
    }

    public static void showToast() {
        Toast.makeText(stageReference);
    }

    private void initWords(String path, ArrayList<String> words) {
        InputStream winning_words = getClass().getResourceAsStream(path);
        assert winning_words != null
                : "Could not find " + path + " file in resources folder.";
        Stream<String> winning_words_lines = new BufferedReader(new InputStreamReader(winning_words)).lines();
        winning_words_lines.forEach(words::add);
    }

    public void initializeWordLists() {
        initWords("/game/winning-words.txt", winningWords);
        initWords("/game/dictionary.txt", dictionaryWords);
    }

    private static HBox giveExampleWord(String sampleWord, String typeExample, int index) {
        String[] letter = sampleWord.split("");
        HBox WordHBox = new HBox(3);
        for (int i = 0; i < letter.length; i++) {
            Label label = new Label(letter[i]);
            if (i == index)
                label.getStyleClass().setAll(typeExample);
            else
                label.getStyleClass().setAll("default-letter-example");
            WordHBox.getChildren().add(label);
        }
        return WordHBox;
    }

    /**
     * Display game instructions.
     */
    private static void displayHelpWindow() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle("HOW TO PLAY");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20, 20, 20, 20));
        Label helpParagraph = new Label("""
                Guess the WORDLE in six tries. \n
                Each guess must be a valid five-letter word.\n
                Hit the enter button to submit.\n\n 
                After each guess, the color of the tiles will change to \n 
                show how close your guess was to the word.""");
        helpParagraph.setTextAlignment(TextAlignment.CENTER);
        helpParagraph.getStyleClass().setAll("lead");

        Line line1 = new Line();
        line1.setStroke(Paint.valueOf("b8b8b8"));
        line1.setEndX(2000);

        Label labelExample = new Label("Examples");
        labelExample.getStyleClass().setAll("h3");
        labelExample.setTextAlignment(TextAlignment.LEFT);

        /* 3 EXAMPLES */
        HBox firstWordHBox = giveExampleWord("WEARY", "correct-letter-example", 0);
        Label firstWordLabel = new Label("The letter W is in the word and in the correct spot.");
        firstWordLabel.getStyleClass().setAll("lead");

        HBox secondWordHBox = giveExampleWord("PILLS", "present-letter-example", 1);
        Label secondWordLabel = new Label("The letter I is in the word but in the wrong spot.");
        secondWordLabel.getStyleClass().setAll("lead");

        HBox thirdWordHBox = giveExampleWord("VAGUE", "wrong-letter-example", 2);
        Label thirdWordLabel = new Label("The letter U is not in the word in any spot.");
        thirdWordLabel.getStyleClass().setAll("lead");

        Line line2 = new Line();
        line2.setStroke(Paint.valueOf("b8b8b8"));
        line2.setEndX(2000);

        Button goBackButton = new Button("GO BACK");
        goBackButton.getStyleClass().setAll("btn", "btn-primary");

        goBackButton.setOnMouseClicked(me -> stage.close());

        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(helpParagraph, line1, labelExample, firstWordHBox,
                firstWordLabel, secondWordHBox, secondWordLabel, thirdWordHBox, thirdWordLabel,
                line2, goBackButton);
        Scene scene = new Scene(root, 500, 515);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        scene.getStylesheets()
                .add(Objects.requireNonNull(ScoreWindow.class.getResource("/front_end/css/wordle.css"))
                        .toExternalForm());

        stage.getIcons().add(new Image(Objects.requireNonNull(MainWordle.class.getResourceAsStream("/game/images/help.png"))));
        stage.setScene(scene);
        stage.showAndWait();
    }
}
