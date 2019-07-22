package com.ega.datarepositorysevice.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
/**
 * @author Michael Remijan mjremijan@yahoo.com @mjremijan
 */
public class ReflectTool {
    public static <T extends Annotation> T getMethodAnnotation(
            Class<?> c, String methodName, Class<T> annotation) {
        try {
            Method m = c.getDeclaredMethod(methodName);
            return m.getAnnotation(annotation);
        } catch (NoSuchMethodException nsme) {
            throw new RuntimeException(nsme);
        }
    }
    public static <T extends Annotation> T getFieldAnnotation(
            Class<?> c, String fieldName, Class<T> annotation) {
        try {
            Field f = c.getDeclaredField(fieldName);
            return f.getAnnotation(annotation);
        } catch (NoSuchFieldException nsme) {
            throw new RuntimeException(nsme);
        }
    }
    public static <T extends Annotation> T getClassAnnotation(
            Class<?> c, Class<T> annotation) {
        return c.getAnnotation(annotation);
    }
}

