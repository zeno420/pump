package domain;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.util.Callback;

public class Set {

    private StringProperty repetitions = new SimpleStringProperty();
    private StringProperty weight = new SimpleStringProperty();
    private BooleanProperty isBulkSet = new SimpleBooleanProperty();

    private Valid valid = Valid.VALID;

    public Set() {
    }

    public enum Valid {

        VALID(0, ""), REPETITIONS(1, "Repetitions invalid."), WEIGHT(2, "Weight invalid.");

        private int code;
        private String errorMessage;

        Valid(int code, String errorMessage) {
            this.code = code;
            this.errorMessage = errorMessage;
        }

        public int getCode() {
            return code;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    public Set makeTmpCopy() {
        Set tmpSet = new Set();
        tmpSet.setRepetitions(repetitions.get());
        tmpSet.setWeight(weight.get());
        tmpSet.setIsBulkSet(isBulkSet.get());
        return tmpSet;
    }

    public static Callback<Set, Observable[]> makeExtractor() {
        return new Callback<Set, Observable[]>() {
            @Override
            public Observable[] call(Set set) {
                return new Observable[]{set.repetitionsProperty(), set.weightProperty()};
            }
        };
    }

    public Valid getValid() {
        if (repetitions == null || isInvalidNumber(repetitions.get(), false)) {
            valid = Valid.REPETITIONS;
        } else if (weight == null || isInvalidNumber(weight.get(), true)) {
            valid = Valid.WEIGHT;
        } else {
            valid = Valid.VALID;
        }
        return valid;
    }

    public String getRepetitions() {
        return repetitions.get();
    }

    public StringProperty repetitionsProperty() {
        return repetitions;
    }

    public void setRepetitions(String repetitions) {
        this.repetitions.set(repetitions);
    }

    public String getWeight() {
        return weight.get();
    }

    public StringProperty weightProperty() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight.set(weight);
    }

    public boolean getIsBulkSet() {
        return isBulkSet.get();
    }

    public BooleanProperty isBulkSetProperty() {
        return isBulkSet;
    }

    public void setIsBulkSet(boolean isBulkSet) {
        this.isBulkSet.set(isBulkSet);
    }

    private static boolean isInvalidNumber(String numberString, boolean pointAllowed) {
        double d;
        if (numberString == null) {
            return true;
        }
        try {
            d = Double.parseDouble(numberString);
            if (d < 0) {
                return true;
            }
            if (!pointAllowed) {
                if (!(d % 1 == 0)) {
                    return true;
                }
            }
        } catch (NumberFormatException numberFormatException) {
            return true;
        }
        return false;
    }
}
