package daten;

import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

import javax.xml.bind.annotation.XmlIDREF;

public class Tag {

    private StringProperty name = new SimpleStringProperty();

    private ListProperty<Workout> workouts = new SimpleListProperty<>(FXCollections.observableArrayList(Workout.makeExtractor()));

    private Valid valid = Valid.VALID;


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

    public Tag makeTmpCopy(){
        Tag tmpTag = new Tag();
        tmpTag.setName(name.get());
        tmpTag.getWorkouts().addAll(workouts.get());
        return tmpTag;
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
        if (name == null || name.get() == null || name.get().equalsIgnoreCase("")) {
            valid = Valid.NAME;
        } else if (workouts.get().size() < 1) {
            valid = Valid.WORKOUTS;
        } else {
            valid = Valid.VALID;
        }
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

    @XmlIDREF
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
