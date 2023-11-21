package views.controllers;

import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import views.File_loader;
import views.animations.GameAnimations.AnimatedGif;
import views.controllers.games_ctrl.CTW_ctrl;
import views.controllers.games_ctrl.MainGame_ctrl;
import views.controllers.games_ctrl.Quizz_ctrl;
import views.controllers.games_ctrl.Wordle_ctrl;

import java.io.IOException;

public class mainPanel_ctrl {

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
    private Button main_dictionary_button;
    @FXML
    private Button google_translate_button;
    @FXML
    private Button game_button;

    private Button focused_button = null;

    Parent home_tab;
    Parent main_dictionary_tab;
    Parent google_translator_tab;
    public Parent game_tab;

    ColorAdjust colorAdjust = new ColorAdjust();

    @FXML
    void initialize() throws IOException {
        colorAdjust.setSaturation(-1);
        AnimatedGif ani = new AnimatedGif(getClass().getResource("/front_end/graphic/icons/mainPanel/logo.gif").
                toExternalForm(), 5000);
        logoImg.setGraphic(ani.getView());
        setTooltip(logoImg, "BILINGO");
        ani.setCycleCount(Animation.INDEFINITE);
        ani.setAutoReverse(true);
        ani.play();

        setNavButton(home_button, "Home");
        setNavButton(main_dictionary_button, "Main Dictionary");
        setNavButton(google_translate_button, "Google Translate");
        setNavButton(game_button, "Game");

        setButton(home_button, "/front_end/graphic/icons/mainPanel/home.gif", 2000);
        setButton(main_dictionary_button, "/front_end/graphic/icons/mainPanel/book.gif", 2000);
        setButton(google_translate_button, "/front_end/graphic/icons/mainPanel/google_translate.gif", 2000);
        setButton(game_button, "/front_end/graphic/icons/mainPanel/game.gif", 2000);

        home_tab = File_loader.getInstance().fxml_homeTab();
        main_dictionary_tab = File_loader.getInstance().fxml_mainDictionaryTab();
        google_translator_tab = File_loader.getInstance().fxml_google_translate_Tab();
        game_tab = File_loader.getInstance().fxml_mainGameTab();

        //mainPane.setCenter(home_tab);
        OnButtonPress(home_button);

        MainGame_ctrl.mainPanelCtrl = this;
        Wordle_ctrl.mainPanelCtrl = this;
        CTW_ctrl.mainPanelCtrl = this;
        Quizz_ctrl.mainPanelCtrl = this;

        setTitleBarEvent();
    }

    public void setButton(Button button, String filename, double durationMs) {
        AnimatedGif ani = new AnimatedGif(getClass().getResource(filename).toExternalForm(), durationMs);
        button.setGraphic(ani.getView());
        ani.getView().setEffect(colorAdjust);
        button.setOnMousePressed(e -> {
            ani.getView().setEffect(null); // Remove the effect
            ani.playOnce();
        });
    }

    private void setNavButton(Button button, String text) {
        Tooltip tt = new Tooltip(text);
        button.setOnAction(e -> {
            OnButtonPress(button);
        });
        button.setTooltip(tt);
        tt.setShowDelay(new Duration(.1));
        tt.setOnShown(s -> {
            //Get button current bounds on computer screen
            Bounds bounds = button.localToScreen(button.getBoundsInLocal());
            tt.setX(bounds.getMaxX() - 5);
            tt.setY(bounds.getCenterY() - tt.getHeight() / 2);
        });
        tt.getStyleClass().add("navbutton-tooltip");
    }

    private void setTooltip(Node node, String text) {
        Tooltip tt = new Tooltip(text);
        Tooltip.install(node, tt);
        tt.setShowDelay(new Duration(.1));
        tt.setOnShown(s -> {
            //Get button current bounds on computer screen
            Bounds bounds = node.localToScreen(node.getBoundsInLocal());
            tt.setX(bounds.getMaxX() - 5);
            tt.setY(bounds.getCenterY() - tt.getHeight() / 2);
        });
        tt.getStyleClass().add("navbutton-tooltip");
    }

    private void OnButtonPress(Button button) {
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
        else if (button == main_dictionary_button)
            mainPane.setCenter(main_dictionary_tab);
        else if (button == google_translate_button)
            mainPane.setCenter(google_translator_tab);
        else if (button == game_button)
            mainPane.setCenter(game_tab);
    }

    final Delta dragDelta = new Delta();
    private void setTitleBarEvent(){
        closeButton.setOnAction(e -> {
            closeButton.getScene().getWindow().hide();
        });

        minimizeButton.setOnAction(e -> {
            Stage stage = (Stage) minimizeButton.getScene().getWindow();
            stage.setIconified(true);
        });

        titleBar.setOnMousePressed(evt -> {
            Stage stage = (Stage) titleBar.getScene().getWindow();
            dragDelta.x = stage.getX() - evt.getScreenX();
            dragDelta.y = stage.getY() - evt.getScreenY();
        });

        titleBar.setOnMouseDragged(evt -> {
            Stage stage = (Stage) titleBar.getScene().getWindow();
            stage.setX(evt.getScreenX() + dragDelta.x);
            stage.setY(evt.getScreenY() + dragDelta.y);
        });
    }

    static class Delta { double x, y; }
}
