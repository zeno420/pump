package design;

import daten.Programm;
import daten.Satz;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import main.RootController;
import main.UebungController;

import java.io.IOException;

public class SatzCell extends ListCell<Satz> {



    @Override
    public void updateItem(Satz satz, boolean empty) {
        super.updateItem(satz, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            HBox box = new HBox();
            Label wdh = new Label(satz.getWiederholungen() + " x ");
            Label gew = new Label(satz.getGewicht());
            Button bearbeiten = new Button("bearbeiten");

            bearbeiten.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    UebungController uc = (UebungController) getScene().getRoot().getUserData();
                    try {
                        //TODO masse defi switch
                        uc.masseSatzBearbeiten(satz);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            box.getChildren().addAll(wdh, gew, bearbeiten);

            //setText(programm.getName());
            setGraphic(box);
        }
    }
}
