package views.games;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.kordamp.bootstrapfx.BootstrapFX;
import views.File_loader;
import views.animations.GameAnimations;
import views.controllers.Games.Game_ctrl;

import static views.games.GameSound.*;


public class GameNotification {

    private double xOffset = 0;
    private double yOffset = 0;

    private void setMouseDrag(BorderPane mainPane) {
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

    public void endGameNotification(boolean isWin, String winningWord,
                                    Game_ctrl game_ctrl, String[] messages) {
        String link;
        MediaPlayer mediaPlayer;
        if (isWin) {
            link = "front_end/fxml/games/win.fxml";
            mediaPlayer = mediaWin;
        } else {
            link = "front_end/fxml/games/lose.fxml";
            mediaPlayer = mediaLose;
        }
        BorderPane tmpPane = (BorderPane) File_loader.getInstance().fxml_loadTab(link, null);
        mediaPlayer.play();
        ((Label) tmpPane.lookup("#winMessage")).setText(messages[0]);
        ((Label) tmpPane.lookup("#winWord")).setText(winningWord.toUpperCase());

        ImageView playAgainButton = (ImageView) tmpPane.lookup("#playAgainButton");
        playAgainButton.setOnMouseEntered(e -> {
            ScaleTransition scaleTransition = GameAnimations.scaleTrans(playAgainButton, 1, 1.4, 150);
            scaleTransition.play();
        });
        playAgainButton.setOnMouseExited(e -> {
            ScaleTransition scaleTransition = GameAnimations.scaleTrans(playAgainButton, 1.4, 1, 150);
            scaleTransition.play();
        });
        playAgainButton.setOnMouseClicked(e -> {
            mediaClick.play();
            reallyRestart(game_ctrl);
        });


        BorderPane mainPane = game_ctrl.notificationPane;
        setPanePosition(mainPane, 400, 300, 300, 100);
        mainPane.setCenter(tmpPane);
        showNotification(game_ctrl.dimSc, mainPane);

        ScaleTransition scaleTransition = GameAnimations.scaleTrans(mainPane, 0, 1, 500);
        scaleTransition.play();

        setMouseDrag(mainPane);
    }

    public void reallyRestart(Game_ctrl game_ctrl) {
        hideNotification(game_ctrl.dimSc, game_ctrl.notificationPane);
        game_ctrl.restart();
    }

    public void showInstruction(Game_ctrl gameCtrl, String gameName) {
        mediaClick.play();
        BorderPane helpPane = gameCtrl.notificationPane;
        setPanePosition(helpPane, 500, 400, 250, 80);
        Parent tmp = File_loader.getInstance().fxml_loadTab(
                "front_end/fxml/games/instructions/" + gameName + "_ins.fxml", null);
        helpPane.setCenter(tmp);
        setMouseDrag(helpPane);
        showNotification(gameCtrl.dimSc, helpPane);
    }

    public void fadedNotification(BorderPane notificationPane, String notification) {
        setPanePosition(notificationPane, 200, 100, 400, 250);
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

    private void showNotification(Region dimSc, BorderPane notificationPane) {
        dimSc.toFront();
        dimSc.setVisible(true);
        notificationPane.toFront();
        notificationPane.setVisible(true);
    }

    public void hideNotification(Region dimSc, BorderPane notificationPane) {
        dimSc.setVisible(false);
        notificationPane.setVisible(false);
        notificationPane.setTranslateX(0);
        notificationPane.setTranslateY(0);
        notificationPane.setOnMousePressed(null);
        notificationPane.setOnMouseDragged(null);
    }

    private void setPanePosition(Pane pane, double prefWidth, double prefHeight, double layoutX, double layoutY) {
        pane.setPrefWidth(prefWidth);
        pane.setPrefHeight(prefHeight);
        pane.setLayoutX(layoutX);
        pane.setLayoutY(layoutY);
    }
}