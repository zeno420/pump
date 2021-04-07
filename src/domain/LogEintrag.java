package domain;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class LogEintrag {

    private StringProperty name = new SimpleStringProperty();
    private StringProperty beschreibung = new SimpleStringProperty();
    private StringProperty date = new SimpleStringProperty();

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public LogEintrag() {
    }

    public LogEintrag(String name, String beschreibung) {
        setName(name);
        setBeschreibung(beschreibung);
        setDate(_2String(ZonedDateTime.now()));
    }

    public static Callback<LogEintrag, Observable[]> makeExtractor() {
        return new Callback<LogEintrag, Observable[]>() {
            @Override
            public Observable[] call(LogEintrag logEintrag) {
                return new Observable[]{logEintrag.nameProperty(), logEintrag.beschreibungProperty(), logEintrag.dateProperty()};
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

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public StringProperty dateProperty() {
        return date;
    }

    private String _2String(ZonedDateTime currentDateTime) {
        return currentDateTime.format(formatter);
    }
}
