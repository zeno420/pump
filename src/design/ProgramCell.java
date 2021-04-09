package design;

import domain.Program;
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

public class ProgramCell extends ListCell<Program> {

    @Override
    public void updateItem(Program program, boolean empty) {
        super.updateItem(program, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            HBox topBox = new HBox();
            HBox leftBox = new HBox();
            HBox rightBox = new HBox();

            Label nameLabel = new Label(program.getName());
            Button editButton = new Button("edit");
            Button executeButton = new Button("execute");
            Button deleteButton = new Button("delete");

            executeButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    RootController rootController = (RootController) getScene().getRoot().getUserData();
                    try {
                        rootController.executeProgram(program);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            editButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    RootController rootController = (RootController) getScene().getRoot().getUserData();
                    try {
                        rootController.editProgram(program);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            deleteButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    RootController rootController = (RootController) getScene().getRoot().getUserData();
                    rootController.deleteProgram(program);
                }
            });

            leftBox.getChildren().addAll(nameLabel);
            rightBox.getChildren().addAll(executeButton, editButton, deleteButton);
            rightBox.setAlignment(Pos.BASELINE_RIGHT);
            rightBox.setSpacing(5);
            topBox.getChildren().addAll(leftBox, rightBox);
            HBox.setHgrow(leftBox, Priority.ALWAYS);
            HBox.setHgrow(rightBox, Priority.ALWAYS);

            setGraphic(topBox);
        }
    }
}