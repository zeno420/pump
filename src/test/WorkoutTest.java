package test;

import domain.Uebung;
import domain.Workout;
import domain.Workout.WorkoutValid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkoutTest {

    @Test
    public void testGetValid() {

        Workout workout = new Workout();

        assertEquals(WorkoutValid.NONAME, workout.getWorkoutValid());

        workout.setName("");
        assertEquals(WorkoutValid.NONAME, workout.getWorkoutValid());

        workout.setName("name");
        assertEquals(WorkoutValid.UEBUNG, workout.getWorkoutValid());

        Uebung uebung = new Uebung();
        workout.getUebungen().add(uebung);
        assertEquals(WorkoutValid.VALID, workout.getWorkoutValid());
    }
}
