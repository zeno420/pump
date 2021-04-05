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

public class WorkoutAnzeigenCell extends ListCell<Workout> {
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
            Button up = new Button("move up");
            Button down = new Button("move down");

            up.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    int index = getListView().getItems().indexOf(getItem());

                    if (index > 0) {

                        Workout w1 = getItem();
                        Workout w2 = getListView().getItems().get(index - 1);

                        getListView().getItems().set(index, w2);
                        getListView().getItems().set(index - 1, w1);
                    }
                }
            });

            down.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    int index = getListView().getItems().indexOf(getItem());

                    if (index < getListView().getItems().size()) {


                        Workout w1 = getItem();
                        Workout w2 = getListView().getItems().get(index + 1);

                        getListView().getItems().set(index, w2);
                        getListView().getItems().set(index + 1, w1);
                    }
                }
            });

            leftBox.getChildren().addAll(name);
            rightBox.getChildren().addAll(up, down);
            rightBox.setAlignment(Pos.BASELINE_RIGHT);
            rightBox.setSpacing(5);
            topBox.getChildren().addAll(leftBox, rightBox);
            HBox.setHgrow(leftBox, Priority.ALWAYS);
            HBox.setHgrow(rightBox, Priority.ALWAYS);

            setGraphic(topBox);
        }
    }
}