package design;

import daten.Satz;
import daten.Uebung;
import javafx.scene.control.ListCell;

public class SatzCell extends ListCell<Satz> {
    //TODO edit button in cell damit bei play editierbar
    @Override
    public void updateItem(Satz satz, boolean empty) {
        super.updateItem(satz, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(satz.getWiederholungen() + " (" + satz.getGewicht() + " )");
            setGraphic(null);
        }
    }
}
