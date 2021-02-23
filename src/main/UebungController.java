package main;

import daten.Programm;
import daten.Satz;
import daten.Uebung;
import design.SatzCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

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

    public void setUpBinding(Uebung uebung, Parent uebungDialog) {
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
        if (tmpUebung.getValid().getCode() == 0) {
            aktuelleUebung.setName(tmpUebung.getName());
            aktuelleUebung.setBeschreibung(tmpUebung.getBeschreibung());
            aktuelleUebung.setMasse(tmpUebung.getMasse());
            aktuelleUebung.setDefi(tmpUebung.getDefi());
            if (isNew) {
                Main.getUebungen().add(aktuelleUebung);
            }
            Stage stage = (Stage) uebungSpeichernBtn.getScene().getWindow();
            stage.close();
            Main.saveDatenbank();
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);

            a.setTitle("Ungültige Eingabe");
            a.setHeaderText(tmpUebung.getValid().getError());
            //TODO contenttext der warung abhängig von wirklich konkretem fehler machen
            // a.setContentText("");
            a.showAndWait();
        }
    }

    public void uebungAbbrechen(ActionEvent event) {
        Stage stage = (Stage) uebungSpeichernBtn.getScene().getWindow();
        stage.close();
    }

    public void masseSatzErstellen(ActionEvent event) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("masse_satz.fxml"));
        Parent satzDialog = fxmlloader.load();

        Stage stage = new Stage();

        SatzController c = fxmlloader.getController();
        c.setUpBinding(null, satzDialog, tmpUebung);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Satz erstellen");
        stage.setScene(new Scene(satzDialog, 1080, 720));

        stage.show();
    }

    public void masseSatzBearbeiten(Satz satz) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("masse_satz.fxml"));
        Parent satzDialog = fxmlloader.load();

        Stage stage = new Stage();

        SatzController c = fxmlloader.getController();

        c.setUpBinding(satz, satzDialog, tmpUebung);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Satz erstellen");
        stage.setScene(new Scene(satzDialog, 1080, 720));

        stage.show();
    }

    public void programmBearbeiten(Programm programm) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("programm.fxml"));
        Parent programmDialog = fxmlloader.load();

        Stage stage = new Stage();

        ProgrammController c = fxmlloader.getController();
        //Programm programm = (Programm) ((ListView)event.getSource()).getSelectionModel().getSelectedItem();
        c.setUpBindingEdit(programm, programmDialog);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Programm bearbeiten");
        stage.setScene(new Scene(programmDialog, 1080, 720));

        stage.show();
    }


    public void defiSatzErstellen(ActionEvent event) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("defi_satz.fxml"));
        Parent satzDialog = fxmlloader.load();

        Stage stage = new Stage();

        SatzController c = fxmlloader.getController();
        c.setUpBinding(null, satzDialog, tmpUebung);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Satz für die Massephase erstellen");
        stage.setScene(new Scene(satzDialog, 1080, 720));

        stage.show();
    }

    public void defiSatzBearbeiten(MouseEvent event) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("defi_satz.fxml"));
        Parent satzDialog = fxmlloader.load();

        Stage stage = new Stage();

        SatzController c = fxmlloader.getController();

        Satz satz = (Satz) ((ListView) event.getSource()).getSelectionModel().getSelectedItem();

        c.setUpBinding(satz, satzDialog, tmpUebung);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Satz für die Definitionsphase erstellen");
        stage.setScene(new Scene(satzDialog, 1080, 720));

        stage.show();
    }
}