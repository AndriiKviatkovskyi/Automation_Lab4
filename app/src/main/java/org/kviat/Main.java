package org.kviat;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) {

        try {
            Product product = new Product("Bubble gum", "ChewChew", 8.99);
            PriceValidator.validatePrice(product);
            System.out.println(product);
            product.setPrice(-5);
            PriceValidator.validatePrice(product);

        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.err.println("Validation failed: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

}
