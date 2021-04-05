package domain;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Phase {

    private BooleanProperty masse = new SimpleBooleanProperty(true);

    public boolean isMasse() {
        return masse.get();
    }

    public BooleanProperty masseProperty() {
        return masse;
    }

    public void setMasse(boolean masse) {
        this.masse.set(masse);
    }
}
