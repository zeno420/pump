package daten;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import main.Pump;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Uebung implements UniqueNamed {

    private StringProperty name = new SimpleStringProperty();
    private StringProperty beschreibung = new SimpleStringProperty();

    private ListProperty<Satz> masse = new SimpleListProperty<>(FXCollections.observableArrayList(Satz.makeExtractor()));
    private ListProperty<Satz> defi = new SimpleListProperty<>(FXCollections.observableArrayList(Satz.makeExtractor()));

    private UebungValid uebungValid = UebungValid.NONAME;

    private List<Property> aenderbareMember = new ArrayList<>();

    //static id generator shared among all instances of Coordinates
    //private static final AtomicInteger idGenerator = new AtomicInteger(1000);

    @XmlAttribute
    @XmlID
    private final String uid;

    public Uebung() {
        //assign unique id to an instance variable
        uid = "u-" + UUID.randomUUID().toString();
        name.set("");
        beschreibung.set("");

        aenderbareMember.add(name);
        aenderbareMember.add(beschreibung);
        aenderbareMember.add(masse);
        aenderbareMember.add(defi);
    }

    public String getId() {
        //return instance variable
        return uid;
    }


    public List<Property> getAenderbareMember() {
        return aenderbareMember;
    }

    public void aenderbareMemberUebertragen(List<Property> tmpAenderbareMember) {
        for (int i = 0; i < tmpAenderbareMember.size(); i++) {
            aenderbareMember.get(i).setValue(tmpAenderbareMember.get(i).getValue());

        }
    }


    public enum UebungValid {

        VALID(0, ""), NONAME(1, "Name ungültig"),
        MASSE(2, "Massesätze ungültig"), DEFI(2, "Definitionssätze ungültig");

        private int code;
        private String error;

        UebungValid(int code, String error) {
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
        tmpUebung.setUebungValid(uebungValid);
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

    public UebungValid getUebungValid() {
        if (name == null || name.get() == null || name.get().equalsIgnoreCase("")) {
            uebungValid = UebungValid.NONAME;
        } else if (masse.get().size() < 1) {
            uebungValid = UebungValid.MASSE;
        } else if (defi.get().size() < 1) {
            uebungValid = UebungValid.DEFI;
        } else {
            uebungValid = UebungValid.VALID;
        }
        return uebungValid;
    }

    private void setUebungValid(UebungValid uebungValid) {
        this.uebungValid = uebungValid;
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
        if (Pump.datenbasis.getPhase().isMasse()) {
            return masse.get();

        } else {
            return defi.get();
        }
    }

}