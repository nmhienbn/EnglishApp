package views.controllers.DictTab;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import views.DictFacade.DictFacade;

public class WordInfoArea {
    MainDictionaryTab_ctrl dictCtrl;

    public WordInfoArea(MainDictionaryTab_ctrl dictCtrl) {
        this.dictCtrl = dictCtrl;
    }

    void init() {
        var wifa_meaning_raw = dictCtrl.wifa_meaning_raw;
        var wifa_meaning = dictCtrl.wifa_meaning;
        var wifa_scrollpane = dictCtrl.wifa_scrollpane;

        wifa_meaning_raw.setDisable(true);
        wifa_meaning_raw.setEditable(false);
        wifa_meaning_raw.setVisible(false);
        wifa_scrollpane.setFitToWidth(true);

        update_word_info_area(null, null, null);
    }

    void update_word_info_area(String word, String meaning, Parent caller) {
        var wifa_word = dictCtrl.wifa_word;
        var wifa_meaning_raw = dictCtrl.wifa_meaning_raw;
        var wifa_meaning = dictCtrl.wifa_meaning;
        var speak_button = dictCtrl.speak_button;

        for (Node node : wifa_meaning.getChildren()) {
            if (node instanceof Text text)
                text.setVisible(false);
        }
        wifa_meaning.getChildren().clear();
        wifa_meaning.requestLayout();
        wifa_meaning.requestFocus();
        if (caller != null) caller.requestFocus();

        if (word == null) {
            speak_button.setDisable(true);
            wifa_word.setText("");
            wifa_meaning_raw.setText("");
            return;
        }
        speak_button.setDisable(false);
        wifa_word.setText(word);
        wifa_meaning_raw.setText(DictFacade.Dict.getMeaning(word));

        String[] lines = meaning.split("\n");

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;
            char firstChar = line.charAt(0);

            switch (firstChar) {
                case '/' -> wifa_meaning.getChildren().add(highlightPronunciation(word, line));
                case '*' -> wifa_meaning.getChildren().add(highlightType(line));
                case '=' -> wifa_meaning.getChildren().add(highlightExample(line));
                case '+' -> wifa_meaning.getChildren().add(highlightExampleImply(line));
                case '-' -> wifa_meaning.getChildren().add(highlightMeaning(line));
                default -> wifa_meaning.getChildren().add(defaultHighlight(line));
            }
        }
    }


    static Text defaultHighlight(String line) {
        Text text = new Text();
        text.getStyleClass().add("word-info-text");
        text.getStyleClass().add("undefined");
        text.setText(line + '\n');
        text.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 20.0));
        text.setFill(Color.BLACK);
        return text;
    }

    static Text highlightMeaning(String line) {
        Text text = new Text();
        text.getStyleClass().add("word-info-text");
        text.getStyleClass().add("meaning");
        text.setText(line + '\n');
        text.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 20.0));
        text.setFill(Color.BLACK);
        return text;
    }

    static Text highlightExampleImply(String line) {
        Text text = new Text();
        text.getStyleClass().add("word-info-text");
        text.getStyleClass().add("example");
        String[] parts = line.split("\\+");

        StringBuilder strb = new StringBuilder();
        //String str = "   Ex: " + parts[0].trim();
        for (String part : parts) {
            //str += "\n   " + "  => " + parts[i].trim();
            strb.append("   ").append("  => ").append(part.trim()).append("\n");
        }
        text.setText(strb.toString());

        text.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 18.0));
        text.setFill(Color.BLUE);
        return text;
    }

    static Text highlightExample(String line) {
        Text text = new Text();
        text.getStyleClass().add("word-info-text");
        text.getStyleClass().add("example");
        String[] parts = line.replace("=", "").split("\\+");
        if (parts.length == 1) {
            String str = "   Ex: " + parts[0].trim();
            text.setText(str + '\n');
        } else {
            StringBuilder strb = new StringBuilder();
            strb.append("   Ex: ").append(parts[0].trim());
            for (int i = 1; i < parts.length; i++) {
                strb.append("\n   ").append("  => ").append(parts[i].trim());
            }
            text.setText(strb.toString() + '\n');
        }
        text.setFont(Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 18.0));
        text.setFill(Color.BLUE);
        return text;
    }

    static Text highlightType(String line) {
        Text text = new Text();
        text.getStyleClass().add("word-info-text");
        text.getStyleClass().add("type");
        line = line.replace("*", "").trim();
        text.setText(line + '\n');
        text.setFont(Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 22.0));
        text.setFill(Color.RED);
        text.setUnderline(true);
        return text;
    }

    static Text highlightPronunciation(String word, String line) {
        Text text = new Text();
        text.getStyleClass().add("word-info-text");
        text.getStyleClass().add("pronounce");
        text.setText(word + "  -  " + line + '\n');
        text.setFont(Font.font("System", FontWeight.THIN, FontPosture.ITALIC, 20.0));
        text.setFill(Color.BLACK);
        return text;
    }

}
