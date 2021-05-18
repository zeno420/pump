package test;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import persistence.Database;

import static org.junit.jupiter.api.Assertions.*;

public class DatabasisTest {

    @Test
    public void testAddExercise() {
        Exercise exercise1 = new Exercise();
        Exercise exercise2 = new Exercise();
        Exercise exercise3 = new Exercise();

        Set set = new Set();

        exercise1.setName("exerci1");
        exercise1.getBulkingSets().add(set);
        exercise1.getCuttingSets().add(set);
        Exercise tmpExercise1 = exercise1.makeTmpCopy();

        exercise2.setName("exerci2");
        exercise2.getBulkingSets().add(set);
        exercise2.getCuttingSets().add(set);
        Exercise tmpExercise2 = exercise2.makeTmpCopy();

        exercise3.setName("exerci2");
        exercise3.getBulkingSets().add(set);
        exercise3.getCuttingSets().add(set);
        Exercise tmpExercise3 = exercise3.makeTmpCopy();

        Databasis databasis = new Databasis();

        //mock Datenbank.save() to avoid JAXBException
        try (MockedStatic<Database> mockBank = Mockito.mockStatic(Database.class)) {
            mockBank.when(() -> Database.save(databasis)).thenAnswer(Answers.RETURNS_DEFAULTS);

            //adds exerci1, error null
            ObservableList<Exercise> correctList = FXCollections.observableArrayList(Exercise.makeExtractor());
            correctList.add(exercise1);

            assertNull(databasis.addExercise(exercise1, tmpExercise1));

            assertEquals(correctList, databasis.getExecises());


            //doesnt add exerci1 again, name exists already, error not null
            assertNotNull(databasis.addExercise(exercise1, tmpExercise1));

            assertEquals(correctList, databasis.getExecises());


            //adds exerci2, error null
            assertNull(databasis.addExercise(exercise2, tmpExercise2));

            correctList.add(exercise2);

            assertEquals(correctList, databasis.getExecises());


            //doesnt add exerci3, name exists already, error not null
            assertNotNull(databasis.addExercise(exercise3, tmpExercise3));

            assertEquals(correctList, databasis.getExecises());
        }
    }


    @Test
    public void testUpdateExercise() {
        Exercise exercise1 = new Exercise();
        Exercise exercise2 = new Exercise();
        Exercise exercise3 = new Exercise();

        Set set = new Set();

        exercise1.setName("exerci1");
        exercise1.getBulkingSets().add(set);
        exercise1.getCuttingSets().add(set);
        Exercise tmpExercise1 = exercise1.makeTmpCopy();

        exercise2.setName("exerci2");
        exercise2.getBulkingSets().add(set);
        exercise2.getCuttingSets().add(set);
        Exercise tmpExercise2 = exercise2.makeTmpCopy();

        exercise3.setName("exerci2");
        exercise3.getBulkingSets().add(set);
        exercise3.getCuttingSets().add(set);
        Exercise tmpExercise3 = exercise3.makeTmpCopy();

        Databasis databasis = new Databasis();

        //mock Datenbank.save() to avoid JAXBException
        try (MockedStatic<Database> mockBank = Mockito.mockStatic(Database.class)) {
            mockBank.when(() -> Database.save(databasis)).thenAnswer(Answers.RETURNS_DEFAULTS);

            assertNull(databasis.addExercise(exercise1, tmpExercise1));
            assertNull(databasis.addExercise(exercise2, tmpExercise2));


            //ändert name, error null
            String oldName = exercise2.getName();
            String newName = "neuer noch nciht vorhandener name";
            tmpExercise2.setName(newName);

            assertNull(databasis.updateExercise(exercise2, tmpExercise2));

            assertEquals(newName, exercise2.getName());
            assertNotEquals(oldName, exercise2.getName());


            //ändert name nicht, name existiert bereits, error not null
            oldName = exercise2.getName();
            newName = "exerci1";
            tmpExercise2.setName(newName);

            assertNotNull(databasis.updateExercise(exercise2, tmpExercise2));

            assertNotEquals(newName, exercise2.getName());
            assertEquals(oldName, exercise2.getName());


            //ändert name, error null
            oldName = exercise2.getName();
            newName = oldName;
            tmpExercise2.setName(newName);

            assertNull(databasis.updateExercise(exercise2, tmpExercise2));

            assertEquals(newName, exercise2.getName());
            assertEquals(oldName, exercise2.getName());
        }
    }

    @Test
    public void testWorkoutHinzufuegen() {


        Exercise exercise = new Exercise();

        Workout workout1 = new Workout();
        Workout workout2 = new Workout();
        Workout workout3 = new Workout();

        workout1.setName("workout1");
        workout1.getExercises().add(exercise);
        Workout tmpWorkout1 = workout1.makeTmpCopy();

        workout2.setName("workout2");
        workout2.getExercises().add(exercise);
        Workout tmpWorkout2 = workout2.makeTmpCopy();

        workout3.setName("workout2");
        workout3.getExercises().add(exercise);
        Workout tmpWorkout3 = workout3.makeTmpCopy();


        Databasis databasis = new Databasis();

        //mock Datenbank.save() to avoid JAXBException
        try (MockedStatic<Database> mockBank = Mockito.mockStatic(Database.class)) {
            mockBank.when(() -> Database.save(databasis)).thenAnswer(Answers.RETURNS_DEFAULTS);

            //adds workout1, error null
            ObservableList<Workout> correctList = FXCollections.observableArrayList(Workout.makeExtractor());
            correctList.add(workout1);

            assertNull(databasis.addWorkout(workout1, tmpWorkout1));

            assertEquals(correctList, databasis.getWorkouts());


            //doesnt add workout1 again, name exists already, error not null
            assertNotNull(databasis.addWorkout(workout1, tmpWorkout1));

            assertEquals(correctList, databasis.getWorkouts());


            //adds workout2, error null
            assertNull(databasis.addWorkout(workout2, tmpWorkout2));

            correctList.add(workout2);

            assertEquals(correctList, databasis.getWorkouts());


            //doesnt add workout3, name exists already, error not null
            assertNotNull(databasis.addWorkout(workout3, tmpWorkout3));

            assertEquals(correctList, databasis.getWorkouts());
        }
    }

    @Test
    public void testWorkoutUpdaten() {

        Exercise exercise = new Exercise();

        Workout workout1 = new Workout();
        Workout workout2 = new Workout();
        Workout workout3 = new Workout();

        workout1.setName("workout1");
        workout1.getExercises().add(exercise);
        Workout tmpWorkout1 = workout1.makeTmpCopy();

        workout2.setName("workout2");
        workout2.getExercises().add(exercise);
        Workout tmpWorkout2 = workout2.makeTmpCopy();

        workout3.setName("workout2");
        workout3.getExercises().add(exercise);
        Workout tmpWorkout3 = workout3.makeTmpCopy();

        Databasis databasis = new Databasis();

        //mock Datenbank.save() to avoid JAXBException
        try (MockedStatic<Database> mockBank = Mockito.mockStatic(Database.class)) {
            mockBank.when(() -> Database.save(databasis)).thenAnswer(Answers.RETURNS_DEFAULTS);

            assertNull(databasis.addWorkout(workout1, tmpWorkout1));
            assertNull(databasis.addWorkout(workout2, tmpWorkout2));

            //ändert name, error null
            String oldName = workout2.getName();
            String newName = "neuer noch nciht vorhandener name";
            tmpWorkout2.setName(newName);

            assertNull(databasis.updateWorkout(workout2, tmpWorkout2));

            assertEquals(newName, workout2.getName());
            assertNotEquals(oldName, workout2.getName());


            //ändert name nicht, name existiert bereits, error not null
            oldName = workout2.getName();
            newName = "workout1";
            tmpWorkout2.setName(newName);

            assertNotNull(databasis.updateWorkout(workout2, tmpWorkout2));

            assertNotEquals(newName, workout2.getName());
            assertEquals(oldName, workout2.getName());


            //ändert name, error null
            oldName = workout2.getName();
            newName = oldName;
            tmpWorkout2.setName(newName);

            assertNull(databasis.updateWorkout(workout2, tmpWorkout2));

            assertEquals(newName, workout2.getName());
            assertEquals(oldName, workout2.getName());
        }
    }


    @Test
    public void testProgrammHinzufuegen() {

        Program program1 = new Program();
        Program program2 = new Program();
        Program program3 = new Program();

        Day day = new Day();

        program1.setName("programm1");
        program1.getDays().add(day);
        Program tmpProgram1 = program1.makeTmpCopy();

        program2.setName("programm2");
        program2.getDays().add(day);
        Program tmpProgram2 = program2.makeTmpCopy();

        program3.setName("programm2");
        program3.getDays().add(day);
        Program tmpProgram3 = program3.makeTmpCopy();

        Databasis databasis = new Databasis();

        //mock Datenbank.save() to avoid JAXBException
        try (MockedStatic<Database> mockBank = Mockito.mockStatic(Database.class)) {
            mockBank.when(() -> Database.save(databasis)).thenAnswer(Answers.RETURNS_DEFAULTS);

            //adds programm1, error null
            ObservableList<Program> correctList = FXCollections.observableArrayList(Program.makeExtractor());
            correctList.add(program1);

            assertNull(databasis.addProgram(program1, tmpProgram1));

            assertEquals(correctList, databasis.getPrograms());


            //doesnt add programm1 again, name exists already, error not null
            assertNotNull(databasis.addProgram(program1, tmpProgram1));

            assertEquals(correctList, databasis.getPrograms());


            //adds programm2, error null
            assertNull(databasis.addProgram(program2, tmpProgram2));

            correctList.add(program2);

            assertEquals(correctList, databasis.getPrograms());


            //doesnt add programm3, name exists already, error not null
            assertNotNull(databasis.addProgram(program3, tmpProgram3));

            assertEquals(correctList, databasis.getPrograms());
        }
    }

    @Test
    public void testProgrammUpdaten() {

        Program program1 = new Program();
        Program program2 = new Program();
        Program program3 = new Program();

        Day day = new Day();

        program1.setName("programm1");
        program1.getDays().add(day);
        Program tmpProgram1 = program1.makeTmpCopy();

        program2.setName("programm2");
        program2.getDays().add(day);
        Program tmpProgram2 = program2.makeTmpCopy();

        program3.setName("programm2");
        program3.getDays().add(day);
        Program tmpProgram3 = program3.makeTmpCopy();

        Databasis databasis = new Databasis();

        //mock Datenbank.save() to avoid JAXBException
        try (MockedStatic<Database> mockBank = Mockito.mockStatic(Database.class)) {
            mockBank.when(() -> Database.save(databasis)).thenAnswer(Answers.RETURNS_DEFAULTS);

            assertNull(databasis.addProgram(program1, tmpProgram1));
            assertNull(databasis.addProgram(program2, tmpProgram2));

            //ändert name, error null
            String oldName = program2.getName();
            String newName = "neuer noch nciht vorhandener name";
            tmpProgram2.setName(newName);

            assertNull(databasis.updateProgram(program2, tmpProgram2));

            assertEquals(newName, program2.getName());
            assertNotEquals(oldName, program2.getName());


            //ändert name nicht, name existiert bereits, error not null
            oldName = program2.getName();
            newName = "programm1";
            tmpProgram2.setName(newName);

            assertNotNull(databasis.updateProgram(program2, tmpProgram2));

            assertNotEquals(newName, program2.getName());
            assertEquals(oldName, program2.getName());


            //ändert name, error null
            oldName = program2.getName();
            newName = oldName;
            tmpProgram2.setName(newName);

            assertNull(databasis.updateProgram(program2, tmpProgram2));

            assertEquals(newName, program2.getName());
            assertEquals(oldName, program2.getName());
        }
    }
}