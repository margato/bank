package br.unesp.banco.app.transaction;

import java.util.Arrays;

public enum TransactionType {
    WITHDRAW("Saque", "-"),
    DEPOSIT("Depósito", ""),
    TRANSFER_RECEIVED("Transferência recebida", ""),
    TRANSFER_MADE("Transferência efetuada", "-");

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
                     .filter(transaction -> transaction.getName().equals(name))
                     .findFirst()
                     .orElseThrow(() -> new RuntimeException(String.format("Unknown TransactionType.name: '%s'", name)));
    }
}
