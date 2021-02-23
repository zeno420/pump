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
        uebungDialog.setUserData(fxmlloader.getController());

        Stage stage = new Stage();

        UebungController c = fxmlloader.getController();
        c.setUpBinding(null, uebungDialog);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Übung erstellen");
        stage.setScene(new Scene(uebungDialog, 1080, 720));

        stage.show();
    }

    public void uebungBearbeiten(Uebung uebung) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("uebung.fxml"));
        Parent uebungDialog = fxmlloader.load();
        uebungDialog.setUserData(fxmlloader.getController());

        Stage stage = new Stage();

        UebungController c = fxmlloader.getController();
        c.setUpBinding(uebung, uebungDialog);


        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Übung bearbeiten");
        stage.setScene(new Scene(uebungDialog, 1080, 720));

        stage.show();
    }

    public void uebungLoeschen(Uebung uebung) {
        //TODO warndialog

        Main.getUebungen().remove(uebung);

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

    public void workoutBearbeiten(Workout workout) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("workout.fxml"));
        Parent workoutDialog = fxmlloader.load();
        workoutDialog.setUserData(fxmlloader.getController());

        Stage stage = new Stage();

        WorkoutController c = fxmlloader.getController();
        c.setUpBinding(workout, workoutDialog);



        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Übung bearbeiten");
        stage.setScene(new Scene(workoutDialog, 1080, 720));

        stage.show();
    }

    public void workoutLoeschen(Workout workout) {
        //TODO warndialog

        Main.getWorkouts().remove(workout);

    }

    public void programmErstellen(ActionEvent event) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("programm.fxml"));
        Parent programmDialog = fxmlloader.load();

        Stage stage = new Stage();

        ProgrammController c = fxmlloader.getController();
        c.setUpBindingEdit(null, programmDialog);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Programm erstellen");
        stage.setScene(new Scene(programmDialog, 1080, 720));

        stage.show();
    }

    public void programmBearbeiten(Programm programm) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("programm.fxml"));
        Parent programmDialog = fxmlloader.load();
        programmDialog.setUserData(fxmlloader.getController());

        Stage stage = new Stage();

        ProgrammController c = fxmlloader.getController();
        //Programm programm = (Programm) ((ListView)event.getSource()).getSelectionModel().getSelectedItem();
        c.setUpBindingEdit(programm, programmDialog);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Programm bearbeiten");
        stage.setScene(new Scene(programmDialog, 1080, 720));

        stage.show();
    }

    public void programmLoeschen(Programm programm) {
        //TODO warndialog

        Main.getProgramme().remove(programm);

    }

    public void programmSpielen(Programm programm) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("programm_spielen.fxml"));
        Parent programmDialog = fxmlloader.load();
        programmDialog.setUserData(fxmlloader.getController());

        Stage stage = new Stage();

        ProgrammController c = fxmlloader.getController();
        c.setUpBindingPlay(programm, programmDialog);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Programm spielen");
        stage.setScene(new Scene(programmDialog, 1080, 720));

        stage.show();
    }

}