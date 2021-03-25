package daten;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Programm {

    private StringProperty name = new SimpleStringProperty();
    private StringProperty beschreibung = new SimpleStringProperty();

    private ListProperty<Tag> tage = new SimpleListProperty<>(FXCollections.observableArrayList(Tag.makeExtractor()));

    private Valid valid = Valid.NONAME;
    private IntegerProperty currentTagIndex = new SimpleIntegerProperty(0);

    //static id generator shared among all instances of Coordinates
    //private static final AtomicInteger idGenerator = new AtomicInteger(1000);

    @XmlAttribute
    @XmlID
    private final String pid;

    public Programm() {
        //assign unique id to an instance variable
        pid = "p-" + UUID.randomUUID().toString();
        name.set("");
        beschreibung.set("");
    }

    public String getId() {
        //return instance variable
        return pid;
    }


    public enum Valid {
        VALID(0, ""), NONAME(1, "Name ungültig"), TAGE(2, "Tage ungültig"),
        NAME(3, "Ein Programm mit diesem Name existiert bereits");

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

    public Programm makeTmpCopy(){
        Programm tmpProgramm = new Programm();
        tmpProgramm.setName(name.get());
        tmpProgramm.setBeschreibung(beschreibung.get());
        tmpProgramm.getTage().addAll(tage.get());
        return tmpProgramm;
    }

    public static Callback<Programm, Observable[]> makeExtractor(){
        return new Callback<Programm, Observable[]>() {
            @Override
            public Observable[] call(Programm programm) {
                return new Observable[] {programm.nameProperty(), programm.beschreibungProperty(), programm.tageProperty()};
            }
        };
    }

    public Valid getValid(List<String> existingNamesList) {
        boolean containsSearchStr = existingNamesList.stream().anyMatch(name.get()::equalsIgnoreCase);
        if(containsSearchStr){
            valid = Valid.NAME;
        } else if (name == null || name.get() == null || name.get().equalsIgnoreCase("")) {
            valid = Valid.NONAME;
        } else if (tage.get().size() < 1) {
            valid = Valid.TAGE;
        } else {
            valid = Valid.VALID;
        }
        return valid;
    }

    public void setValid(Valid valid) {
        this.valid = valid;
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

    public ObservableList<Tag> getTage() {
        return tage.get();
    }

    public ListProperty<Tag> tageProperty() {
        return tage;
    }

    public void setTage(ObservableList<Tag> tage) {
        this.tage.set(tage);
    }

    public int getCurrentTagIndex() {
        return currentTagIndex.get();
    }

    public IntegerProperty currentTagIndexProperty() {
        return currentTagIndex;
    }

    public void setCurrentTagIndex(int currentTagIndex) {
        this.currentTagIndex.set(currentTagIndex);
    }

    public void increaseAktuellerTag() {
        setCurrentTagIndex(currentTagIndex.get() + 1);
        validateIndex();
    }

    public void decreaseAktuellerTag() {
        setCurrentTagIndex(currentTagIndex.get() - 1);
        validateIndex();
    }

    private void validateIndex() {
        if (currentTagIndex.get() > tage.get().size() - 1) {
            currentTagIndex.set(0);
        } else if (currentTagIndex.get() < 0) {
            currentTagIndex.set(tage.get().size() - 1);
        }
    }
}
