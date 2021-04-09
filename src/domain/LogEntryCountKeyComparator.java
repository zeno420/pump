package domain;

import java.util.Comparator;

public class LogEntryCountKeyComparator implements Comparator<LogEntryCount> {

    @Override
    public int compare(LogEntryCount o1, LogEntryCount o2) {
        return o1.getKey().compareTo(o2.getKey());
    }
}
