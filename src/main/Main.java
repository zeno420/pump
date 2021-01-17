package main;

import daten.*;
import design.ProgrammCell;
import design.UebungCell;
import design.WorkoutCell;
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

    private String uebungFilePath = "uebung_datenbank.xml";
    private static File uebungFile;
    private String workoutFilePath = "workout_datenbank.xml";
    private static File workoutFile;
    private String programmFilePath = "programm_datenbank.xml";
    private static File programmFile;



    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            uebungFile = new File(uebungFilePath);
            uebungFile.createNewFile();
            workoutFile = new File(workoutFilePath);
            workoutFile.createNewFile();
            programmFile = new File(programmFilePath);
            programmFile.createNewFile();
        } catch (Exception e) {
            System.out.println("no file yet!");
        }
        loadDatenbank();

        Parent root = FXMLLoader.load(getClass().getResource("root.fxml"));
        primaryStage.setTitle("pump");


        ListView<Uebung> uebungenListView = (ListView) root.lookup("#uebungenListView");
        uebungenListView.setItems(Uebungen);
        uebungenListView.setCellFactory(new Callback<ListView<Uebung>,
                                                ListCell<Uebung>>() {
                                            @Override
                                            public ListCell<Uebung> call(ListView<Uebung> list) {
                                                return new UebungCell();
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
                                                return new WorkoutCell();
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
     *
     */
    public void loadDatenbank() throws JAXBException {
        try {
            JAXBContext uc = JAXBContext.newInstance(UebungListWrapper.class);
            Unmarshaller uum = uc.createUnmarshaller();
            UebungListWrapper uebungWrapper = (UebungListWrapper) uum.unmarshal(uebungFile);
            Uebungen.clear();
            Uebungen.addAll(uebungWrapper.getUebungen());

            JAXBContext wc = JAXBContext.newInstance(WorkoutListWrapper.class);
            Unmarshaller wum = wc.createUnmarshaller();
            WorkoutListWrapper workoutWrapper = (WorkoutListWrapper) wum.unmarshal(workoutFile);
            Workouts.clear();
            Workouts.addAll(workoutWrapper.getWorkouts());

            JAXBContext pc = JAXBContext.newInstance(ProgrammListWrapper.class);
            Unmarshaller pum = pc.createUnmarshaller();
            ProgrammListWrapper programmWrapper = (ProgrammListWrapper) pum.unmarshal(programmFile);
            Programme.clear();
            Programme.addAll(programmWrapper.getProgramme());

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
           // alert.setContentText("Could not load data from file:\n" + file.getPath());
            alert.showAndWait();
        }
    }

    /**
     * Saves the current person data to the specified file.
     *
     */
    public static void saveDatenbank() {
        try {
            JAXBContext uc = JAXBContext.newInstance(UebungListWrapper.class);
            Marshaller um = uc.createMarshaller();
            um.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            UebungListWrapper uebungWrapper = new UebungListWrapper();
            uebungWrapper.setUebungen(Uebungen);
            um.marshal(uebungWrapper, uebungFile);

            JAXBContext wc = JAXBContext.newInstance(WorkoutListWrapper.class);
            Marshaller wm = wc.createMarshaller();
            wm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            WorkoutListWrapper workoutWrapper = new WorkoutListWrapper();
            workoutWrapper.setWorkouts(Workouts);
            wm.marshal(workoutWrapper, workoutFile);

            JAXBContext pc = JAXBContext.newInstance(ProgrammListWrapper.class);
            Marshaller pm = pc.createMarshaller();
            pm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            ProgrammListWrapper programmWrapper = new ProgrammListWrapper();
            programmWrapper.setProgramme(Programme);
            pm.marshal(programmWrapper, programmFile);

            // Save the file path to the registry.
           // setPersonFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
           // alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }
}