package design;

import domain.Uebung;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class UebungCell extends ListCell<Uebung> {
    @Override
    public void updateItem(Uebung ue, boolean empty) {
        super.updateItem(ue, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {

            HBox topBox = new HBox();

            Label name = new Label(ue.getName());
            topBox.getChildren().addAll(name);

            setGraphic(topBox);
        }
    }
}