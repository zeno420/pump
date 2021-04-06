package test;

import domain.Tag;
import domain.Tag.Valid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagTest {

    @Test
    public void testGetValid() {

        Tag tag = new Tag();
        assertEquals(Valid.NAME, tag.getValid());

        tag.setName("");
        assertEquals(Valid.NAME, tag.getValid());

        tag.setName("name");
        assertEquals(Valid.VALID, tag.getValid());

    }
}
