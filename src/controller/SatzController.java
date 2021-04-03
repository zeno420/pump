package controller;

import daten.Datenbank;
import daten.Satz;
import daten.Uebung;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.Pump;

public class SatzController {

    @FXML
    private TextField satzWiederholungenField;
    @FXML
    private TextField satzGewichtField;
    @FXML
    private Button satzSpeichernBtn;

    private Satz aktuellerSatz;
    private Satz tmpSatz;
    private boolean isNew = false;
    private Uebung uebung;

    public void setUpBinding(Boolean masse, Satz satz, Parent satzDialog, Uebung uebung) {

        if (satz != null) {
            aktuellerSatz = satz;
            isNew = false;
        } else {
            aktuellerSatz = new Satz();
            aktuellerSatz.setMasse(masse);
            isNew = true;
        }
        this.uebung = uebung;

        tmpSatz = aktuellerSatz.makeTmpCopy();

        satzWiederholungenField.textProperty().bindBidirectional(tmpSatz.wiederholungenProperty());
        satzGewichtField.textProperty().bindBidirectional(tmpSatz.gewichtProperty());

    }

    public void satzSpeichern(ActionEvent event) {
        if (tmpSatz.getValid().getCode() == 0) {
            aktuellerSatz.aenderbareMemberUebertragen(tmpSatz.getAenderbareMember());

            if (isNew) {
                if (tmpSatz.isMasse()) {
                    uebung.getMasse().add(aktuellerSatz);
                } else {
                    uebung.getDefi().add(aktuellerSatz);
                }
            }
            Stage stage = (Stage) satzSpeichernBtn.getScene().getWindow();
            stage.close();
            try {
                Datenbank.save(Pump.datenbasis);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Could not save data");
                //alert.setContentText();
                e.printStackTrace(System.out);
                alert.showAndWait();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);

            a.setTitle("Ungültige Eingabe");
            a.setHeaderText(tmpSatz.getValid().getError());
            a.showAndWait();
        }
    }

    public void satzAbbrechen(ActionEvent event) {
        Stage stage = (Stage) satzSpeichernBtn.getScene().getWindow();
        stage.close();
    }
}
