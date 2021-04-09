package test;

import domain.Set;
import domain.Set.Valid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SetTest {

    @Test
    public void testGetValid() {

        Set set = new Set();

        assertEquals(Valid.REPETITIONS, set.getValid());

        set.setRepetitions("1");
        assertEquals(Valid.WEIGHT, set.getValid());

        set.setWeight("1");
        assertEquals(Valid.VALID, set.getValid());

    }
}
