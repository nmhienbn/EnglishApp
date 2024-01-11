package views;

import java.io.IOException;
import java.net.URL;

import controllers.AppControllers;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class File_loader {

    private static File_loader instance = null;

    public static File_loader getInstance() {
        if (instance == null)
            instance = new File_loader();
        return instance;
    }

    public Parent fxml_loadTab(String path, AppControllers controllerObject) {
        FXMLLoader loader = new FXMLLoader(getUrl(path));

        if (controllerObject != null) {
            loader.setController(controllerObject);
        }

        try {
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File_loader() {
    }

    public URL getUrl(String path) {
        return getClass().getClassLoader().getResource(path);
    }

    public String get_css(String name) {
        return getUrl("front_end/css/" + name).toExternalForm();
    }

}
