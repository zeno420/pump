package main;

import daten.Methoden;
import daten.Satz;
import daten.Uebung;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SatzController {

    @FXML
    private TextField satzWiederholungenField;
    @FXML
    private TextField satzGewichtField;
    @FXML
    private Button satzSpeichernBtn;
    @FXML
    private Button satzLoeschenBtn;

    private Satz aktuellerSatz;
    private Satz tmpSatz;
    private boolean isNew = false;
    private Uebung uebung;

    public void setUpBinding(Satz satz, Parent satzDialog, Uebung uebung) {

        if (satz != null) {
            aktuellerSatz = satz;
            isNew = false;
        } else {
            aktuellerSatz = new Satz();
            isNew = true;
        }
        this.uebung = uebung;
        tmpSatz = (Satz) Methoden.deepCopy(aktuellerSatz);
        satzWiederholungenField.textProperty().bindBidirectional(tmpSatz.wiederholungenProperty());
        satzGewichtField.textProperty().bindBidirectional(tmpSatz.gewichtProperty());

    }

    public void masseSatzSpeichern(ActionEvent event) {
        aktuellerSatz.setWiederholungen(tmpSatz.getWiederholungen());
        aktuellerSatz.setGewicht(tmpSatz.getGewicht());
        if (isNew) {
            uebung.getMasse().add(aktuellerSatz);
        }
        Stage stage = (Stage) satzSpeichernBtn.getScene().getWindow();
        stage.close();
    }

    public void masseSatzLoeschen(ActionEvent event) {

        uebung.getMasse().remove(aktuellerSatz);
        Stage stage = (Stage) satzSpeichernBtn.getScene().getWindow();
        stage.close();
    }

    public void defiSatzSpeichern(ActionEvent event) {
        aktuellerSatz.setWiederholungen(tmpSatz.getWiederholungen());
        aktuellerSatz.setGewicht(tmpSatz.getGewicht());
        if (isNew) {
            uebung.getDefi().add(aktuellerSatz);
        }
        Stage stage = (Stage) satzSpeichernBtn.getScene().getWindow();
        stage.close();
    }

    public void defiSatzLoeschen(ActionEvent event) {

        uebung.getDefi().remove(aktuellerSatz);
        Stage stage = (Stage) satzSpeichernBtn.getScene().getWindow();
        stage.close();
    }

    public void satzAbbrechen(ActionEvent event) {
        Stage stage = (Stage) satzLoeschenBtn.getScene().getWindow();
        stage.close();
    }
}
