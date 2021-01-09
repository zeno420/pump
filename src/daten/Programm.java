package daten;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Programm {

    private StringProperty name = new SimpleStringProperty();
    private StringProperty beschreibung = new SimpleStringProperty();

    private ListProperty<Tag> tage = new SimpleListProperty<>(FXCollections.observableArrayList());

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
}
