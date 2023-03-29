package isel.mpd.reflection;

import org.dom4j.DocumentHelper;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class XmlSerializer {

    public void toXml(Object o, String xmlFile)
        throws FileNotFoundException {
        try(PrintStream ps = new PrintStream(xmlFile)) {
            var root = DocumentHelper.createElement("object");
            var doc = DocumentHelper.createDocument(root);

            String xmlText = doc.asXML();
            ps.println(xmlText);
        }
    }

}
