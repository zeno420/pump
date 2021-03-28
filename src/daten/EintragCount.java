package daten;

public class EintragCount {
    private String date;
    private int count;

    public EintragCount(String date, int count) {
        this.date = date;
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public int getCount() {
        return count;
    }

    public static EintragCount frueher(EintragCount eintragCount1, EintragCount eintragCount2){
        if(Integer.parseInt(eintragCount1.getDate()) > Integer.parseInt(eintragCount2.getDate())) return eintragCount2;
        return eintragCount1;
    }

    @Override
    public boolean equals(Object o){
        if ( !(o instanceof EintragCount)){
            return false;
        } else if (this == (EintragCount) o){
            return true;
        } else return this.date.equalsIgnoreCase(((EintragCount) o).getDate());
    }
}
