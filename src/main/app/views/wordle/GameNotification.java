package views.wordle;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import views.controllers.CTW_ctrl;
import views.controllers.Game_ctrl;
import views.controllers.WordleTab_ctrl;

public class GameNotification {

    private GameNotification() {
    }

    public static WordleTab_ctrl wordleTab_ctrl;
    public static CTW_ctrl ctw_ctrl;
    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void endGameNotification(boolean guessed, String winningWord, BorderPane mainPane, Game_ctrl game_ctrl) {
        mainPane.setPrefWidth(400);
        mainPane.setPrefHeight(300);
        mainPane.setLayoutX(200);
        mainPane.setLayoutY(100);
        mainPane.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);

        Label ins = new Label();
        ins.setTextAlignment(TextAlignment.CENTER);
        ins.getStyleClass().setAll("h2", "strong");

        Line line = new Line();
        line.setStroke(Paint.valueOf("b8b8b8"));
        line.setEndX(400);

        Label mainLabel = new Label();
        if (guessed) {
            ins.setText("CONGRATULATIONS!");
            mainLabel.setText("           You won! \n The winning word was");
            mainLabel.getStyleClass().setAll("lead", "big-font");
        } else {
            ins.setText("GAME OVER!");
            mainLabel.setText("           You lost! \n The winning word was");
            mainLabel.getStyleClass().setAll("big-font");
        }
        Label winningWordLabel = new Label(winningWord.toUpperCase());
        winningWordLabel.getStyleClass().setAll("h2", "strong");

        Button playAgainButton = new Button("PLAY AGAIN");
        playAgainButton.getStyleClass().setAll("btn", "btn-primary");
        playAgainButton.setOnMouseClicked(me -> {
            mainPane.setVisible(false);
            mainPane.setTranslateX(0);
            mainPane.setTranslateY(0);
            game_ctrl.restart();
        });

        vbox.getChildren().addAll(ins, line, mainLabel, winningWordLabel, playAgainButton);
        mainPane.setCenter(vbox);
        mainPane.setStyle("-fx-background-color: rgba(255, 255, 255, 1); -fx-padding: 3;" +
                "-fx-border-color: #000000; -fx-border-width: 2px;" +
                "-fx-border-radius: 5; -fx-background-radius: 2;");
        mainPane.setVisible(true);

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

    public static void instructionNotification(BorderPane helpPane) {
        helpPane.setPrefWidth(500);
        helpPane.setPrefHeight(500);
        helpPane.setLayoutX(150);
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

        HBox secondWordHBox = giveExampleWord("PILLS", "present-letter-example", 1);
        Label secondWordLabel = new Label("The letter I is in the word but in the wrong spot.");
        secondWordLabel.getStyleClass().setAll("lead");

        HBox thirdWordHBox = giveExampleWord("VAGUE", "wrong-letter-example", 2);
        Label thirdWordLabel = new Label("The letter U is not in the word in any spot.");
        thirdWordLabel.getStyleClass().setAll("lead");

        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(ins, line, insParagraph, labelExample, firstWordHBox,
                firstWordLabel, secondWordHBox, secondWordLabel, thirdWordHBox,
                thirdWordLabel);


        helpPane.setCenter(vbox);
        helpPane.setStyle("-fx-background-color: rgba(255, 255, 255, 1); -fx-padding: 3;" +
                "-fx-border-color: #000000; -fx-border-width: 2px;" +
                "-fx-border-radius: 5; -fx-background-radius: 2;");
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
        notificationPane.setLayoutX(300);
        notificationPane.setLayoutY(250);
        notificationPane.setOpacity(0);
        notificationPane.setVisible(true);
        notificationPane.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());

        Text text = new Text(notification);
        text.setFill(Color.WHITE);
        text.getStyleClass().setAll("h2", "strong");

        notificationPane.setCenter(text);
        notificationPane.setStyle("-fx-background-radius: 5; " +
                "-fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 10;");

        FadeTransition fadeTransition = GameAnimations.fadeTrans(notificationPane, 0.75, 1, 750);
        fadeTransition.setOnFinished(e -> notificationPane.setVisible(false));
        fadeTransition.play();
    }
}