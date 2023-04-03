package isel.mpd.reflection;

import isel.mpd.reflection.data.*;
import org.junit.jupiter.api.Test;

import static isel.mpd.reflection.ReflectionUtils.instanceOf;
import static isel.mpd.reflection.ReflectionUtils.showPublicFields;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReflectionUtilsTests {
    @Test
    public void show_public_fields_of_an_object() {
        MyPoint p = new MyPoint(2,3);

        showPublicFields(p);
    }

    @Test
    public void check_if_some_object_is_an_instance_of_the_given_types() {
        C2 c2 = new C2();

        assertTrue(instanceOf(c2, C2.class));
        assertTrue(instanceOf(c2, C1.class));
        assertTrue(instanceOf(c2, Object.class));
        assertTrue(instanceOf(c2, Object.class));

        assertTrue(instanceOf(c2, I1.class));
        assertTrue(instanceOf(c2, I2.class));



    }

    @Test
    public void check_if_some_object_is_not_an_instance_of_the_given_type() {
        C1 c1 = new C1();
        assertFalse(instanceOf(c1, String.class));
        assertFalse(instanceOf(c1, I2.class));
    }
}
