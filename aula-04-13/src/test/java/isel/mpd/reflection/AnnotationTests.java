package isel.mpd.reflection;

import isel.mpd.reflection.annotations.Attribute;
import isel.mpd.reflection.annotations.data.Person;
import isel.mpd.reflection.converters.ConverterFactory;
import isel.mpd.reflection.converters.DateConverter;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AnnotationTests {

    @Test
    public void personAnnotationsCheck() throws
        NoSuchFieldException {
        var birth = LocalDate.of(200,12,25);
        Person p = new Person("John", birth);

        Class<?> cls = p.getClass();

        Field field = cls.getDeclaredField("name");

        Annotation[] annotations = field.getAnnotations();

        for(Annotation a : annotations) {
            System.out.println("annotation type= " + a.annotationType().getName());
        }

        Attribute[] attrs = field.getAnnotationsByType(Attribute.class);

        for(Attribute a : attrs) {
            System.out.println("attribute= " + a.annotationType().getName());
        }

        System.out.println(field);

    }

    @Test
    public void personConvertBirthdayToDBDateTypeCheck() throws
        NoSuchFieldException, IllegalAccessException {
        var birth = LocalDate.of(200,12,25);
        Person p = new Person("John", birth);

        Class<?> cls = p.getClass();
        Field field = cls.getDeclaredField("name");


        Field birthDay = cls.getDeclaredField("birthDate");
        Attribute attr = birthDay.getAnnotation(Attribute.class);
        assertNotNull(attr);

        String converterName = attr.converter();

        DateConverter conv =
            ConverterFactory.getDateConverter(converterName);

        assertNotNull(conv);


        birthDay.setAccessible(true);


        Date sqlDate = conv.convert( birthDay.get(p));

        System.out.println(sqlDate.getClass().getName());
        System.out.println(sqlDate);

    }

}
