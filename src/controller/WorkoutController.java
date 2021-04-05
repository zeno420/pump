package controller;

import domain.*;
import design.SatzSpielenCell;
import design.UebungAnzeigenCell;
import design.UebungCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.Pump;

import java.io.IOException;

public class WorkoutController implements SetupableController<Workout> {

    private Workout aktuellesWorkout;
    private Parent aktuellerWorkoutDialog;
    private Workout tmpWorkout;
    private boolean isNew = false;

    public ComboBox<Uebung> uebungComboBox;
    public ListView<Uebung> workoutUebungenListView;

    @FXML
    private TextField workoutNameField;
    @FXML
    private TextField workoutBeschreibungField;
    @FXML
    private Button workouSpeichernBtn;
    @FXML
    private ListView<Satz> satzListView;
    @FXML
    private Label uebungNameLabel;
    @FXML
    private Label indexLabel;
    @FXML
    private Button fertigBtn;

    public void setUpBindingEdit(Workout workout, Parent workoutDialog) {

        if (workout != null) {
            aktuellesWorkout = workout;
            isNew = false;
        } else {
            aktuellesWorkout = new Workout();
            isNew = true;
        }
        
        tmpWorkout = aktuellesWorkout.makeTmpCopy();

        workoutUebungenListView = (ListView) workoutDialog.lookup("#workoutUebungenListView");
        workoutUebungenListView.setItems(tmpWorkout.getUebungen());
        workoutUebungenListView.setCellFactory(new Callback<ListView<Uebung>,
                                                       ListCell<Uebung>>() {
                                                   @Override
                                                   public ListCell<Uebung> call(ListView<Uebung> list) {
                                                       return new UebungAnzeigenCell();
                                                   }
                                               }
        );

        workoutNameField.textProperty().bindBidirectional(tmpWorkout.nameProperty());
        workoutBeschreibungField.textProperty().bindBidirectional(tmpWorkout.beschreibungProperty());

        uebungComboBox = (ComboBox) workoutDialog.lookup("#uebungComboBox");
        uebungComboBox.setItems(Pump.datenbasis.getUebungen());
        uebungComboBox.setCellFactory(new Callback<ListView<Uebung>,
                                              ListCell<Uebung>>() {
                                          @Override
                                          public ListCell<Uebung> call(ListView<Uebung> list) {
                                              return new UebungCell();
                                          }
                                      }
        );
    }

    public void setUpBindingPlay(Workout workout, Parent workoutDialog) {

        aktuellesWorkout = workout;
        aktuellerWorkoutDialog = workoutDialog;

        satzListView.setItems(workout.getUebungen().get(workout.getCurrentUebungIndex()).getSaetze(Pump.datenbasis.getPhase().isMasse()));
        satzListView.setCellFactory(new Callback<ListView<Satz>,
                                            ListCell<Satz>>() {
                                        @Override
                                        public ListCell<Satz> call(ListView<Satz> list) {
                                            return new SatzSpielenCell();
                                        }
                                    }
        );

        uebungNameLabel.textProperty().bind(workout.getUebungen().get(workout.currentUebungIndexProperty().get()).nameProperty());
        int currentUebung = workout.getCurrentUebungIndex() + 1;
        String bla = "Übung " + currentUebung + " von " + workout.getUebungen().size();
        indexLabel.setText(bla);
    }

    public void workoutSpeichern(ActionEvent event) {
        String error;
        if (isNew) {
            error = Pump.datenbasis.workoutHinzufuegen(aktuellesWorkout, tmpWorkout);
        } else {
            error = Pump.datenbasis.workoutUpdaten(aktuellesWorkout, tmpWorkout);
        }
        speichernAlarmieren(error);
    }

    private void speichernAlarmieren(String error) {
        if (error == null) {
            Stage stage = (Stage) workouSpeichernBtn.getScene().getWindow();
            stage.close();
        } else {
            new SpeicherAlert(Alert.AlertType.WARNING, error);
        }
    }


    public void workoutAbbrechen(ActionEvent event) {
        Stage stage = (Stage) workouSpeichernBtn.getScene().getWindow();
        stage.close();
    }

    public void uebungZuWorkoutHinzufuegen(ActionEvent event) {
        Uebung uebung = uebungComboBox.getSelectionModel().getSelectedItem();
        if (uebung == null) {
            return;
        }
        tmpWorkout.getUebungen().add(uebung);
    }

    public void uebungEntfernen(ActionEvent event) throws IOException {
        if (workoutUebungenListView.getSelectionModel().getSelectedIndex() >= 0) {
            tmpWorkout.getUebungen().remove(workoutUebungenListView.getSelectionModel().getSelectedIndex());
        }
    }

    public void nextUebung(ActionEvent event) throws IOException {
        aktuellesWorkout.increaseAktuelleUebung();
        setUpBindingPlay(aktuellesWorkout, aktuellerWorkoutDialog);
    }

    public void nextUebungDone(ActionEvent event) throws IOException {
        Uebung uebung = aktuellesWorkout.getUebungen().get(aktuellesWorkout.currentUebungIndexProperty().get());
        Pump.datenbasis.getUebungLog().add(new LogEintrag(uebung.getName(), uebung.getBeschreibung()));
        aktuellesWorkout.increaseAktuelleUebung();
        setUpBindingPlay(aktuellesWorkout, aktuellerWorkoutDialog);
    }

    public void previousUebung(ActionEvent event) throws IOException {
        aktuellesWorkout.decreaseAktuelleUebung();
        setUpBindingPlay(aktuellesWorkout, aktuellerWorkoutDialog);
    }

    public void fertig(ActionEvent event) {
        Pump.datenbasis.getWorkoutLog().add(new LogEintrag(aktuellesWorkout.getName(), aktuellesWorkout.getBeschreibung()));
        Stage stage = (Stage) fertigBtn.getScene().getWindow();
        stage.close();
    }
}
