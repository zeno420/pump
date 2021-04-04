package daten;

public class EintragCount {
    private String key;
    private int count;

    public EintragCount(String key, int count) {
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
        if (!(o instanceof EintragCount)) {
            return false;
        } else if (this == o) {
            return true;
        } else return this.key.equalsIgnoreCase(((EintragCount) o).getKey());
    }
}
