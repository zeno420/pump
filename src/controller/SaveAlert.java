package controller;

import javafx.scene.control.Alert;

public class SaveAlert extends Alert {
    public SaveAlert(AlertType alertType, String error) {
        super(alertType);
        super.setTitle("Invalid input or Database error");
        super.setHeaderText(error);
        super.showAndWait();
    }
}