package main;

import daten.Programm;
import daten.Satz;
import daten.Uebung;
import daten.Workout;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class RootController {

    private static ObservableList<Satz> masseSaetze;
    private static ObservableList<Satz> defiSaetze;

    public void uebungErstellen(ActionEvent event) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("uebung.fxml"));
        Parent uebungDialog = fxmlloader.load();

        Stage stage = new Stage();

        UebungController c = fxmlloader.getController();
        c.setUpBinding(null, uebungDialog);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Übung erstellen");
        stage.setScene(new Scene(uebungDialog, 1080, 720));

        stage.show();
    }

    public void uebungBearbeiten(MouseEvent event) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("uebung.fxml"));
        Parent uebungDialog = fxmlloader.load();

        Stage stage = new Stage();

        UebungController c = fxmlloader.getController();
        Uebung uebung = (Uebung)((ListView)event.getSource()).getSelectionModel().getSelectedItem();
        c.setUpBinding(uebung, uebungDialog);


        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Übung bearbeiten");
        stage.setScene(new Scene(uebungDialog, 1080, 720));

        stage.show();
    }

    public void workoutErstellen(ActionEvent event) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("workout.fxml"));
        Parent workoutDialog = fxmlloader.load();

        Stage stage = new Stage();

        WorkoutController c = fxmlloader.getController();
        c.setUpBinding(null, workoutDialog);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Workout erstellen");
        stage.setScene(new Scene(workoutDialog, 1080, 720));

        stage.show();
    }

    public void workoutBearbeiten(MouseEvent event) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("workout.fxml"));
        Parent workoutDialog = fxmlloader.load();

        Stage stage = new Stage();

        WorkoutController c = fxmlloader.getController();
        Workout workout = (Workout) ((ListView)event.getSource()).getSelectionModel().getSelectedItem();
        c.setUpBinding(workout, workoutDialog);


        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Workout bearbeiten");
        stage.setScene(new Scene(workoutDialog, 1080, 720));

        stage.show();
    }

    public void programmErstellen(ActionEvent event) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("programm.fxml"));
        Parent programmDialog = fxmlloader.load();

        Stage stage = new Stage();

        ProgrammController c = fxmlloader.getController();
        c.setUpBinding(null, programmDialog);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Programm erstellen");
        stage.setScene(new Scene(programmDialog, 1080, 720));

        stage.show();
    }

    public void programmBearbeiten(MouseEvent event) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("programm.fxml"));
        Parent programmDialog = fxmlloader.load();

        Stage stage = new Stage();

        ProgrammController c = fxmlloader.getController();
        Programm programm = (Programm) ((ListView)event.getSource()).getSelectionModel().getSelectedItem();
        c.setUpBinding(programm, programmDialog);


        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Programm bearbeiten");
        stage.setScene(new Scene(programmDialog, 1080, 720));

        stage.show();
    }

}