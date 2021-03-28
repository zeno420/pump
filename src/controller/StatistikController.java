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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.*;

import static daten.EintragCount.frueher;


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
    private static List<EintragCount> LeerTage = new ArrayList<>();


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

        WorkoutLogsByDate.clear();
        UebungLogsByDate.clear();
        WorkoutLogsByDate.addAll(Pump.getLogsByDate(Pump.getWorkoutLogs()));
        UebungLogsByDate.addAll(Pump.getLogsByDate(Pump.getUebungLogs()));
        LeerTage = leertageGenerieren(WorkoutLogsByDate, UebungLogsByDate);

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

        for (EintragCount eintragCount : WorkoutLogsByDate) {
            wSeries.getData().add(new XYChart.Data(eintragCount.getDate(), eintragCount.getCount()));
        }
        for (EintragCount eintragCount : UebungLogsByDate) {
            uSeries.getData().add(new XYChart.Data(eintragCount.getDate(), eintragCount.getCount()));
        }
        for (EintragCount eintragCount : LeerTage) {
            wSeries.getData().add(new XYChart.Data(eintragCount.getDate(), eintragCount.getCount()));
        }
        for (EintragCount eintragCount : LeerTage) {
            uSeries.getData().add(new XYChart.Data(eintragCount.getDate(), eintragCount.getCount()));
        }

        Collections.sort(wSeries.getData(), new Comparator<XYChart.Data>() {

            @Override
            public int compare(XYChart.Data o1, XYChart.Data o2) {
                return new BigDecimal((String)o1.getXValue()).compareTo(new BigDecimal((String)o2.getXValue()));
            }
        });
        Collections.sort(uSeries.getData(), new Comparator<XYChart.Data>() {

            @Override
            public int compare(XYChart.Data o1, XYChart.Data o2) {
                return new BigDecimal((String)o1.getXValue()).compareTo(new BigDecimal((String)o2.getXValue()));
            }
        });

        ScrollPane root = new ScrollPane(barChart);
        root.setMinSize(720, 1080);
        barChart.setMinSize(root.getMinWidth(), root.getMinHeight() - 20);
        Scene scene = new Scene(root, 720, 1080);
        barChart.getData().addAll(wSeries, uSeries);

        stage.setScene(scene);
        stage.show();
    }

    private static ArrayList<EintragCount> leertageGenerieren(ObservableList<EintragCount> uebungsTage, ObservableList<EintragCount> workoutTage) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        ZonedDateTime heute = ZonedDateTime.now();

        //frühesten tag ermitteln
        EintragCount fruehsterTag = new EintragCount(heute.format(formatter), 0);
        for (EintragCount u : uebungsTage) {
            fruehsterTag = frueher(fruehsterTag, u);
        }
        for (EintragCount w : workoutTage) {
            fruehsterTag = frueher(fruehsterTag, w);
        }

        ZonedDateTime fruehestesDate = convertToZDT(fruehsterTag.getDate());

        long days = zonedDateTimeDifference(fruehestesDate, heute, ChronoUnit.DAYS);
        ArrayList<EintragCount> leertageList = new ArrayList<>();

        for (int i = Integer.parseInt(fruehsterTag.getDate()); i <= Integer.parseInt(fruehsterTag.getDate()) + days; i++) {
            EintragCount eintrag = new EintragCount(String.valueOf(i), 0);
            if (!uebungsTage.contains(eintrag) && !workoutTage.contains(eintrag)) {
                leertageList.add(eintrag);
            }
        }
        return leertageList;
    }

    private static long zonedDateTimeDifference(ZonedDateTime d1, ZonedDateTime d2, ChronoUnit unit) {
        return unit.between(d1, d2);
    }

    public static ZonedDateTime convertToZDT(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        ZonedDateTime result = localDate.atStartOfDay(ZoneId.systemDefault());
        return result;
    }

}
