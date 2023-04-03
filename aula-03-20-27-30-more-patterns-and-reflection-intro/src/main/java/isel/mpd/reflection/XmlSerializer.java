package isel.mpd.reflection;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.print.Doc;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static isel.mpd.reflection.ReflectionUtils.getFields;



public class XmlSerializer {

    private Object getValueFromString(String strVal, Class<?> type) {
        Object objVal = null;

        if (type == int.class) {
            objVal = Integer.parseInt(strVal);
        }
        else if (type == double.class) {
            objVal = Double.parseDouble(strVal);
        }
        else if (type == String.class) {
            objVal = strVal;
        }
        return objVal;
    }

    private void loadFields(Object o, List<Element> xmlFields)
                    throws NoSuchFieldException, IllegalAccessException {
        Class<?> objClass = o.getClass();
        for(Element xmlField : xmlFields) {
            Field f = objClass.getDeclaredField(xmlField.getName());
            f.setAccessible(true);
            String isNullVal = xmlField.attributeValue("isNull");
            Object value = null;

            if (!"true".equals(isNullVal)) {
               String strVal = xmlField.getText();
               value = getValueFromString(strVal, f.getType());
            }

            f.set(o, value);
        }
    }

    private void saveFields(Object o, Element parent, List<Field> fields)
            throws IllegalAccessException {
        for(Field f : fields) {
            Element child  = parent.addElement(f.getName());
            child.addAttribute("type", f.getType().getName());
            child.addAttribute("declaredAt", f.getDeclaringClass().getName());
            f.setAccessible(true);
            Object value = f.get(o);
            if (f.getType().isPrimitive()) {
                child.addText(value.toString());
            }
            else {
                if (value == null) {
                    child.addAttribute("isNull", "true");
                }
                else {
                    child.addText(value.toString());
                }
            }
        }
    }

    public void toXml(Object o, String xmlFile)
        throws FileNotFoundException {
        try(PrintStream ps = new PrintStream(xmlFile)) {
            var root = DocumentHelper.createElement("object");
            var doc = DocumentHelper.createDocument(root);

            if (o == null) {
                root.addAttribute("isNull", "true");
            }
            else {
                Class<?> objClass = o.getClass();
                root.addAttribute("type", objClass.getName());
                List<Field> fields = getFields(objClass);
                saveFields(o, root, fields );

            }
            String xmlText = doc.asXML();
            ps.println(xmlText);
        }
        catch(RuntimeException e) {
            throw e;
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }

    }

    public Object fromXml(String xmlText) {
        try {
            Document doc = DocumentHelper.parseText(xmlText);
            Element root = doc.getRootElement();
            String typeName = root.attributeValue("type");

            Class<?> objClass = Class.forName(typeName);
            Constructor<?> ctor = objClass.getConstructor();
            Object obj = ctor.newInstance();
            List<Field> fields = getFields(objClass);
            loadFields(obj, root.elements());
            return obj;
        }
        catch(DocumentException | ClassNotFoundException |
             NoSuchMethodException | SecurityException |
             InstantiationException | IllegalAccessException |
            InvocationTargetException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

}
