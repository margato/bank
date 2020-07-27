package br.unesp.banco.system.account;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AccountFacade {

    private final AccountRepository accountRepository;

    public AccountFacade(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account login(String number, String password) throws SQLException {
        if (number.isEmpty()) {
            System.out.println("Numero invalido");
            return null;
        }
        if (password.isEmpty()) {
            System.out.println("senha invalida");
            return null;
        }


        Map<String, Object> params = new HashMap<>();
        params.put("number", number);
        params.put("password", password);


        Optional<Account> account = accountRepository.findByAnd(params);

        if (!account.isPresent()) {
            System.out.println("Número da conta ou senha inválida");
            return null;
        }

        System.out.println("Logado!");
        return account.get();
    }

    public Account createAccount(String number, String password) throws SQLException {
        Map<String, Object> params = new HashMap<>();
        params.put("number", number);

        Boolean accountAlreadyExists = accountRepository.findByAnd(params).isPresent();
        if (accountAlreadyExists) {
            System.out.println("ja existe");
            return null;
        }

        if (number.isEmpty() || number.length() > 8) {
            System.out.println("Numero invalido");
            return null;
        }
        if (password.isEmpty()) {
            System.out.println("senha invalida");
            return null;
        }

        return accountRepository.create(new Account(number, password));
    }

}
