package domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.Datenbank;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.stream.Collectors;

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

    private ObservableList<Workout> workouts = FXCollections.observableArrayList(Workout.makeExtractor());

    @XmlElement(name = "workout")
    public ObservableList<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(ObservableList<Workout> workouts) {
        this.workouts = workouts;
    }

    private ObservableList<Uebung> uebungen = FXCollections.observableArrayList(Uebung.makeExtractor());

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

    private ObservableList<LogEintrag> uebungLog = FXCollections.observableArrayList(LogEintrag.makeExtractor());

    @XmlElement(name = "uebung_log")
    public ObservableList<LogEintrag> getUebungLog() {
        return uebungLog;
    }

    public void setUebungLog(ObservableList<LogEintrag> uebungLog) {
        this.uebungLog = uebungLog;
    }

    private ObservableList<LogEintrag> workoutLog = FXCollections.observableArrayList(LogEintrag.makeExtractor());

    @XmlElement(name = "workout_log")
    public ObservableList<LogEintrag> getWorkoutLog() {
        return workoutLog;
    }

    public void setWorkoutLog(ObservableList<LogEintrag> programmLog) {
        this.workoutLog = programmLog;
    }


    public String uebungHinzufuegen(Uebung uebung, Uebung tmpUebung) {
        if (tmpUebung.getUebungValid().getCode() == 0) {
            if (nameIsUnique(null, tmpUebung.getName(), uebungen)) {
                uebung.aenderbareMemberUebertragen(tmpUebung.getAenderbareMember());
                uebungen.add(uebung);
                try {
                    Datenbank.save(this);
                } catch (Exception e) {
                    return "Could not save data to database";
                }
                return null;
            }
            return "Name exists already!";
        } else {
            return tmpUebung.getUebungValid().getError();
        }
    }

    public String uebungUpdaten(Uebung uebung, Uebung tmpUebung) {
        if (tmpUebung.getUebungValid().getCode() == 0) {
            if (nameIsUnique(uebung.getName(), tmpUebung.getName(), uebungen)) {
                uebung.aenderbareMemberUebertragen(tmpUebung.getAenderbareMember());
                try {
                    Datenbank.save(this);
                } catch (Exception e) {
                    return "Could not save data to database";
                }
                return null;
            }
            return "Name exists already!";
        } else {
            return tmpUebung.getUebungValid().getError();
        }
    }

    public String workoutHinzufuegen(Workout workout, Workout tmpWorkout) {
        if (tmpWorkout.getWorkoutValid().getCode() == 0) {
            if (nameIsUnique(null, tmpWorkout.getName(), workouts)) {
                workout.aenderbareMemberUebertragen(tmpWorkout.getAenderbareMember());
                workouts.add(workout);
                try {
                    Datenbank.save(this);
                } catch (Exception e) {
                    return "Could not save data to database";
                }
                return null;
            }
            return "Name exists already!";
        } else {
            return tmpWorkout.getWorkoutValid().getError();
        }
    }

    public String workoutUpdaten(Workout workout, Workout tmpWorkout) {
        if (tmpWorkout.getWorkoutValid().getCode() == 0) {
            if (nameIsUnique(workout.getName(), tmpWorkout.getName(), workouts)) {
                workout.aenderbareMemberUebertragen(tmpWorkout.getAenderbareMember());
                try {
                    Datenbank.save(this);
                } catch (Exception e) {
                    return "Could not save data to database";
                }
                return null;
            }
            return "Name exists already!";
        } else {
            return tmpWorkout.getWorkoutValid().getError();
        }
    }

    public String programmHinzufuegen(Programm programm, Programm tmpProgramm) {
        if (tmpProgramm.getProgrammValid().getCode() == 0) {
            if (nameIsUnique(null, tmpProgramm.getName(), programme)) {
                programm.aenderbareMemberUebertragen(tmpProgramm.getAenderbareMember());
                programme.add(programm);
                try {
                    Datenbank.save(this);
                } catch (Exception e) {
                    return "Could not save data to database";
                }
                return null;
            }
            return "Name exists already!";
        } else {
            return tmpProgramm.getProgrammValid().getError();
        }
    }

    public String programmUpdaten(Programm programm, Programm tmpProgramm) {
        if (tmpProgramm.getProgrammValid().getCode() == 0) {
            if (nameIsUnique(programm.getName(), tmpProgramm.getName(), programme)) {
                programm.aenderbareMemberUebertragen(tmpProgramm.getAenderbareMember());
                try {
                    Datenbank.save(this);
                } catch (Exception e) {
                    return "Could not save data to database";
                }
                return null;
            }
            return "Name exists already!";
        } else {
            return tmpProgramm.getProgrammValid().getError();
        }
    }

    private boolean nameIsUnique(String oldName, String name, List<? extends UniqueNamed> list) {
        List<String> nameList = list.stream().map(UniqueNamed::getName).collect(Collectors.toList());
        if (oldName != null) {
            nameList.remove(oldName);
        }
        return nameList.stream().noneMatch(name::equalsIgnoreCase);
    }
}
