package main;

import daten.*;
import design.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;


public class Main extends Application {

    private static ObservableList<Uebung> Uebungen = FXCollections.observableArrayList(Uebung.makeExtractor());
    private static ObservableList<Workout> Workouts = FXCollections.observableArrayList(Workout.makeExtractor());
    private static ObservableList<Programm> Programme = FXCollections.observableArrayList(Programm.makeExtractor());

    private String dataFilePath = "datenbank.xml";
    private static File datenbank;

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            datenbank = new File(dataFilePath);
            datenbank.createNewFile();
        } catch (Exception e) {
            System.out.println("no file yet!");
        }
        loadDatenbank();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("root.fxml"));
        Parent root = loader.load();

        root.setUserData(loader.getController());


        primaryStage.setTitle("pump");


        ListView<Uebung> uebungenListView = (ListView) root.lookup("#uebungenListView");
        uebungenListView.setItems(Uebungen);
        uebungenListView.setCellFactory(new Callback<ListView<Uebung>,
                                                ListCell<Uebung>>() {
                                            @Override
                                            public ListCell<Uebung> call(ListView<Uebung> list) {
                                                return new UebungBearbeitenCell();
                                            }
                                        }
        );
        //uebungenListView.itemsProperty().bind(Uebungen);

        ListView<Workout> workoutListView = (ListView) root.lookup("#workoutListView");
        workoutListView.setItems(Workouts);
        workoutListView.setCellFactory(new Callback<ListView<Workout>,
                                               ListCell<Workout>>() {
                                           @Override
                                           public ListCell<Workout> call(ListView<Workout> list) {
                                               return new WorkoutBearbeitenCell();
                                           }
                                       }
        );

        ListView<Programm> programmListView = (ListView) root.lookup("#programmListView");
        programmListView.setItems(Programme);
        programmListView.setCellFactory(new Callback<ListView<Programm>,
                                                ListCell<Programm>>() {
                                            @Override
                                            public ListCell<Programm> call(ListView<Programm> list) {
                                                return new ProgrammCell();
                                            }
                                        }
        );

        primaryStage.setScene(new Scene(root, 1080, 720));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        saveDatenbank();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static ObservableList<Uebung> getUebungen() {
        return Uebungen;
    }

    public static ObservableList<Workout> getWorkouts() {
        return Workouts;
    }

    public static ObservableList<Programm> getProgramme() {
        return Programme;
    }

    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     */
    public void loadDatenbank() throws JAXBException {
        try {
            JAXBContext c = JAXBContext.newInstance(DataWrapper.class);
            Unmarshaller um = c.createUnmarshaller();
            DataWrapper dw = (DataWrapper) um.unmarshal(datenbank);
            Programme.clear();
            Programme.addAll(dw.getProgramme());
            Workouts.clear();
            Workouts.addAll(dw.getWorkouts());
            Uebungen.clear();
            Uebungen.addAll(dw.getUebungen());

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            // alert.setContentText("Could not load data from file:\n" + file.getPath());
            e.printStackTrace(System.out);
            alert.showAndWait();
        }
    }

    /**
     * Saves the current person data to the specified file.
     */
    public static void saveDatenbank() {
        try {
            JAXBContext c = JAXBContext.newInstance(DataWrapper.class);
            Marshaller m = c.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            DataWrapper dw = new DataWrapper();
            dw.setProgramme(Programme);
            dw.setWorkouts(Workouts);
            dw.setUebungen(Uebungen);
            m.marshal(dw, datenbank);

            // Save the file path to the registry.
            // setPersonFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            //alert.setContentText();
            e.printStackTrace(System.out);
            alert.showAndWait();
        }
    }
}