package controller;

import domain.EditableDomainObject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EditDialogBuilder<T extends EditableDomainObject> {

    private String fxmlResource;
    private T editableObject;
    private String windowTitle = "";

    public EditDialogBuilder() {
    }

    public EditDialogBuilder<T> setFxmlResource(String fxmlResource) {
        this.fxmlResource = fxmlResource;
        return this;
    }

    public EditDialogBuilder<T> setEditableObject(T object) {
        this.editableObject = object;
        return this;
    }

    public EditDialogBuilder<T> setWindowTitle(String windowTitle) {
        this.windowTitle = windowTitle;
        return this;
    }

    public <TC extends SetupableController<T>> Stage build() throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(this.fxmlResource));
        Parent parentDialog = fxmlloader.load();

        Stage stage = new Stage();

        TC controller = fxmlloader.getController();
        parentDialog.setUserData(controller);

        controller.setUpBindingEdit(this.editableObject, parentDialog);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(this.windowTitle);
        stage.setScene(new Scene(parentDialog));

        return stage;
    }
}