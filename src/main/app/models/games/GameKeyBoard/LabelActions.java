package models.games.GameKeyBoard;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public final class LabelActions {
    public static Label getLabel(GridPane gridPane, int searchRow, int searchColumn) {
        for (Node child : gridPane.getChildren()) {
            if (Integer.valueOf(searchRow).equals(GridPane.getRowIndex(child))
                    && Integer.valueOf(searchColumn).equals(GridPane.getColumnIndex(child))
                    && child instanceof Label label) {
                return label;
            }
        }
        return null;
    }

    public static Label getLabel(GridPane gridPane, String letter) {
        for (Node child : gridPane.getChildren()) {
            if (child instanceof Label label && letter.equalsIgnoreCase(label.getText())) {
                return label;
            }
        }
        return null;
    }

    public static String getLabelText(GridPane gridPane, int searchRow, int searchColumn) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label == null) {
            return null;
        }
        return label.getText().toLowerCase();
    }

    public static void setLabelText(GridPane gridPane, int searchRow, int searchColumn, String input) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label == null) {
            return;
        }
        label.setText(input.toUpperCase());
    }

    public static void setDefaultTile(GridPane gridPane, int searchRow, int searchColumn) {
        Label label = getLabel(gridPane, searchRow, searchColumn);
        if (label == null) {
            return;
        }
        label.getStyleClass().setAll("default-tile");
    }
}
