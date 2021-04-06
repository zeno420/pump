package test;

import domain.EintragCount;
import domain.EintragCountKeyComparator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class EintragCountKeyComparatorTest {

    @Test
    public void testCompare() {

        EintragCount eintragCount1 = new EintragCount("1", 0);
        EintragCount eintragCount2 = new EintragCount("2", 0);
        EintragCount eintragCount3 = new EintragCount("3", 0);
        EintragCount eintragCount4 = new EintragCount("4", 0);
        EintragCount eintragCount5 = new EintragCount("5", 0);

        List<EintragCount> toBeSortedList = new ArrayList<>();
        toBeSortedList.add(eintragCount1);
        toBeSortedList.add(eintragCount3);
        toBeSortedList.add(eintragCount4);
        toBeSortedList.add(eintragCount5);
        Collections.shuffle(toBeSortedList);
        toBeSortedList.add(eintragCount2);

        List<EintragCount> sortedList = new ArrayList<>();
        sortedList.add(eintragCount1);
        sortedList.add(eintragCount2);
        sortedList.add(eintragCount3);
        sortedList.add(eintragCount4);
        sortedList.add(eintragCount5);

        assertNotEquals(sortedList, toBeSortedList);

        toBeSortedList.sort(new EintragCountKeyComparator());
        assertEquals(sortedList, toBeSortedList);
    }
}
