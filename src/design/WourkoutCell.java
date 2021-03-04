package design;

import daten.Workout;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class WourkoutCell extends ListCell<Workout> {
    @Override
    public void updateItem(Workout workout, boolean empty) {
        super.updateItem(workout, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {

            HBox topBox = new HBox();

            Label name = new Label(workout.getName());
            topBox.getChildren().addAll(name);

            setGraphic(topBox);
        }
    }
}