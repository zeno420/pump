package design;

import daten.Workout;
import javafx.scene.control.ListCell;

public class WorkoutAnzeigenCell extends ListCell<Workout> {
    @Override
    public void updateItem(Workout workout, boolean empty) {
        super.updateItem(workout, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(workout.getName());
            setGraphic(null);
        }
    }
}