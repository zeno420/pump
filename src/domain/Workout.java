package domain;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Workout implements UniqueNamed, EditableDomainObject {

    private StringProperty name = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();

    private ListProperty<Exercise> exercises = new SimpleListProperty<>(FXCollections.observableArrayList(Exercise.makeExtractor()));

    private IntegerProperty currentExerciseIndex = new SimpleIntegerProperty(0);

    private WorkoutValid workoutValid = WorkoutValid.NONAME;

    private List<Property> editableMembers = new ArrayList<>();

    @XmlAttribute
    @XmlID
    private final String wid;

    public Workout() {
        //assign unique id to an instance variable
        wid = "w-" + UUID.randomUUID().toString();
        name.set("");
        description.set("");

        editableMembers.add(name);
        editableMembers.add(description);
        editableMembers.add(exercises);
    }

    public String getId() {
        return wid;
    }

    public enum WorkoutValid {
        VALID(0, ""), NONAME(1, "Name invalid."), EXERCISE(2, "Exercise invalid.");

        private int code;
        private String errorText;

        WorkoutValid(int code, String errorText) {
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

    public Workout makeTmpCopy() {
        Workout tmpWorkout = new Workout();
        tmpWorkout.setName(name.get());
        tmpWorkout.setDescription(description.get());
        tmpWorkout.getExercises().addAll(exercises.get());
        return tmpWorkout;
    }

    public static Callback<Workout, Observable[]> makeExtractor() {
        return new Callback<Workout, Observable[]>() {
            @Override
            public Observable[] call(Workout workout) {
                return new Observable[]{workout.nameProperty(), workout.descriptionProperty(), workout.exercisesProperty()};
            }
        };
    }

    public WorkoutValid getWorkoutValid() {

        if (name == null || name.get() == null) {
            workoutValid = WorkoutValid.NONAME;
        } else if (name.get().equalsIgnoreCase("")) {
            workoutValid = WorkoutValid.NONAME;
        } else if (exercises.get().size() < 1) {
            workoutValid = WorkoutValid.EXERCISE;
        } else {
            workoutValid = WorkoutValid.VALID;
        }
        return workoutValid;
    }

    public List<Property> getEditableMembers() {
        return editableMembers;
    }

    public void transferEditableMembers(List<Property> tmpAenderbareMember) {
        //set aber kein setter
        for (int i = 0; i < tmpAenderbareMember.size(); i++) {
            editableMembers.get(i).setValue(tmpAenderbareMember.get(i).getValue());

        }
    }

    public void setWorkoutValid(WorkoutValid workoutValid) {
        this.workoutValid = workoutValid;
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

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    @XmlIDREF
    public ObservableList<Exercise> getExercises() {
        return exercises.get();
    }

    public ListProperty<Exercise> exercisesProperty() {
        return exercises;
    }

    public void setExercises(ObservableList<Exercise> exercises) {
        this.exercises.set(exercises);
    }

    public int getCurrentExerciseIndex() {
        validateIndex();
        return currentExerciseIndex.get();
    }

    public IntegerProperty currentExerciseIndexProperty() {
        return currentExerciseIndex;
    }

    public void setCurrentExerciseIndex(int currentExerciseIndex) {
        this.currentExerciseIndex.set(currentExerciseIndex);
    }

    public void increaseCurrentExerciseIndex() {
        setCurrentExerciseIndex(currentExerciseIndex.get() + 1);
        validateIndex();
    }

    public void decreaseCurrentExerciseIndex() {
        setCurrentExerciseIndex(currentExerciseIndex.get() - 1);
        validateIndex();
    }

    private void validateIndex() {
        if (currentExerciseIndex.get() > exercises.get().size() - 1) {
            currentExerciseIndex.set(0);
        } else if (currentExerciseIndex.get() < 0) {
            currentExerciseIndex.set(exercises.get().size() - 1);
        }
    }
}
