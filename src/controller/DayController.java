package controller;

import domain.*;
import design.DisplayWorkoutInDayCell;
import design.WourkoutCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.Pump;

import java.io.IOException;

public class DayController {

    @FXML
    private TextField dayNameField;
    @FXML
    private Button saveDayButton;

    @FXML
    public ComboBox<Workout> workoutComboBox;
    @FXML
    public ListView<Workout> programWorkoutsListView;

    private Day currentDay;
    private Day temporaryDay;
    private boolean isNew = false;
    private Program program;

    public void setUpBinding(Day day, Parent dayDialog, Program program) {

        if (day != null) {
            currentDay = day;
            isNew = false;
        } else {
            currentDay = new Day();
            isNew = true;
        }
        this.program = program;

        temporaryDay = currentDay.makeTmpCopy();

        setUpWorkoutsListView();
        setUpWorkoutsComboBox();

        dayNameField.textProperty().bindBidirectional(temporaryDay.nameProperty());
    }

    private void setUpWorkoutsComboBox() {
        workoutComboBox.setItems(Pump.databasis.getWorkouts());
        workoutComboBox.setCellFactory(new Callback<ListView<Workout>,
                                               ListCell<Workout>>() {
                                           @Override
                                           public ListCell<Workout> call(ListView<Workout> list) {
                                               return new WourkoutCell();
                                           }
                                       }
        );
    }

    private void setUpWorkoutsListView() {
        programWorkoutsListView.setItems(temporaryDay.getWorkouts());
        programWorkoutsListView.setCellFactory(new Callback<ListView<Workout>,
                                                       ListCell<Workout>>() {
                                                   @Override
                                                   public ListCell<Workout> call(ListView<Workout> list) {
                                                       return new DisplayWorkoutInDayCell();
                                                   }
                                               }
        );
    }

    public void saveDay(ActionEvent event) {
        if (temporaryDay.getValid().getCode() == 0) {

            if (!isNew) {
                int oldIndex = program.getDays().indexOf(currentDay);
                program.getDays().set(oldIndex, temporaryDay);
            } else {
                program.getDays().add(temporaryDay);
            }

            Stage stage = (Stage) saveDayButton.getScene().getWindow();
            stage.close();

        } else {
            new SaveAlert(Alert.AlertType.WARNING, temporaryDay.getValid().getErrorText());
        }
    }

    public void cancel(ActionEvent event) {
        Stage stage = (Stage) saveDayButton.getScene().getWindow();
        stage.close();
    }

    public void addWorkoutToDay(ActionEvent event) {
        Workout workout = workoutComboBox.getSelectionModel().getSelectedItem();
        if (workout == null) {
            return;
        }
        temporaryDay.getWorkouts().add(workout);
    }

    public void removeWorkoutFromDay(ActionEvent event) throws IOException {
        if (programWorkoutsListView.getSelectionModel().getSelectedIndex() >= 0) {
            temporaryDay.getWorkouts().remove(programWorkoutsListView.getSelectionModel().getSelectedIndex());
        }
    }
}