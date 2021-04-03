package daten;

import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static daten.EintragCount.frueher;

public class Statistik {


    public static List<EintragCount> getLogsByDate(List<LogEintrag> list) {
        HashMap<String, List<LogEintrag>> map = new HashMap<>();

        for (LogEintrag logEintrag : list) {
            List<LogEintrag> itemsWithSameName = map.get(logEintrag.getDate());
            if (itemsWithSameName == null) { //does not exist in map yet
                itemsWithSameName = new ArrayList<LogEintrag>();
                map.put(logEintrag.getDate(), itemsWithSameName);
            }
            itemsWithSameName.add(logEintrag); //now add the item to the list for this key
        }

        List<EintragCount> resultList = new ArrayList<>();
        map.forEach((n, l) -> resultList.add(new EintragCount(n, l.size())));

        return resultList;
    }

    public static List<EintragCount> getAllDays(List<LogEintrag> list) {
        return leertageEinfuegen(getLogsByDate(list));
    }


    public static List<EintragCount> getLogsByName(List<LogEintrag> list) {
        HashMap<String, List<LogEintrag>> map = new HashMap<>();

        for (LogEintrag logEintrag : list) {
            List<LogEintrag> itemsWithSameName = map.get(logEintrag.getName());
            if (itemsWithSameName == null) { //does not exist in map yet
                itemsWithSameName = new ArrayList<LogEintrag>();
                map.put(logEintrag.getName(), itemsWithSameName);
            }
            itemsWithSameName.add(logEintrag); //now add the item to the list for this key
        }

        List<EintragCount> resultList = new ArrayList<>();
        map.forEach((n, l) -> resultList.add(new EintragCount(n, l.size())));

        return resultList;
    }

    public static List<EintragCount> leertageEinfuegen(List<EintragCount> list) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        ZonedDateTime heute = ZonedDateTime.now();

        //frühesten tag ermitteln
        EintragCount fruehsterTag = new EintragCount(heute.format(formatter), 0);
        for (EintragCount e : list) {
            fruehsterTag = frueher(fruehsterTag, e);
        }

        ZonedDateTime fruehestesDate = convertToZDT(fruehsterTag.getKey());

        long days = zonedDateTimeDifference(fruehestesDate, heute, ChronoUnit.DAYS);
        ArrayList<EintragCount> leertageList = new ArrayList<>();

        ZonedDateTime spaetestesDate = fruehestesDate.plusDays(days);

        for (ZonedDateTime currentDate = fruehestesDate; currentDate.isBefore(spaetestesDate.plusDays(1)); currentDate = currentDate.plusDays(1)) {
            EintragCount eintrag = new EintragCount(currentDate.format(formatter), 0);
            if (!list.contains(eintrag)) {
                leertageList.add(eintrag);
            } else {
                leertageList.add(list.get(list.indexOf(eintrag)));
            }
        }

        return leertageList;
    }

    private static long zonedDateTimeDifference(ZonedDateTime d1, ZonedDateTime d2, ChronoUnit unit) {
        return unit.between(d1, d2);
    }

    private static ZonedDateTime convertToZDT(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(date, formatter);

        ZonedDateTime result = localDate.atStartOfDay(ZoneId.systemDefault());
        return result;
    }

}
