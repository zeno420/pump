package design;

import daten.Programm;
import daten.Tag;
import daten.Workout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import main.ProgrammController;
import main.RootController;

import java.io.IOException;

public class PlayWorkoutCell extends ListCell<Workout> {



    @Override
    public void updateItem(Workout workout, boolean empty) {
        super.updateItem(workout, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            HBox box = new HBox();
            Label name = new Label(workout.getName());

            Button start = new Button("start");

            start.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                   /* //ProgrammController pc = (ProgrammController) getScene().getRoot().getUserData();
                    try {
                        //TODO
                        //pc.
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }
            });

            box.getChildren().addAll(name, start);
            setGraphic(box);
        }
    }
}
