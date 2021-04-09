package domain;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


public class LogEntry {

    private StringProperty name = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();
    private StringProperty date = new SimpleStringProperty();

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    public LogEntry() {
    }

    public LogEntry(String name, String description) {
        setName(name);
        setDescription(description);
        setDate(_2String(ZonedDateTime.now()));
    }

    public static Callback<LogEntry, Observable[]> makeExtractor() {
        return new Callback<LogEntry, Observable[]>() {
            @Override
            public Observable[] call(LogEntry logEntry) {
                return new Observable[]{logEntry.nameProperty(), logEntry.descriptionProperty(), logEntry.dateProperty()};
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

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
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
