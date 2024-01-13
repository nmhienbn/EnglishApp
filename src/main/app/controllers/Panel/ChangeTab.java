package controllers.Panel;

import controllers.DictTab.MainDictionaryTab_ctrl;
import controllers.Games.CTW_ctrl;
import controllers.Games.GameTab_ctrl;
import controllers.Games.Quizz_ctrl;
import controllers.Games.Wordle_ctrl;
import controllers.GoogleTab.GoogleTab_ctrl;
import javafx.scene.layout.BorderPane;
import models.facades.DictFacade;
import views.File_loader;

public class ChangeTab implements AutoCloseable {
    public ChangeTab() {
         DictFacade.Shutdown();
    }

    public void HomeTab(BorderPane mainPane) {
        mainPane.setCenter(File_loader.getInstance().
                fxml_loadTab("front_end/fxml/home_tab.fxml", null));
    }

    public void DictionaryTab(BorderPane mainPane) {
        mainPane.setCenter(File_loader.getInstance().
                fxml_loadTab("front_end/fxml/main_dictionary.fxml", new MainDictionaryTab_ctrl()));
    }

    public void GoogleTab(BorderPane mainPane) {
        mainPane.setCenter(File_loader.getInstance().
                fxml_loadTab("front_end/fxml/google_tab.fxml", new GoogleTab_ctrl()));
    }

    public void GameTab(BorderPane mainPane) {
        mainPane.setCenter(File_loader.getInstance().
                fxml_loadTab("front_end/fxml/game_tab.fxml", new GameTab_ctrl()));
    }

    public Quizz_ctrl QuizzTab(BorderPane mainPane) {
        var controller = new Quizz_ctrl();
        mainPane.setCenter(File_loader.getInstance().
                fxml_loadTab("front_end/fxml/games/quizz.fxml", controller));
        return controller;
    }

    public Wordle_ctrl WordleTab(BorderPane mainPane) {
        var controller = new Wordle_ctrl();
        mainPane.setCenter(File_loader.getInstance().
                fxml_loadTab("front_end/fxml/games/wordle.fxml", controller));
        return controller;
    }

    public CTW_ctrl CTWTab(BorderPane mainPane) {
        mainPane.setCenter(null);
        var controller = new CTW_ctrl();
        mainPane.setCenter(File_loader.getInstance().
                fxml_loadTab("front_end/fxml/games/ctw.fxml", controller));
        return controller;
    }

    @Override
    public void close() {
        System.gc();
    }
}
