package views.games;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import org.kordamp.bootstrapfx.BootstrapFX;
import views.File_loader;
import views.animations.GameAnimations;
import views.controllers.games_ctrl.CTW_ctrl;
import views.controllers.games_ctrl.Game_ctrl;
import views.controllers.games_ctrl.Wordle_ctrl;

import java.io.File;


public class GameNotification {
    static Media media = new Media(new File(System.getProperty("user.dir") +
            "\\src\\main\\resources\\audio\\click.mp3").toURI().toString());
    static MediaPlayer mediaPlayer = new MediaPlayer(media);

    static Media win = new Media(new File(System.getProperty("user.dir") +
            "\\src\\main\\resources\\audio\\win.mp3").toURI().toString());
    static MediaPlayer mediaWin = new MediaPlayer(win);

    static Media lose = new Media(new File(System.getProperty("user.dir") +
            "\\src\\main\\resources\\audio\\lose.mp3").toURI().toString());
    static MediaPlayer mediaLose = new MediaPlayer(lose);


    private GameNotification() {
    }

    public static Wordle_ctrl wordle_ctrl;
    public static CTW_ctrl ctw_ctrl;
    private static double xOffset = 0;
    private static double yOffset = 0;

    private static void setMouseDrag(BorderPane mainPane) {
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
            mediaWin.play();
            mediaWin.setOnEndOfMedia(() -> {
                mediaWin.stop();
                mediaWin.seek(Duration.ZERO);
            });
            ins.setText("CONGRATULATIONS!");
            mainLabel.setText(messages[0]);
        } else {
            mediaLose.play();
            mediaLose.setOnEndOfMedia(() -> {
                mediaLose.stop();
                mediaLose.seek(Duration.ZERO);
            });
            ins.setText("GAME OVER!");
            mainLabel.setText(messages[1]);
        }
        mainLabel.getStyleClass().setAll("lead", "big-font");
        mainLabel.setTextFill(Paint.valueOf("FFFFFF"));

        Label answer = new Label(winningWord.toUpperCase());
        answer.getStyleClass().setAll("h1", "strong");
        answer.setTextFill(Paint.valueOf("FFFFFF"));

        ImageView playAgainButton = new ImageView(new Image("game/images/play_again.png",
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
            mediaPlayer.play();
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

        setMouseDrag(mainPane);
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
        Parent tmp = File_loader.getInstance().fxml_loadTab(
                "front_end/fxml/games/instructions/wordle_ins.fxml", null);
        helpPane.setCenter(tmp);
        setMouseDrag(helpPane);
    }

    public static void instructionCTW(BorderPane helpPane) {
        helpPane.setPrefWidth(500);
        helpPane.setPrefHeight(400);
        helpPane.setLayoutX(250);
        helpPane.setLayoutY(80);

        Parent tmp = File_loader.getInstance().fxml_loadTab(
                "front_end/fxml/games/instructions/ctw_ins.fxml", null);
        helpPane.setCenter(tmp);
        setMouseDrag(helpPane);
    }

    public static void instructionQuizz(BorderPane helpPane) {
        helpPane.setPrefWidth(500);
        helpPane.setPrefHeight(400);
        helpPane.setLayoutX(250);
        helpPane.setLayoutY(80);

        Parent tmp = File_loader.getInstance().fxml_loadTab(
                "front_end/fxml/games/instructions/quizz_ins.fxml", null);
        helpPane.setCenter(tmp);
        setMouseDrag(helpPane);
    }

    public static void fadedNotification(BorderPane notificationPane, String notification) {
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