package daten;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Programm implements UniqueNamed{

    private StringProperty name = new SimpleStringProperty();
    private StringProperty beschreibung = new SimpleStringProperty();

    private ListProperty<Tag> tage = new SimpleListProperty<>(FXCollections.observableArrayList(Tag.makeExtractor()));

    private ProgrammValid programmValid = ProgrammValid.NONAME;
    private IntegerProperty currentTagIndex = new SimpleIntegerProperty(0);

    private List<Property> aenderbareMember = new ArrayList<>();

    //static id generator shared among all instances of Coordinates
    //private static final AtomicInteger idGenerator = new AtomicInteger(1000);

    @XmlAttribute
    @XmlID
    private final String pid;

    public Programm() {
        //assign unique id to an instance variable
        pid = "p-" + UUID.randomUUID().toString();
        name.set("");
        beschreibung.set("");

        aenderbareMember.add(name);
        aenderbareMember.add(beschreibung);
        aenderbareMember.add(tage);
    }

    public String getId() {
        //return instance variable
        return pid;
    }


    public enum ProgrammValid {
        VALID(0, ""), NONAME(1, "Name ungültig"), TAGE(2, "Tage ungültig");

        private int code;
        private String error;

        ProgrammValid(int code, String error) {
            this.code = code;
            this.error = error;
        }

        public int getCode() {
            return code;
        }

        public String getError() {
            return error;
        }
    }

    public Programm makeTmpCopy() {
        Programm tmpProgramm = new Programm();
        tmpProgramm.setName(name.get());
        tmpProgramm.setBeschreibung(beschreibung.get());
        tmpProgramm.getTage().addAll(tage.get());
        return tmpProgramm;
    }

    public static Callback<Programm, Observable[]> makeExtractor() {
        return new Callback<Programm, Observable[]>() {
            @Override
            public Observable[] call(Programm programm) {
                return new Observable[]{programm.nameProperty(), programm.beschreibungProperty(), programm.tageProperty()};
            }
        };
    }

    public ProgrammValid getProgrammValid() {
        if (name == null || name.get() == null || name.get().equalsIgnoreCase("")) {
            programmValid = ProgrammValid.NONAME;
        } else if (tage.get().size() < 1) {
            programmValid = ProgrammValid.TAGE;
        } else {
            programmValid = ProgrammValid.VALID;
        }
        return programmValid;
    }

    public List<Property> getAenderbareMember() {
        return aenderbareMember;
    }

    public void aenderbareMemberUebertragen(List<Property> tmpAenderbareMember) {
        for (int i = 0; i < tmpAenderbareMember.size(); i++) {
            aenderbareMember.get(i).setValue(tmpAenderbareMember.get(i).getValue());

        }
    }

    public void setProgrammValid(ProgrammValid programmValid) {
        this.programmValid = programmValid;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getBeschreibung() {
        return beschreibung.get();
    }

    public StringProperty beschreibungProperty() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung.set(beschreibung);
    }

    public ObservableList<Tag> getTage() {
        return tage.get();
    }

    public ListProperty<Tag> tageProperty() {
        return tage;
    }

    public void setTage(ObservableList<Tag> tage) {
        this.tage.set(tage);
    }

    public int getCurrentTagIndex() {
        return currentTagIndex.get();
    }

    public IntegerProperty currentTagIndexProperty() {
        return currentTagIndex;
    }

    public void setCurrentTagIndex(int currentTagIndex) {
        this.currentTagIndex.set(currentTagIndex);
    }

    public void increaseAktuellerTag() {
        setCurrentTagIndex(currentTagIndex.get() + 1);
        validateIndex();
    }

    public void decreaseAktuellerTag() {
        setCurrentTagIndex(currentTagIndex.get() - 1);
        validateIndex();
    }

    private void validateIndex() {
        if (currentTagIndex.get() > tage.get().size() - 1) {
            currentTagIndex.set(0);
        } else if (currentTagIndex.get() < 0) {
            currentTagIndex.set(tage.get().size() - 1);
        }
    }
}
