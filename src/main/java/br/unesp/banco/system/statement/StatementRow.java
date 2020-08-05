package br.unesp.banco.system.statement;

import br.unesp.banco.system.money.Money;
import br.unesp.banco.system.transaction.TransactionType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatementRow {

    private String date;
    private TransactionType type;
    private Money value;

    public String getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.date = date.format(formatter);
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Money getValue() {
        return value;
    }

    public void setValue(Money value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Statement{" +
                "date=" + date +
                ", type=" + type +
                ", value=" + value +
                '}';
    }
}
