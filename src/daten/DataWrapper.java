package daten;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "data")
public class DataWrapper {

    private List<Programm> programme;

    @XmlElement(name = "programm")
    public List<Programm> getProgramme() {
        return programme;
    }

    public void setProgramme(List<Programm> programme) {
        this.programme = programme;
    }

    private List<Workout> workouts;

    @XmlElement(name = "workout")
    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }

    private List<Uebung> uebungen;

    @XmlElement(name = "uebung")
    public List<Uebung> getUebungen() {
        return uebungen;
    }

    public void setUebungen(List<Uebung> uebungen) {
        this.uebungen = uebungen;
    }

}
