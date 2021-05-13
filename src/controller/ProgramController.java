package controller;

import domain.*;
import design.ExecuteWorkoutCell;
import design.DayCell;
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

public class ProgramController implements SetupableController<Program> {

    @FXML
    private TextField programNameField;
    @FXML
    private TextField programDescriptionField;
    @FXML
    private Label dayNameLabel;
    @FXML
    private Button saveProgramButton;
    @FXML
    private Label indexLabel;
    @FXML
    private Button doneButton;
    @FXML
    ListView<Day> dayListView;
    @FXML
    ListView<Workout> workoutListView;

    private Program currentProgram;
    private Parent currentProgramDialog;
    private Day currentlyPlayedDay;
    private Program temporaryProgram;
    private boolean isNew = false;

    public void setUpBindingEdit(Program program, Parent programDialog) {
        if (program != null) {
            currentProgram = program;
            isNew = false;
        } else {
            currentProgram = new Program();
            isNew = true;
        }

        temporaryProgram = currentProgram.makeTmpCopy();

        setUpEditListView();
        setUpTextFields();
    }

    private void setUpTextFields() {
        programNameField.textProperty().bindBidirectional(temporaryProgram.nameProperty());
        programDescriptionField.textProperty().bindBidirectional(temporaryProgram.descriptionProperty());
    }

    private void setUpEditListView() {
        dayListView.setItems(temporaryProgram.getDays());
        dayListView.setCellFactory(new Callback<ListView<Day>,
                                           ListCell<Day>>() {
                                       @Override
                                       public ListCell<Day> call(ListView<Day> list) {
                                           return new DayCell();
                                       }
                                   }
        );
    }

    public void setUpBindingPlay(Program program, Parent programDialog) {
        currentProgram = program;
        currentProgramDialog = programDialog;
        currentlyPlayedDay = program.getDays().get(program.getCurrentDayIndex());

        setUpPlayListView();
        setUpDayInfoText(program);
    }

    private void setUpDayInfoText(Program program) {
        dayNameLabel.textProperty().bind(currentlyPlayedDay.nameProperty());
        int currentTag = program.getCurrentDayIndex() + 1;
        String tagInfoText = "Day " + currentTag + " of " + program.getDays().size();
        indexLabel.setText(tagInfoText);
    }

    private void setUpPlayListView() {
        workoutListView.setItems(currentlyPlayedDay.getWorkouts());
        workoutListView.setCellFactory(new Callback<ListView<Workout>,
                                               ListCell<Workout>>() {
                                           @Override
                                           public ListCell<Workout> call(ListView<Workout> list) {
                                               return new ExecuteWorkoutCell();
                                           }
                                       }
        );
    }

    public void saveProgram(ActionEvent event) {
        String errorText;
        if (isNew) {
            errorText = Pump.databasis.addProgram(currentProgram, temporaryProgram);
        } else {
            errorText = Pump.databasis.updateProgram(currentProgram, temporaryProgram);
        }
        alertSaving(errorText);
    }

    private void alertSaving(String errorText) {
        if (errorText == null) {
            Stage stage = (Stage) saveProgramButton.getScene().getWindow();
            stage.close();
        } else {
            new SaveAlert(Alert.AlertType.WARNING, errorText);
        }
    }

    public void cancel(ActionEvent event) {
        Stage stage = (Stage) saveProgramButton.getScene().getWindow();
        stage.close();
    }

    public void createDay(ActionEvent event) throws IOException {

        //TODO builder
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/day.fxml"));
        Parent dayDialog = fxmlloader.load();

        Stage stage = new Stage();

        DayController controller = fxmlloader.getController();
        controller.setUpBinding(null, dayDialog, temporaryProgram);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Create day");
        stage.setScene(new Scene(dayDialog));

        stage.show();
    }

    public void editDay(Day day) throws IOException {

        //TODO builder
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/day.fxml"));
        Parent dayDialog = fxmlloader.load();
        dayDialog.setUserData(fxmlloader.getController());

        Stage stage = new Stage();

        DayController controller = fxmlloader.getController();
        controller.setUpBinding(day, dayDialog, temporaryProgram);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tag bearbeiten");
        stage.setScene(new Scene(dayDialog));

        stage.show();
    }

    public void deleteDay(Day day) {

        DeleteAlert deleteAlert = new DeleteAlert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setHeaderText("The day will be deleted permanently!");

        Optional<ButtonType> result = deleteAlert.customizeDeleteAlert(null);
        if (result.get() == ButtonType.OK) {
            temporaryProgram.getDays().remove(day);
        }
    }

    public void executeWorkout(Workout workout) throws IOException {

        //TODO builder
        FXMLLoader workoutFxmlloader = new FXMLLoader(getClass().getResource("/fxml/execute_workout.fxml"));
        Parent workoutDialog = workoutFxmlloader.load();

        FXMLLoader exerciseFxmlloader = new FXMLLoader(getClass().getResource("/fxml/exercise.fxml"));

        Map<String, Object> map = new HashMap<>();
        map.put("workoutController", workoutFxmlloader.getController());
        map.put("exerciseController", exerciseFxmlloader.getController());

        workoutDialog.setUserData(map);

        Stage stage = new Stage();

        WorkoutController controller = workoutFxmlloader.getController();
        controller.setUpBindingPlay(workout, workoutDialog);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(workout.getName());
        stage.setScene(new Scene(workoutDialog));

        stage.show();
    }

    public void nextDay(ActionEvent event) {
        currentProgram.increaseCurrentDay();
        setUpBindingPlay(currentProgram, currentProgramDialog);
    }

    public void previousTag(ActionEvent event) {
        currentProgram.decreaseCurrentDay();
        setUpBindingPlay(currentProgram, currentProgramDialog);
    }

    public void programDone(ActionEvent event) {
        Stage stage = (Stage) doneButton.getScene().getWindow();
        stage.close();
    }

}
