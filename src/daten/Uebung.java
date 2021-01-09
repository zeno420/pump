package daten;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Uebung {

    private StringProperty name = new SimpleStringProperty();
    private StringProperty beschreibung = new SimpleStringProperty();

    private ListProperty<Satz> masse = new SimpleListProperty<>(FXCollections.observableArrayList());
    private ListProperty<Satz> defi = new SimpleListProperty<>(FXCollections.observableArrayList());


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
}