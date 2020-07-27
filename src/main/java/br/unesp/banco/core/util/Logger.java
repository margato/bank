package br.unesp.banco.core.util;

public class Logger {

    public static void logUi(String message, Class clazz) {
        System.out.printf("[UI] %s %s\n", message, clazz.getName());
    }

    public static void logDb(String message) {
        System.out.printf("[DB] %s\n", message);
    }

    public static void logInput(String message) {
        System.out.printf("[INPUT] %s\n", message);
    }

    public static void log(String tag, String message) {
        System.out.printf("[%s] %s\n", tag.toUpperCase(), message);
    }

}
