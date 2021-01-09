package main;

import daten.Programm;
import daten.Uebung;
import daten.Workout;
import design.ProgrammCell;
import design.UebungCell;
import design.WorkoutCell;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;


public class Main extends Application {

    private static ObservableList<Uebung> Uebungen = FXCollections.observableArrayList();
    private static ObservableList<Workout> Workouts = FXCollections.observableArrayList();
    private static ObservableList<Programm> Programme = FXCollections.observableArrayList();


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("root.fxml"));
        primaryStage.setTitle("pump");


        ListView<Uebung> uebungenListView = (ListView) root.lookup("#uebungenListView");
        uebungenListView.setItems(Uebungen);
        uebungenListView.setCellFactory(new Callback<ListView<Uebung>,
                                                ListCell<Uebung>>() {
                                            @Override
                                            public ListCell<Uebung> call(ListView<Uebung> list) {
                                                return new UebungCell();
                                            }
                                        }
        );
        ListView<Workout> workoutListView = (ListView) root.lookup("#workoutListView");
        workoutListView.setItems(Workouts);
        workoutListView.setCellFactory(new Callback<ListView<Workout>,
                                                ListCell<Workout>>() {
                                            @Override
                                            public ListCell<Workout> call(ListView<Workout> list) {
                                                return new WorkoutCell();
                                            }
                                        }
        );
        ListView<Programm> programmListView = (ListView) root.lookup("#programmListView");
        programmListView.setItems(Programme);
        programmListView.setCellFactory(new Callback<ListView<Programm>,
                                               ListCell<Programm>>() {
                                           @Override
                                           public ListCell<Programm> call(ListView<Programm> list) {
                                               return new ProgrammCell();
                                           }
                                       }
        );

        primaryStage.setScene(new Scene(root, 1080, 720));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static ObservableList<Uebung> getUebungen() {
        return Uebungen;
    }

    public static ObservableList<Workout> getWorkouts() {
        return Workouts;
    }

    public static ObservableList<Programm> getProgramme() {
        return Programme;
    }
}