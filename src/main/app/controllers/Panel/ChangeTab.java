package controllers.Panel;

import controllers.DictTab.MainDictionaryTab_ctrl;
import controllers.Games.CTW_ctrl;
import controllers.Games.GameTab_ctrl;
import controllers.Games.Quizz_ctrl;
import controllers.Games.Wordle_ctrl;
import controllers.GoogleTab.GoogleTab_ctrl;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import views.File_loader;

public class ChangeTab {
//    private static final Parent homeTab = File_loader.getInstance().
//            fxml_loadTab("front_end/fxml/home_tab.fxml", null);
//    private static final Parent mainDictionaryTab = File_loader.getInstance().
//            fxml_loadTab("front_end/fxml/main_dictionary.fxml", new MainDictionaryTab_ctrl());
//    private static final Parent gameTab = File_loader.getInstance().
//            fxml_loadTab("front_end/fxml/game_tab.fxml", new GameTab_ctrl());
//    private static final Parent googleTab = File_loader.getInstance().
//            fxml_loadTab("front_end/fxml/google_tab.fxml", new GoogleTab_ctrl());
//    private static final Parent quizzTab = File_loader.getInstance().
//            fxml_loadTab("front_end/fxml/games/quizz.fxml", new Quizz_ctrl());
//    private static final Parent wordleTab = File_loader.getInstance().
//            fxml_loadTab("front_end/fxml/games/wordle.fxml", new Wordle_ctrl());
//    private static final Parent ctwTab = File_loader.getInstance().
//            fxml_loadTab("front_end/fxml/games/ctw.fxml", new CTW_ctrl());

    public static void HomeTab(BorderPane mainPane) {
        mainPane.setCenter(File_loader.getInstance().
                fxml_loadTab("front_end/fxml/home_tab.fxml", null));
    }

    public static void DictionaryTab(BorderPane mainPane) {
        mainPane.setCenter(File_loader.getInstance().
                fxml_loadTab("front_end/fxml/main_dictionary.fxml", new MainDictionaryTab_ctrl()));
    }

    public static void GoogleTab(BorderPane mainPane) {
        mainPane.setCenter(File_loader.getInstance().
                fxml_loadTab("front_end/fxml/google_tab.fxml", new GoogleTab_ctrl()));
    }

    public static void GameTab(BorderPane mainPane) {
        mainPane.setCenter(File_loader.getInstance().
                fxml_loadTab("front_end/fxml/game_tab.fxml", new GameTab_ctrl()));
    }

    public static Quizz_ctrl QuizzTab(BorderPane mainPane) {
        var controller = new Quizz_ctrl();
        mainPane.setCenter(File_loader.getInstance().
                fxml_loadTab("front_end/fxml/games/quizz.fxml", controller));
        return controller;
    }

    public static Wordle_ctrl WordleTab(BorderPane mainPane) {
        var controller = new Wordle_ctrl();
        mainPane.setCenter(File_loader.getInstance().
                fxml_loadTab("front_end/fxml/games/wordle.fxml", controller));
        return controller;
    }

    public static CTW_ctrl CTWTab(BorderPane mainPane) {
        var controller = new CTW_ctrl();
        mainPane.setCenter(File_loader.getInstance().
                fxml_loadTab("front_end/fxml/games/ctw.fxml", controller));
        return controller;
    }
}
