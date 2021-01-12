package daten;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of persons. This is used for saving the
 * list of persons to XML.
 *
 */

@XmlRootElement(name = "programme")
public class ProgrammListWrapper {

    private List<Programm> programme;

    @XmlElement(name = "programm")
    public List<Programm> getProgramme() {
        return programme;
    }

    public void setProgramme(List<Programm> programme) {
        this.programme = programme;
    }
}
