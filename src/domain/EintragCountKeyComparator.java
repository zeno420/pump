package domain;

import java.util.Comparator;

public class EintragCountKeyComparator implements Comparator<EintragCount> {

    @Override
    public int compare(EintragCount o1, EintragCount o2) {
        return o1.getKey().compareTo(o2.getKey());
    }
}
