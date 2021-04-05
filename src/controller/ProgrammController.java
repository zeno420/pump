package controller;

import domain.*;
import design.WorkoutSpielenCell;
import design.TagCell;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProgrammController implements SetupableController<Programm> {

    @FXML
    private TextField programmNameField;
    @FXML
    private TextField programmBeschreibungField;
    @FXML
    private Label tagNameLabel;
    @FXML
    private Button programmSpeichernBtn;
    @FXML
    private Label indexLabel;
    @FXML
    private Button fertigBtn;

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
        aktuellesProgramm = programm;
        aktuellerProgrammDialog = programmDialog;

        ListView<Workout> workoutListView = (ListView) programmDialog.lookup("#workoutListView");
        workoutListView.setItems(programm.getTage().get(programm.getCurrentTagIndex()).getWorkouts());
        workoutListView.setCellFactory(new Callback<ListView<Workout>,
                                               ListCell<Workout>>() {
                                           @Override
                                           public ListCell<Workout> call(ListView<Workout> list) {
                                               return new WorkoutSpielenCell();
                                           }
                                       }
        );
        tagNameLabel.textProperty().bind(programm.getTage().get(programm.currentTagIndexProperty().get()).nameProperty());
        int currentTag = programm.getCurrentTagIndex() + 1;
        String bla = "Tag " + currentTag + " von " + programm.getTage().size();
        indexLabel.setText(bla);
    }

    public void programmSpeichern(ActionEvent event) {
        String error;
        if (isNew) {
            error = Pump.datenbasis.programmHinzufuegen(aktuellesProgramm, tmpProgramm);
        } else {
            error = Pump.datenbasis.programmUpdaten(aktuellesProgramm, tmpProgramm);
        }
        speichernAlarmieren(error);
    }

    private void speichernAlarmieren(String error) {
        if (error == null) {
            Stage stage = (Stage) programmSpeichernBtn.getScene().getWindow();
            stage.close();
        } else {
            new SpeicherAlert(Alert.AlertType.WARNING, error);
        }
    }

    public void programmAbbrechen(ActionEvent event) {
        Stage stage = (Stage) programmSpeichernBtn.getScene().getWindow();
        stage.close();
    }

    public void tagErstellen(ActionEvent event) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../fxml/tag.fxml"));
        Parent tagDialog = fxmlloader.load();

        Stage stage = new Stage();

        TagController c = fxmlloader.getController();
        c.setUpBinding(null, tagDialog, tmpProgramm);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tag erstellen");
        stage.setScene(new Scene(tagDialog));

        stage.show();
    }

    public void tagBearbeiten(Tag tag) throws IOException {


        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../fxml/tag.fxml"));
        Parent tagDialog = fxmlloader.load();
        tagDialog.setUserData(fxmlloader.getController());

        Stage stage = new Stage();

        TagController c = fxmlloader.getController();
        c.setUpBinding(tag, tagDialog, tmpProgramm);


        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tag bearbeiten");
        stage.setScene(new Scene(tagDialog));

        stage.show();
    }

    public void tagLoeschen(Tag tag) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Achtung: wirklich löschen?");
        a.setHeaderText("Der Tag wird dauerhaft gelöscht!");
        Button lb = (Button) a.getDialogPane().lookupButton(ButtonType.OK);
        lb.setText("löschen");
        lb.setDefaultButton(false);
        Button cb = (Button) a.getDialogPane().lookupButton(ButtonType.CANCEL);
        cb.setText("abbrechen");
        cb.setDefaultButton(true);

        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK) {
            tmpProgramm.getTage().remove(tag);
        }
    }

    public void workoutSpielen(Workout workout) throws IOException {
        FXMLLoader workoutFxmlloader = new FXMLLoader(getClass().getResource("../fxml/workout_spielen.fxml"));
        Parent workoutDialog = workoutFxmlloader.load();

        FXMLLoader uebungFxmlloader = new FXMLLoader(getClass().getResource("../fxml/uebung.fxml"));
        Parent uebungDialog = uebungFxmlloader.load();

        Map<String, Object> map = new HashMap<>();
        map.put("workoutController", workoutFxmlloader.getController());
        map.put("uebungController", uebungFxmlloader.getController());

        workoutDialog.setUserData(map);

        Stage stage = new Stage();

        WorkoutController c = workoutFxmlloader.getController();
        c.setUpBindingPlay(workout, workoutDialog);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(workout.getName());
        stage.setScene(new Scene(workoutDialog));

        stage.show();
    }

    public void nextTag(ActionEvent event) throws IOException {
        aktuellesProgramm.increaseAktuellerTag();
        setUpBindingPlay(aktuellesProgramm, aktuellerProgrammDialog);
    }

    public void previousTag(ActionEvent event) throws IOException {
        aktuellesProgramm.decreaseAktuellerTag();
        setUpBindingPlay(aktuellesProgramm, aktuellerProgrammDialog);
    }

    public void fertig(ActionEvent event) {
        Stage stage = (Stage) fertigBtn.getScene().getWindow();
        stage.close();
    }

}
