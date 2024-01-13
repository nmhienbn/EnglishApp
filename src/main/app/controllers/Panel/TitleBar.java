package controllers.Panel;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.facades.DictFacade;

public class TitleBar {

    static class Delta {
        double x, y;
    }

    final Delta dragDelta = new Delta();
    final MainPanel_ctrl mainPanelCtrl;

    public TitleBar(MainPanel_ctrl mainPanelCtrl) {
        this.mainPanelCtrl = mainPanelCtrl;
    }

    void setTitleBarEvent() {
        closeAction(mainPanelCtrl.closeButton);
        minimizeAction(mainPanelCtrl.minimizeButton);
        dragAction(mainPanelCtrl.titleBar);
    }

    private void dragAction(HBox titleBar) {
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

    private static void minimizeAction(Button minimizeButton) {
        minimizeButton.setOnAction(e -> {
            Stage stage = (Stage) minimizeButton.getScene().getWindow();
            stage.setIconified(true);
        });
    }

    private static void closeAction(Button closeButton) {
        closeButton.setOnAction(e -> {
            closeButton.getScene().getWindow().hide();
            DictFacade.Shutdown();
            Platform.runLater(() -> {
                System.out.println("Exiting...");

                // Additional cleanup or finalization if needed

                // Exit the JavaFX application
                Platform.exit();

                // Terminate the Java Virtual Machine
                System.exit(0);
            });
        });
    }
}
