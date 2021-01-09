package design;

import daten.Programm;
import daten.Tag;
import javafx.scene.control.ListCell;

public class ProgrammCell extends ListCell<Programm> {
    @Override
    public void updateItem(Programm programm, boolean empty) {
        super.updateItem(programm, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(programm.getName());
            setGraphic(null);
        }
    }
}
