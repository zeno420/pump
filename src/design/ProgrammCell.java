package design;

import domain.Programm;
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

public class ProgrammCell extends ListCell<Programm> {

    @Override
    public void updateItem(Programm programm, boolean empty) {
        super.updateItem(programm, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            HBox topBox = new HBox();
            HBox leftBox = new HBox();
            HBox rightBox = new HBox();

            Label name = new Label(programm.getName());
            Button bearbeiten = new Button("bearbeiten");
            Button start = new Button("start");
            Button loeschen = new Button("löschen");

            start.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    RootController rc = (RootController) getScene().getRoot().getUserData();
                    try {
                        rc.programmSpielen(programm);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            bearbeiten.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    RootController rc = (RootController) getScene().getRoot().getUserData();
                    try {
                        rc.programmBearbeiten(programm);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            loeschen.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    RootController rc = (RootController) getScene().getRoot().getUserData();
                    rc.programmLoeschen(programm);
                }
            });

            leftBox.getChildren().addAll(name);
            rightBox.getChildren().addAll(start, bearbeiten, loeschen);
            rightBox.setAlignment(Pos.BASELINE_RIGHT);
            rightBox.setSpacing(5);
            topBox.getChildren().addAll(leftBox, rightBox);
            HBox.setHgrow(leftBox, Priority.ALWAYS);
            HBox.setHgrow(rightBox, Priority.ALWAYS);

            setGraphic(topBox);
        }
    }
}
