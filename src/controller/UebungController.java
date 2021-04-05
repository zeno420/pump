package controller;

import domain.Satz;
import domain.Uebung;
import design.SatzCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.Pump;

import java.io.IOException;

public class UebungController implements SetupableController<Uebung>  {

    @FXML
    private TextField uebungNameField;
    @FXML
    private TextField uebungBeschreibungField;
    @FXML
    private Button uebungSpeichernBtn;

    private Uebung aktuelleUebung;
    private Uebung tmpUebung;
    private boolean isNew = false;

    public void setUpBindingEdit(Uebung uebung, Parent uebungDialog) {
        if (uebung != null) {
            aktuelleUebung = uebung;
            isNew = false;
        } else {
            aktuelleUebung = new Uebung();
            isNew = true;
        }

        tmpUebung = aktuelleUebung.makeTmpCopy();

        ListView<Satz> masseSatzListView = (ListView) uebungDialog.lookup("#masseSatzListView");
        masseSatzListView.setItems(tmpUebung.getMasse());
        masseSatzListView.setCellFactory(new Callback<ListView<Satz>,
                                                 ListCell<Satz>>() {
                                             @Override
                                             public ListCell<Satz> call(ListView<Satz> list) {
                                                 return new SatzCell();
                                             }
                                         }
        );
        ListView<Satz> defiSatzListView = (ListView) uebungDialog.lookup("#defiSatzListView");
        defiSatzListView.setItems(tmpUebung.getDefi());
        defiSatzListView.setCellFactory(new Callback<ListView<Satz>,
                                                ListCell<Satz>>() {
                                            @Override
                                            public ListCell<Satz> call(ListView<Satz> list) {
                                                return new SatzCell();
                                            }
                                        }
        );

        uebungNameField.textProperty().bindBidirectional(tmpUebung.nameProperty());
        uebungBeschreibungField.textProperty().bindBidirectional(tmpUebung.beschreibungProperty());
    }

    public void uebungSpeichern(ActionEvent event) {
        String error;
        if (isNew) {
            error = Pump.datenbasis.uebungHinzufuegen(aktuelleUebung, tmpUebung);
        } else {
            error = Pump.datenbasis.uebungUpdaten(aktuelleUebung, tmpUebung);
        }
        speichernAlarmieren(error);
    }

    private void speichernAlarmieren(String error) {
        if (error == null) {
            Stage stage = (Stage) uebungSpeichernBtn.getScene().getWindow();
            stage.close();
        } else {
            new SpeicherAlert(Alert.AlertType.WARNING, error);
        }
    }

    public void uebungAbbrechen(ActionEvent event) {
        Stage stage = (Stage) uebungSpeichernBtn.getScene().getWindow();
        stage.close();
    }

    public void satzErstellen(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../fxml/satz.fxml"));
        Parent satzDialog = fxmlloader.load();

        Stage stage = new Stage();

        Boolean masse = ((Button) event.getSource()).getId().equalsIgnoreCase("masseSatzBtn");

        SatzController c = fxmlloader.getController();
        c.setUpBinding(masse, null, satzDialog, tmpUebung);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Satz erstellen");
        stage.setScene(new Scene(satzDialog));

        stage.show();
    }

    public void satzBearbeiten(Satz satz) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../fxml/satz.fxml"));
        Parent satzDialog = fxmlloader.load();

        Stage stage = new Stage();

        SatzController c = fxmlloader.getController();

        c.setUpBinding(null, satz, satzDialog, tmpUebung);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Satz erstellen");
        stage.setScene(new Scene(satzDialog));

        stage.show();
    }

    public void satzLoeschen(Satz satz) {
        if (satz.isMasse()) {
            tmpUebung.getMasse().remove(satz);
        } else {
            tmpUebung.getDefi().remove(satz);
        }
    }
}