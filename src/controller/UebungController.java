package controller;

import daten.Datenbank;
import daten.Satz;
import daten.Uebung;
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
import java.util.List;
import java.util.stream.Collectors;

public class UebungController {

    @FXML
    private TextField uebungNameField;
    @FXML
    private TextField uebungBeschreibungField;
    @FXML
    private Button uebungSpeichernBtn;

    private Uebung aktuelleUebung;
    private Uebung tmpUebung;
    private boolean isNew = false;

    //List<String> exisitngNamesList;

    public void setUpBinding(Uebung uebung, Parent uebungDialog) {
        if (uebung != null) {
            aktuelleUebung = uebung;
            isNew = false;
        } else {
            aktuelleUebung = new Uebung();
            isNew = true;
        }

        /*exisitngNamesList = Pump.datenbasis.getUebungen().stream().map(Uebung::getName).collect(Collectors.toList());
        if (!isNew) {
            exisitngNamesList.remove(uebung.getName());
        }*/

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

  /*  public void uebungSpeichern(ActionEvent event) {
        if (tmpUebung.getValid(exisitngNamesList).getCode() == 0) {
            aktuelleUebung.aenderbareMemberUebertragen(tmpUebung.getAenderbareMember());
            if (isNew) {
                Pump.datenbasis.getUebungen().add(aktuelleUebung);
            }
            Stage stage = (Stage) uebungSpeichernBtn.getScene().getWindow();
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
            a.setHeaderText(tmpUebung.getValid(exisitngNamesList).getError());
            a.showAndWait();
        }
    }*/

    public void uebungSpeichern(ActionEvent event) {
        if (isNew) {
            String error = Pump.datenbasis.uebungHinzufuegen(aktuelleUebung, tmpUebung);
            speichernAlarmieren(error);
        } else {
            String error = Pump.datenbasis.uebungUpdaten(aktuelleUebung, tmpUebung);
            speichernAlarmieren(error);
        }
    }

    private void speichernAlarmieren(String error) {
        if (error == null) {
            Stage stage = (Stage) uebungSpeichernBtn.getScene().getWindow();
            stage.close();
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);

            a.setTitle("Ungültige Eingabe oder Datenbankfehler");
            a.setHeaderText(error);
            a.showAndWait();
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

    public void satzLoeschen(Satz satz) throws IOException {
        if (satz.isMasse()) {
            tmpUebung.getMasse().remove(satz);
        } else {
            tmpUebung.getDefi().remove(satz);
        }
    }
}