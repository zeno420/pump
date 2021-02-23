package main;

import daten.Satz;
import daten.Uebung;
import daten.Workout;
import design.SatzCell;
import design.UebungAnzeigenCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class WorkoutController {

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
    private ListView satzListView;
    @FXML
    private Label uebungNameLabel;
    @FXML
    private Label workoutNameLabel;
    @FXML
    private Label indexLabel;

    public void setUpBinding(Workout workout, Parent workoutDialog) {

        if (workout != null) {
            aktuellesWorkout = workout;
            isNew = false;
        } else {
            aktuellesWorkout = new Workout();
            isNew = true;
        }

        //tmpWorkout = (Workout) Methoden.deepCopy(aktuellesWorkout);
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
        uebungComboBox.setItems(Main.getUebungen());
        uebungComboBox.setCellFactory(new Callback<ListView<Uebung>,
                                              ListCell<Uebung>>() {
                                          @Override
                                          public ListCell<Uebung> call(ListView<Uebung> list) {
                                              return new UebungAnzeigenCell();
                                          }
                                      }
        );
    }

    public void setUpBindingPlay(Workout workout, Parent workoutDialog) {

        aktuellesWorkout = workout;
        aktuellerWorkoutDialog = workoutDialog;

        ListView<Satz> satzListView = (ListView) workoutDialog.lookup("#satzListView");
        //TODO getSaetze abhängig von masse defi toggle
        satzListView.setItems(workout.getUebungen().get(workout.getCurrentUebungIndex()).getMasse());
        satzListView.setCellFactory(new Callback<ListView<Satz>,
                                            ListCell<Satz>>() {
                                        @Override
                                        public ListCell<Satz> call(ListView<Satz> list) {
                                            return new SatzCell();
                                        }
                                    }
        );
        workoutNameLabel.textProperty().bind(workout.nameProperty());
        uebungNameLabel.textProperty().bind(workout.getUebungen().get(workout.currentUebungIndexProperty().get()).nameProperty());
        indexLabel.textProperty().bind(workout.currentUebungIndexProperty().asString());
    }

    public void workoutSpeichern(ActionEvent event) {
        //TODO initial speichern ohne eingabe gibt nullpointer
        if (tmpWorkout.getValid().getCode() == 0) {
            aktuellesWorkout.setName(tmpWorkout.getName());
            aktuellesWorkout.setBeschreibung(tmpWorkout.getBeschreibung());
            //TODO set get uebung erzeugt falsche uebungsobjekte -> änderung in orig uebung kommt nicht in uebung innerhalb workout an
            aktuellesWorkout.setUebungen(tmpWorkout.getUebungen());
            if (isNew) {
                Main.getWorkouts().add(aktuellesWorkout);
            }
            Stage stage = (Stage) workouSpeichernBtn.getScene().getWindow();
            stage.close();
            Main.saveDatenbank();
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);

            a.setTitle("Ungültige Eingabe");
            a.setHeaderText(tmpWorkout.getValid().getError());
            //TODO contenttext der warung abhängig von wirklich konkretem fehler machen
            // a.setContentText("");
            a.showAndWait();

        }
    }

    public void workoutAbbrechen(ActionEvent event) {
        Stage stage = (Stage) workouSpeichernBtn.getScene().getWindow();
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

    public void nextUebung(ActionEvent event) throws IOException {
        aktuellesWorkout.increaseAktuelleUebung();
        setUpBindingPlay(aktuellesWorkout, aktuellerWorkoutDialog);
    }

    public void previousUebung(ActionEvent event) throws IOException {
        aktuellesWorkout.decreaseAktuelleUebung();
        setUpBindingPlay(aktuellesWorkout, aktuellerWorkoutDialog);
    }
}
