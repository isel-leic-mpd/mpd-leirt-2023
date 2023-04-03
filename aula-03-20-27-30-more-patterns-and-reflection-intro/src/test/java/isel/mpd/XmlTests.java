package isel.mpd;

import isel.mpd.reflection.ReflectionUtils;
import isel.mpd.reflection.XmlSerializer;
import isel.mpd.reflection.data.MyPoint;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.Reference;

import static isel.mpd.reflection.ReflectionUtils.getObjectFromFile;
import static isel.mpd.reflection.ReflectionUtils.saveObjectToFile;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlTests {

    @Test
    public void createXmlEmptyFile()
        throws FileNotFoundException  {
        XmlSerializer xs = new XmlSerializer();

        xs.toXml(null, "empty.xml");
    }

    @Test
    public void save_my_point_to_xml_file()
        throws FileNotFoundException  {
        MyPoint p = new MyPoint(5, 10);
        XmlSerializer xs = new XmlSerializer();

        xs.toXml(p, "point.xml");
    }

    @Test
    public void save_to_xml_file_and_retrieve_my_point()
        throws FileNotFoundException, IOException {
        var fileName = "point.xml";
        MyPoint p = new MyPoint(5, 10);
        XmlSerializer xs = new XmlSerializer();
        xs.toXml(p, fileName);

        try(BufferedReader reader =
                new BufferedReader(new FileReader(fileName))) {
            String text = reader.lines().collect(joining());
            MyPoint np = (MyPoint) xs.fromXml(text);
            System.out.println("np=" + np);
            assertEquals(p, np);
        }
    }

    @Test
    public void save_to_java_object_file_and_retrieve_my_point() {
        var fileName = "pointobj.dat";
        MyPoint p = new MyPoint(5, 10);
        saveObjectToFile(p, fileName);

        MyPoint np = getObjectFromFile(fileName);
        System.out.println("np=" + np);
        assertEquals(p, np);
    }
}
