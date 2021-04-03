package daten;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

public class Datenbank {


    private static String dataFilePath = "datenbank.xml";
    private static File datenbank;

    public static void init() throws IOException {
        datenbank = new File(dataFilePath);
        datenbank.createNewFile();

    }

    public static Datenbasis load() throws JAXBException {
        try {
            JAXBContext c = JAXBContext.newInstance(Datenbasis.class);
            Unmarshaller um = c.createUnmarshaller();
            Datenbasis dw = (Datenbasis) um.unmarshal(datenbank);

            return dw;

        } catch (Exception e) { // catches ANY exception
            throw e;
        }
    }

    public static void save(Datenbasis datenbasis) throws JAXBException {
        try {
            JAXBContext c = JAXBContext.newInstance(Datenbasis.class);
            Marshaller m = c.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            m.marshal(datenbasis, datenbank);

        } catch (Exception e) { // catches ANY exception
            throw e;
        }
    }

}
