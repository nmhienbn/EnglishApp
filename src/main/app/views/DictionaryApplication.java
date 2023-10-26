package views;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DictionaryApplication extends Application {
    public static void main(String[] args) throws Exception {
        TestAPI.SetupDict();

        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Parent root = File_loader.getInstance().fxml_mainPanel();

        if (root == null)
            System.out.println("null");

        Scene scene = new Scene(root);
        // scene.getStylesheets().add(File_loader.getInstance().get_css("main_dictionary_tab.css"));
        stage.setTitle("hi");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
