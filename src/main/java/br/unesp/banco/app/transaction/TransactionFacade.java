package br.unesp.banco.app.transaction;


import br.unesp.banco.app.primitives.money.Money;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionFacade {

    private final TransactionRepository transactionRepository;

    public TransactionFacade(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllByAccountId(Long accountId) throws SQLException {
        return transactionRepository.findAllOrderDesc(accountId);
    }

    public List<Transaction> getInTheLastTenDays(Long accountId) throws SQLException {
        return transactionRepository.findAllInTheLastNDays(10, accountId);
    }

    public Transaction create(Long accountId, TransactionType type, Money value) throws SQLException {
        Money val = new Money(value.getAmount());
        if (type.getSignal().equals("-"))
            val.setNegative();
        return transactionRepository.create(new Transaction(val, type, LocalDateTime.now(), accountId));
    }

    public Transaction withdraw(Long accountId, Money value) throws SQLException {
        return create(accountId, TransactionType.WITHDRAW, value);
    }

    public Transaction makeDeposit(Long accountId, Money value) throws SQLException {
        return create(accountId, TransactionType.DEPOSIT, value);
    }

    public Transaction transfer(Long accountIdRem, Long accountIdDest, Money value) throws SQLException {
        create(accountIdRem, TransactionType.TRANSFER_MADE, value);
        return create(accountIdDest, TransactionType.TRANSFER_RECEIVED, value);
    }

    public Money getBalance(Long accountId) throws SQLException {
        return transactionRepository.getAccountBalance(accountId);
    }

}
