package br.unesp.banco.utils;

public class Logger {

    public static void logUi(String message) {
        System.out.printf("[UI] %s%n", message);
    }

    public static void logDb(String message) {
        System.out.printf("[DB] %s%n", message);
    }

    public static void logInput(String message) {
        System.out.printf("[INPUT] %s%n", message);
    }

    public static void log(String tag, String message) {
        System.out.printf("[%s] %s%n", tag, message);
    }

}
