package br.unesp.banco.system.transaction;

import br.unesp.banco.core.db.stereotype.Entity;
import br.unesp.banco.system.money.Money;

import java.time.LocalDateTime;

@Entity(table = "transactions")
public class Transaction {

    private Long id;
    private Money value;
    private TransactionType type;
    private LocalDateTime date;
    private Integer accountId;

    public Transaction(Long id, Money value, TransactionType type, LocalDateTime date, Integer accountId) {
        this.id = id;
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

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
