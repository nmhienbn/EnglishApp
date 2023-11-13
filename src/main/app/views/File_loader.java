package views;

import java.io.IOException;
import java.net.URL;

import views.controllers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class File_loader {

    private static File_loader instance = null;

    public static File_loader getInstance() {
        if (instance == null)
            instance = new File_loader();
        return instance;
    }

    private File_loader() {
    }

    public URL getUrl(String path) {
        return getClass().getClassLoader().getResource(path);
    }

    // * use this to handle exception
    private Parent FXMLloader_load(FXMLLoader loader) {
        try {
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String get_css(String name) {
        return getUrl("front_end/css/" + name).toExternalForm();
    }

    public Parent fxml_mainPanel() {
        FXMLLoader loader = new FXMLLoader(getUrl("front_end/fxml/main.fxml"));

        Object controllerObject = new mainPanel_ctrl();
        loader.setController(controllerObject);
        return FXMLloader_load(loader);
    }

    public Parent fxml_homeTab() {
        FXMLLoader loader = new FXMLLoader(getUrl("front_end/fxml/hometab.fxml"));

        // Object controllerObject = new mainPanel_ctrl();
        // loader.setController(controllerObject);
        return FXMLloader_load(loader);
    }

    public Parent fxml_mainDictionaryTab() {
        FXMLLoader loader = new FXMLLoader(getUrl("front_end/fxml/main_dictionary.fxml"));

        Object controllerObject = new mainDictionaryTab_ctrl();
        loader.setController(controllerObject);
        return FXMLloader_load(loader);
    }

    public Parent fxml_google_translate_Tab() {
        FXMLLoader loader = new FXMLLoader(getUrl("front_end/fxml/google_tab.fxml"));

        Object controllerObject = new GoogleTab_ctrl();
        loader.setController(controllerObject);
        return FXMLloader_load(loader);
    }

    public Parent fxml_quizz_Tab() {
        FXMLLoader loader = new FXMLLoader(getUrl("front_end/fxml/quizz.fxml"));

        Object controllerObject = new Quizz_ctrl();
        loader.setController(controllerObject);
        return FXMLloader_load(loader);
    }

    public Parent fxml_wordle_Tab(Object controllerObject) {
        try {
            FXMLLoader loader = new FXMLLoader(getUrl("front_end/fxml/wordle.fxml"));
            loader.setController(controllerObject);
            return FXMLloader_load(loader);
        } catch (Exception e) {
            return null;
        }
    }

    public Parent fxml_ctw_Tab(Object controllerObject) {
        try {
            FXMLLoader loader = new FXMLLoader(getUrl("front_end/fxml/ctw.fxml"));
            loader.setController(controllerObject);
            return FXMLloader_load(loader);
        } catch (Exception e) {
            return null;
        }
    }

}
