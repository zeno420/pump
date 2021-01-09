package daten;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Tag {

    private StringProperty name = new SimpleStringProperty();

    private ListProperty<Workout> workouts = new SimpleListProperty<>(FXCollections.observableArrayList());

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public ObservableList<Workout> getWorkouts() {
        return workouts.get();
    }

    public ListProperty<Workout> workoutsProperty() {
        return workouts;
    }

    public void setWorkouts(ObservableList<Workout> workouts) {
        this.workouts.set(workouts);
    }
}
