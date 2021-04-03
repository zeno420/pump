package daten;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "data")
public class Datenbasis {

    private ObservableList<Programm> programme = FXCollections.observableArrayList(Programm.makeExtractor());

    @XmlElement(name = "programm")
    public ObservableList<Programm> getProgramme() {
        return programme;
    }

    public void setProgramme(ObservableList<Programm> programme) {
        this.programme = programme;
    }

    private ObservableList<Workout> workouts = FXCollections.observableArrayList(Workout.makeExtractor());;

    @XmlElement(name = "workout")
    public ObservableList<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(ObservableList<Workout> workouts) {
        this.workouts = workouts;
    }

    private ObservableList<Uebung> uebungen = FXCollections.observableArrayList(Uebung.makeExtractor());;

    @XmlElement(name = "uebung")
    public ObservableList<Uebung> getUebungen() {
        return uebungen;
    }

    public void setUebungen(ObservableList<Uebung> uebungen) {
        this.uebungen = uebungen;
    }

    private Phase phase;

    @XmlElement(name = "phase")
    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    private ObservableList<LogEintrag> uebungLog = FXCollections.observableArrayList(LogEintrag.makeExtractor());;

    @XmlElement(name = "uebung_log")
    public ObservableList<LogEintrag> getUebungLog() {
        return uebungLog;
    }

    public void setUebungLog(ObservableList<LogEintrag> uebungLog) {
        this.uebungLog = uebungLog;
    }

    private ObservableList<LogEintrag> workoutLog = FXCollections.observableArrayList(LogEintrag.makeExtractor());;

    @XmlElement(name = "workout_log")
    public ObservableList<LogEintrag> getWorkoutLog() {
        return workoutLog;
    }

    public void setWorkoutLog(ObservableList<LogEintrag> programmLog) {
        this.workoutLog = programmLog;
    }
}
