package design;

import daten.Satz;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import main.UebungController;

import java.io.IOException;
import java.util.Map;

public class SatzSpielenCell extends ListCell<Satz> {


    @Override
    public void updateItem(Satz satz, boolean empty) {
        super.updateItem(satz, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            HBox topBox = new HBox();
            HBox leftBox = new HBox();
            HBox rightBox = new HBox();

            Label wiederholungenMalGewicht = new Label(satz.getWiederholungen() + " x " + satz.getGewicht());
            Button bearbeiten = new Button("bearbeiten");

            bearbeiten.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    UebungController uc;
                    if (getScene().getRoot().getUserData() instanceof UebungController) {
                        uc = (UebungController) getScene().getRoot().getUserData();
                    } else {
                        uc = (UebungController) ((Map) getScene().getRoot().getUserData()).get("uebungController");
                    }
                    try {
                        uc.satzBearbeiten(satz);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


            leftBox.getChildren().addAll(wiederholungenMalGewicht);
            rightBox.getChildren().addAll(bearbeiten);
            rightBox.setAlignment(Pos.BASELINE_RIGHT);
            rightBox.setSpacing(5);
            topBox.getChildren().addAll(leftBox, rightBox);
            HBox.setHgrow(leftBox, Priority.ALWAYS);
            HBox.setHgrow(rightBox, Priority.ALWAYS);

            setGraphic(topBox);
        }
    }
}
