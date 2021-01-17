package main;

import daten.*;
import design.SatzCell;
import design.TagCell;
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

public class ProgrammController {


    @FXML
    private TextField programmNameField;
    @FXML
    private TextField programmBeschreibungField;
    @FXML
    private Button programmSpeichernBtn;
    @FXML
    private Button programmLoeschenBtn;

    private Programm aktuellesProgramm;
    private Programm tmpProgramm;
    private boolean isNew = false;

    public void setUpBinding(Programm programm, Parent programmDialog) {
        if (programm != null) {
            aktuellesProgramm = programm;
            isNew = false;
        } else {
            aktuellesProgramm = new Programm();
            isNew = true;
        }

        //tmpProgramm = (Programm) Methoden.deepCopy(aktuellesProgramm);
        tmpProgramm = aktuellesProgramm.makeTmpCopy();

        ListView<Tag> tageListView = (ListView) programmDialog.lookup("#tageListView");
        tageListView.setItems(tmpProgramm.getTage());
        tageListView.setCellFactory(new Callback<ListView<Tag>,
                                            ListCell<Tag>>() {
                                        @Override
                                        public ListCell<Tag> call(ListView<Tag> list) {
                                            return new TagCell();
                                        }
                                    }
        );


        programmNameField.textProperty().bindBidirectional(tmpProgramm.nameProperty());
        programmBeschreibungField.textProperty().bindBidirectional(tmpProgramm.beschreibungProperty());
    }

    public void programmSpeichern(ActionEvent event) {
        if (tmpProgramm.getValid().getCode() == 0) {
            aktuellesProgramm.setName(tmpProgramm.getName());
            aktuellesProgramm.setBeschreibung(tmpProgramm.getBeschreibung());
            aktuellesProgramm.setTage(tmpProgramm.getTage());
            if (isNew) {
                Main.getProgramme().add(aktuellesProgramm);
            }
            Stage stage = (Stage) programmSpeichernBtn.getScene().getWindow();
            stage.close();
            Main.saveDatenbank();
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);

            a.setTitle("Ungültige Eingabe");
            a.setHeaderText(tmpProgramm.getValid().getError());
            //TODO contenttext der warung abhängig von wirklich konkretem fehler machen
            // a.setContentText("");
            a.showAndWait();
        }
    }

    public void programmLoeschen(ActionEvent event) {
        //TODO warndialog

        Main.getProgramme().remove(aktuellesProgramm);

        Stage stage = (Stage) programmLoeschenBtn.getScene().getWindow();
        stage.close();
    }

    public void programmAbbrechen(ActionEvent event) {
        Stage stage = (Stage) programmLoeschenBtn.getScene().getWindow();
        stage.close();
    }

    public void tagErstellen(ActionEvent event) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("tag.fxml"));
        Parent tagDialog = fxmlloader.load();

        Stage stage = new Stage();

        TagController c = fxmlloader.getController();
        c.setUpBinding(null, tagDialog, tmpProgramm);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tag erstellen");
        stage.setScene(new Scene(tagDialog, 1080, 720));

        stage.show();
    }

    public void tagBearbeiten(MouseEvent event) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("tag.fxml"));
        Parent tagDialog = fxmlloader.load();

        Stage stage = new Stage();

        TagController c = fxmlloader.getController();

        Tag tag = (Tag) ((ListView) event.getSource()).getSelectionModel().getSelectedItem();

        c.setUpBinding(tag, tagDialog, tmpProgramm);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tag erstellen");
        stage.setScene(new Scene(tagDialog, 1080, 720));

        stage.show();
    }
}
