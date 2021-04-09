package design;

import domain.Set;
import domain.Exercise;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import controller.ExerciseController;

import java.io.IOException;
import java.util.Map;

public class ExecuteSetCell extends ListCell<Set> {

    private Exercise parentExercise;

    public ExecuteSetCell(Exercise parentExercise) {
        this.parentExercise = parentExercise;
    }

    @Override
    public void updateItem(Set set, boolean empty) {
        super.updateItem(set, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            HBox topBox = new HBox();
            HBox leftBox = new HBox();
            HBox rightBox = new HBox();

            Label repetitionAndWeightLabel = new Label(set.getRepetitions() + " x " + set.getWeight());
            Button editButton = new Button("edit");

            editButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    ExerciseController exerciseController;
                    if (getScene().getRoot().getUserData() instanceof ExerciseController) {
                        exerciseController = (ExerciseController) getScene().getRoot().getUserData();
                    } else {
                        exerciseController = (ExerciseController) ((Map) getScene().getRoot().getUserData()).get("uebungController");
                    }
                    try {
                        exerciseController.editSet(set, parentExercise);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


            leftBox.getChildren().addAll(repetitionAndWeightLabel);
            rightBox.getChildren().addAll(editButton);
            rightBox.setAlignment(Pos.BASELINE_RIGHT);
            rightBox.setSpacing(5);
            topBox.getChildren().addAll(leftBox, rightBox);
            HBox.setHgrow(leftBox, Priority.ALWAYS);
            HBox.setHgrow(rightBox, Priority.ALWAYS);

            setGraphic(topBox);
        }
    }
}