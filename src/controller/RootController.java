package controller;

import daten.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Pump;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RootController {

    public void uebungErstellen(ActionEvent event) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../fxml/uebung.fxml"));
        Parent uebungDialog = fxmlloader.load();
        uebungDialog.setUserData(fxmlloader.getController());

        Stage stage = new Stage();

        UebungController c = fxmlloader.getController();
        c.setUpBinding(null, uebungDialog);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Übung erstellen");
        stage.setScene(new Scene(uebungDialog));

        stage.show();
    }

    public void uebungBearbeiten(Uebung uebung) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../fxml/uebung.fxml"));
        Parent uebungDialog = fxmlloader.load();
        uebungDialog.setUserData(fxmlloader.getController());

        Stage stage = new Stage();

        UebungController c = fxmlloader.getController();
        c.setUpBinding(uebung, uebungDialog);


        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Übung bearbeiten");
        stage.setScene(new Scene(uebungDialog));

        stage.show();
    }

    public void uebungLoeschen(Uebung uebung) {
        List<Workout> workoutList = Pump.datenbasis.getWorkouts();
        List<Workout> containingWorkoutList = new ArrayList<>();
        List<Workout> emptyAfterDeletionWorkoutList = new ArrayList<>();

        StringBuilder warnung = new StringBuilder();

        for (Workout w : workoutList) {
            if (w.getUebungen().contains(uebung)) {
                containingWorkoutList.add(w);
                if (w.getUebungen().stream().allMatch(uebung::equals)){
                    emptyAfterDeletionWorkoutList.add(w);
                    warnung.append(w.getName()).append(": will be empty after deleting this Übung.");
                } else{
                    warnung.append(w.getName()).append(": contains this Übung.");
                }
                warnung.append("\n");
            }
        }

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Achtung: wirklich löschen?");
        a.setHeaderText(emptyAfterDeletionWorkoutList.size() + " Workouts enthalten NUR diese Übung, " + (containingWorkoutList.size() - emptyAfterDeletionWorkoutList.size()) + " weitere Workouts enthalten diese Übung.");
        Optional<ButtonType> result = customizeDeleteAlert(warnung, a);
        if (result.get() == ButtonType.OK) {
            Pump.datenbasis.getUebungen().remove(uebung);
            for (Workout w : containingWorkoutList) {
                while (w.getUebungen().contains(uebung)) {
                    w.getUebungen().remove(uebung);
                }
            }
        }
    }

    public void workoutErstellen(ActionEvent event) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../fxml/workout.fxml"));
        Parent workoutDialog = fxmlloader.load();

        Stage stage = new Stage();

        WorkoutController c = fxmlloader.getController();
        c.setUpBinding(null, workoutDialog);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Workout erstellen");
        stage.setScene(new Scene(workoutDialog));

        stage.show();
    }

    public void workoutBearbeiten(Workout workout) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../fxml/workout.fxml"));
        Parent workoutDialog = fxmlloader.load();
        workoutDialog.setUserData(fxmlloader.getController());

        Stage stage = new Stage();

        WorkoutController c = fxmlloader.getController();
        c.setUpBinding(workout, workoutDialog);


        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Workout bearbeiten");
        stage.setScene(new Scene(workoutDialog));

        stage.show();
    }

    public void workoutLoeschen(Workout workout) {
        List<Programm> programmList = Pump.datenbasis.getProgramme();
        List<Programm> containingProgrammList = new ArrayList<>();
        List<Tag> containingTagList = new ArrayList<>();
        StringBuilder warnung = new StringBuilder();

        for (Programm p : programmList) {
            for (Tag t : p.getTage()) {
                if (t.getWorkouts().contains(workout)) {
                    if (!containingProgrammList.contains(p)) {
                        containingProgrammList.add(p);
                        warnung.append(p.getName());
                        warnung.append("\n");
                    }
                    containingTagList.add(t);
                }
            }
        }

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Achtung: wirklich löschen?");
        a.setHeaderText(containingProgrammList.size() + " Programme enthalten dieses Workout an " + containingTagList.size() + " Tagen:");
        Optional<ButtonType> result = customizeDeleteAlert(warnung, a);
        if (result.get() == ButtonType.OK) {
            Pump.datenbasis.getWorkouts().remove(workout);
            for (Tag t : containingTagList) {
                while (t.getWorkouts().contains(workout)) {
                    t.getWorkouts().remove(workout);
                }
            }
        }
    }

    private Optional<ButtonType> customizeDeleteAlert(StringBuilder warnung, Alert a) {
        a.setContentText(warnung.toString());
        Button lb = (Button) a.getDialogPane().lookupButton(ButtonType.OK);
        lb.setText("löschen");
        lb.setDefaultButton(false);
        Button cb = (Button) a.getDialogPane().lookupButton(ButtonType.CANCEL);
        cb.setText("abbrechen");
        cb.setDefaultButton(true);

        Optional<ButtonType> result = a.showAndWait();
        return result;
    }

    public void programmErstellen(ActionEvent event) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../fxml/programm.fxml"));
        Parent programmDialog = fxmlloader.load();

        Stage stage = new Stage();

        ProgrammController c = fxmlloader.getController();
        c.setUpBindingEdit(null, programmDialog);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Programm erstellen");
        stage.setScene(new Scene(programmDialog));

        stage.show();
    }

    public void programmBearbeiten(Programm programm) throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../fxml/programm.fxml"));
        Parent programmDialog = fxmlloader.load();
        programmDialog.setUserData(fxmlloader.getController());

        Stage stage = new Stage();

        ProgrammController c = fxmlloader.getController();
        c.setUpBindingEdit(programm, programmDialog);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Programm bearbeiten");
        stage.setScene(new Scene(programmDialog));

        stage.show();
    }

    public void programmLoeschen(Programm programm) {

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Achtung: wirklich löschen?");
        a.setHeaderText("Das Programm wird dauerhaft gelöscht!");
        Button lb = (Button) a.getDialogPane().lookupButton(ButtonType.OK);
        lb.setText("löschen");
        lb.setDefaultButton(false);
        Button cb = (Button) a.getDialogPane().lookupButton(ButtonType.CANCEL);
        cb.setText("abbrechen");
        cb.setDefaultButton(true);

        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK) {
            Pump.datenbasis.getProgramme().remove(programm);
        }
    }

    public void programmSpielen(Programm programm) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../fxml/programm_spielen.fxml"));
        Parent programmDialog = fxmlloader.load();
        programmDialog.setUserData(fxmlloader.getController());

        Stage stage = new Stage();

        ProgrammController c = fxmlloader.getController();
        c.setUpBindingPlay(programm, programmDialog);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(programm.getName());
        stage.setScene(new Scene(programmDialog));

        stage.show();
    }

    public void statistikOeffnen() throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../fxml/statistik.fxml"));
        Parent statistikDialog = fxmlloader.load();
        statistikDialog.setUserData(fxmlloader.getController());

        Stage stage = new Stage();

        StatistikController c = fxmlloader.getController();
        c.setUpBinding();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Statistik");
        stage.setScene(new Scene(statistikDialog));

        stage.show();
    }

}