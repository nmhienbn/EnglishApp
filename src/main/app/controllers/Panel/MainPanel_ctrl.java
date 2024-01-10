package controllers.Panel;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import views.File_loader;
import controllers.AppControllers;
import controllers.DictTab.MainDictionaryTab_ctrl;
import controllers.Games.*;
import controllers.GoogleTab.GoogleTab_ctrl;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static controllers.Panel.PanelButton.colorAdjust;

public class MainPanel_ctrl extends AppControllers {

    @FXML
    public BorderPane mainPane;
    @FXML
    public HBox titleBar;
    @FXML
    public Button closeButton;
    @FXML
    public Button minimizeButton;
    @FXML
    private Button logoImg;

    @FXML
    private Button home_button;
    @FXML
    private Button dict_button;
    @FXML
    private Button gg_dict_button;
    @FXML
    private Button game_button;

    private Button focused_button = null;

    Parent home_tab;
    Parent dict_tab;
    Parent gg_dict_tab;
    public Parent game_tab;


    @FXML
    protected void initialize() throws IOException {
        PanelButton panelButton = new PanelButton(this);
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        executorService.submit(() -> new Logo().init(logoImg));

        executorService.submit(() -> panelButton.init(home_button,
                "/front_end/graphic/icons/mainPanel/home.gif",
                2000, "Home"));
        executorService.submit(() -> panelButton.init(dict_button,
                "/front_end/graphic/icons/mainPanel/book.gif",
                2000, "Main Dictionary"));
        executorService.submit(() -> panelButton.init(gg_dict_button,
                "/front_end/graphic/icons/mainPanel/google_translate.gif",
                2000, "Google Translate"));
        executorService.submit(() -> panelButton.init(game_button,
                "/front_end/graphic/icons/mainPanel/game.gif",
                2000, "Game"));

        executorService.submit(() -> {
            home_tab = File_loader.getInstance().
                    fxml_loadTab("front_end/fxml/hometab.fxml", null);
        });
        executorService.submit(() -> {
            dict_tab = File_loader.getInstance().
                    fxml_loadTab("front_end/fxml/main_dictionary.fxml", new MainDictionaryTab_ctrl());
        });
        executorService.submit(() -> {
            gg_dict_tab = File_loader.getInstance().
                    fxml_loadTab("front_end/fxml/google_tab.fxml", new GoogleTab_ctrl());
        });
        executorService.submit(() -> {
            GameTab_ctrl.mainPanelCtrl = this;
            game_tab = File_loader.getInstance().
                    fxml_loadTab("front_end/fxml/game_tab.fxml", new GameTab_ctrl());
        });

        executorService.submit(() -> new TitleBar(this).setTitleBarEvent());



        executorService.shutdown();
        try {
            if (executorService.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.NANOSECONDS)) {
                onButtonPress(home_button);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    void onButtonPress(Button button) {
        System.out.println("choose tab: " + button.getTooltip().getText());
        if (focused_button == button) {
            return;
        }

        if (focused_button != null) {
            focused_button.getStyleClass().remove("focused");
            focused_button.getGraphic().setEffect(colorAdjust);
        }
        focused_button = button;
        focused_button.getStyleClass().add("focused");
        focused_button.getGraphic().setEffect(null);

        if (button == home_button)
            mainPane.setCenter(home_tab);
        else if (button == dict_button)
            mainPane.setCenter(dict_tab);
        else if (button == gg_dict_button)
            mainPane.setCenter(gg_dict_tab);
        else if (button == game_button)
            mainPane.setCenter(game_tab);
    }
}
