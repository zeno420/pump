package main;

import daten.*;
import design.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Pump extends Application {

    //TODO uebung.fxml spacing zwischen anchorpanes dynamisch machen
    //TODO fullscreen problem beheben
    //TODO new scene statt popup


    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            Datenbank.init();
            Datenbank.load();
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
        masseToggleBtn.setSelected(Datenbank.getPhase().isMasse());
        defiToggleBtn.setSelected(!Datenbank.getPhase().isMasse());

        masseDefiToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            Datenbank.getPhase().setMasse(((RadioButton) newValue).getId().equalsIgnoreCase("masseToggleBtn"));
        });

        ListView<Uebung> uebungenListView = (ListView) root.lookup("#uebungenListView");
        uebungenListView.setItems(Datenbank.getUebungen());
        uebungenListView.setCellFactory(new Callback<ListView<Uebung>,
                                                ListCell<Uebung>>() {
                                            @Override
                                            public ListCell<Uebung> call(ListView<Uebung> list) {
                                                return new UebungBearbeitenCell();
                                            }
                                        }
        );

        ListView<Workout> workoutListView = (ListView) root.lookup("#workoutListView");
        workoutListView.setItems(Datenbank.getWorkouts());
        workoutListView.setCellFactory(new Callback<ListView<Workout>,
                                               ListCell<Workout>>() {
                                           @Override
                                           public ListCell<Workout> call(ListView<Workout> list) {
                                               return new WorkoutBearbeitenCell();
                                           }
                                       }
        );

        ListView<Programm> programmListView = (ListView) root.lookup("#programmListView");
        programmListView.setItems(Datenbank.getProgramme());
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
        Datenbank.save();
    }

    public static void main(String[] args) {
        launch(args);
    }
}