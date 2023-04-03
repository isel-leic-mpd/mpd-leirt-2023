package isel.mpd.reflection;

import isel.mpd.reflection.data.MyPoint;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Classe que que contém um conjunto de métodos estáticos
 * de suporte à utilizaçáo da API de introspecção/reflexão
 */
public class ReflectionUtils {

    /**
     * Exemplo introdutório: listagem dos campos públicos
     * de um objeto arbirário
     * @param o o objeto a inspeccionar
     */
    public static void showPublicFields(Object o)  {
        Class<?> objCls = o.getClass();

        for (Field f : objCls.getFields()) {
            String s = String.format("%s : %s",
                f.getName(), f.getType().getName());
            System.out.println(s);
        }
    }

    /**
     * Verfica se determinada classe (representada por "cls1") tem uma relação de Is-A
     * com outra (representada por "cls2"). A relação de Is-A existe se "cls1" e "cls2"
     * forem o mesmo objeto ou se "cls1" representar uma classe que deriva direta ou
     * indiretamente da classe representada por "cls2"
     * @param cls1
     * @param cls2
     * @return
     */
    public static boolean isA(Class<?> cls1, Class<?> cls2) {
        if (cls1 == cls2) return true;
        do {
            cls1 = cls1.getSuperclass();
            if (cls1 == cls2) return true;
        }
        while(cls1 != Object.class);
        return false;
    }

    /**
     * permite obter as interfaces implementadas diretamente por uma dada classe
     * (representada por "cls"), ou indiretamente (pelas suas suas superclasses)
     * @param cls
     * @return
     */
    public  static  List<Class<?>> getInterfaces(Class<?> cls) {
        List<Class<?>> interfaces = new LinkedList<>();
        while(cls != Object.class) {
            interfaces.addAll(Arrays.asList(cls.getInterfaces()));
            cls = cls.getSuperclass();
        }
        return interfaces;
    }

    /**
     * Tirando partido do método anterior verifica se a classe representado
     * por "cls" implementa a interface representada por "interf"
     * @param cls
     * @param interf
     * @return
     */
    public static boolean implement(Class<?> cls, Class<?> interf) {
       var interfaces = getInterfaces(cls);
       return interfaces.contains(interf);
    }


    /**
     * Permite determinar se o objecto "o" é uma instância da classe representada
     * por "type" ou implementa (direta ou indiretamente) a interface reprsentada
     * por "type"
     * @param o
     * @param type
     * @return
     */
    public static boolean instanceOf(Object o, Class<?> type) {
        if (o == null) return false;
        Class<?> objClass = o.getClass();
        return type.isInterface()
            ? implement(objClass, type)
            : isA(objClass, type);
    }


    public static List<Field> getFields(Class<?> cls) {
        List<Field> fields = new ArrayList<>();

        while(cls != Object.class) {
           Field[] lf = cls.getDeclaredFields();
           fields.addAll(Arrays.asList(lf));
           cls = cls.getSuperclass();
        }
        return fields;
    }

    public static void saveObjectToFile(Object o, String fileName) {
        try(ObjectOutputStream os =
            new ObjectOutputStream(new FileOutputStream(fileName))) {
            os.writeObject(o);
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static <T> T getObjectFromFile(String fileName) {
        try(ObjectInputStream is =
            new ObjectInputStream(new FileInputStream(fileName))) {
            return (T) is.readObject();
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
        catch(ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
