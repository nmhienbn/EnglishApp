package views;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DictionaryApplication extends Application {
    public static void main(String[] args){
        launch(args);
        System.out.println(
            ClassLoader.getSystemClassLoader().getResource("front_end/test.txt")
        );
        System.out.println("minh ok");
    }

    @Override
    public void start(Stage stage){
        Parent root=new AnchorPane();
        Scene scene=new Scene(root,600,600);

        stage.setScene(scene);
        stage.setTitle("ok");
        stage.show();

    }
}
