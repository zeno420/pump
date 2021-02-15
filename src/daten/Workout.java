package daten;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

public class Workout {

    private StringProperty name = new SimpleStringProperty();
    private StringProperty beschreibung = new SimpleStringProperty();

    private ListProperty<Uebung> uebungen = new SimpleListProperty<>(FXCollections.observableArrayList(Uebung.makeExtractor()));

    private IntegerProperty currentUebungIndex = new SimpleIntegerProperty(0);

    private Valid valid = Valid.VALID;


    public enum Valid {

        VALID(0, ""), NAME(1, "Name ungültig"), UEBUNG(2, "Übungen ungültig");

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

    public Workout makeTmpCopy(){
        Workout tmpWorkout = new Workout();
        tmpWorkout.setName(name.get());
        tmpWorkout.setBeschreibung(beschreibung.get());
        tmpWorkout.getUebungen().addAll(uebungen.get());
        return tmpWorkout;
    }

    public static Callback<Workout, Observable[]> makeExtractor(){
        return new Callback<Workout, Observable[]>() {
            @Override
            public Observable[] call(Workout workout) {
                return new Observable[] {workout.nameProperty(), workout.beschreibungProperty(), workout.uebungenProperty()};
            }
        };
    }

    public Valid getValid() {
        if (name == null || name.get() == null) {
            valid = Valid.NAME;
        } else if (name.get().equalsIgnoreCase("")) {
            valid = Valid.NAME;
        } else if (uebungen.get().size() < 1) {
            valid = Valid.UEBUNG;
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

    public String getBeschreibung() {
        return beschreibung.get();
    }

    public StringProperty beschreibungProperty() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung.set(beschreibung);
    }

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
