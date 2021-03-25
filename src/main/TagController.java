package main;

import daten.*;
import design.WorkoutAnzeigenCell;
import design.WourkoutCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class TagController {

    @FXML
    private TextField tagNameField;
    @FXML
    private Button tagSpeichernBtn;

    public ComboBox<Workout> workoutComboBox;
    public ListView<Workout> programmWorkoutsListView;

    private Tag aktuellerTag;
    private Tag tmpTag;
    private boolean isNew = false;
    private Programm programm;

    public void setUpBinding(Tag tag, Parent tagDialog, Programm programm) {

        if (tag != null) {
            aktuellerTag = tag;
            isNew = false;
        } else {
            aktuellerTag = new Tag();
            isNew = true;
        }
        this.programm = programm;

        tmpTag = aktuellerTag.makeTmpCopy();

        programmWorkoutsListView = (ListView) tagDialog.lookup("#programmWorkoutsListView");
        programmWorkoutsListView.setItems(tmpTag.getWorkouts());
        programmWorkoutsListView.setCellFactory(new Callback<ListView<Workout>,
                                                        ListCell<Workout>>() {
                                                    @Override
                                                    public ListCell<Workout> call(ListView<Workout> list) {
                                                        return new WorkoutAnzeigenCell();
                                                    }
                                                }
        );

        tagNameField.textProperty().bindBidirectional(tmpTag.nameProperty());

        workoutComboBox = (ComboBox) tagDialog.lookup("#workoutComboBox");
        workoutComboBox.setItems(Main.getWorkouts());
        workoutComboBox.setCellFactory(new Callback<ListView<Workout>,
                                               ListCell<Workout>>() {
                                           @Override
                                           public ListCell<Workout> call(ListView<Workout> list) {
                                               return new WourkoutCell();
                                           }
                                       }
        );
    }

    public void tagSpeichern(ActionEvent event) {
        if (tmpTag.getValid().getCode() == 0) {
            aktuellerTag.setName(tmpTag.getName());
            aktuellerTag.setWorkouts(tmpTag.getWorkouts());
            if (isNew) {
                programm.getTage().add(aktuellerTag);
            }
            Stage stage = (Stage) tagSpeichernBtn.getScene().getWindow();
            stage.close();
            Main.saveDatenbank();
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);

            a.setTitle("Ungültige Eingabe");
            a.setHeaderText(tmpTag.getValid().getError());
            a.showAndWait();
        }
    }

    public void tagAbbrechen(ActionEvent event) {
        Stage stage = (Stage) tagSpeichernBtn.getScene().getWindow();
        stage.close();
    }

    public void workoutZuTagHinzufuegen(ActionEvent event) {
        Workout workout = workoutComboBox.getSelectionModel().getSelectedItem();
        if (workout == null) {
            return;
        }
        tmpTag.getWorkouts().add(workout);
    }

    public void workoutEntfernen(ActionEvent event) throws IOException {
        if (programmWorkoutsListView.getSelectionModel().getSelectedIndex() >= 0) {
            tmpTag.getWorkouts().remove(programmWorkoutsListView.getSelectionModel().getSelectedIndex());
        }
    }
}
