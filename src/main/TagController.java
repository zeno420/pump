package main;

import daten.*;
import design.UebungCell;
import design.WorkoutCell;
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
    @FXML
    private Button tagLoeschenBtn;

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
        tmpTag = (Tag) Methoden.deepCopy(aktuellerTag);
        programmWorkoutsListView = (ListView) tagDialog.lookup("#programmWorkoutsListView");
        programmWorkoutsListView.setItems(tmpTag.getWorkouts());
        programmWorkoutsListView.setCellFactory(new Callback<ListView<Workout>,
                                                       ListCell<Workout>>() {
                                                   @Override
                                                   public ListCell<Workout> call(ListView<Workout> list) {
                                                       return new WorkoutCell();
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
                                              return new WorkoutCell();
                                          }
                                      }
        );
    }


    public void tagSpeichern(ActionEvent event) {
        if(tmpTag.getValid().getCode() == 0){
        aktuellerTag.setName(tmpTag.getName());
        aktuellerTag.setWorkouts(tmpTag.getWorkouts());
        if (isNew) {
            programm.getTage().add(aktuellerTag);
        }
        Stage stage = (Stage) tagSpeichernBtn.getScene().getWindow();
        stage.close();}
        else {
            Alert a = new Alert(Alert.AlertType.WARNING);

            a.setTitle("Ungültige Eingabe");
            a.setHeaderText(tmpTag.getValid().getError());
            //TODO contenttext der warung abhängig von wirklich konkretem fehler machen
            // a.setContentText("");
            a.showAndWait();
        }
    }

    public void tagLoeschen(ActionEvent event) {

        programm.getTage().remove(aktuellerTag);
        Stage stage = (Stage) tagSpeichernBtn.getScene().getWindow();
        stage.close();
    }

    public void tagAbbrechen(ActionEvent event) {
        Stage stage = (Stage) tagLoeschenBtn.getScene().getWindow();
        stage.close();
    }

    public void workoutZuTagHinzufuegen(ActionEvent event) {
        //TODO add mit index vll dann kann manned nur hinten anfügen
        Workout workout = (Workout) workoutComboBox.getSelectionModel().getSelectedItem();
        tmpTag.getWorkouts().add(workout);
    }

    public void workoutEntfernen(ActionEvent event) throws IOException {
        tmpTag.getWorkouts().remove(programmWorkoutsListView.getSelectionModel().getSelectedIndex());
    }
}
