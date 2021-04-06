package test;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import persistence.Datenbank;

import static org.junit.jupiter.api.Assertions.*;

public class DatenbasisTest {

    @Test
    public void testUebungHinzufuegen() {
        Uebung uebung1 = new Uebung();

        Uebung uebung2 = new Uebung();


        Uebung uebung3 = new Uebung();

        Satz satz = new Satz();


        uebung1.setName("uebung1");
        uebung1.getMasse().add(satz);
        uebung1.getDefi().add(satz);

        Uebung tmpUebung1 = uebung1.makeTmpCopy();

        uebung2.setName("uebung2");
        uebung2.getMasse().add(satz);
        uebung2.getDefi().add(satz);

        Uebung tmpUebung2 = uebung2.makeTmpCopy();

        uebung3.setName("uebung2");
        uebung3.getMasse().add(satz);
        uebung3.getDefi().add(satz);

        Uebung tmpUebung3 = uebung3.makeTmpCopy();

        Datenbasis datenbasis = new Datenbasis();

        //mock Datenbank.save() to avoid JAXBException
        try (MockedStatic<Datenbank> mockBank = Mockito.mockStatic(Datenbank.class)) {
            mockBank.when(() -> Datenbank.save(datenbasis)).thenAnswer(Answers.RETURNS_DEFAULTS);


            //adds uebung1, error null
            ObservableList<Uebung> correctList = FXCollections.observableArrayList(Uebung.makeExtractor());
            correctList.add(uebung1);

            assertNull(datenbasis.uebungHinzufuegen(uebung1, tmpUebung1));

            assertEquals(correctList, datenbasis.getUebungen());


            //doesnt add uebung1 again, name exists already, error not null
            assertNotNull(datenbasis.uebungHinzufuegen(uebung1, tmpUebung1));

            assertEquals(correctList, datenbasis.getUebungen());


            //adds uebung2, error null
            assertNull(datenbasis.uebungHinzufuegen(uebung2, tmpUebung2));

            correctList.add(uebung2);

            assertEquals(correctList, datenbasis.getUebungen());


            //doesnt add uebung3, name exists already, error not null
            assertNotNull(datenbasis.uebungHinzufuegen(uebung3, tmpUebung3));

            assertEquals(correctList, datenbasis.getUebungen());
        }
    }


    @Test
    public void testUebungUpdaten() {
        Uebung uebung1 = new Uebung();

        Uebung uebung2 = new Uebung();


        Uebung uebung3 = new Uebung();

        Satz satz = new Satz();


        uebung1.setName("uebung1");
        uebung1.getMasse().add(satz);
        uebung1.getDefi().add(satz);

        Uebung tmpUebung1 = uebung1.makeTmpCopy();

        uebung2.setName("uebung2");
        uebung2.getMasse().add(satz);
        uebung2.getDefi().add(satz);

        Uebung tmpUebung2 = uebung2.makeTmpCopy();

        uebung3.setName("uebung2");
        uebung3.getMasse().add(satz);
        uebung3.getDefi().add(satz);

        Uebung tmpUebung3 = uebung3.makeTmpCopy();

        Datenbasis datenbasis = new Datenbasis();

        //mock Datenbank.save() to avoid JAXBException
        try (MockedStatic<Datenbank> mockBank = Mockito.mockStatic(Datenbank.class)) {
            mockBank.when(() -> Datenbank.save(datenbasis)).thenAnswer(Answers.RETURNS_DEFAULTS);

            assertNull(datenbasis.uebungHinzufuegen(uebung1, tmpUebung1));
            assertNull(datenbasis.uebungHinzufuegen(uebung2, tmpUebung2));


            //ändert name, error null
            String oldName = uebung2.getName();
            String newName = "neuer noch nciht vorhandener name";
            tmpUebung2.setName(newName);

            assertNull(datenbasis.uebungUpdaten(uebung2, tmpUebung2));

            assertEquals(newName, uebung2.getName());
            assertNotEquals(oldName, uebung2.getName());


            //ändert name nicht, name existiert bereits, error not null
            oldName = uebung2.getName();
            newName = "uebung1";
            tmpUebung2.setName(newName);

            assertNotNull(datenbasis.uebungUpdaten(uebung2, tmpUebung2));

            assertNotEquals(newName, uebung2.getName());
            assertEquals(oldName, uebung2.getName());


            //ändert name, error null
            oldName = uebung2.getName();
            newName = oldName;
            tmpUebung2.setName(newName);

            assertNull(datenbasis.uebungUpdaten(uebung2, tmpUebung2));

            assertEquals(newName, uebung2.getName());
            assertEquals(oldName, uebung2.getName());
        }
    }

    @Test
    public void testWorkoutHinzufuegen() {


        Uebung uebung = new Uebung();
        Workout workout1 = new Workout();

        Workout workout2 = new Workout();

        Workout workout3 = new Workout();

        workout1.setName("workout1");
        workout1.getUebungen().add(uebung);

        Workout tmpWorkout1 = workout1.makeTmpCopy();

        workout2.setName("workout2");
        workout2.getUebungen().add(uebung);

        Workout tmpWorkout2 = workout2.makeTmpCopy();

        workout3.setName("workout2");
        workout3.getUebungen().add(uebung);

        Workout tmpWorkout3 = workout3.makeTmpCopy();


        Datenbasis datenbasis = new Datenbasis();

        //mock Datenbank.save() to avoid JAXBException
        try (MockedStatic<Datenbank> mockBank = Mockito.mockStatic(Datenbank.class)) {
            mockBank.when(() -> Datenbank.save(datenbasis)).thenAnswer(Answers.RETURNS_DEFAULTS);


            //adds workout1, error null
            ObservableList<Workout> correctList = FXCollections.observableArrayList(Workout.makeExtractor());
            correctList.add(workout1);

            assertNull(datenbasis.workoutHinzufuegen(workout1, tmpWorkout1));

            assertEquals(correctList, datenbasis.getWorkouts());


            //doesnt add workout1 again, name exists already, error not null
            assertNotNull(datenbasis.workoutHinzufuegen(workout1, tmpWorkout1));

            assertEquals(correctList, datenbasis.getWorkouts());


            //adds workout2, error null
            assertNull(datenbasis.workoutHinzufuegen(workout2, tmpWorkout2));

            correctList.add(workout2);

            assertEquals(correctList, datenbasis.getWorkouts());


            //doesnt add workout3, name exists already, error not null
            assertNotNull(datenbasis.workoutHinzufuegen(workout3, tmpWorkout3));

            assertEquals(correctList, datenbasis.getWorkouts());
        }
    }

    @Test
    public void testWorkoutUpdaten() {

        Uebung uebung = new Uebung();

        Workout workout1 = new Workout();

        Workout workout2 = new Workout();

        Workout workout3 = new Workout();

        workout1.setName("workout1");
        workout1.getUebungen().add(uebung);

        Workout tmpWorkout1 = workout1.makeTmpCopy();

        workout2.setName("workout2");
        workout2.getUebungen().add(uebung);

        Workout tmpWorkout2 = workout2.makeTmpCopy();

        workout3.setName("workout2");
        workout3.getUebungen().add(uebung);

        Workout tmpWorkout3 = workout3.makeTmpCopy();


        Datenbasis datenbasis = new Datenbasis();

        //mock Datenbank.save() to avoid JAXBException
        try (MockedStatic<Datenbank> mockBank = Mockito.mockStatic(Datenbank.class)) {
            mockBank.when(() -> Datenbank.save(datenbasis)).thenAnswer(Answers.RETURNS_DEFAULTS);

            assertNull(datenbasis.workoutHinzufuegen(workout1, tmpWorkout1));
            assertNull(datenbasis.workoutHinzufuegen(workout2, tmpWorkout2));

            //ändert name, error null
            String oldName = workout2.getName();
            String newName = "neuer noch nciht vorhandener name";
            tmpWorkout2.setName(newName);

            assertNull(datenbasis.workoutUpdaten(workout2, tmpWorkout2));

            assertEquals(newName, workout2.getName());
            assertNotEquals(oldName, workout2.getName());


            //ändert name nicht, name existiert bereits, error not null
            oldName = workout2.getName();
            newName = "workout1";
            tmpWorkout2.setName(newName);

            assertNotNull(datenbasis.workoutUpdaten(workout2, tmpWorkout2));

            assertNotEquals(newName, workout2.getName());
            assertEquals(oldName, workout2.getName());


            //ändert name, error null
            oldName = workout2.getName();
            newName = oldName;
            tmpWorkout2.setName(newName);

            assertNull(datenbasis.workoutUpdaten(workout2, tmpWorkout2));

            assertEquals(newName, workout2.getName());
            assertEquals(oldName, workout2.getName());
        }
    }


    @Test
    public void testProgrammHinzufuegen() {

        Programm programm1 = new Programm();

        Programm programm2 = new Programm();

        Programm programm3 = new Programm();

        Tag tag = new Tag();


        programm1.setName("programm1");
        programm1.getTage().add(tag);

        Programm tmpProgramm1 = programm1.makeTmpCopy();

        programm2.setName("programm2");
        programm2.getTage().add(tag);

        Programm tmpProgramm2 = programm2.makeTmpCopy();

        programm3.setName("programm2");
        programm3.getTage().add(tag);

        Programm tmpProgramm3 = programm3.makeTmpCopy();

        Datenbasis datenbasis = new Datenbasis();

        //mock Datenbank.save() to avoid JAXBException
        try (MockedStatic<Datenbank> mockBank = Mockito.mockStatic(Datenbank.class)) {
            mockBank.when(() -> Datenbank.save(datenbasis)).thenAnswer(Answers.RETURNS_DEFAULTS);


            //adds programm1, error null
            ObservableList<Programm> correctList = FXCollections.observableArrayList(Programm.makeExtractor());
            correctList.add(programm1);

            assertNull(datenbasis.programmHinzufuegen(programm1, tmpProgramm1));

            assertEquals(correctList, datenbasis.getProgramme());


            //doesnt add programm1 again, name exists already, error not null
            assertNotNull(datenbasis.programmHinzufuegen(programm1, tmpProgramm1));

            assertEquals(correctList, datenbasis.getProgramme());


            //adds programm2, error null
            assertNull(datenbasis.programmHinzufuegen(programm2, tmpProgramm2));

            correctList.add(programm2);

            assertEquals(correctList, datenbasis.getProgramme());


            //doesnt add programm3, name exists already, error not null
            assertNotNull(datenbasis.programmHinzufuegen(programm3, tmpProgramm3));

            assertEquals(correctList, datenbasis.getProgramme());
        }
    }

    @Test
    public void testProgrammUpdaten() {

        Programm programm1 = new Programm();

        Programm programm2 = new Programm();

        Programm programm3 = new Programm();

        Tag tag = new Tag();


        programm1.setName("programm1");
        programm1.getTage().add(tag);

        Programm tmpProgramm1 = programm1.makeTmpCopy();

        programm2.setName("programm2");
        programm2.getTage().add(tag);

        Programm tmpProgramm2 = programm2.makeTmpCopy();

        programm3.setName("programm2");
        programm3.getTage().add(tag);

        Programm tmpProgramm3 = programm3.makeTmpCopy();

        Datenbasis datenbasis = new Datenbasis();

        //mock Datenbank.save() to avoid JAXBException
        try (MockedStatic<Datenbank> mockBank = Mockito.mockStatic(Datenbank.class)) {
            mockBank.when(() -> Datenbank.save(datenbasis)).thenAnswer(Answers.RETURNS_DEFAULTS);

            assertNull(datenbasis.programmHinzufuegen(programm1, tmpProgramm1));
            assertNull(datenbasis.programmHinzufuegen(programm2, tmpProgramm2));

            //ändert name, error null
            String oldName = programm2.getName();
            String newName = "neuer noch nciht vorhandener name";
            tmpProgramm2.setName(newName);

            assertNull(datenbasis.programmUpdaten(programm2, tmpProgramm2));

            assertEquals(newName, programm2.getName());
            assertNotEquals(oldName, programm2.getName());


            //ändert name nicht, name existiert bereits, error not null
            oldName = programm2.getName();
            newName = "programm1";
            tmpProgramm2.setName(newName);

            assertNotNull(datenbasis.programmUpdaten(programm2, tmpProgramm2));

            assertNotEquals(newName, programm2.getName());
            assertEquals(oldName, programm2.getName());


            //ändert name, error null
            oldName = programm2.getName();
            newName = oldName;
            tmpProgramm2.setName(newName);

            assertNull(datenbasis.programmUpdaten(programm2, tmpProgramm2));

            assertEquals(newName, programm2.getName());
            assertEquals(oldName, programm2.getName());
        }
    }
}