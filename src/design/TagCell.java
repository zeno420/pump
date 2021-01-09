package design;

import daten.Tag;
import javafx.scene.control.ListCell;

public class TagCell  extends ListCell<Tag> {
    @Override
    public void updateItem(Tag tag, boolean empty) {
        super.updateItem(tag, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(tag.getName());
            setGraphic(null);
        }
    }
}
