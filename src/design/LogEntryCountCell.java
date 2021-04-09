package design;

import domain.LogEntryCount;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class LogEntryCountCell extends ListCell<LogEntryCount> {
    @Override
    public void updateItem(LogEntryCount logEntryCount, boolean empty) {
        super.updateItem(logEntryCount, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {

            HBox topBox = new HBox();
            HBox leftBox = new HBox();
            HBox rightBox = new HBox();

            Label name = new Label(logEntryCount.getKey());
            Label count = new Label(Integer.toString(logEntryCount.getCount()));

            leftBox.getChildren().addAll(name);
            rightBox.getChildren().addAll(count);
            rightBox.setAlignment(Pos.BASELINE_RIGHT);
            rightBox.setSpacing(5);
            topBox.getChildren().addAll(leftBox, rightBox);
            HBox.setHgrow(leftBox, Priority.ALWAYS);
            HBox.setHgrow(rightBox, Priority.ALWAYS);

            setGraphic(topBox);
        }
    }
}