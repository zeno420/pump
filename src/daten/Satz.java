package daten;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Satz {

    private StringProperty wiederholungen = new SimpleStringProperty();
    private StringProperty gewicht = new SimpleStringProperty();

    public String getWiederholungen() {
        return wiederholungen.get();
    }

    public StringProperty wiederholungenProperty() {
        return wiederholungen;
    }

    public void setWiederholungen(String wiederholungen) {
        this.wiederholungen.set(wiederholungen);
    }

    public String getGewicht() {
        return gewicht.get();
    }

    public StringProperty gewichtProperty() {
        return gewicht;
    }

    public void setGewicht(String gewicht) {
        this.gewicht.set(gewicht);
    }
}
