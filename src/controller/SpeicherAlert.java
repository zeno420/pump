package controller;

import javafx.scene.control.Alert;

public class SpeicherAlert extends Alert {
    public SpeicherAlert(AlertType alertType, String error) {
        super(alertType);
        super.setTitle("Ungültige Eingabe oder Datenbankfehler");
        super.setHeaderText(error);
        super.showAndWait();
    }
}