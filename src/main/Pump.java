package main;

import controller.SaveAlert;
import domain.*;
import design.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import persistence.Database;


public class Pump extends Application {

    //TODO new scene statt popup

    public static Databasis databasis;

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            Database.init();
            databasis = (Databasis) Database.load(Databasis.class);
        } catch (Exception e) {
            new SaveAlert(Alert.AlertType.ERROR, "Could not load data.");
            databasis = new Databasis();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/root.fxml"));
        Parent root = loader.load();

        root.setUserData(loader.getController());


        primaryStage.setTitle("pump");


        RadioButton phaseToggleButtonBulking = (RadioButton) root.lookup("#phaseToggleButtonBulking");
        RadioButton phaseToggleButtonCutting = (RadioButton) root.lookup("#phaseToggleButtonCutting");


        ToggleGroup bulkingCuttingToggleGroup = new ToggleGroup();

        phaseToggleButtonBulking.setToggleGroup(bulkingCuttingToggleGroup);
        phaseToggleButtonCutting.setToggleGroup(bulkingCuttingToggleGroup);
        phaseToggleButtonBulking.setSelected(databasis.getPhase().getBulk());
        phaseToggleButtonCutting.setSelected(!databasis.getPhase().getBulk());

        bulkingCuttingToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            databasis.getPhase().setBulk(((RadioButton) newValue).getId().equalsIgnoreCase("phaseToggleButtonBulking"));
        });

        ListView<Exercise> exerciseListView = (ListView) root.lookup("#exerciseListView");
        exerciseListView.setItems(databasis.getExecises());
        exerciseListView.setCellFactory(new Callback<ListView<Exercise>,
                                                ListCell<Exercise>>() {
                                            @Override
                                            public ListCell<Exercise> call(ListView<Exercise> list) {
                                                return new EditExerciseCell();
                                            }
                                        }
        );

        ListView<Workout> workoutListView = (ListView) root.lookup("#workoutListView");
        workoutListView.setItems(databasis.getWorkouts());
        workoutListView.setCellFactory(new Callback<ListView<Workout>,
                                               ListCell<Workout>>() {
                                           @Override
                                           public ListCell<Workout> call(ListView<Workout> list) {
                                               return new EditWorkoutCell();
                                           }
                                       }
        );

        ListView<Program> programListView = (ListView) root.lookup("#programListView");
        programListView.setItems(databasis.getPrograms());
        programListView.setCellFactory(new Callback<ListView<Program>,
                                               ListCell<Program>>() {
                                           @Override
                                           public ListCell<Program> call(ListView<Program> list) {
                                               return new ProgramCell();
                                           }
                                       }
        );

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        Database.save(databasis);
    }

    public static void main(String[] args) {
        launch(args);
    }
}