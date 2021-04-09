package controller;

import domain.*;
import design.ExecuteSetCell;
import design.DisplayExerciseInWorkoutCell;
import design.ExerciseCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.Pump;

import java.io.IOException;

public class WorkoutController implements SetupableController<Workout> {

    private Workout currentWorkout;
    private Parent currentWorkoutDialog;
    private Workout temporaryWorkout;
    private boolean isNew = false;
    private Exercise currentlyPlayedExercise;

    @FXML
    private ComboBox<Exercise> exerciseComboBox;
    @FXML
    private ListView<Exercise> workoutExercisesListView;

    @FXML
    private TextField workoutNameField;
    @FXML
    private TextField workoutDescriptionField;
    @FXML
    private Button saveWorkoutButton;
    @FXML
    private ListView<Set> setListView;
    @FXML
    private Label exerciseNameLabel;
    @FXML
    private Label indexLabel;
    @FXML
    private Button doneButton;

    public void setUpBindingEdit(Workout workout, Parent workoutDialog) {

        if (workout != null) {
            currentWorkout = workout;
            isNew = false;
        } else {
            currentWorkout = new Workout();
            isNew = true;
        }

        temporaryWorkout = currentWorkout.makeTmpCopy();

        setUpExercisesListView();
        setUpExercisesComboBox();
        setUpTextFields();

    }

    private void setUpTextFields() {
        workoutNameField.textProperty().bindBidirectional(temporaryWorkout.nameProperty());
        workoutDescriptionField.textProperty().bindBidirectional(temporaryWorkout.descriptionProperty());
    }

    private void setUpExercisesComboBox() {
        exerciseComboBox.setItems(Pump.databasis.getExecises());
        exerciseComboBox.setCellFactory(new Callback<ListView<Exercise>,
                                              ListCell<Exercise>>() {
                                          @Override
                                          public ListCell<Exercise> call(ListView<Exercise> list) {
                                              return new ExerciseCell();
                                          }
                                      }
        );
    }

    private void setUpExercisesListView() {
        workoutExercisesListView.setItems(temporaryWorkout.getExercises());
        workoutExercisesListView.setCellFactory(new Callback<ListView<Exercise>,
                                                       ListCell<Exercise>>() {
                                                   @Override
                                                   public ListCell<Exercise> call(ListView<Exercise> list) {
                                                       return new DisplayExerciseInWorkoutCell();
                                                   }
                                               }
        );
    }

    public void setUpBindingPlay(Workout workout, Parent workoutDialog) {

        currentWorkout = workout;
        currentWorkoutDialog = workoutDialog;
        currentlyPlayedExercise = workout.getExercises().get(workout.getCurrentExerciseIndex());

        setUpCurrentlyPlayedExerciseListView();
        setUpExerciseInfoText(workout);
    }

    private void setUpExerciseInfoText(Workout workout) {
        exerciseNameLabel.textProperty().bind(currentlyPlayedExercise.nameProperty());
        int currentUebungDisplayIndex = workout.getCurrentExerciseIndex() + 1;
        String uebungInfoText = "Übung " + currentUebungDisplayIndex + " von " + workout.getExercises().size();
        indexLabel.setText(uebungInfoText);
    }


    private void setUpCurrentlyPlayedExerciseListView() {
        setListView.setItems(currentlyPlayedExercise.getSets(Pump.databasis.getPhase().getBulk()));
        setListView.setCellFactory(new Callback<ListView<Set>,
                                            ListCell<Set>>() {
                                        @Override
                                        public ListCell<Set> call(ListView<Set> list) {
                                            return new ExecuteSetCell(currentlyPlayedExercise);
                                        }
                                    }
        );
    }

    public void saveWorkout(ActionEvent event) {
        String errorText;
        if (isNew) {
            errorText = Pump.databasis.addWorkout(currentWorkout, temporaryWorkout);
        } else {
            errorText = Pump.databasis.updateWorkout(currentWorkout, temporaryWorkout);
        }
        alertSaving(errorText);
    }

    private void alertSaving(String errorText) {
        if (errorText == null) {
            Stage stage = (Stage) saveWorkoutButton.getScene().getWindow();
            stage.close();
        } else {
            new SaveAlert(Alert.AlertType.WARNING, errorText);
        }
    }


    public void cancel(ActionEvent event) {
        Stage stage = (Stage) saveWorkoutButton.getScene().getWindow();
        stage.close();
    }

    public void addExerciseToWorkout(ActionEvent event) {
        Exercise exercise = exerciseComboBox.getSelectionModel().getSelectedItem();
        if (exercise == null) {
            return;
        }
        temporaryWorkout.getExercises().add(exercise);
    }

    public void removeExerciseFromWorkout(ActionEvent event) throws IOException {
        if (workoutExercisesListView.getSelectionModel().getSelectedIndex() >= 0) {
            temporaryWorkout.getExercises().remove(workoutExercisesListView.getSelectionModel().getSelectedIndex());
        }
    }

    public void nextExercise(ActionEvent event) throws IOException {
        currentWorkout.increaseCurrentExerciseIndex();
        setUpBindingPlay(currentWorkout, currentWorkoutDialog);
    }

    public void doneAndNextExercise(ActionEvent event) throws IOException {
        Pump.databasis.getExeciseLog().add(new LogEntry(currentlyPlayedExercise.getName(), currentWorkout.getDescription()));
        currentWorkout.increaseCurrentExerciseIndex();
        setUpBindingPlay(currentWorkout, currentWorkoutDialog);
    }

    public void previousExercise(ActionEvent event) throws IOException {
        currentWorkout.decreaseCurrentExerciseIndex();
        setUpBindingPlay(currentWorkout, currentWorkoutDialog);
    }

    public void done(ActionEvent event) {
        Pump.databasis.getWorkoutLog().add(new LogEntry(currentWorkout.getName(), currentWorkout.getDescription()));
        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
    }
}
