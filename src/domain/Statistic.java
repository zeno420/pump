package domain;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Statistic {

    public static List<LogEntryCount> aggregateLogEntriesByDate(List<LogEntry> logEntries) {
        HashMap<String, List<LogEntry>> map = new HashMap<>();

        for (LogEntry logEntry : logEntries) {
            List<LogEntry> itemsWithSameName = map.get(logEntry.getDate());
            if (itemsWithSameName == null) { //does not exist in map yet
                itemsWithSameName = new ArrayList<LogEntry>();
                map.put(logEntry.getDate(), itemsWithSameName);
            }
            itemsWithSameName.add(logEntry); //now add the item to the list for this key
        }

        List<LogEntryCount> resultList = new ArrayList<>();
        map.forEach((n, l) -> resultList.add(new LogEntryCount(n, l.size())));

        return resultList;
    }

    public static List<LogEntryCount> getAllDays(List<LogEntry> logEntries) {
        return fillInLogEntriesForEmptyDays(aggregateLogEntriesByDate(logEntries));
    }


    public static List<LogEntryCount> aggregateLogEntriesByName(List<LogEntry> logEntries) {
        HashMap<String, List<LogEntry>> map = new HashMap<>();

        for (LogEntry logEntry : logEntries) {
            List<LogEntry> itemsWithSameName = map.get(logEntry.getName());
            if (itemsWithSameName == null) { //does not exist in map yet
                itemsWithSameName = new ArrayList<LogEntry>();
                map.put(logEntry.getName(), itemsWithSameName);
            }
            itemsWithSameName.add(logEntry); //now add the item to the list for this key
        }

        List<LogEntryCount> resultList = new ArrayList<>();
        map.forEach((n, l) -> resultList.add(new LogEntryCount(n, l.size())));

        return resultList;
    }

    public static List<LogEntryCount> fillInLogEntriesForEmptyDays(List<LogEntryCount> logEntryCounts) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        ZonedDateTime today = ZonedDateTime.now();

        logEntryCounts.sort(new LogEntryCountKeyComparator());

        ZonedDateTime earliestDate = convertToZonedDateTime(logEntryCounts.get(0).getKey());

        long days = zonedDateTimeDifference(earliestDate, today);
        ArrayList<LogEntryCount> emptyDays = new ArrayList<>();

        for (ZonedDateTime currentDate = earliestDate; currentDate.isBefore(earliestDate.plusDays(days).plusDays(1)); currentDate = currentDate.plusDays(1)) {
            LogEntryCount logEntryCount = new LogEntryCount(currentDate.format(formatter), 0);
            if (!logEntryCounts.contains(logEntryCount)) {
                emptyDays.add(logEntryCount);
            } else {
                emptyDays.add(logEntryCounts.get(logEntryCounts.indexOf(logEntryCount)));
            }
        }

        return emptyDays;
    }

    private static long zonedDateTimeDifference(ZonedDateTime d1, ZonedDateTime d2) {
        return ChronoUnit.DAYS.between(d1, d2);
    }

    private static ZonedDateTime convertToZonedDateTime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return localDate.atStartOfDay(ZoneId.systemDefault());
    }
}