package domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.Database;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.stream.Collectors;

@XmlRootElement(name = "data")
public class Databasis {

    public Databasis() {
        phase = new Phase();
    }

    private ObservableList<Program> programs = FXCollections.observableArrayList(Program.makeExtractor());

    @XmlElement(name = "program")
    public ObservableList<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(ObservableList<Program> programs) {
        this.programs = programs;
    }

    private ObservableList<Workout> workouts = FXCollections.observableArrayList(Workout.makeExtractor());

    @XmlElement(name = "workout")
    public ObservableList<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(ObservableList<Workout> workouts) {
        this.workouts = workouts;
    }

    private ObservableList<Exercise> execises = FXCollections.observableArrayList(Exercise.makeExtractor());

    @XmlElement(name = "exercise")
    public ObservableList<Exercise> getExecises() {
        return execises;
    }

    public void setExecises(ObservableList<Exercise> execises) {
        this.execises = execises;
    }

    private Phase phase;

    @XmlElement(name = "phase")
    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    private ObservableList<LogEntry> execiseLog = FXCollections.observableArrayList(LogEntry.makeExtractor());

    @XmlElement(name = "exercise_log")
    public ObservableList<LogEntry> getExeciseLog() {
        return execiseLog;
    }

    public void setExeciseLog(ObservableList<LogEntry> execiseLog) {
        this.execiseLog = execiseLog;
    }

    private ObservableList<LogEntry> workoutLog = FXCollections.observableArrayList(LogEntry.makeExtractor());

    @XmlElement(name = "workout_log")
    public ObservableList<LogEntry> getWorkoutLog() {
        return workoutLog;
    }

    public void setWorkoutLog(ObservableList<LogEntry> workoutLog) {
        this.workoutLog = workoutLog;
    }


    public String addExercise(Exercise exercise, Exercise tmpExercise) {
        if (tmpExercise.getExerciseValid().getCode() == 0) {
            if (nameIsUnique(null, tmpExercise.getName(), execises)) {
                exercise.aenderbareMemberUebertragen(tmpExercise.getEditableMembers());
                execises.add(exercise);
                try {
                    Database.save(this);
                } catch (Exception e) {
                    return "Could not save data to database";
                }
                return null;
            }
            return "Name exists already!";
        } else {
            return tmpExercise.getExerciseValid().getErrorText();
        }
    }

    public String updateExercise(Exercise exercise, Exercise tmpExercise) {
        if (tmpExercise.getExerciseValid().getCode() == 0) {
            if (nameIsUnique(exercise.getName(), tmpExercise.getName(), execises)) {
                exercise.aenderbareMemberUebertragen(tmpExercise.getEditableMembers());
                try {
                    Database.save(this);
                } catch (Exception e) {
                    return "Could not save data to database";
                }
                return null;
            }
            return "Name exists already!";
        } else {
            return tmpExercise.getExerciseValid().getErrorText();
        }
    }

    public String addWorkout(Workout workout, Workout tmpWorkout) {
        if (tmpWorkout.getWorkoutValid().getCode() == 0) {
            if (nameIsUnique(null, tmpWorkout.getName(), workouts)) {
                workout.transferEditableMembers(tmpWorkout.getEditableMembers());
                workouts.add(workout);
                try {
                    Database.save(this);
                } catch (Exception e) {
                    return "Could not save data to database";
                }
                return null;
            }
            return "Name exists already!";
        } else {
            return tmpWorkout.getWorkoutValid().getErrorText();
        }
    }

    public String updateWorkout(Workout workout, Workout tmpWorkout) {
        if (tmpWorkout.getWorkoutValid().getCode() == 0) {
            if (nameIsUnique(workout.getName(), tmpWorkout.getName(), workouts)) {
                workout.transferEditableMembers(tmpWorkout.getEditableMembers());
                try {
                    Database.save(this);
                } catch (Exception e) {
                    return "Could not save data to database";
                }
                return null;
            }
            return "Name exists already!";
        } else {
            return tmpWorkout.getWorkoutValid().getErrorText();
        }
    }

    public String addProgram(Program program, Program tmpProgram) {
        if (tmpProgram.getProgramValid().getCode() == 0) {
            if (nameIsUnique(null, tmpProgram.getName(), programs)) {
                program.transferEditableMembers(tmpProgram.getEditableMembers());
                programs.add(program);
                try {
                    Database.save(this);
                } catch (Exception e) {
                    return "Could not save data to database";
                }
                return null;
            }
            return "Name exists already!";
        } else {
            return tmpProgram.getProgramValid().getErrorMessage();
        }
    }

    public String updateProgram(Program program, Program tmpProgram) {
        if (tmpProgram.getProgramValid().getCode() == 0) {
            if (nameIsUnique(program.getName(), tmpProgram.getName(), programs)) {
                program.transferEditableMembers(tmpProgram.getEditableMembers());
                try {
                    Database.save(this);
                } catch (Exception e) {
                    return "Could not save data to database";
                }
                return null;
            }
            return "Name exists already!";
        } else {
            return tmpProgram.getProgramValid().getErrorMessage();
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
