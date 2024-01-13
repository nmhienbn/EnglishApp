package controllers.DictTab;

import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.util.Duration;

public class DictPopup {

    static final double DEFAULT_POPUP_TIME = 1.2;
    final Popup popup;
    final Label label;
    final int width;
    final int height;

    public DictPopup(Popup popup, Label label, int width, int height) {
        this.popup = popup;
        this.label = label;
        this.width = width;
        this.height = height;
    }

    public static DictPopup popup_word_not_exist(String word) {
        Popup popup = new Popup();
        popup.setAutoHide(true);
        Label label = new Label("Error: Word does not exists");
        System.out.println("Word does not exists, WORD: \"" + word + "\"");
        return new DictPopup(popup, label, 400, 100);
    }

    public static DictPopup popup_exist(String word) {
        Popup popup = new Popup();
        popup.setAutoHide(true);
        Label label = new Label("Error: Word exists\n\"" + word + "\"");
        System.out.println("Word exists, WORD: \"" + word + "\"");
        return new DictPopup(popup, label, 400, 100);
    }

    public static DictPopup popup_word_updated(String word) {
        Popup popup = new Popup();
        popup.setAutoHide(true);
        Label label = new Label("Word's meaning updated\n\"" + word + "\"");
        return new DictPopup(popup, label, 400, 100);
    }

    public static DictPopup popup_word_added(String word) {
        Popup popup = new Popup();
        popup.setAutoHide(true);
        Label label = new Label("Added word to dictionary\n\"" + word + "\"");
        return new DictPopup(popup, label, 400, 100);
    }

    public static DictPopup popup_word_not_added(String word) {
        Popup popup = new Popup();
        popup.setAutoHide(true);
        Label label = new Label(
                "Error: Cannot add word to dictionary\n" + "\"" + word + "\"");
        return new DictPopup(popup, label, 500, 150);
    }

    public static DictPopup popup_word_removed(String word) {
        Popup popup = new Popup();
        popup.setAutoHide(true);
        Label label = new Label("Removed word from dictionary\n\"" + word + "\"");
        return new DictPopup(popup, label, 400, 100);
    }
    
    public void showPopup(MainDictionaryTab_ctrl dictCtrl) {
        label.setTextAlignment(TextAlignment.CENTER);
        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setFont(new Font(25));
        label.setTextFill(Color.WHITE);
        label.setBackground(new Background(new BackgroundFill(Color.color(0, 0, 0, 1), new CornerRadii(10), null)));
        label.setPrefSize(width, height);

        PauseTransition delay = new PauseTransition(Duration.seconds(DEFAULT_POPUP_TIME));
        delay.setOnFinished(event -> popup.hide());
        delay.play();

        popup.getContent().add(label);
        popup.show(dictCtrl.wifa_word.getScene().getWindow());
    }
}
