package controller;

import daten.EditableDomainObject;
import javafx.scene.Parent;

public interface SetupableController <T extends EditableDomainObject> {
    void setUpBindingEdit(T editableDomainObject, Parent parentDialog);
}