package main;

import daten.*;
import design.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Pump extends Application {

    //TODO uebung.fxml spacing zwischen anchorpanes dynamisch machen
    //TODO fullscreen problem beheben

    //TODO sinvollere fehlermeldungen in Valids

    private static ObservableList<Uebung> Uebungen = FXCollections.observableArrayList(Uebung.makeExtractor());
    private static ObservableList<Workout> Workouts = FXCollections.observableArrayList(Workout.makeExtractor());
    private static ObservableList<Programm> Programme = FXCollections.observableArrayList(Programm.makeExtractor());

    private static ObservableList<LogEintrag> UebungLogs = FXCollections.observableArrayList(LogEintrag.makeExtractor());
    private static ObservableList<LogEintrag> WorkoutLogs = FXCollections.observableArrayList(LogEintrag.makeExtractor());


    private static Phase phase = new Phase();

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


        RadioButton masseToggleBtn = (RadioButton) root.lookup("#masseToggleBtn");
        RadioButton defiToggleBtn = (RadioButton) root.lookup("#defiToggleBtn");
        ToggleGroup masseDefiToggleGroup = new ToggleGroup();

        masseToggleBtn.setToggleGroup(masseDefiToggleGroup);
        defiToggleBtn.setToggleGroup(masseDefiToggleGroup);
        masseToggleBtn.setSelected(phase.isMasse());
        defiToggleBtn.setSelected(!phase.isMasse());

        masseDefiToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            phase.setMasse(((RadioButton) newValue).getId().equalsIgnoreCase("masseToggleBtn"));
        });

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






        primaryStage.setScene(new Scene(root));
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

    public static Phase getPhase() {
        return phase;
    }

    public static ObservableList<LogEintrag> getUebungLogs() {
        return UebungLogs;
    }

    public static ObservableList<LogEintrag> getWorkoutLogs() {
        return WorkoutLogs;
    }

    public static List<EintragCount> getLogsByName(ObservableList<LogEintrag> list){
        HashMap<String, List<LogEintrag>> map = new HashMap<>();

        for (LogEintrag logEintrag : list) {
            List<LogEintrag> itemsWithSameName = map.get(logEintrag.getName());
            if (itemsWithSameName == null) { //does not exist in map yet
                itemsWithSameName = new ArrayList<LogEintrag>();
                map.put(logEintrag.getName(), itemsWithSameName);
            }
            itemsWithSameName.add(logEintrag); //now add the item to the list for this key
        }

        List<EintragCount> resultList = new ArrayList<>();
        map.forEach((n, l) -> resultList.add(new EintragCount(n, l.size())));

        return resultList;
    }

    public static List<EintragCount> getLogsByDate(ObservableList<LogEintrag> list){
        HashMap<String, List<LogEintrag>> map = new HashMap<>();

        for (LogEintrag logEintrag : list) {
            List<LogEintrag> itemsWithSameName = map.get(logEintrag.getDate());
            if (itemsWithSameName == null) { //does not exist in map yet
                itemsWithSameName = new ArrayList<LogEintrag>();
                map.put(logEintrag.getDate(), itemsWithSameName);
            }
            itemsWithSameName.add(logEintrag); //now add the item to the list for this key
        }

        List<EintragCount> resultList = new ArrayList<>();
        map.forEach((n, l) -> resultList.add(new EintragCount(n, l.size())));

        return resultList;
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
            if (dw.getProgramme() != null && dw.getProgramme().size() != 0) {
                Programme.addAll(dw.getProgramme());
            }

            Workouts.clear();
            if (dw.getWorkouts() != null && dw.getWorkouts().size() != 0) {
                Workouts.addAll(dw.getWorkouts());
            }

            Uebungen.clear();
            if (dw.getUebungen() != null && dw.getUebungen().size() != 0) {
                Uebungen.addAll(dw.getUebungen());
            }

            if (dw.getPhase() != null) {
                phase = dw.getPhase();
            }

            UebungLogs.clear();
            if (dw.getUebungLog() != null && dw.getUebungLog().size() != 0) {
                UebungLogs.addAll(dw.getUebungLog());
            }

            WorkoutLogs.clear();
            if (dw.getWorkoutLog() != null && dw.getWorkoutLog().size() != 0) {
                WorkoutLogs.addAll(dw.getWorkoutLog());
            }

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
            dw.setPhase(phase);
            dw.setUebungLog(UebungLogs);
            dw.setWorkoutLog(WorkoutLogs);
            m.marshal(dw, datenbank);

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