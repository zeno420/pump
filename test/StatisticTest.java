package test;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StatisticTest {

    private static ObservableList<LogEntry> observableList1 = FXCollections.observableArrayList();
    private static ObservableList<LogEntry> observableList2 = FXCollections.observableArrayList();
    private static ObservableList<LogEntry> observableList3 = FXCollections.observableArrayList();
    private static ObservableList<LogEntry> observableList4 = FXCollections.observableArrayList();
    private static LogEntry logEntry1;
    private static LogEntry logEntry2;
    private static LogEntry logEntry3;
    private static LogEntry logEntry4;
    private static Comparator<LogEntryCount> comparator;
    private static DateTimeFormatter formatter;

    @BeforeAll
    static void initAll() throws Exception {

        formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        Exercise exercise1 = new Exercise();
        exercise1.setName("name");

        Exercise exercise2 = new Exercise();
        exercise2.setName("anderer name");

        logEntry1 = new LogEntry(exercise1.getName(), exercise1.getDescription());
        logEntry2 = new LogEntry(exercise1.getName(), exercise1.getDescription());
        logEntry3 = new LogEntry(exercise2.getName(), exercise2.getDescription());
        logEntry4 = new LogEntry(exercise2.getName(), exercise2.getDescription());
        logEntry4.setDate(ZonedDateTime.now().minusDays(2).format(formatter));

        //observableList1: eine übung, ein tag
        observableList1.add(logEntry1);

        //observableList2: eine übung 2x, ein tag
        observableList2.add(logEntry1);
        observableList2.add(logEntry2);

        //observableList3: eine übung 2x + eine andere übung, ein tag
        observableList3.add(logEntry1);
        observableList3.add(logEntry2);
        observableList3.add(logEntry3);

        //observableList3: eine übung 2x + eine andere übung 2x, zwei tage
        observableList4.add(logEntry1);
        observableList4.add(logEntry2);
        observableList4.add(logEntry3);
        observableList4.add(logEntry4);

        comparator = new Comparator<LogEntryCount>() {
            @Override
            public int compare(LogEntryCount o1, LogEntryCount o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        };
    }


    @Test
    public void testGetLogsByDate() {

        ArrayList<LogEntryCount> correctList = new ArrayList<>();
        correctList.add(new LogEntryCount(logEntry1.getDate(), 1));

        assertEquals(correctList, Statistic.aggregateLogEntriesByDate(observableList1));


        correctList.clear();
        correctList.add(new LogEntryCount(logEntry1.getDate(), 2));

        assertEquals(correctList, Statistic.aggregateLogEntriesByDate(observableList2));


        correctList.clear();
        correctList.add(new LogEntryCount(logEntry1.getDate(), 3));

        assertEquals(correctList, Statistic.aggregateLogEntriesByDate(observableList3));


        correctList.add(new LogEntryCount(logEntry4.getDate(), 1));
        List<LogEntryCount> list4 = Statistic.aggregateLogEntriesByDate(observableList4);

        correctList.sort(comparator);
        list4.sort(comparator);

        assertEquals(correctList, list4);
    }

    @Test
    public void testGetLogsByName() {

        ArrayList<LogEntryCount> correctList = new ArrayList<>();
        correctList.add(new LogEntryCount(logEntry1.getName(), 1));

        assertEquals(correctList, Statistic.aggregateLogEntriesByName(observableList1));


        correctList.clear();
        correctList.add(new LogEntryCount(logEntry1.getName(), 2));

        assertEquals(correctList, Statistic.aggregateLogEntriesByName(observableList2));


        correctList.add(new LogEntryCount(logEntry3.getName(), 1));
        List<LogEntryCount> list3 = Statistic.aggregateLogEntriesByName(observableList3);

        correctList.sort(comparator);
        list3.sort(comparator);

        assertEquals(correctList, list3);


        correctList.clear();
        correctList.add(new LogEntryCount(logEntry1.getName(), 3));
        correctList.add(new LogEntryCount(logEntry3.getName(), 1));

        List<LogEntryCount> list4 = Statistic.aggregateLogEntriesByName(observableList4);

        correctList.sort(comparator);
        list4.sort(comparator);

        assertEquals(correctList, list4);
    }


    @Test
    public void testFillInLogEntriesForEmptyDays() {

        List<LogEntryCount> logsByDays = Statistic.aggregateLogEntriesByDate(observableList4);

        List<LogEntryCount> correctLeertage = logsByDays;
        correctLeertage.add(new LogEntryCount(ZonedDateTime.now().minusDays(1).format(formatter), 0));

        List<LogEntryCount> testLeertage = Statistic.fillInLogEntriesForEmptyDays(logsByDays);

        correctLeertage.sort(comparator);
        testLeertage.sort(comparator);

        assertEquals(correctLeertage, testLeertage);
    }


    @Test
    public void testGetAllDays() {

        List<LogEntryCount> correctLeertage = Statistic.aggregateLogEntriesByDate(observableList4);
        correctLeertage.add(new LogEntryCount(ZonedDateTime.now().minusDays(1).format(formatter), 0));

        List<LogEntryCount> testLeertage = Statistic.getAllDays(observableList4);

        correctLeertage.sort(comparator);
        testLeertage.sort(comparator);

        assertEquals(correctLeertage, testLeertage);
    }
}