package test;

import daten.Uebung;
import daten.Workout;
import daten.Workout.Valid;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkoutTest {

    @BeforeAll
    static void initAll() throws Exception {
    }


    @Test
    public void testGetValid() {

        List<String> existingWorkoutNameList = new ArrayList<>();
        Workout workout = new Workout();

        assertEquals(Valid.NONAME, workout.getValid(existingWorkoutNameList));

        workout.setName("");
        assertEquals(Valid.NONAME, workout.getValid(existingWorkoutNameList));

        workout.setName("name");
        assertEquals(Valid.UEBUNG, workout.getValid(existingWorkoutNameList));

        Uebung uebung = new Uebung();
        workout.getUebungen().add(uebung);
        assertEquals(Valid.VALID, workout.getValid(existingWorkoutNameList));

        existingWorkoutNameList.add("name");
        assertEquals(Valid.NAME, workout.getValid(existingWorkoutNameList));

    }
}
