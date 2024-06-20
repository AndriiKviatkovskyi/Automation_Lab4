package org.kviat;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
public class PriceValidator {
    public static void validatePrice(Object obj) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(ValidatePrice.class)) {
                ValidatePrice validatePrice = method.getAnnotation(ValidatePrice.class);
                method.setAccessible(true);

                Method getter = obj.getClass().getMethod("get" + capitalize(method.getName().substring(3)));
                double price = (double) getter.invoke(obj);
                if (price < validatePrice.min()) {
                    throw new IllegalArgumentException("Price must be greater than or equal to " + validatePrice.min());
                }
            }
        }
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}