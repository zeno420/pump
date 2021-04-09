package domain;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
//import main.Pump;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Exercise implements UniqueNamed, EditableDomainObject {

    private StringProperty name = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();

    private ListProperty<Set> bulkingSets = new SimpleListProperty<>(FXCollections.observableArrayList(Set.makeExtractor()));
    private ListProperty<Set> cuttingSets = new SimpleListProperty<>(FXCollections.observableArrayList(Set.makeExtractor()));

    private ExerciseValid exerciseValid = ExerciseValid.NONAME;

    private List<Property> editableMembers = new ArrayList<>();

    //static id generator shared among all instances of Coordinates
    //private static final AtomicInteger idGenerator = new AtomicInteger(1000);

    @XmlAttribute
    @XmlID
    private final String eid;

    public Exercise() {
        //assign unique id to an instance variable
        eid = "e-" + UUID.randomUUID().toString();
        name.set("");
        description.set("");

        editableMembers.add(name);
        editableMembers.add(description);
        editableMembers.add(bulkingSets);
        editableMembers.add(cuttingSets);
    }

    public String getId() {
        //return instance variable
        return eid;
    }


    public List<Property> getEditableMembers() {
        return editableMembers;
    }

    public void aenderbareMemberUebertragen(List<Property> tmpAenderbareMember) {

        for (int i = 0; i < tmpAenderbareMember.size(); i++) {
            editableMembers.get(i).setValue(tmpAenderbareMember.get(i).getValue());
        }
    }


    public enum ExerciseValid {

        VALID(0, ""), NONAME(1, "Name invalid."),
        BULKING(2, "Bulking sets invalid."), CUTTING(2, "Cutting sets invalid.");

        private int code;
        private String errorText;

        ExerciseValid(int code, String errorText) {
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

    public Exercise makeTmpCopy() {
        Exercise tmpExercise = new Exercise();
        tmpExercise.setName(name.get());
        tmpExercise.setDescription(description.get());
        tmpExercise.getBulkingSets().addAll(bulkingSets.get());
        tmpExercise.getCuttingSets().addAll(cuttingSets.get());
        tmpExercise.setExerciseValid(exerciseValid);
        return tmpExercise;
    }

    public static Callback<Exercise, Observable[]> makeExtractor() {
        return new Callback<Exercise, Observable[]>() {
            @Override
            public Observable[] call(Exercise exercise) {
                return new Observable[]{exercise.nameProperty(), exercise.descriptionProperty(), exercise.bulkingSetsProperty(), exercise.cuttingSetsProperty()};
            }
        };
    }

    public ExerciseValid getExerciseValid() {
        if (name == null || name.get() == null || name.get().equalsIgnoreCase("")) {
            exerciseValid = ExerciseValid.NONAME;
        } else if (bulkingSets.get().size() < 1) {
            exerciseValid = ExerciseValid.BULKING;
        } else if (cuttingSets.get().size() < 1) {
            exerciseValid = ExerciseValid.CUTTING;
        } else {
            exerciseValid = ExerciseValid.VALID;
        }
        return exerciseValid;
    }

    private void setExerciseValid(ExerciseValid exerciseValid) {
        this.exerciseValid = exerciseValid;
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

    public ObservableList<Set> getBulkingSets() {
        return bulkingSets.get();
    }

    public ListProperty<Set> bulkingSetsProperty() {
        return bulkingSets;
    }

    public void setBulkingSets(ObservableList<Set> bulkingSets) {
        this.bulkingSets.set(bulkingSets);
    }

    public ObservableList<Set> getCuttingSets() {
        return cuttingSets.get();
    }

    public ListProperty<Set> cuttingSetsProperty() {
        return cuttingSets;
    }

    public void setCuttingSets(ObservableList<Set> cuttingSets) {
        this.cuttingSets.set(cuttingSets);
    }

    public ObservableList<Set> getSets(boolean isBulkingPhase) {
        if (isBulkingPhase) {
            return bulkingSets.get();

        } else {
            return cuttingSets.get();
        }
    }

}