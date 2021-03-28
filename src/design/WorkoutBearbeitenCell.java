package design;

import daten.Workout;
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

public class WorkoutBearbeitenCell extends ListCell<Workout> {
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

            Label name = new Label(workout.getName());
            Button bearbeiten = new Button("bearbeiten");
            Button loeschen = new Button("löschen");

            bearbeiten.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    RootController rc = (RootController) getScene().getRoot().getUserData();
                    try {
                        rc.workoutBearbeiten(workout);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            loeschen.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    RootController rc = (RootController) getScene().getRoot().getUserData();
                    rc.workoutLoeschen(workout);
                }
            });

            leftBox.getChildren().addAll(name);
            rightBox.getChildren().addAll(bearbeiten, loeschen);
            rightBox.setAlignment(Pos.BASELINE_RIGHT);
            rightBox.setSpacing(5);
            topBox.getChildren().addAll(leftBox, rightBox);
            HBox.setHgrow(leftBox, Priority.ALWAYS);
            HBox.setHgrow(rightBox, Priority.ALWAYS);

            setGraphic(topBox);
        }
    }
}

