package test.daten;

import daten.Tag;
import daten.Tag.Valid;
import daten.Workout;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagTest {

    @BeforeAll
    static void initAll() throws Exception {
    }


    @Test
    public void testGetValid() {

        Tag tag = new Tag();
        assertEquals(Valid.NAME, tag.getValid());

        tag.setName("");
        assertEquals(Valid.NAME, tag.getValid());

        tag.setName("name");
        assertEquals(Valid.WORKOUTS, tag.getValid());

        Workout workout = new Workout();
        tag.getWorkouts().add(workout);
        assertEquals(Valid.VALID, tag.getValid());

    }
}
