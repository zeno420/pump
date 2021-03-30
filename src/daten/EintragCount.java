package daten;

public class EintragCount {
    private String key;
    private int count;

    public EintragCount(String date, int count) {
        this.key = date;
        this.count = count;
    }

    public String getKey() {
        return key;
    }

    public int getCount() {
        return count;
    }

    public static EintragCount frueher(EintragCount eintragCount1, EintragCount eintragCount2){
        if(Integer.parseInt(eintragCount1.getKey()) > Integer.parseInt(eintragCount2.getKey())) return eintragCount2;
        return eintragCount1;
    }

    @Override
    public boolean equals(Object o){
        if ( !(o instanceof EintragCount)){
            return false;
        } else if (this == (EintragCount) o){
            return true;
        } else return this.key.equalsIgnoreCase(((EintragCount) o).getKey());
    }
}
