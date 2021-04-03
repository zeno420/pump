package daten;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

public class Datenbank {

    //TODO refactor

    private static ObservableList<Uebung> Uebungen = FXCollections.observableArrayList(Uebung.makeExtractor());
    private static ObservableList<Workout> Workouts = FXCollections.observableArrayList(Workout.makeExtractor());
    private static ObservableList<Programm> Programme = FXCollections.observableArrayList(Programm.makeExtractor());

    private static ObservableList<LogEintrag> UebungLogs = FXCollections.observableArrayList(LogEintrag.makeExtractor());
    private static ObservableList<LogEintrag> WorkoutLogs = FXCollections.observableArrayList(LogEintrag.makeExtractor());

    private static Phase phase = new Phase();

    private static String dataFilePath = "datenbank.xml";
    private static File datenbank;

    public static void init() throws IOException {
        datenbank = new File(dataFilePath);
        datenbank.createNewFile();

    }

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
            throw e;
        }
    }

    public static void save() throws JAXBException {
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
            throw e;
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
