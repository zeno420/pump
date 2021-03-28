package controller;

import daten.EintragCount;
import design.EintragCountCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.Pump;


public class StatistikController {

    @FXML
    private Label workoutAnzahlLabel;
    @FXML
    private Label uebungAnzahlLabel;
    @FXML
    private ListView<EintragCount> workoutLogListView;
    @FXML
    private ListView<EintragCount> uebungLogListView;

    private static ObservableList<EintragCount> WorkoutLogsByName = FXCollections.observableArrayList();
    private static ObservableList<EintragCount> UebungLogsByName = FXCollections.observableArrayList();
    private static ObservableList<EintragCount> WorkoutLogsByDate = FXCollections.observableArrayList();
    private static ObservableList<EintragCount> UebungLogsByDate = FXCollections.observableArrayList();


    public void setUpBindingPlay() {

        WorkoutLogsByName.clear();
        UebungLogsByName.clear();
        WorkoutLogsByName.addAll(Pump.getLogsByName(Pump.getWorkoutLogs()));
        UebungLogsByName.addAll(Pump.getLogsByName(Pump.getUebungLogs()));

        workoutAnzahlLabel.setText(Integer.toString(Pump.getWorkoutLogs().size()));
        uebungAnzahlLabel.setText(Integer.toString(Pump.getUebungLogs().size()));

        workoutLogListView.setItems(WorkoutLogsByName);
        workoutLogListView.setCellFactory(new Callback<ListView<EintragCount>,
                                                  ListCell<EintragCount>>() {
                                              @Override
                                              public ListCell<EintragCount> call(ListView<EintragCount> list) {
                                                  return new EintragCountCell();
                                              }
                                          }
        );

        uebungLogListView.setItems(UebungLogsByName);
        uebungLogListView.setCellFactory(new Callback<ListView<EintragCount>,
                                                 ListCell<EintragCount>>() {
                                             @Override
                                             public ListCell<EintragCount> call(ListView<EintragCount> list) {
                                                 return new EintragCountCell();
                                             }
                                         }
        );
    }

    public void chartOeffnen() {
        //TODO leertage

        WorkoutLogsByDate.clear();
        UebungLogsByDate.clear();
        WorkoutLogsByDate.addAll(Pump.getLogsByDate(Pump.getWorkoutLogs()));
        UebungLogsByDate.addAll(Pump.getLogsByDate(Pump.getUebungLogs()));

        Stage stage = new Stage();
        stage.setTitle("Zeitstrahl");

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        yAxis.setLabel("Anzahl");

        final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        XYChart.Series wSeries = new XYChart.Series();
        wSeries.setName("Workouts");
        XYChart.Series uSeries = new XYChart.Series();
        uSeries.setName("Übungen");

        //TODO sortierung der tage prüfen
        for (EintragCount eintragCount : WorkoutLogsByDate) {
            wSeries.getData().add(new XYChart.Data(eintragCount.getName(), eintragCount.getCount()));
        }
        for (EintragCount eintragCount : UebungLogsByDate) {
            uSeries.getData().add(new XYChart.Data(eintragCount.getName(), eintragCount.getCount()));
        }

        ScrollPane root = new ScrollPane(barChart);
        root.setMinSize(720, 1080);
        barChart.setMinSize(root.getMinWidth(), root.getMinHeight() - 20);
        Scene scene = new Scene(root, 720, 1080);
        barChart.getData().addAll(wSeries, uSeries);

        stage.setScene(scene);
        stage.show();
    }
}
