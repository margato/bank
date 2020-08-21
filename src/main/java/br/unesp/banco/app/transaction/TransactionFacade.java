package br.unesp.banco.app.transaction;


import br.unesp.banco.app.account.AccountFacade;
import br.unesp.banco.app.primitives.money.Money;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class TransactionFacade {

    private final TransactionRepository transactionRepository;
    private final AccountFacade accountFacade;

    public TransactionFacade(TransactionRepository transactionRepository, AccountFacade accountFacade) {
        this.transactionRepository = transactionRepository;
        this.accountFacade = accountFacade;
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

    public void withdraw(Long accountId, Money value) throws Exception {
        if (value.getAmount().compareTo(getBalance(accountId).getAmount()) > 0){
            throw new Exception("Valor excede o saldo em conta");
        }
        else if (value.getAmount().compareTo(BigDecimal.valueOf(0)) < 0)
            throw new Exception("Valor inválido");

        create(accountId, TransactionType.WITHDRAW, value);
    }

    public void makeDeposit(Long accountId, Money value) throws Exception {
        if (value.getAmount().compareTo(BigDecimal.valueOf(0)) < 0)
            throw new Exception("Valor inválido");
        create(accountId, TransactionType.DEPOSIT, value);
    }

    public Transaction transfer(Long accountIdRem, Long accountIdDest, Money value) throws Exception {
        if (value.getAmount().compareTo(getBalance(accountIdRem).getAmount()) > 0){
            throw new Exception("Valor excede o saldo em conta");
        }
        else if (value.getAmount().compareTo(BigDecimal.valueOf(0)) < 0)
            throw new Exception("Valor inválido");
        try{
            accountFacade.getAccount(accountIdRem);
            create(accountIdRem, TransactionType.TRANSFER_MADE, value);
            return create(accountIdDest, TransactionType.TRANSFER_RECEIVED, value);

        }
        catch (Exception ex){
            throw ex;
        }



    }

    public Money getBalance(Long accountId) throws SQLException {
        return transactionRepository.getAccountBalance(accountId);
    }

}
