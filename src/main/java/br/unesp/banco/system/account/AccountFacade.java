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

    public Account login(String number, String password) throws Exception {
        if (number.isEmpty() || password.isEmpty())
            throw new Exception("Número da conta ou senha inválida");

        Map<String, Object> params = new HashMap<>();
        params.put("number", number);
        params.put("password", password);


        Optional<Account> account = accountRepository.findByAnd(params);

        if (!account.isPresent())
            throw new Exception("Credenciais inválidas");

        System.out.println("Logado!");
        return account.get();
    }

    public Account createAccount(String number, String password) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("number", number);

        Boolean accountAlreadyExists = accountRepository.findByAnd(params).isPresent();
        if (accountAlreadyExists) {
            throw new Exception("Número de conta já existe");
        }

        if (number.isEmpty() || number.length() > 8) {
            throw new Exception("Número inválido");
        }
        if (password.isEmpty()) {
            throw new Exception("Senha inválida");
        }

        return accountRepository.create(new Account(number, password));
    }

}
