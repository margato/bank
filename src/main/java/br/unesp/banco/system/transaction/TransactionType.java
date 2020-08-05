package br.unesp.banco.system.transaction;

import java.util.Arrays;

public enum TransactionType {
    DEBIT("Débito", "-"), CREDIT("Crédito", "");

    private final String name;
    private final String signal;

    TransactionType(String name, String signal) {
        this.name = name;
        this.signal = signal;
    }

    public String getSignal() {
        return signal;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static TransactionType getByName(String name) {
        return Arrays.stream(TransactionType.values())
                     .filter(v -> v.getName().equals(name))
                     .findFirst()
                     .orElseThrow(() -> new RuntimeException(String.format("Unknown TransactionType.name: '%s'", name)));
    }
}
