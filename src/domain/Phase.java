package domain;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Phase {

    private BooleanProperty bulk = new SimpleBooleanProperty(true);

    public boolean getBulk() {
        return bulk.get();
    }

    public BooleanProperty bulkProperty() {
        return bulk;
    }

    public void setBulk(boolean bulk) {
        this.bulk.set(bulk);
    }
}
