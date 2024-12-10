package org.jgap.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Utility class for accessing private fields and methods.
 * same as junitx.util.PrivateAccessor in junit-addons
 * @author ray xue
 */
public class PrivateAccessor {
    private PrivateAccessor() {
    }

    public static Object getField(Object object, String name) throws NoSuchFieldException {
        if (object == null) {
            throw new IllegalArgumentException("Invalid null object argument");
        } else {
            for(Class<?> cls = object.getClass(); cls != null; cls = cls.getSuperclass()) {
                try {
                    Field field = cls.getDeclaredField(name);
                    field.setAccessible(true);
                    return field.get(object);
                } catch (IllegalAccessException | NoSuchFieldException ignored) {
                }
            }

            throw new NoSuchFieldException("Could get value for field " + object.getClass().getName() + "." + name);
        }
    }

    public static Object getField(Class<?> cls, String name) throws NoSuchFieldException {
        if (cls == null) {
            throw new IllegalArgumentException("Invalid null cls argument");
        } else {
            for(Class<?> base = cls; base != null; base = base.getSuperclass()) {
                try {
                    Field field = base.getDeclaredField(name);
                    field.setAccessible(true);
                    return field.get(base);
                } catch (IllegalAccessException | NoSuchFieldException ignored) {
                }
            }

            throw new NoSuchFieldException("Could get value for static field " + cls.getName() + "." + name);
        }
    }

    public static void setField(Object object, String name, Object value) throws NoSuchFieldException {
        if (object == null) {
            throw new IllegalArgumentException("Invalid null object argument");
        } else {
            for(Class<?> cls = object.getClass(); cls != null; cls = cls.getSuperclass()) {
                try {
                    Field field = cls.getDeclaredField(name);
                    field.setAccessible(true);
                    field.set(object, value);
                    return;
                } catch (IllegalAccessException | NoSuchFieldException ignored) {
                }
            }

            throw new NoSuchFieldException("Could set value for field " + object.getClass().getName() + "." + name);
        }
    }

    public static void setField(Class<?> cls, String name, Object value) throws NoSuchFieldException {
        if (cls == null) {
            throw new IllegalArgumentException("Invalid null cls argument");
        } else {
            for(Class<?> base = cls; base != null; base = base.getSuperclass()) {
                try {
                    Field field = base.getDeclaredField(name);
                    field.setAccessible(true);
                    field.set(base, value);
                    return;
                } catch (IllegalAccessException | NoSuchFieldException ignored) {
                }
            }

            throw new NoSuchFieldException("Could set value for static field " + cls.getName() + "." + name);
        }
    }

    public static Object invoke(Object object, String name, Class<?>[] parameterTypes, Object[] args) throws Throwable {
        if (object == null) {
            throw new IllegalArgumentException("Invalid null object argument");
        } else {
            for(Class<?> cls = object.getClass(); cls != null; cls = cls.getSuperclass()) {
                try {
                    Method method = cls.getDeclaredMethod(name, parameterTypes);
                    method.setAccessible(true);
                    return method.invoke(object, args);
                } catch (InvocationTargetException e) {
                    throw e.getTargetException();
                }
            }

            throw new NoSuchMethodException("Failed method invocation: " + object.getClass().getName() + "." + name + "()");
        }
    }

    public static Object invoke(Class<?> cls, String name, Class<?>[] parameterTypes, Object[] args) throws Throwable {
        if (cls == null) {
            throw new IllegalArgumentException("Invalid null cls argument");
        } else {
            for(Class<?> base = cls; base != null; base = base.getSuperclass()) {
                try {
                    Method method = base.getDeclaredMethod(name, parameterTypes);
                    method.setAccessible(true);
                    return method.invoke(base, args);
                } catch (InvocationTargetException e) {
                    throw (Exception)e.getTargetException();
                }
            }

            throw new NoSuchMethodException("Failed static method invocation: " + cls.getName() + "." + name + "()");
        }
    }
}

