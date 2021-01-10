package main;

import daten.Methoden;
import daten.Uebung;
import daten.Workout;
import design.UebungCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class WorkoutController {

    private Workout aktuellesWorkout;
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
    private Button workoutLoeschenBtn;

    public void setUpBinding(Workout workout, Parent workoutDialog) {

        if (workout != null) {
            aktuellesWorkout = workout;
            isNew = false;
        } else {
            aktuellesWorkout = new Workout();
            isNew = true;
        }

        tmpWorkout = (Workout) Methoden.deepCopy(aktuellesWorkout);

        workoutUebungenListView = (ListView) workoutDialog.lookup("#workoutUebungenListView");
        workoutUebungenListView.setItems(tmpWorkout.getUebungen());
        workoutUebungenListView.setCellFactory(new Callback<ListView<Uebung>,
                                                       ListCell<Uebung>>() {
                                                   @Override
                                                   public ListCell<Uebung> call(ListView<Uebung> list) {
                                                       return new UebungCell();
                                                   }
                                               }
        );

        workoutNameField.textProperty().bindBidirectional(tmpWorkout.nameProperty());
        workoutBeschreibungField.textProperty().bindBidirectional(tmpWorkout.beschreibungProperty());

        uebungComboBox = (ComboBox) workoutDialog.lookup("#uebungComboBox");
        uebungComboBox.setItems(Main.getUebungen());
        uebungComboBox.setCellFactory(new Callback<ListView<Uebung>,
                                              ListCell<Uebung>>() {
                                          @Override
                                          public ListCell<Uebung> call(ListView<Uebung> list) {
                                              return new UebungCell();
                                          }
                                      }
        );
    }

    public void workoutSpeichern(ActionEvent event) {
        //TODO initial speichern ohne eingabe gibt nullpointer
        tmpWorkout.isValid();
        if (tmpWorkout.getValid().getCode() == 0) {
            aktuellesWorkout.setName(tmpWorkout.getName());
            aktuellesWorkout.setBeschreibung(tmpWorkout.getBeschreibung());
            aktuellesWorkout.setUebungen(tmpWorkout.getUebungen());
            if (isNew) {
                Main.getWorkouts().add(aktuellesWorkout);
            }
            Stage stage = (Stage) workouSpeichernBtn.getScene().getWindow();
            stage.close();
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);

            a.setTitle("Ungültige Eingabe");
            a.setHeaderText(tmpWorkout.getValid().getError());
            //TODO contenttext der warung abhängig von wirklich konkretem fehler machen
           // a.setContentText("");
            a.showAndWait();

        }
    }

    public void workoutLoeschen(ActionEvent event) {
        //TODO warndialog

        Main.getWorkouts().remove(aktuellesWorkout);

        Stage stage = (Stage) workoutLoeschenBtn.getScene().getWindow();
        stage.close();
    }

    public void workoutAbbrechen(ActionEvent event) {
        Stage stage = (Stage) workoutLoeschenBtn.getScene().getWindow();
        stage.close();
    }

    public void uebungZuWorkoutHinzufuegen(ActionEvent event) {
        //TODO add mit index vll dann kann manned nur hinten anfügen
        Uebung uebung = (Uebung) uebungComboBox.getSelectionModel().getSelectedItem();
        tmpWorkout.getUebungen().add(uebung);
    }

    public void uebungEntfernen(ActionEvent event) throws IOException {
        tmpWorkout.getUebungen().remove(workoutUebungenListView.getSelectionModel().getSelectedIndex());
    }
}
