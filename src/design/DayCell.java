package design;

import domain.Day;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import controller.ProgramController;

import java.io.IOException;

public class DayCell extends ListCell<Day> {
    @Override
    public void updateItem(Day day, boolean empty) {
        super.updateItem(day, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            HBox topBox = new HBox();
            HBox leftBox = new HBox();
            HBox rightBox = new HBox();

            Label nameLabel = new Label(day.getName());
            Button editButton = new Button("edit");
            Button deleteButton = new Button("delete");
            Button moveUpButton = new Button("move up");
            Button moveDownButton = new Button("move down");

            editButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    ProgramController programController = (ProgramController) getScene().getRoot().getUserData();
                    try {
                        programController.editDay(day);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            deleteButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    ProgramController programController = (ProgramController) getScene().getRoot().getUserData();
                    programController.deleteDay(day);
                }
            });

            moveUpButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    int index = getListView().getItems().indexOf(getItem());

                    if (index > 0) {

                        Day day1 = getItem();
                        Day day2 = getListView().getItems().get(index - 1);

                        getListView().getItems().set(index, day2);
                        getListView().getItems().set(index - 1, day1);
                    }
                }
            });

            moveDownButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    int index = getListView().getItems().indexOf(getItem());

                    if (index < getListView().getItems().size()) {

                        Day day1 = getItem();
                        Day day2 = getListView().getItems().get(index + 1);

                        getListView().getItems().set(index, day2);
                        getListView().getItems().set(index + 1, day1);
                    }
                }
            });

            leftBox.getChildren().addAll(nameLabel);
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