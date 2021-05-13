package controller;

import domain.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Pump;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RootController {

    public void createExercise(ActionEvent event) throws IOException {

        EditDialogBuilder<Exercise> editDialogBuilder = new EditDialogBuilder<>();
        editDialogBuilder.setWindowTitle("Create exercise").setFxmlResource("/fxml/exercise.fxml").build().show();
    }

    public void editExercise(Exercise exercise) throws IOException {

        EditDialogBuilder<Exercise> editDialogBuilder = new EditDialogBuilder<>();
        editDialogBuilder.setWindowTitle("Edit exercise").setFxmlResource("/fxml/exercise.fxml").setEditableObject(exercise).build().show();
    }

    public void deleteExercise(Exercise exercise) {

        List<Workout> containingWorkoutList = new ArrayList<>();
        List<Workout> emptyAfterDeletionWorkoutList = new ArrayList<>();

        StringBuilder warnText = new StringBuilder();

        buildRemoveExerciseWarnText(exercise, containingWorkoutList, emptyAfterDeletionWorkoutList, warnText, Pump.databasis.getWorkouts());

        DeleteAlert deleteAlert = new DeleteAlert(Alert.AlertType.CONFIRMATION);
        String warnHeaderText = emptyAfterDeletionWorkoutList.size() + " workouts consist of ONLY that Exercise, " + (containingWorkoutList.size() - emptyAfterDeletionWorkoutList.size()) + " more workouts contain this exercise.";
        deleteAlert.setHeaderText(warnHeaderText);
        Optional<ButtonType> buttonResult = deleteAlert.customizeDeleteAlert(warnText);

        if (buttonResult.get() == ButtonType.OK) {
            removeExerciseFromDatabasis(exercise, containingWorkoutList, emptyAfterDeletionWorkoutList, Pump.databasis.getExecises());
        }
    }

    private void removeExerciseFromDatabasis(Exercise exercise, List<Workout> containingWorkoutList, List<Workout> emptyAfterDeletionWorkoutList, List<Exercise> databasisExerciseList) {
        databasisExerciseList.remove(exercise);
        for (Workout workout : containingWorkoutList) {
            while (workout.getExercises().contains(exercise)) {
                workout.getExercises().remove(exercise);
            }
        }
        for (Workout workout : emptyAfterDeletionWorkoutList) {
            deleteWorkout(workout, false);
        }
    }

    private void buildRemoveExerciseWarnText(Exercise exercise, List<Workout> containingWorkoutList, List<Workout> emptyAfterDeletionWorkoutList, StringBuilder warnText, List<Workout> databasisWorkoutList) {
        for (Workout workout : databasisWorkoutList) {
            if (workout.getExercises().contains(exercise)) {
                containingWorkoutList.add(workout);
                if (workout.getExercises().stream().allMatch(exercise::equals)) {
                    emptyAfterDeletionWorkoutList.add(workout);
                    warnText.append(workout.getName()).append(": will be empty after deleting this exercise and will be also deleted.");
                } else {
                    warnText.append(workout.getName()).append(": contains this exercise.");
                }
                warnText.append("\n");
            }
        }
    }

    public void createWorkout(ActionEvent event) throws IOException {

        EditDialogBuilder<Workout> editDialogBuilder = new EditDialogBuilder<>();
        editDialogBuilder.setWindowTitle("Create workout").setFxmlResource("/fxml/workout.fxml").build().show();
    }

    public void editWorkout(Workout workout) throws IOException {

        EditDialogBuilder<Workout> editDialogBuilder = new EditDialogBuilder<>();
        editDialogBuilder.setWindowTitle("create workout").setFxmlResource("/fxml/workout.fxml").setEditableObject(workout).build().show();
    }

    public void deleteWorkout(Workout workout, boolean manually) {

        List<Program> programList = Pump.databasis.getPrograms();
        List<Program> containingProgramList = new ArrayList<>();
        List<Day> containingDayList = new ArrayList<>();
        StringBuilder warnText = new StringBuilder();

        buildRemoveProgramWarnText(workout, programList, containingProgramList, containingDayList, warnText);

        Optional<ButtonType> buttonResult;

        if (manually) {
            DeleteAlert deleteAlert = new DeleteAlert(Alert.AlertType.CONFIRMATION);
            deleteAlert.setHeaderText(containingProgramList.size() + " programs contain this workout in " + containingDayList.size() + " days:");
            buttonResult = deleteAlert.customizeDeleteAlert(warnText);
        } else {
            buttonResult = Optional.of(ButtonType.OK);
        }
        if (buttonResult.get() == ButtonType.OK) {
            removeWorkoutFromDatabasis(workout, Pump.databasis.getWorkouts(), containingDayList);
        }
    }

    private void removeWorkoutFromDatabasis(Workout workout, List<Workout> databasisWorkoutList, List<Day> containingDayList) {
        databasisWorkoutList.remove(workout);
        for (Day t : containingDayList) {
            while (t.getWorkouts().contains(workout)) {
                t.getWorkouts().remove(workout);
            }
        }
    }

    private void buildRemoveProgramWarnText(Workout workout, List<Program> programList, List<Program> containingProgramList, List<Day> containingDayList, StringBuilder warnung) {
        for (Program p : programList) {
            for (Day t : p.getDays()) {
                if (t.getWorkouts().contains(workout)) {
                    if (!containingProgramList.contains(p)) {
                        containingProgramList.add(p);
                        warnung.append(p.getName());
                        warnung.append("\n");
                    }
                    containingDayList.add(t);
                }
            }
        }
    }

    public void createProgram(ActionEvent event) throws IOException {

        EditDialogBuilder<Program> editDialogBuilder = new EditDialogBuilder<>();
        editDialogBuilder.setWindowTitle("Create program").setFxmlResource("/fxml/program.fxml").build().show();
    }

    public void editProgram(Program program) throws IOException {

        EditDialogBuilder<Program> editDialogBuilder = new EditDialogBuilder<>();
        editDialogBuilder.setWindowTitle("Edit program").setFxmlResource("/fxml/program.fxml").setEditableObject(program).build().show();
    }

    public void deleteProgram(Program program) {

        DeleteAlert deleteAlert = new DeleteAlert(Alert.AlertType.CONFIRMATION);

        deleteAlert.setHeaderText("This program will be delted permanently!");

        Optional<ButtonType> buttonResult = deleteAlert.customizeDeleteAlert(null);

        if (buttonResult.get() == ButtonType.OK) {
            Pump.databasis.getPrograms().remove(program);
        }
    }

    public void executeProgram(Program program) throws IOException {

        //TODO builder
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/execute_program.fxml"));
        Parent programDialog = fxmlloader.load();
        programDialog.setUserData(fxmlloader.getController());

        Stage stage = new Stage();

        ProgramController controller = fxmlloader.getController();
        controller.setUpBindingPlay(program, programDialog);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(program.getName());
        stage.setScene(new Scene(programDialog));

        stage.show();
    }

    public void openStatistics() throws IOException {

        //TODO builder
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/statistic.fxml"));
        Parent statistikDialog = fxmlloader.load();
        statistikDialog.setUserData(fxmlloader.getController());

        Stage stage = new Stage();

        StatisticController controller = fxmlloader.getController();
        controller.setUpBinding();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Statistics");
        stage.setScene(new Scene(statistikDialog));

        stage.show();
    }
}