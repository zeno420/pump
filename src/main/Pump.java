package main;

import daten.*;
import design.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;


public class Pump extends Application {

    //TODO uebung.fxml spacing zwischen anchorpanes dynamisch machen
    //TODO fullscreen problem beheben
    //TODO new scene statt popup

    public static Datenbasis datenbasis;


    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            Datenbank.init();
            datenbasis = Datenbank.load();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            // alert.setContentText("Could not load data from file:\n" + file.getPath());
            e.printStackTrace(System.out);
            alert.showAndWait();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/root.fxml"));
        Parent root = loader.load();

        root.setUserData(loader.getController());


        primaryStage.setTitle("pump");


        RadioButton masseToggleBtn = (RadioButton) root.lookup("#masseToggleBtn");
        RadioButton defiToggleBtn = (RadioButton) root.lookup("#defiToggleBtn");
        ToggleGroup masseDefiToggleGroup = new ToggleGroup();

        masseToggleBtn.setToggleGroup(masseDefiToggleGroup);
        defiToggleBtn.setToggleGroup(masseDefiToggleGroup);
        masseToggleBtn.setSelected(Pump.datenbasis.getPhase().isMasse());
        defiToggleBtn.setSelected(!Pump.datenbasis.getPhase().isMasse());

        masseDefiToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            Pump.datenbasis.getPhase().setMasse(((RadioButton) newValue).getId().equalsIgnoreCase("masseToggleBtn"));
        });

        ListView<Uebung> uebungenListView = (ListView) root.lookup("#uebungenListView");
        uebungenListView.setItems(Pump.datenbasis.getUebungen());
        uebungenListView.setCellFactory(new Callback<ListView<Uebung>,
                                                ListCell<Uebung>>() {
                                            @Override
                                            public ListCell<Uebung> call(ListView<Uebung> list) {
                                                return new UebungBearbeitenCell();
                                            }
                                        }
        );

        ListView<Workout> workoutListView = (ListView) root.lookup("#workoutListView");
        workoutListView.setItems(Pump.datenbasis.getWorkouts());
        workoutListView.setCellFactory(new Callback<ListView<Workout>,
                                               ListCell<Workout>>() {
                                           @Override
                                           public ListCell<Workout> call(ListView<Workout> list) {
                                               return new WorkoutBearbeitenCell();
                                           }
                                       }
        );

        ListView<Programm> programmListView = (ListView) root.lookup("#programmListView");
        programmListView.setItems(Pump.datenbasis.getProgramme());
        programmListView.setCellFactory(new Callback<ListView<Programm>,
                                                ListCell<Programm>>() {
                                            @Override
                                            public ListCell<Programm> call(ListView<Programm> list) {
                                                return new ProgrammCell();
                                            }
                                        }
        );


        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        Datenbank.save(Pump.datenbasis);
    }

    public static void main(String[] args) {
        launch(args);
    }
}