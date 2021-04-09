package domain;

public class LogEntryCount {
    
    private final String key;
    private final int count;

    public LogEntryCount(String key, int count) {
        this.key = key;
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LogEntryCount)) {
            return false;
        } else if (this == o) {
            return true;
        } else return this.key.equalsIgnoreCase(((LogEntryCount) o).getKey());
    }
}
