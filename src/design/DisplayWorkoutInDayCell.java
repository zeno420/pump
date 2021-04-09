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

public class DisplayWorkoutInDayCell extends ListCell<Workout> {
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
            Button moveUpButton = new Button("move up");
            Button moveDownButton = new Button("move down");

            moveUpButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    int index = getListView().getItems().indexOf(getItem());

                    if (index > 0) {

                        Workout workout1 = getItem();
                        Workout workout2 = getListView().getItems().get(index - 1);

                        getListView().getItems().set(index, workout2);
                        getListView().getItems().set(index - 1, workout1);
                    }
                }
            });

            moveDownButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    int index = getListView().getItems().indexOf(getItem());

                    if (index < getListView().getItems().size()) {

                        Workout workout1 = getItem();
                        Workout workout2 = getListView().getItems().get(index + 1);

                        getListView().getItems().set(index, workout2);
                        getListView().getItems().set(index + 1, workout1);
                    }
                }
            });

            leftBox.getChildren().addAll(nameLabel);
            rightBox.getChildren().addAll(moveUpButton, moveDownButton);
            rightBox.setAlignment(Pos.BASELINE_RIGHT);
            rightBox.setSpacing(5);
            topBox.getChildren().addAll(leftBox, rightBox);
            HBox.setHgrow(leftBox, Priority.ALWAYS);
            HBox.setHgrow(rightBox, Priority.ALWAYS);

            setGraphic(topBox);
        }
    }
}