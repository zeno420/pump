package design;

import domain.Workout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import controller.RootController;

import java.io.IOException;

public class EditWorkoutCell extends ListCell<Workout> {
    @Override
    public void updateItem(Workout workout, boolean empty) {
        super.updateItem(workout, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            HBox topBox = new HBox();
            HBox leftBox = new HBox();
            HBox rightBox = new HBox();

            Label nameLabel = new Label(workout.getName());
            Button editButton = new Button("edit");
            Button deleteButton = new Button("delete");

            editButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    RootController rootController = (RootController) getScene().getRoot().getUserData();
                    try {
                        rootController.editWorkout(workout);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            deleteButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    RootController rootController = (RootController) getScene().getRoot().getUserData();
                    rootController.deleteWorkout(workout, true);
                }
            });

            leftBox.getChildren().addAll(nameLabel);
            rightBox.getChildren().addAll(editButton, deleteButton);
            rightBox.setAlignment(Pos.BASELINE_RIGHT);
            rightBox.setSpacing(5);
            topBox.getChildren().addAll(leftBox, rightBox);
            HBox.setHgrow(leftBox, Priority.ALWAYS);
            HBox.setHgrow(rightBox, Priority.ALWAYS);

            setGraphic(topBox);
        }
    }
}