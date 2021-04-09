package test;

import domain.LogEntryCount;
import domain.LogEntryCountKeyComparator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class LogEntryCountKeyComparatorTest {

    @Test
    public void testCompare() {

        LogEntryCount logEntryCount1 = new LogEntryCount("1", 0);
        LogEntryCount logEntryCount2 = new LogEntryCount("2", 0);
        LogEntryCount logEntryCount3 = new LogEntryCount("3", 0);
        LogEntryCount logEntryCount4 = new LogEntryCount("4", 0);
        LogEntryCount logEntryCount5 = new LogEntryCount("5", 0);

        List<LogEntryCount> toBeSortedList = new ArrayList<>();
        toBeSortedList.add(logEntryCount1);
        toBeSortedList.add(logEntryCount3);
        toBeSortedList.add(logEntryCount4);
        toBeSortedList.add(logEntryCount5);
        Collections.shuffle(toBeSortedList);
        toBeSortedList.add(logEntryCount2);

        List<LogEntryCount> sortedList = new ArrayList<>();
        sortedList.add(logEntryCount1);
        sortedList.add(logEntryCount2);
        sortedList.add(logEntryCount3);
        sortedList.add(logEntryCount4);
        sortedList.add(logEntryCount5);

        assertNotEquals(sortedList, toBeSortedList);

        toBeSortedList.sort(new LogEntryCountKeyComparator());
        assertEquals(sortedList, toBeSortedList);
    }
}
