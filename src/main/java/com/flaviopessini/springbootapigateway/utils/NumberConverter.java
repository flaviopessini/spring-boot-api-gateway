package com.flaviopessini.springbootapigateway.utils;

public class NumberConverter {

    public static boolean isNumeric(String n) {
        if (n == null || n.isEmpty()) {
            return false;
        }
        final var value = n.replaceAll(",", ".").trim();
        return value.matches("[-+]?[0-9]*\\.?[0-9]+");
    }

    public static Double convertToDouble(String n) {
        if (n == null || n.isEmpty()) {
            return 0D;
        }
        final var value = n.replaceAll(",", ".").trim();
        if (isNumeric(value)) {
            return Double.parseDouble(value);
        }
        return 0D;
    }
}
