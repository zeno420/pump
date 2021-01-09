package daten;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class Methoden {

    /**
     * Makes a deep copy of any Java object that is passed.
     */
    public static Object deepCopy(Object object) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        XMLEncoder out = new XMLEncoder(bos);
        out.writeObject(object);
        out.close();
        ByteArrayInputStream bis = new
                ByteArrayInputStream(bos.toByteArray());
        XMLDecoder in = new XMLDecoder(bis);
        Object toBean = in.readObject();
        in.close();
        return toBean;

    }
}
