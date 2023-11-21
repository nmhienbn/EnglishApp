package views;

import java.io.IOException;
import java.net.URL;

import views.controllers.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import views.controllers.MainGame_ctrl;
import views.controllers.games_ctrl.Quizz_ctrl;

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

    public Parent fxml_loadTab(String path, AppControllers controllerObject) {
        FXMLLoader loader = new FXMLLoader(getUrl(path));

        if (controllerObject != null) {
            loader.setController(controllerObject);
        }
        return FXMLloader_load(loader);
    }

}
