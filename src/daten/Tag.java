package daten;

import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

public class Tag {

    private StringProperty name = new SimpleStringProperty();

    private ListProperty<Workout> workouts = new SimpleListProperty<>(FXCollections.observableArrayList(Workout.makeExtractor()));

    private Valid valid = Valid.VALID;

    public void isValid() {

        if (name == null || name.get() == null || name.get().equalsIgnoreCase("")) {
            valid = Valid.NAME;
        } else if (workouts.get().size() < 1) {
            valid = Valid.WORKOUTS;
        } else {
            valid = Valid.VALID;
        }
    }

    public enum Valid {

        VALID(0, ""), NAME(1, "Name ungültig"), WORKOUTS(2, "Workouts ungültig");

        private int code;
        private String error;

        Valid(int code, String error) {
            this.code = code;
            this.error = error;
        }

        public int getCode() {
            return code;
        }

        public String getError() {
            return error;
        }
    }

    public static Callback<Tag, Observable[]> makeExtractor(){
        return new Callback<Tag, Observable[]>() {
            @Override
            public Observable[] call(Tag tag) {
                return new Observable[] {tag.nameProperty(), tag.workoutsProperty()};
            }
        };
    }

    public Valid getValid() {
        return valid;
    }

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
