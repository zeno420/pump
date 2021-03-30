package test;

import com.sun.jmx.remote.internal.ArrayQueue;
import daten.*;
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

public class StatistikTest {

    private static ObservableList<LogEintrag> observableList1 = FXCollections.observableArrayList();
    private static ObservableList<LogEintrag> observableList2 = FXCollections.observableArrayList();
    private static ObservableList<LogEintrag> observableList3 = FXCollections.observableArrayList();
    private static ObservableList<LogEintrag> observableList4 = FXCollections.observableArrayList();
    private static LogEintrag logEintrag1;
    private static LogEintrag logEintrag2;
    private static LogEintrag logEintrag3;
    private static LogEintrag logEintrag4;
    private static Comparator<EintragCount> comparator;
    private static DateTimeFormatter formatter;

    @BeforeAll
    static void initAll() throws Exception {

        // TODO mock?
        // UebungLogsByName.addAll(Statistik.getLogsByName(Datenbank.getUebungLogs()));



        formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        Uebung uebung1 = new Uebung();
        uebung1.setName("name");

        Uebung uebung2 = new Uebung();
        uebung2.setName("anderer name");

        logEintrag1 = new LogEintrag(uebung1.getName(), uebung1.getBeschreibung());
        logEintrag2 = new LogEintrag(uebung1.getName(), uebung1.getBeschreibung());
        logEintrag3 = new LogEintrag(uebung2.getName(), uebung2.getBeschreibung());
        logEintrag4 = new LogEintrag(uebung2.getName(), uebung2.getBeschreibung());
        logEintrag4.setDate(ZonedDateTime.now().minusDays(2).format(formatter));

        //observableList1: eine übung, ein tag
        observableList1.add(logEintrag1);

        //observableList2: eine übung 2x, ein tag
        observableList2.add(logEintrag1);
        observableList2.add(logEintrag2);

        //observableList3: eine übung 2x + eine andere übung, ein tag
        observableList3.add(logEintrag1);
        observableList3.add(logEintrag2);
        observableList3.add(logEintrag3);

        //observableList3: eine übung 2x + eine andere übung 2x, zwei tage
        observableList4.add(logEintrag1);
        observableList4.add(logEintrag2);
        observableList4.add(logEintrag3);
        observableList4.add(logEintrag4);

        comparator = new Comparator<EintragCount>() {
            @Override
            public int compare(EintragCount o1, EintragCount o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        };
    }


    @Test
    public void testGetLogsByDate() {

        // TODO mock?
        // UebungLogsByName.addAll(Statistik.getLogsByName(Datenbank.getUebungLogs()));

        ArrayList<EintragCount> correctList = new ArrayList<>();
        correctList.add(new EintragCount(logEintrag1.getDate(), 1));

        assertEquals(correctList, Statistik.getLogsByDate(observableList1));


        correctList.clear();
        correctList.add(new EintragCount(logEintrag1.getDate(), 2));

        assertEquals(correctList, Statistik.getLogsByDate(observableList2));


        correctList.clear();
        correctList.add(new EintragCount(logEintrag1.getDate(), 3));

        assertEquals(correctList, Statistik.getLogsByDate(observableList3));


        correctList.add(new EintragCount(logEintrag4.getDate(), 1));
        List<EintragCount> list4 = Statistik.getLogsByDate(observableList4);

        correctList.sort(comparator);
        list4.sort(comparator);

        assertEquals(correctList, list4);
    }

    @Test
    public void testGetLogsByName() {

        ArrayList<EintragCount> correctList = new ArrayList<>();
        correctList.add(new EintragCount(logEintrag1.getName(), 1));

        assertEquals(correctList, Statistik.getLogsByName(observableList1));


        correctList.clear();
        correctList.add(new EintragCount(logEintrag1.getName(), 2));

        assertEquals(correctList, Statistik.getLogsByName(observableList2));


        correctList.add(new EintragCount(logEintrag3.getName(), 1));
        List<EintragCount> list3 = Statistik.getLogsByName(observableList3);

        correctList.sort(comparator);
        list3.sort(comparator);

        assertEquals(correctList, list3);


        correctList.clear();
        correctList.add(new EintragCount(logEintrag1.getName(), 3));
        correctList.add(new EintragCount(logEintrag3.getName(), 1));

        List<EintragCount> list4 = Statistik.getLogsByName(observableList4);

        correctList.sort(comparator);
        list4.sort(comparator);

        assertEquals(correctList, list4);
    }


    @Test
    public void testLeertageEinfuegen() {

        List<EintragCount> logsByDays = Statistik.getLogsByDate(observableList4);

        List<EintragCount> correctLeertage = logsByDays;
        correctLeertage.add(new EintragCount(ZonedDateTime.now().minusDays(1).format(formatter), 0));

        List<EintragCount> testLeertage = Statistik.leertageEinfuegen(logsByDays);

        correctLeertage.sort(comparator);
        testLeertage.sort(comparator);

        assertEquals(correctLeertage, testLeertage);
    }


    @Test
    public void testGetAllDays() {

        List<EintragCount> correctLeertage = Statistik.getLogsByDate(observableList4);
        correctLeertage.add(new EintragCount(ZonedDateTime.now().minusDays(1).format(formatter), 0));

        List<EintragCount> testLeertage = Statistik.getAllDays(observableList4);

        correctLeertage.sort(comparator);
        testLeertage.sort(comparator);

        assertEquals(correctLeertage, testLeertage);
    }
}