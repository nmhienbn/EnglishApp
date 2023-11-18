package views.games;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.kordamp.bootstrapfx.BootstrapFX;
import views.animations.GameAnimations;
import views.controllers.games_ctrl.CTW_ctrl;
import views.controllers.games_ctrl.Game_ctrl;
import views.controllers.games_ctrl.Wordle_ctrl;


public class GameNotification {

    private GameNotification() {
    }

    public static Wordle_ctrl wordle_ctrl;
    public static CTW_ctrl ctw_ctrl;
    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void endGameNotification(boolean isWin, String winningWord,
                                           Game_ctrl game_ctrl, String[] messages) {
        BorderPane mainPane = game_ctrl.notificationPane;
        mainPane.setPrefWidth(400);
        mainPane.setPrefHeight(300);
        mainPane.setLayoutX(300);
        mainPane.setLayoutY(100);
        mainPane.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);

        Text ins = new Text();
        ins.setTextAlignment(TextAlignment.CENTER);
        ins.getStyleClass().setAll("h1", "strong");
        ins.setFill(Paint.valueOf("FFFFFF"));

        Line line = new Line();
        line.setStroke(Paint.valueOf("FFFFFF"));
        line.setEndX(400);

        Label mainLabel = new Label();
        if (isWin) {
            ins.setText("CONGRATULATIONS!");
            mainLabel.setText(messages[0]);
        } else {
            ins.setText("GAME OVER!");
            mainLabel.setText(messages[1]);
        }
        mainLabel.getStyleClass().setAll("lead", "big-font");
        mainLabel.setTextFill(Paint.valueOf("FFFFFF"));

        Label answer = new Label(winningWord.toUpperCase());
        answer.getStyleClass().setAll("h1", "strong");
        answer.setTextFill(Paint.valueOf("FFFFFF"));

        ImageView playAgainButton = new ImageView(new Image("front_end/graphic/icons/play_again.png",
                200, 50, true, false));
        playAgainButton.setOnMouseEntered(e -> {
            ScaleTransition scaleTransition = GameAnimations.scaleTrans(playAgainButton, 1, 1.4, 150);
            scaleTransition.play();
        });
        playAgainButton.setOnMouseExited(e -> {
            ScaleTransition scaleTransition = GameAnimations.scaleTrans(playAgainButton, 1.4, 1, 150);
            scaleTransition.play();
        });
        playAgainButton.setOnMouseClicked(e -> {
            reallyRestart(game_ctrl);
        });

        vbox.getChildren().addAll(ins, line, mainLabel, answer, playAgainButton);

        mainPane.setCenter(vbox);
        String style = "-fx-background-color: ";
        if (isWin)
            style += "linear-gradient(to top, #95FC51, #5B9931);";
        else
            style += "linear-gradient(to top, #C92A2C, #7D1A1C);";
        style += "-fx-padding: 3; -fx-background-radius: 30;";
        mainPane.setStyle(style);

        game_ctrl.dimSc.setVisible(true);
        game_ctrl.dimSc.toFront();
        mainPane.setVisible(true);
        mainPane.toFront();

        ScaleTransition scaleTransition = GameAnimations.scaleTrans(mainPane, 0, 1, 500);
        scaleTransition.play();

        mainPane.setOnMousePressed(e -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });

        mainPane.setOnMouseDragged(e -> {
            double x = e.getSceneX();
            double y = e.getSceneY();

            double deltaX = x - xOffset;
            double deltaY = y - yOffset;

            mainPane.setTranslateX(mainPane.getTranslateX() + deltaX);
            mainPane.setTranslateY(mainPane.getTranslateY() + deltaY);

            xOffset = x;
            yOffset = y;
        });
    }

    public static void reallyRestart(Game_ctrl game_ctrl) {
        BorderPane mainPane = game_ctrl.notificationPane;
        game_ctrl.dimSc.setVisible(false);
        mainPane.setVisible(false);
        mainPane.setTranslateX(0);
        mainPane.setTranslateY(0);
        mainPane.setOnMousePressed(null);
        mainPane.setOnMouseDragged(null);
        game_ctrl.restart();
    }

    public static void instructionWordle(BorderPane helpPane) {
        helpPane.setPrefWidth(500);
        helpPane.setPrefHeight(500);
        helpPane.setLayoutX(250);
        helpPane.setLayoutY(80);
        VBox vbox = new VBox(5);
        vbox.setPadding(new Insets(0, 0, 0, 0));

        Label ins = new Label("HOW TO PLAY");
        ins.setTextAlignment(TextAlignment.CENTER);
        ins.getStyleClass().setAll("h2", "strong");

        Line line = new Line();
        line.setStroke(Paint.valueOf("b8b8b8"));
        line.setEndX(490);

        Label insParagraph = new Label("""
                • Guess the WORDLE in six tries.
                • Each guess must be a valid five-letter word.
                • Hit the enter button to submit.
                • After each guess, the color of the tiles will change to
                show how close your guess was to the word.""");
        insParagraph.setTextAlignment(TextAlignment.LEFT);
        insParagraph.getStyleClass().setAll("lead");
        insParagraph.setStyle("-fx-line-spacing: -0.2em;");

        Label labelExample = new Label("Examples");
        labelExample.getStyleClass().setAll("h3", "strong");
        labelExample.setTextAlignment(TextAlignment.LEFT);

        /* 3 EXAMPLES */
        HBox firstWordHBox = giveExampleWord("WEARY", "correct-letter-example", 0);
        Label firstWordLabel = new Label("The letter W is in the word and in the correct spot.");
        firstWordLabel.getStyleClass().setAll("lead");

        HBox secondWordHBox = giveExampleWord("PILLS", "valid-letter-example", 1);
        Label secondWordLabel = new Label("The letter I is in the word but in the wrong spot.");
        secondWordLabel.getStyleClass().setAll("lead");

        HBox thirdWordHBox = giveExampleWord("VAGUE", "absent-letter-example", 2);
        Label thirdWordLabel = new Label("The letter G is not in the word in any spot.");
        thirdWordLabel.getStyleClass().setAll("lead");

        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(ins, line, insParagraph, labelExample, firstWordHBox,
                firstWordLabel, secondWordHBox, secondWordLabel, thirdWordHBox,
                thirdWordLabel);


        helpPane.setCenter(vbox);
        helpPane.setStyle("-fx-background-color: rgba(255, 255, 255, 1); -fx-padding: 3;" +
                "-fx-border-color: #000000; -fx-border-width: 2px;" +
                "-fx-border-radius: 5; -fx-background-radius: 5;");
        helpPane.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

    public static void instructionCTW(BorderPane helpPane) {
        helpPane.setPrefWidth(500);
        helpPane.setPrefHeight(400);
        helpPane.setLayoutX(250);
        helpPane.setLayoutY(80);
        VBox vbox = new VBox(5);
        vbox.setPadding(new Insets(0, 0, 0, 0));

        Label ins = new Label("HOW TO PLAY");
        ins.setTextAlignment(TextAlignment.CENTER);
        ins.getStyleClass().setAll("h2", "strong");

        Line line = new Line();
        line.setStroke(Paint.valueOf("b8b8b8"));
        line.setEndX(490);

        ImageView img = new ImageView(new Image("game/ctw/CTW_example.png",
                480, 480, true, false));

        Label insParagraph = new Label("""
                • The left picture is RAIN
                • The right picture is BOW
                • Hence, the answer is RAINBOW.""");
        insParagraph.setTextAlignment(TextAlignment.LEFT);
        insParagraph.getStyleClass().setAll("lead");
        insParagraph.setStyle("-fx-line-spacing: -0.2em;");

        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(ins, line, img, insParagraph);


        helpPane.setCenter(vbox);
        helpPane.setStyle("-fx-background-color: rgba(255, 255, 255, 1); -fx-padding: 3;" +
                "-fx-border-color: #000000; -fx-border-width: 2px;" +
                "-fx-border-radius: 5; -fx-background-radius: 5;");
        helpPane.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

    private static HBox giveExampleWord(String sampleWord, String typeExample, int index) {
        String[] letter = sampleWord.split("");
        HBox WordHBox = new HBox(3);
        WordHBox.setAlignment(Pos.CENTER);
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

    public static void showNotification(BorderPane notificationPane, String notification) {
        notificationPane.setPrefWidth(200);
        notificationPane.setPrefHeight(100);
        notificationPane.setLayoutX(400);
        notificationPane.setLayoutY(250);
        notificationPane.setOpacity(0);
        notificationPane.setVisible(true);
        notificationPane.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        Text text = new Text(notification);
        text.setFill(Color.WHITE);
        text.getStyleClass().setAll("h2", "strong");

        notificationPane.setCenter(text);
        notificationPane.setStyle("-fx-background-radius: 5; " +
                "-fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 10;");

        FadeTransition fadeTransition = GameAnimations.fadeTrans(notificationPane, 0.75, 1, 750);
        fadeTransition.setOnFinished(e -> notificationPane.setVisible(false));
        fadeTransition.play();
    }
}