package controller;

import daten.Programm;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class EditDialogBuilder {

    private String fxmlResource;
    private Programm programm;
    private String title;

    public EditDialogBuilder() {
    }

    public EditDialogBuilder setFxmlResource(String fxmlResource) {
        this.fxmlResource = fxmlResource;
        return this;
    }

    public EditDialogBuilder setProgramm(Programm programm) {
        this.programm = programm;
        return this;
    }

    public EditDialogBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public Stage build() throws IOException {

        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(this.fxmlResource));
        Parent parentDialog = fxmlloader.load();

        Stage stage = new Stage();

        ProgrammController c = fxmlloader.getController();
        c.setUpBindingEdit(this.programm, parentDialog);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(this.title);
        stage.setScene(new Scene(parentDialog));

        return stage;
    }
}