package mmt.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Assertions;

public class PrivateFieldTester {

    public static void checkPrivateFields(Class<?> Class) {
        for (Field field : Class.getDeclaredFields()) {
            Assertions.assertTrue(
                Modifier.isPrivate(field.getModifiers()),
                "The field " + field.getName() + " is supposed to be private."
            );
        }
    }
}
