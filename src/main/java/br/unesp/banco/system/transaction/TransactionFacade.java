package br.unesp.banco.system.transaction;


import java.sql.SQLException;
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
