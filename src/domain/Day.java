package domain;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

import javax.xml.bind.annotation.XmlIDREF;

public class Day {

    private StringProperty name = new SimpleStringProperty();
    private ListProperty<Workout> workouts = new SimpleListProperty<>(FXCollections.observableArrayList(Workout.makeExtractor()));
    private Valid valid = Valid.VALID;

    public Day() {
    }

    public enum Valid {

        VALID(0, ""), NAME(1, "Name invalid.");

        private int code;
        private String errorText;

        Valid(int code, String errorText) {
            this.code = code;
            this.errorText = errorText;
        }

        public int getCode() {
            return code;
        }

        public String getErrorText() {
            return errorText;
        }
    }

    public Day makeTmpCopy() {
        Day tmpDay = new Day();
        tmpDay.setName(name.get());
        tmpDay.getWorkouts().addAll(workouts.get());
        return tmpDay;
    }

    public static Callback<Day, Observable[]> makeExtractor() {
        return new Callback<Day, Observable[]>() {
            @Override
            public Observable[] call(Day day) {
                return new Observable[]{day.nameProperty(), day.workoutsProperty()};
            }
        };
    }

    public Valid getValid() {
        if (name == null || name.get() == null || name.get().equalsIgnoreCase("")) {
            valid = Valid.NAME;
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
