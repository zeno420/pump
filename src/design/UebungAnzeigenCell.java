package design;

import daten.Uebung;
import javafx.scene.control.ListCell;

public class UebungAnzeigenCell extends ListCell<Uebung> {
    @Override
    public void updateItem(Uebung uebung, boolean empty) {
        super.updateItem(uebung, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(uebung.getName());
            setGraphic(null);
        }
    }
}