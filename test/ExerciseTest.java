package test;

import domain.Exercise;
import domain.Set;
import domain.Exercise.ExerciseValid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExerciseTest {

    @Test
    public void testGetValid() {

        Exercise exercise = new Exercise();

        assertEquals(ExerciseValid.NONAME, exercise.getExerciseValid());

        exercise.setName("");
        assertEquals(ExerciseValid.NONAME, exercise.getExerciseValid());

        exercise.setName("name");
        assertEquals(ExerciseValid.BULKING, exercise.getExerciseValid());

        Set set = new Set();
        exercise.getBulkingSets().add(set);
        assertEquals(ExerciseValid.CUTTING, exercise.getExerciseValid());

        exercise.getCuttingSets().add(set);
        assertEquals(ExerciseValid.VALID, exercise.getExerciseValid());

    }
}
