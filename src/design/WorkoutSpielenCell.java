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
import controller.ProgrammController;

import java.io.IOException;

public class WorkoutSpielenCell extends ListCell<Workout> {



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

            Button start = new Button("start");

            start.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                   ProgrammController pc = (ProgrammController) getScene().getRoot().getUserData();
                    try {
                        pc.workoutSpielen(workout);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            leftBox.getChildren().addAll(name);
            rightBox.getChildren().addAll(start);
            rightBox.setAlignment(Pos.BASELINE_RIGHT);
            rightBox.setSpacing(5);
            topBox.getChildren().addAll(leftBox, rightBox);
            HBox.setHgrow(leftBox, Priority.ALWAYS);
            HBox.setHgrow(rightBox, Priority.ALWAYS);

            setGraphic(topBox);
        }
    }
}
