package persistence;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;

public class Database {


    private static final String databaseFilePath = "datenbank.xml";
    private static File databaseFile;

    public static void init() throws IOException {
        databaseFile = new File(databaseFilePath);
        databaseFile.createNewFile();
    }

    public static Object load(Class<?> aClass) throws JAXBException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(aClass);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Object databasis = unmarshaller.unmarshal(databaseFile);

            return databasis;
        } catch (Exception exception) { // catches ANY exception
            throw exception;
        }
    }

    public static void save(Object databasis) throws JAXBException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(databasis.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            marshaller.marshal(databasis, databaseFile);
        } catch (Exception e) { // catches ANY exception
            throw e;
        }
    }
}