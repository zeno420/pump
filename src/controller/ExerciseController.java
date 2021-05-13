package controller;

import domain.Exercise;
import domain.Set;
import design.SetCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.Pump;

import java.io.IOException;

public class ExerciseController implements SetupableController<Exercise> {

    @FXML
    private TextField exerciseNameField;
    @FXML
    private TextField exerciseDescriptionField;
    @FXML
    private Button saveExerciseButton;
    @FXML
    private ListView<Set> bulkingSetListView;
    @FXML
    private ListView<Set> cuttingSetListView;

    private Exercise currentExercise;
    private Exercise temporaryExercise;
    private boolean isNew = false;

    public void setUpBindingEdit(Exercise exercise, Parent exerciseDialog) {
        if (exercise != null) {
            currentExercise = exercise;
            isNew = false;
        } else {
            currentExercise = new Exercise();
            isNew = true;
        }

        temporaryExercise = currentExercise.makeTmpCopy();

        setUpBulkinSetListView();
        setUpCuttingSetView();
        setUpTextFields();
    }

    private void setUpTextFields() {
        exerciseNameField.textProperty().bindBidirectional(temporaryExercise.nameProperty());
        exerciseDescriptionField.textProperty().bindBidirectional(temporaryExercise.descriptionProperty());
    }

    private void setUpCuttingSetView() {
        cuttingSetListView.setItems(temporaryExercise.getCuttingSets());
        cuttingSetListView.setCellFactory(new Callback<ListView<Set>,
                                                  ListCell<Set>>() {
                                              @Override
                                              public ListCell<Set> call(ListView<Set> list) {
                                                  return new SetCell();
                                              }
                                          }
        );
    }

    private void setUpBulkinSetListView() {
        bulkingSetListView.setItems(temporaryExercise.getBulkingSets());
        bulkingSetListView.setCellFactory(new Callback<ListView<Set>,
                                                  ListCell<Set>>() {
                                              @Override
                                              public ListCell<Set> call(ListView<Set> list) {
                                                  return new SetCell();
                                              }
                                          }
        );
    }

    public void saveExercise(ActionEvent event) {
        String errorText;
        if (isNew) {
            errorText = Pump.databasis.addExercise(currentExercise, temporaryExercise);
        } else {
            errorText = Pump.databasis.updateExercise(currentExercise, temporaryExercise);
        }
        if (event == null) return;
        alertSaving(errorText);
    }

    private void alertSaving(String errorText) {
        if (errorText == null) {
            Stage stage = (Stage) saveExerciseButton.getScene().getWindow();
            stage.close();
        } else {
            new SaveAlert(Alert.AlertType.WARNING, errorText);
        }
    }

    public void cancel(ActionEvent event) {
        Stage stage = (Stage) saveExerciseButton.getScene().getWindow();
        stage.close();
    }

    public void createSet(ActionEvent event) throws IOException {

        //TODO builder
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/set.fxml"));
        Parent setDialog = fxmlloader.load();

        Stage stage = new Stage();

        Boolean isBulking = ((Button) event.getSource()).getId().equalsIgnoreCase("addBulkingSetButton");

        SetController controller = fxmlloader.getController();
        controller.setUpBinding(isBulking, null, setDialog, temporaryExercise);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Create set");
        stage.setScene(new Scene(setDialog));

        stage.show();
    }

    public void editSet(Set set, Exercise parentExercise) throws IOException {

        //TODO builder
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/fxml/set.fxml"));
        Parent setDialog = fxmlloader.load();

        Stage stage = new Stage();

        SetController controller = fxmlloader.getController();

        //FIXME nur ein temporärer workaround
        boolean editingWhileExecuting = false;
        if (temporaryExercise == null) {
            editingWhileExecuting = true;
            currentExercise = parentExercise;
            temporaryExercise = currentExercise.makeTmpCopy();
        }

        controller.setUpBinding(null, set, setDialog, temporaryExercise);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit set");
        stage.setScene(new Scene(setDialog));
        if (editingWhileExecuting) {
            stage.showAndWait();
            saveExercise(null);
            temporaryExercise = null;
        } else {
            stage.show();
        }

    }

    public void deleteSet(Set set) {
        if (set.getIsBulkSet()) {
            temporaryExercise.getBulkingSets().remove(set);
        } else {
            temporaryExercise.getCuttingSets().remove(set);
        }
    }
}