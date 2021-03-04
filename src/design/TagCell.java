package design;

import daten.Tag;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import main.ProgrammController;
import main.RootController;

import java.io.IOException;

public class TagCell extends ListCell<Tag> {
    @Override
    public void updateItem(Tag tag, boolean empty) {
        super.updateItem(tag, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            HBox topBox = new HBox();
            HBox leftBox = new HBox();
            HBox rightBox = new HBox();

            Label name = new Label(tag.getName());
            Button bearbeiten = new Button("bearbeiten");
            Button loeschen = new Button("löschen");
            Button up = new Button("move up");
            Button down = new Button("move down");

            bearbeiten.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    ProgrammController pc = (ProgrammController) getScene().getRoot().getUserData();
                    try {
                        pc.tagBearbeiten(tag);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            loeschen.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    ProgrammController pc = (ProgrammController) getScene().getRoot().getUserData();
                    pc.tagLoeschen(tag);
                }
            });

            up.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    int index = getListView().getItems().indexOf(getItem());

                    if (index > 0) {

                        Tag t1 = getItem();
                        Tag t2 = getListView().getItems().get(index - 1);

                        getListView().getItems().set(index, t2);
                        getListView().getItems().set(index - 1, t1);
                    }
                }
            });

            down.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    int index = getListView().getItems().indexOf(getItem());

                    if (index < getListView().getItems().size()) {

                        Tag t1 = getItem();
                        Tag t2 = getListView().getItems().get(index + 1);

                        getListView().getItems().set(index, t2);
                        getListView().getItems().set(index + 1, t1);
                    }
                }
            });

            leftBox.getChildren().addAll(name);
            rightBox.getChildren().addAll(up, down, bearbeiten, loeschen);
            rightBox.setAlignment(Pos.BASELINE_RIGHT);
            rightBox.setSpacing(5);
            topBox.getChildren().addAll(leftBox, rightBox);
            HBox.setHgrow(leftBox, Priority.ALWAYS);
            HBox.setHgrow(rightBox, Priority.ALWAYS);

            setGraphic(topBox);
        }
    }
}
