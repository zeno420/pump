package domain;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Workout implements UniqueNamed, EditableDomainObject {

    private StringProperty name = new SimpleStringProperty();
    private StringProperty beschreibung = new SimpleStringProperty();

    private ListProperty<Uebung> uebungen = new SimpleListProperty<>(FXCollections.observableArrayList(Uebung.makeExtractor()));

    private IntegerProperty currentUebungIndex = new SimpleIntegerProperty(0);

    private WorkoutValid workoutValid = WorkoutValid.NONAME;

    private List<Property> aenderbareMember = new ArrayList<>();


    //static id generator shared among all instances of Coordinates
    //private static final AtomicInteger idGenerator = new AtomicInteger(1000);

    @XmlAttribute
    @XmlID
    private final String wid;

    public Workout() {
        //assign unique id to an instance variable
        wid = "w-" + UUID.randomUUID().toString();
        name.set("");
        beschreibung.set("");

        aenderbareMember.add(name);
        aenderbareMember.add(beschreibung);
        aenderbareMember.add(uebungen);
    }

    public String getId() {
        //return instance variable
        return wid;
    }


    public enum WorkoutValid {
        VALID(0, ""), NONAME(1, "Name ungültig"), UEBUNG(2, "Übungen ungültig");

        private int code;
        private String error;

        WorkoutValid(int code, String error) {
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

    public Workout makeTmpCopy() {
        Workout tmpWorkout = new Workout();
        tmpWorkout.setName(name.get());
        tmpWorkout.setBeschreibung(beschreibung.get());
        tmpWorkout.getUebungen().addAll(uebungen.get());
        return tmpWorkout;
    }

    public static Callback<Workout, Observable[]> makeExtractor() {
        return new Callback<Workout, Observable[]>() {
            @Override
            public Observable[] call(Workout workout) {
                return new Observable[]{workout.nameProperty(), workout.beschreibungProperty(), workout.uebungenProperty()};
            }
        };
    }

    public WorkoutValid getWorkoutValid() {

        if (name == null || name.get() == null) {
            workoutValid = WorkoutValid.NONAME;
        } else if (name.get().equalsIgnoreCase("")) {
            workoutValid = WorkoutValid.NONAME;
        } else if (uebungen.get().size() < 1) {
            workoutValid = WorkoutValid.UEBUNG;
        } else {
            workoutValid = WorkoutValid.VALID;
        }
        return workoutValid;
    }

    public List<Property> getAenderbareMember() {
        return aenderbareMember;
    }

    public void aenderbareMemberUebertragen(List<Property> tmpAenderbareMember) {
        //set aber kein setter
        for (int i = 0; i < tmpAenderbareMember.size(); i++) {
            aenderbareMember.get(i).setValue(tmpAenderbareMember.get(i).getValue());

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

    public String getBeschreibung() {
        return beschreibung.get();
    }

    public StringProperty beschreibungProperty() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung.set(beschreibung);
    }

    @XmlIDREF
    public ObservableList<Uebung> getUebungen() {
        return uebungen.get();
    }

    public ListProperty<Uebung> uebungenProperty() {
        return uebungen;
    }

    public void setUebungen(ObservableList<Uebung> uebungen) {
        this.uebungen.set(uebungen);
    }

    public int getCurrentUebungIndex() {
        validateIndex();
        return currentUebungIndex.get();
    }

    public IntegerProperty currentUebungIndexProperty() {
        return currentUebungIndex;
    }

    public void setCurrentUebungIndex(int currentUebungIndex) {
        this.currentUebungIndex.set(currentUebungIndex);
    }

    public void increaseAktuelleUebung() {
        setCurrentUebungIndex(currentUebungIndex.get() + 1);
        validateIndex();
    }

    public void decreaseAktuelleUebung() {
        setCurrentUebungIndex(currentUebungIndex.get() - 1);
        validateIndex();
    }

    private void validateIndex() {
        if (currentUebungIndex.get() > uebungen.get().size() - 1) {
            currentUebungIndex.set(0);
        } else if (currentUebungIndex.get() < 0) {
            currentUebungIndex.set(uebungen.get().size() - 1);
        }
    }
}