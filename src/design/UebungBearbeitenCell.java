
package design;

import daten.Uebung;
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

public class UebungBearbeitenCell extends ListCell<Uebung> {
    @Override
    public void updateItem(Uebung uebung, boolean empty) {
        super.updateItem(uebung, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            HBox topBox = new HBox();
            HBox leftBox = new HBox();
            HBox rightBox = new HBox();

            Label name = new Label(uebung.getName());
            Button bearbeiten = new Button("bearbeiten");
            Button loeschen = new Button("löschen");

            bearbeiten.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    RootController rc = (RootController) getScene().getRoot().getUserData();
                    try {
                        rc.uebungBearbeiten(uebung);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            loeschen.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    RootController rc = (RootController) getScene().getRoot().getUserData();
                    rc.uebungLoeschen(uebung);
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
