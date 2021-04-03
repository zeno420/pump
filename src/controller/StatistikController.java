package controller;

import daten.EintragCount;
import daten.Statistik;
import design.EintragCountCell;
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

import java.math.BigDecimal;
import java.util.*;


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

    public void setUpBinding() {

        WorkoutLogsByName.clear();
        UebungLogsByName.clear();
        WorkoutLogsByName.addAll(Statistik.getLogsByName(Pump.datenbasis.getWorkoutLog()));
        UebungLogsByName.addAll(Statistik.getLogsByName(Pump.datenbasis.getUebungLog()));

        workoutAnzahlLabel.setText(Integer.toString(Pump.datenbasis.getWorkoutLog().size()));
        uebungAnzahlLabel.setText(Integer.toString(Pump.datenbasis.getUebungLog().size()));

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

        WorkoutLogsByDate.clear();
        UebungLogsByDate.clear();
        WorkoutLogsByDate.addAll(Statistik.getAllDays(Pump.datenbasis.getWorkoutLog()));
        UebungLogsByDate.addAll(Statistik.getAllDays(Pump.datenbasis.getUebungLog()));

        //Map<String, Object> map = Statistik.leertageGenerieren(WorkoutLogsByDate, UebungLogsByDate);

       // LeerTage = (ArrayList) map.get("leertageList");

        Stage stage = new Stage();
        stage.setTitle("Zeitstrahl");

        final CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Tag");

        //TODO major ticks ganze schritte
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Anzahl");
        yAxis.setSide(Side.RIGHT);
        yAxis.setForceZeroInRange(true);
        yAxis.setMinorTickVisible(false);

        final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        XYChart.Series wSeries = new XYChart.Series();
        wSeries.setName("Workouts");
        XYChart.Series uSeries = new XYChart.Series();
        uSeries.setName("Übungen");

        for (EintragCount eintragCount : WorkoutLogsByDate) {
            wSeries.getData().add(new XYChart.Data(eintragCount.getKey(), eintragCount.getCount()));
        }
        for (EintragCount eintragCount : UebungLogsByDate) {
            uSeries.getData().add(new XYChart.Data(eintragCount.getKey(), eintragCount.getCount()));
        }

        Collections.sort(wSeries.getData(), new Comparator<XYChart.Data>() {

            @Override
            public int compare(XYChart.Data o1, XYChart.Data o2) {
                return new BigDecimal((String) o1.getXValue()).compareTo(new BigDecimal((String) o2.getXValue()));
            }
        });
        Collections.sort(uSeries.getData(), new Comparator<XYChart.Data>() {

            @Override
            public int compare(XYChart.Data o1, XYChart.Data o2) {
                return new BigDecimal((String) o1.getXValue()).compareTo(new BigDecimal((String) o2.getXValue()));
            }
        });


        ScrollPane root = new ScrollPane(barChart);
        root.setMinSize(720, 1080);

        if (WorkoutLogsByDate.size() > UebungLogsByDate.size()) barChart.setMinSize((long) WorkoutLogsByDate.size() * 16, root.getMinHeight() - 100);
        barChart.setMinSize((long) UebungLogsByDate.size() * 16, root.getMinHeight() - 100);

        root.setHvalue(root.getHmax());

        Scene scene = new Scene(root, 1920, 1080);
        barChart.getData().addAll(wSeries, uSeries);

        stage.setScene(scene);
        stage.show();
    }


}
