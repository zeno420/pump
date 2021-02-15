package main;

import daten.*;
import design.PlayWorkoutCell;
import design.SatzCell;
import design.TagCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    private Label programmNameLabel;
    @FXML
    private Label tagNameLabel;
    @FXML
    private Button programmSpeichernBtn;
    @FXML
    private Button programmLoeschenBtn;
    @FXML
    private Label indexLabel;

    private Programm aktuellesProgramm;
    private Parent aktuellerProgrammDialog;
    private Programm tmpProgramm;
    private boolean isNew = false;

    public void setUpBindingEdit(Programm programm, Parent programmDialog) {
        if (programm != null) {
            aktuellesProgramm = programm;
            isNew = false;
        } else {
            aktuellesProgramm = new Programm();
            isNew = true;
        }

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

    public void setUpBindingPlay(Programm programm, Parent programmDialog) {
        //TODO
        aktuellesProgramm = programm;
        aktuellerProgrammDialog = programmDialog;

        ListView<Workout> workoutListView = (ListView) programmDialog.lookup("#workoutListView");
        workoutListView.setItems(programm.getTage().get(programm.getCurrentTagIndex()).getWorkouts());
        workoutListView.setCellFactory(new Callback<ListView<Workout>,
                                               ListCell<Workout>>() {
                                           @Override
                                           public ListCell<Workout> call(ListView<Workout> list) {
                                               return new PlayWorkoutCell();
                                           }
                                       }
        );
        programmNameLabel.textProperty().bind(programm.nameProperty());
        tagNameLabel.textProperty().bind(programm.getTage().get(programm.currentTagIndexProperty().get()).nameProperty());
        indexLabel.textProperty().bind(programm.currentTagIndexProperty().asString());
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

    public void workoutSpielen(Workout workout) throws IOException {
        //TODO
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("programm_spielen.fxml"));
        Parent programmDialog = fxmlloader.load();

        Stage stage = new Stage();

        ProgrammController c = fxmlloader.getController();
        // c.setUpBindingPlay(programm, programmDialog);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Programm spielen");
        stage.setScene(new Scene(programmDialog, 1080, 720));

        stage.show();
    }

    public void nextTag(ActionEvent event) throws IOException {
        //TODO neu laden und index speichern
        aktuellesProgramm.increaseAktuellerTag();
        setUpBindingPlay(aktuellesProgramm, aktuellerProgrammDialog);
    }

    public void previousTag(ActionEvent event) throws IOException {
        //TODO neu laden
        aktuellesProgramm.decreaseAktuellerTag();
        setUpBindingPlay(aktuellesProgramm, aktuellerProgrammDialog);
    }
}
