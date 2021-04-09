package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class DeleteAlert extends Alert {

    public DeleteAlert(AlertType alertType) {
        super(alertType);
        super.setTitle("Are you sure?");

    }

    public Optional<ButtonType> customizeDeleteAlert(StringBuilder warnText) {

        if (warnText != null) this.setContentText(warnText.toString());

        Button lb = (Button) this.getDialogPane().lookupButton(ButtonType.OK);
        lb.setText("delete");
        lb.setDefaultButton(false);
        Button cb = (Button) this.getDialogPane().lookupButton(ButtonType.CANCEL);
        cb.setText("cancel");
        cb.setDefaultButton(true);

        return this.showAndWait();
    }

}
