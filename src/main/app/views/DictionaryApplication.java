package views;

import controllers.Panel.MainPanel_ctrl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.facades.DictFacade;

public class DictionaryApplication extends Application {
    final long startTime = System.currentTimeMillis();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        Parent root = File_loader.getInstance().fxml_loadTab("front_end/fxml/main.fxml", new MainPanel_ctrl());

        if (root == null)
            System.out.println("null");

        setIcon(stage);

        Scene scene = null;
        String osName = System.getProperty("os.name");
        if (osName != null && osName.startsWith("Windows")) {

            //
            // Windows hack b/c unlike Mac and Linux, UNDECORATED doesn't include a shadow
            //
            scene = (new WindowsHack()).getShadowScene(root);
            stage.initStyle(StageStyle.TRANSPARENT);

        } else {
            scene = new Scene(root);
            stage.initStyle(StageStyle.UNDECORATED);
        }
        setCursor(scene);

        stage.setTitle("BILINGO");
        stage.setScene(scene);

        stage.setResizable(false);
        //TODO set resizable
        //stage.setMinWidth(1100);
        //stage.setMinHeight(700);
        stage.show();
        System.out.println("Init time: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    // Set app icon
    private void setIcon(Stage stage) {
        stage.getIcons().add(new Image("front_end/graphic/icons/icon.png"));
    }

    // Load your custom cursor image
    private void setCursor(Scene scene) {
        Image cursorImage = new Image("front_end/graphic/icons/download.gif");
        ImageCursor cursor = new ImageCursor(cursorImage);
        scene.setCursor(cursor);
    }

    public static class WindowsHack {

        public Scene getShadowScene(Parent p) {
            Scene scene;
            VBox outer = new VBox();
            outer.getChildren().add(p);
            outer.setPadding(new Insets(10.0d));
            outer.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0), new CornerRadii(0), new
                    Insets(0))));

            p.setEffect(new DropShadow());
            ((BorderPane) p).setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), new Insets(0)
            )));

            scene = new Scene(outer);
            scene.setFill(Color.rgb(0, 255, 0, 0));
            return scene;
        }
    }
}
