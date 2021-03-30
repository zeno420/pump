package daten;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class Datenbank {

    private static ObservableList<Uebung> Uebungen = FXCollections.observableArrayList(Uebung.makeExtractor());
    private static ObservableList<Workout> Workouts = FXCollections.observableArrayList(Workout.makeExtractor());
    private static ObservableList<Programm> Programme = FXCollections.observableArrayList(Programm.makeExtractor());

    private static ObservableList<LogEintrag> UebungLogs = FXCollections.observableArrayList(LogEintrag.makeExtractor());
    private static ObservableList<LogEintrag> WorkoutLogs = FXCollections.observableArrayList(LogEintrag.makeExtractor());

    private static Phase phase = new Phase();

    private static String dataFilePath = "datenbank.xml";
    private static File datenbank;

    public static void init() {
        try {
            datenbank = new File(dataFilePath);
            datenbank.createNewFile();
        } catch (Exception e) {
            System.out.println("no file yet!");
        }
    }


    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     */
    public static void load() throws JAXBException {
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
    public static void save() {
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


}
