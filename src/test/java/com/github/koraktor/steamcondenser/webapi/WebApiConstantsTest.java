package com.github.koraktor.steamcondenser.webapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Test;

public class WebApiConstantsTest {
    @Test()
    public void testPrivateConstructors() throws InstantiationException, IllegalAccessException, IllegalArgumentException {
        final Constructor<?>[] constructors = WebApiConstants.class.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            assertTrue(Modifier.isPrivate(constructor.getModifiers()));
            constructor.setAccessible(true);
            try {
                constructor.newInstance();
            }catch(InvocationTargetException ex) {
                assertEquals(UnsupportedOperationException.class, ex.getCause().getClass());
            }
        }
    }
}
