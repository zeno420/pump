package design;

import domain.Set;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import controller.ExerciseController;

import java.io.IOException;
import java.util.Map;

public class SetCell extends ListCell<Set> {


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
            Button deleteButton = new Button("delete");
            Button moveUpButton = new Button("move up");
            Button moveDownButton = new Button("move down");

            editButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    ExerciseController exerciseController;
                    Object userData = getScene().getRoot().getUserData();
                    if (userData instanceof ExerciseController) {
                        exerciseController = (ExerciseController) getScene().getRoot().getUserData();
                    } else {
                        exerciseController = (ExerciseController) ((Map) getScene().getRoot().getUserData()).get("uebungController");
                    }
                    try {
                        exerciseController.editSet(set, null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            deleteButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    ExerciseController exerciseController;
                    if (getScene().getRoot().getUserData() instanceof ExerciseController) {
                        exerciseController = (ExerciseController) getScene().getRoot().getUserData();
                    } else {
                        exerciseController = (ExerciseController) ((Map) getScene().getRoot().getUserData()).get("uebungController");
                    }
                    exerciseController.deleteSet(set);
                }
            });

            moveUpButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    int index = getListView().getItems().indexOf(getItem());

                    if (index > 0) {

                        Set set1 = getItem();
                        Set set2 = getListView().getItems().get(index - 1);

                        getListView().getItems().set(index, set2);
                        getListView().getItems().set(index - 1, set1);
                    }
                }
            });

            moveDownButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    int index = getListView().getItems().indexOf(getItem());

                    if (index < getListView().getItems().size()) {

                        Set set1 = getItem();
                        Set set2 = getListView().getItems().get(index + 1);

                        getListView().getItems().set(index, set2);
                        getListView().getItems().set(index + 1, set1);
                    }
                }
            });

            leftBox.getChildren().addAll(repetitionAndWeightLabel);
            rightBox.getChildren().addAll(moveUpButton, moveDownButton, editButton, deleteButton);
            rightBox.setAlignment(Pos.BASELINE_RIGHT);
            rightBox.setSpacing(5);
            topBox.getChildren().addAll(leftBox, rightBox);
            HBox.setHgrow(leftBox, Priority.ALWAYS);
            HBox.setHgrow(rightBox, Priority.ALWAYS);

            setGraphic(topBox);
        }
    }
}