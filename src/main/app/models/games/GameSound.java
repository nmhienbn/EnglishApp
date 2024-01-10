package models.games;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class GameSound {
    public static MediaPlayer mediaClick;
    public static MediaPlayer mediaWin;
    public static MediaPlayer mediaLose;
    public static MediaPlayer mediaKeyboardPress;

    static {
        Media click = new Media(new File(System.getProperty("user.dir") +
                "\\src\\main\\resources\\audio\\click.mp3").toURI().toString());
        mediaClick = new MediaPlayer(click);
        mediaClick.setOnEndOfMedia(() -> {
            mediaClick.stop();
            mediaClick.seek(Duration.ZERO);
        });

        Media win = new Media(new File(System.getProperty("user.dir") +
                "\\src\\main\\resources\\audio\\win.mp3").toURI().toString());
        mediaWin = new MediaPlayer(win);
        mediaWin.setOnEndOfMedia(() -> {
            mediaWin.stop();
            mediaWin.seek(Duration.ZERO);
        });

        Media lose = new Media(new File(System.getProperty("user.dir") +
                "\\src\\main\\resources\\audio\\lose.mp3").toURI().toString());
        mediaLose = new MediaPlayer(lose);
        mediaLose.setOnEndOfMedia(() -> {
            mediaLose.stop();
            mediaLose.seek(Duration.ZERO);
        });

        Media keyboardPress = new Media(new File(System.getProperty("user.dir") +
                "\\src\\main\\resources\\audio\\keyboard.mp3").toURI().toString());
        mediaKeyboardPress = new MediaPlayer(keyboardPress);
        mediaKeyboardPress.setOnEndOfMedia(() -> {
            mediaKeyboardPress.stop();
            mediaKeyboardPress.seek(Duration.ZERO);
        });
    }
}
