package controllers.DictTab;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;
import controllers.AppControllers;

public class MainDictionaryTab_ctrl extends AppControllers {
    @FXML
    ToggleButton search_button;

    @FXML
    ToggleButton favorite_button;

    @FXML
    ToggleButton history_button;

    @FXML
    TextField search_box;

    @FXML
    Button speak_button;

    @FXML
    ListView<String> word_list_box;

    @FXML
    Button add_word_button;

    @FXML
    Button remove_word_button;

    @FXML
    Button edit_word_button;

    @FXML
    Button save_edit_button;

    @FXML
    ToggleButton favorite_toggle_button;

    @FXML
    Button reset_dictionary_button;

    //? wifa = word information area
    @FXML
    Label wifa_word;
    @FXML
    TextArea wifa_meaning_raw;
    @FXML
    TextFlow wifa_meaning;
    @FXML
    ScrollPane wifa_scrollpane;

    private boolean editing = false;

    String current_word = null;
    String current_meaning = null;

    //? search, history, favorite
    final ToggleGroup SHF_group = new ToggleGroup();

    @FXML
    protected void initialize() {
        new SearchArea().init(this);
        new WordInfoArea(this).init();
        new AllButtonSetup().init_function_button(this);
        new EditWord(this).setup();
        new SHFGroup(this).setup();

        var buttons = new ButtonBase[]{search_button, favorite_button,
                history_button, speak_button, add_word_button, remove_word_button,
                edit_word_button, save_edit_button, favorite_toggle_button,
                reset_dictionary_button};
        new AllButtonSetup().setup_all_tooltips(buttons);
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }
}
