package test;

import domain.LogEntryCount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogEntryCountTest {

    @Test
    public void testEquals() {

        LogEntryCount logEntryCount1 = new LogEntryCount("name", 1);
        LogEntryCount logEntryCount2 = new LogEntryCount("name", 1);
        LogEntryCount logEntryCount3 = new LogEntryCount("noname", 1);
        LogEntryCount logEntryCount4 = logEntryCount1;

        assertEquals(logEntryCount2, logEntryCount1);
        assertEquals(logEntryCount4, logEntryCount1);
        assertNotEquals(logEntryCount3, logEntryCount1);
        assertNotEquals(logEntryCount1, "bla");

    }
}
