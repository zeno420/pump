package design;

import daten.Programm;
import daten.Tag;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import main.RootController;

import java.io.IOException;

public class ProgrammCell extends ListCell<Programm> {



    @Override
    public void updateItem(Programm programm, boolean empty) {
        super.updateItem(programm, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            HBox box = new HBox();
            Label name = new Label(programm.getName());
            Button bearbeiten = new Button("bearbeiten");
            Button start = new Button("start");

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

            box.getChildren().addAll(name, start, bearbeiten);

            //setText(programm.getName());
            setGraphic(box);
        }
    }
}
