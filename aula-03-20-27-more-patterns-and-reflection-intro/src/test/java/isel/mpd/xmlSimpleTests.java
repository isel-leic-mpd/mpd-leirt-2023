package isel.mpd;

import isel.mpd.reflection.XmlSerializer;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

public class xmlSimpleTests {

    @Test
    public void createXmlEmptyFile()
        throws FileNotFoundException  {
        XmlSerializer xs = new XmlSerializer();

        xs.toXml(null, "empty.xml");
    }
}
