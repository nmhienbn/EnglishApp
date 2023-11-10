package views.wordle;

import javafx.animation.FadeTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
import views.controllers.WordleTab_ctrl;

public class ScoreWindow {

    private ScoreWindow() {
    }

    public static WordleTab_ctrl wordleTab_ctrl;

    public static void displayEndGame(boolean guessed, String winningWord, BorderPane mainPane) {
        mainPane.setPrefWidth(400);
        mainPane.setPrefHeight(260);
        mainPane.setLayoutX(200);
        mainPane.setLayoutY(100);
        mainPane.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);

        Label mainLabel = new Label();
        if (guessed) {
            mainLabel.setText("           You won! \n The winning word was");
            mainLabel.getStyleClass().setAll("lead", "big-font");
        } else {
            mainLabel.setText("           You lost! \n The winning word was");
            mainLabel.getStyleClass().setAll("big-font");
        }
        Label winningWordLabel = new Label(winningWord.toUpperCase());
        winningWordLabel.getStyleClass().setAll("h2", "strong");

        Button playAgainButton = new Button("PLAY AGAIN");
        playAgainButton.getStyleClass().setAll("btn", "btn-primary");
        playAgainButton.setOnMouseClicked(me -> {
            mainPane.setVisible(false);
            wordleTab_ctrl.restart();
        });

        vbox.getChildren().addAll(mainLabel, winningWordLabel, playAgainButton);
        mainPane.setCenter(vbox);
        mainPane.setStyle("-fx-background-color: rgba(255, 255, 255, 1); -fx-padding: 3;" +
                "-fx-border-color: #000000; -fx-border-width: 2px;" +
                "-fx-border-radius: 5; -fx-background-radius: 2;");
        mainPane.setVisible(true);
    }

    public static void createHelpWindow(BorderPane helpPane) {
        helpPane.setPrefWidth(490);
        helpPane.setPrefHeight(490);
        VBox vbox = new VBox(5);
        vbox.setPadding(new Insets(20, 20, 20, 20));

        Label ins = new Label("HOW TO PLAY");
        ins.setTextAlignment(TextAlignment.CENTER);
        ins.getStyleClass().setAll("h4", "strong");

        Line line = new Line();
        line.setStroke(Paint.valueOf("b8b8b8"));
        line.setEndX(490);

        Label helpParagraph = new Label("""
                • Guess the WORDLE in six tries.
                • Each guess must be a valid five-letter word.
                • Hit the enter button to submit.
                • After each guess, the color of the tiles will change to
                show how close your guess was to the word.""");
        helpParagraph.setTextAlignment(TextAlignment.LEFT);
        helpParagraph.getStyleClass().setAll("lead");
        helpParagraph.setStyle("-fx-line-spacing: -0.2em;");

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
        vbox.getChildren().addAll(ins, line, helpParagraph, labelExample, firstWordHBox,
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



    public static void showNotFoundWord(BorderPane notificationPane) {
        notificationPane.setPrefWidth(200);
        notificationPane.setPrefHeight(100);
        notificationPane.setLayoutX(300);
        notificationPane.setLayoutY(250);
        notificationPane.setOpacity(0);
        notificationPane.setVisible(true);

        Text text = new Text("Not in word list");
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