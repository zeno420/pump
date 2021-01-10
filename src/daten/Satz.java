package daten;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Callback;

public class Satz {

    private StringProperty wiederholungen = new SimpleStringProperty();
    private StringProperty gewicht = new SimpleStringProperty();

    private Valid valid = Valid.VALID;

    public void isValid() {

        if (wiederholungen == null || !isValidNumber(wiederholungen.get(), false)) {
            valid = Valid.WIEDERHOLUNGEN;
        } else if (gewicht == null || !isValidNumber(gewicht.get(), true)) {
            valid = Valid.GEWICHT;
        } else {
            valid = Valid.VALID;
        }
    }

    public enum Valid {

        VALID(0, ""), WIEDERHOLUNGEN(1, "Wiederholungen ungültig"), GEWICHT(2, "Gewicht ungültig");

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

    public static Callback<Satz, Observable[]> makeExtractor(){
        return new Callback<Satz, Observable[]>() {
            @Override
            public Observable[] call(Satz satz) {
                return new Observable[] {satz.wiederholungenProperty(), satz.gewichtProperty()};
            }
        };
    }

    public Valid getValid() {
        return valid;
    }

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

    public static boolean isValidNumber(String strNum, boolean pointAllowed) {
        double d;
        if (strNum == null) {
            return false;
        }
        try {
            d = Double.parseDouble(strNum);
            if (d < 0) {
                return false;
            }
            if (!pointAllowed) {
                if (!(d % 1 == 0)) {
                    return false;
                }
            }
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
