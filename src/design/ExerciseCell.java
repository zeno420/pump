package design;

import domain.Exercise;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

public class ExerciseCell extends ListCell<Exercise> {
    @Override
    public void updateItem(Exercise exercise, boolean empty) {
        super.updateItem(exercise, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {

            HBox topBox = new HBox();

            Label nameLabel = new Label(exercise.getName());
            topBox.getChildren().addAll(nameLabel);

            setGraphic(topBox);
        }
    }
}