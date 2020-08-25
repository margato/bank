package br.unesp.banco.app.transaction;


import br.unesp.banco.app.account.Account;
import br.unesp.banco.app.account.AccountFacade;
import br.unesp.banco.app.primitives.money.Money;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

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

    private void create(Long accountId, TransactionType type, Money value) throws SQLException {
        Money val = new Money(value.getAmount());
        if (type.getSignal().equals("-"))
            val.setNegative();
        transactionRepository.create(new Transaction(val, type, LocalDateTime.now(), accountId));
    }

    public void withdraw(Long accountId, Money value) throws Exception {
        if (value.getAmount().compareTo(getBalance(accountId).getAmount()) > 0) {
            throw new Exception("Valor excede o saldo em conta");
        } else if (value.getAmount().compareTo(BigDecimal.valueOf(0)) < 0)
            throw new Exception("Valor inválido");

        create(accountId, TransactionType.WITHDRAW, value);
    }

    public void deposit(Long accountId, Money value) throws Exception {
        if (value.getAmount().compareTo(BigDecimal.valueOf(0)) < 0)
            throw new Exception("Valor inválido");
        create(accountId, TransactionType.DEPOSIT, value);
    }

    public void transfer(String originId, String destinationId, Money value) throws Exception {
        boolean isAccountValid = Pattern.matches("[0-9]+",destinationId);
        if (destinationId.equals(originId))
            throw new Exception("Você não pode fazer transferir para si mesmo");
        else if(!isAccountValid)
            throw new Exception("Conta inválida, tente novamente");

        Account originAccount = accountFacade.getAccountByNumber(originId);
        Account destAccount = accountFacade.getAccountByNumber(destinationId);

        boolean isBalanceNotEnough = value.getAmount().compareTo(getBalance(originAccount.getId()).getAmount()) > 0;
        boolean isValueInvalid = value.getAmount().compareTo(BigDecimal.valueOf(0)) < 0;

        if (isBalanceNotEnough)
            throw new Exception("Valor excede o saldo em conta");
        else if (isValueInvalid)
            throw new Exception("Valor inválido");

        create(originAccount.getId(), TransactionType.TRANSFER_MADE, value);
        create(destAccount.getId(), TransactionType.TRANSFER_RECEIVED, value);
    }

    public Money getBalance(Long accountId) throws SQLException {
        return transactionRepository.getAccountBalance(accountId);
    }

}
