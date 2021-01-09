package design;

import daten.Workout;
import javafx.scene.control.ListCell;

public class WorkoutCell extends ListCell<Workout> {
    @Override
    public void updateItem(Workout workout, boolean empty) {
        super.updateItem(workout, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(workout.getName() + " (" + workout.getBeschreibung() + " )");
            setGraphic(null);
        }
    }
}