package design;

import daten.Uebung;
import javafx.scene.control.ListCell;

public class UebungCell extends ListCell<Uebung> {
    @Override
    public void updateItem(Uebung uebung, boolean empty) {
        super.updateItem(uebung, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(uebung.getName() + " (" + uebung.getBeschreibung() + " )");
            setGraphic(null);
        }
    }
}