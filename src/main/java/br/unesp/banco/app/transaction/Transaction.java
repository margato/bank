package br.unesp.banco.app.transaction;

import br.unesp.banco.core.db.stereotype.Entity;
import br.unesp.banco.app.primitives.money.Money;

import java.time.LocalDateTime;

@Entity(table = "transactions")
public class Transaction {
    private Long id;
    private Money value;
    private TransactionType type;
    private LocalDateTime date;
    private Long accountId;

    public Transaction( Money value, TransactionType type, LocalDateTime date, Long accountId) {

        this.value = value;
        this.type = type;
        this.date = date;
        this.accountId = accountId;
    }

    public Transaction() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Money getValue() {
        return value;
    }

    public void setValue(Money value) {
        this.value = value;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
