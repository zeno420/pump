package domain;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlID;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Program implements UniqueNamed, EditableDomainObject {

    private StringProperty name = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();

    private ListProperty<Day> days = new SimpleListProperty<>(FXCollections.observableArrayList(Day.makeExtractor()));

    private ProgramValid programValid = ProgramValid.NONAME;
    private IntegerProperty currentDayIndex = new SimpleIntegerProperty(0);

    private List<Property> editableMembers = new ArrayList<>();

    @XmlAttribute
    @XmlID
    private final String pid;

    public Program() {
        //assign unique id to an instance variable
        pid = "p-" + UUID.randomUUID().toString();
        name.set("");
        description.set("");

        editableMembers.add(name);
        editableMembers.add(description);
        editableMembers.add(days);
    }

    public String getId() {
        //return instance variable
        return pid;
    }


    public enum ProgramValid {
        VALID(0, ""), NONAME(1, "Name invalid."), DAYS(2, "Days invalid.");

        private int code;
        private String errorMessage;

        ProgramValid(int code, String errorMessage) {
            this.code = code;
            this.errorMessage = errorMessage;
        }

        public int getCode() {
            return code;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    public Program makeTmpCopy() {
        Program tmpProgram = new Program();
        tmpProgram.setName(name.get());
        tmpProgram.setDescription(description.get());
        tmpProgram.getDays().addAll(days.get());
        return tmpProgram;
    }

    public static Callback<Program, Observable[]> makeExtractor() {
        return new Callback<Program, Observable[]>() {
            @Override
            public Observable[] call(Program program) {
                return new Observable[]{program.nameProperty(), program.descriptionProperty(), program.daysProperty()};
            }
        };
    }

    public ProgramValid getProgramValid() {
        if (name == null || name.get() == null || name.get().equalsIgnoreCase("")) {
            programValid = ProgramValid.NONAME;
        } else if (days.get().size() < 1) {
            programValid = ProgramValid.DAYS;
        } else {
            programValid = ProgramValid.VALID;
        }
        return programValid;
    }

    public List<Property> getEditableMembers() {
        return editableMembers;
    }

    public void transferEditableMembers(List<Property> tmpAenderbareMember) {
        for (int i = 0; i < tmpAenderbareMember.size(); i++) {
            editableMembers.get(i).setValue(tmpAenderbareMember.get(i).getValue());

        }
    }

    public void setProgramValid(ProgramValid programValid) {
        this.programValid = programValid;
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

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public ObservableList<Day> getDays() {
        return days.get();
    }

    public ListProperty<Day> daysProperty() {
        return days;
    }

    public void setDays(ObservableList<Day> days) {
        this.days.set(days);
    }

    public int getCurrentDayIndex() {
        validateIndex();
        return currentDayIndex.get();
    }

    public IntegerProperty currentDayIndexProperty() {
        return currentDayIndex;
    }

    public void setCurrentDayIndex(int currentDayIndex) {
        this.currentDayIndex.set(currentDayIndex);
    }

    public void increaseCurrentDay() {
        setCurrentDayIndex(currentDayIndex.get() + 1);
        validateIndex();
    }

    public void decreaseCurrentDay() {
        setCurrentDayIndex(currentDayIndex.get() - 1);
        validateIndex();
    }

    private void validateIndex() {
        if (currentDayIndex.get() > days.get().size() - 1) {
            currentDayIndex.set(0);
        } else if (currentDayIndex.get() < 0) {
            currentDayIndex.set(days.get().size() - 1);
        }
    }
}