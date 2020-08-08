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

    public Transaction op(Long accountId, TransactionType type, Money value) throws SQLException {
        String tst;
        tst = type.getSignal();
        if (tst.equals("-"))
            value.setNegative();
        return transactionRepository.create(new Transaction(value, type, LocalDateTime.now(), accountId));

    }
    public Transaction sacar(Long accountId, Money value) throws SQLException {
        return  op(accountId, TransactionType.WITHDRAW, value);
    }

    public Money getBalance(Long accountId) throws SQLException {
        return  transactionRepository.getAccountBalance(accountId);


    }

//    public Transaction login(String number, String password) throws Exception {
//        if (number.isEmpty() || password.isEmpty())
//            throw new Exception("Número da conta ou senha inválida");
//
//        Map<String, Object> params = new HashMap<>();
//        params.put("number", number);
//        params.put("password", password);
//
//        Optional<Transaction> account = transactionRepository.findByAnd(params);
//
//        if (!account.isPresent())
//            throw new Exception("Credenciais inválidas");
//
//        Logger.log("Login", "Logado!");
//        return account.get();
//    }
//
//    public Transaction createAccount(String number, String password) throws Exception {
//        Map<String, Object> params = new HashMap<>();
//        params.put("number", number);
//
//        Boolean accountAlreadyExists = transactionRepository.findByAnd(params).isPresent();
//        if (accountAlreadyExists) {
//            throw new Exception("Número de conta já existe");
//        }
//
//        if (number.isEmpty() || number.length() > 8) {
//            throw new Exception("Número inválido");
//        }
//        if (password.isEmpty()) {
//            throw new Exception("Senha inválida");
//        }
//
//        return transactionRepository.create(new Transaction(number, password));
//    }

}
