package daten;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of persons. This is used for saving the
 * list of persons to XML.
 *
 */

@XmlRootElement(name = "uebungen")
public class UebungListWrapper {

    private List<Uebung> uebungen;

    @XmlElement(name = "uebung")
    public List<Uebung> getUebungen() {
        return uebungen;
    }

    public void setUebungen(List<Uebung> uebungen) {
        this.uebungen = uebungen;
    }
}
