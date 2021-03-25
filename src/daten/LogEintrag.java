package daten;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

import java.time.LocalDateTime;

public class LogEintrag {

    private StringProperty name = new SimpleStringProperty();
    private StringProperty beschreibung = new SimpleStringProperty();
    //TODO date als property
    private LocalDateTime date;

    public LogEintrag(String name, String beschreibung) {
        this.name.set(name);
        this.beschreibung.set(beschreibung);
        this.date = LocalDateTime.now();
    }

    public static Callback<LogEintrag, Observable[]> makeExtractor(){
        return new Callback<LogEintrag, Observable[]>() {
            @Override
            public Observable[] call(LogEintrag logEintrag) {
                return new Observable[] {logEintrag.nameProperty(), logEintrag.beschreibungProperty()};
            }
        };
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
