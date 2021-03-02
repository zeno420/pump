package daten;

import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import main.Main;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Uebung {

    private StringProperty name = new SimpleStringProperty();
    private StringProperty beschreibung = new SimpleStringProperty();

    private ListProperty<Satz> masse = new SimpleListProperty<>(FXCollections.observableArrayList(Satz.makeExtractor()));
    private ListProperty<Satz> defi = new SimpleListProperty<>(FXCollections.observableArrayList(Satz.makeExtractor()));

    private Valid valid = Valid.VALID;

    //static id generator shared among all instances of Coordinates
    //private static final AtomicInteger idGenerator = new AtomicInteger(1000);

    @XmlAttribute
    @XmlID
    private final String uid;

    public Uebung() {
        //assign unique id to an instance variable
        uid = "u-" + UUID.randomUUID().toString();
    }

    public String getId() {
        //return instance variable
        return uid;
    }


    public enum Valid {

        VALID(0, ""), NONAME(1, "Name ungültig"),
        NAME(2, "Eine Übung mit diesem Name existiert bereits"),
        MASSE(3, "Massesätze ungültig"), DEFI(4, "Definitionssätze ungültig");

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

    public Uebung makeTmpCopy() {
        Uebung tmpUebung = new Uebung();
        tmpUebung.setName(name.get());
        tmpUebung.setBeschreibung(beschreibung.get());
        tmpUebung.getMasse().addAll(masse.get());
        tmpUebung.getDefi().addAll(defi.get());
        return tmpUebung;
    }

    public static Callback<Uebung, Observable[]> makeExtractor() {
        return new Callback<Uebung, Observable[]>() {
            @Override
            public Observable[] call(Uebung uebung) {
                return new Observable[]{uebung.nameProperty(), uebung.beschreibungProperty(), uebung.masseProperty(), uebung.defiProperty()};
            }
        };
    }

    //TODO name nur einmal zulassen
    public Valid getValid(List<String> existingNamesList) {
        boolean containsSearchStr = existingNamesList.stream().anyMatch(name.get()::equalsIgnoreCase);
        if(containsSearchStr){
            valid = Valid.NAME;
        } else if (name == null || name.get() == null || name.get().equalsIgnoreCase("")) {
            valid = Valid.NONAME;
        } else if (masse.get().size() < 1) {
            valid = Valid.MASSE;
        } else if (defi.get().size() < 1) {
            valid = Valid.DEFI;
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

    public ObservableList<Satz> getMasse() {
        return masse.get();
    }

    public ListProperty<Satz> masseProperty() {
        return masse;
    }

    public void setMasse(ObservableList<Satz> masse) {
        this.masse.set(masse);
    }

    public ObservableList<Satz> getDefi() {
        return defi.get();
    }

    public ListProperty<Satz> defiProperty() {
        return defi;
    }

    public void setDefi(ObservableList<Satz> defi) {
        this.defi.set(defi);
    }

    public ObservableList<Satz> getSaetze() {
        if (Main.getPhase().isMasse()) {
            return masse.get();

        } else {
            return defi.get();
        }
    }

}