package controller;

import domain.LogEntryCount;
import domain.LogEntryCountKeyComparator;
import domain.Statistic;
import design.LogEntryCountCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.Pump;

import java.util.*;


public class StatisticController {

    @FXML
    private Label workoutCountLabel;
    @FXML
    private Label exerciseCountLabel;
    @FXML
    private ListView<LogEntryCount> workoutLogListView;
    @FXML
    private ListView<LogEntryCount> exerciseLogListView;

    private static ObservableList<LogEntryCount> WorkoutLogsByName = FXCollections.observableArrayList();
    private static ObservableList<LogEntryCount> ExerciseLogsByName = FXCollections.observableArrayList();
    private static ObservableList<LogEntryCount> WorkoutLogsByDate = FXCollections.observableArrayList();
    private static ObservableList<LogEntryCount> ExerciseLogsByDate = FXCollections.observableArrayList();

    public void setUpBinding() {

        WorkoutLogsByName.addAll(Statistic.aggregateLogEntriesByName(Pump.databasis.getWorkoutLog()));
        ExerciseLogsByName.addAll(Statistic.aggregateLogEntriesByName(Pump.databasis.getExeciseLog()));

        workoutCountLabel.setText(Integer.toString(Pump.databasis.getWorkoutLog().size()));
        exerciseCountLabel.setText(Integer.toString(Pump.databasis.getExeciseLog().size()));

        setUpWorkoutListView();
        setUpExerciseListView();
    }

    private void setUpExerciseListView() {
        exerciseLogListView.setItems(ExerciseLogsByName);
        exerciseLogListView.setCellFactory(new Callback<ListView<LogEntryCount>,
                                                 ListCell<LogEntryCount>>() {
                                             @Override
                                             public ListCell<LogEntryCount> call(ListView<LogEntryCount> list) {
                                                 return new LogEntryCountCell();
                                             }
                                         }
        );
    }

    private void setUpWorkoutListView() {
        workoutLogListView.setItems(WorkoutLogsByName);
        workoutLogListView.setCellFactory(new Callback<ListView<LogEntryCount>,
                                                  ListCell<LogEntryCount>>() {
                                              @Override
                                              public ListCell<LogEntryCount> call(ListView<LogEntryCount> list) {
                                                  return new LogEntryCountCell();
                                              }
                                          }
        );
    }

    public void openTimeline() {

        List<LogEntryCount> workoutLogEntryCountList = Statistic.getAllDays(Pump.databasis.getWorkoutLog());
        workoutLogEntryCountList.sort(new LogEntryCountKeyComparator());
        WorkoutLogsByDate.addAll(workoutLogEntryCountList);

        List<LogEntryCount> exerciseLogEntryCountList = Statistic.getAllDays(Pump.databasis.getExeciseLog());
        exerciseLogEntryCountList.sort(new LogEntryCountKeyComparator());
        ExerciseLogsByDate.addAll(exerciseLogEntryCountList);

        Stage stage = new Stage();
        stage.setTitle("Timeline");

        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Day");

        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Count");
        yAxis.setSide(Side.RIGHT);
        yAxis.setForceZeroInRange(true);
        yAxis.setMinorTickVisible(false);

        final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        XYChart.Series wSeries = new XYChart.Series<>();
        setUpSeries(wSeries, "Workouts", WorkoutLogsByDate);

        XYChart.Series uSeries = new XYChart.Series<>();
        setUpSeries(uSeries, "Exercises", ExerciseLogsByDate);

        ScrollPane root = new ScrollPane(barChart);
        root.setMinSize(720, 1080);

        if (WorkoutLogsByDate.size() > ExerciseLogsByDate.size()) {
            barChart.setMinSize((long) WorkoutLogsByDate.size() * 16, root.getMinHeight() - 100);
        } else {
            barChart.setMinSize((long) ExerciseLogsByDate.size() * 16, root.getMinHeight() - 100);
        }

        root.setHvalue(root.getHmax());

        Scene scene = new Scene(root, 1920, 1080);
        barChart.getData().addAll(wSeries, uSeries);

        stage.setScene(scene);
        stage.show();
    }

    private void setUpSeries(XYChart.Series wSeries, String title, List<LogEntryCount> list) {
        wSeries.setName(title);
        for (LogEntryCount logEntryCount : list) {
            wSeries.getData().add(new XYChart.Data(logEntryCount.getKey(), logEntryCount.getCount()));
        }
    }
}