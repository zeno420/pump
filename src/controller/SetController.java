package controller;

import domain.Exercise;
import domain.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SetController {

    @FXML
    private TextField setRepetitionsField;
    @FXML
    private TextField setWeightField;
    @FXML
    private Button saveSetButton;

    private Set currentSet;
    private Set temporarySet;
    private boolean isNew = false;
    private Exercise exercise;

    public void setUpBinding(Boolean masse, Set set, Parent setDialog, Exercise exercise) {

        if (set != null) {
            currentSet = set;
            isNew = false;
        } else {
            currentSet = new Set();
            currentSet.setIsBulkSet(masse);
            isNew = true;
        }
        this.exercise = exercise;

        temporarySet = currentSet.makeTmpCopy();

        setRepetitionsField.textProperty().bindBidirectional(temporarySet.repetitionsProperty());
        setWeightField.textProperty().bindBidirectional(temporarySet.weightProperty());

    }

    public void saveSet(ActionEvent event) {
        if (temporarySet.getValid().getCode() == 0) {

            if (!isNew) {
                switchSet();
            } else {
                addSet();
            }

            Stage stage = (Stage) saveSetButton.getScene().getWindow();
            stage.close();

        } else {
            new SaveAlert(Alert.AlertType.WARNING, temporarySet.getValid().getErrorMessage());
        }
    }

    private void addSet() {
        if (temporarySet.getIsBulkSet()) {
            exercise.getBulkingSets().add(temporarySet);
        } else {
            exercise.getCuttingSets().add(temporarySet);
        }
    }

    private void switchSet() {
        if (temporarySet.getIsBulkSet()) {
            int oldIndex = exercise.getBulkingSets().indexOf(currentSet);
            exercise.getBulkingSets().set(oldIndex, temporarySet);
        } else {
            int oldIndex = exercise.getCuttingSets().indexOf(currentSet);
            exercise.getCuttingSets().set(oldIndex, temporarySet);
        }
    }

    public void cancel(ActionEvent event) {
        Stage stage = (Stage) saveSetButton.getScene().getWindow();
        stage.close();
    }
}
