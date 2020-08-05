package br.unesp.banco.system.transaction;

import br.unesp.banco.core.db.operation.Entity;
import org.omg.CORBA.TRANSACTION_MODE;

import java.time.LocalDateTime;

@Entity(table = "transactions")
public class Transaction {




    private Long id;
    private Double value;
    private TransactionType typeOp;
    private LocalDateTime transactionDate;
    private Integer accountId;

    public Transaction(Long id, Double value, TransactionType typeOp, LocalDateTime transactionDate, Integer accountId) {
        this.id = id;
        this.value = value;
        this.typeOp = typeOp;
        this.transactionDate = transactionDate;
        this.accountId = accountId;
    }
    public Transaction(){};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public TransactionType getTypeOp() {
        return typeOp;
    }

    public void setTypeOp(TransactionType typeOp) {
        this.typeOp = typeOp;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
