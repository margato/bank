package br.unesp.banco;

import br.unesp.banco.core.db.init.DatabaseInitializer;
import br.unesp.banco.core.db.init.MySQL;
import br.unesp.banco.system.account.Account;
import br.unesp.banco.system.account.AccountMapper;
import br.unesp.banco.system.account.AccountRepository;
import br.unesp.banco.ui.BankApplication;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {

        MySQL mysql = new MySQL();
        DatabaseInitializer.executeMigrations(mysql);

        BankApplication bankApplication = new BankApplication();
        bankApplication.run();

        AccountRepository accountRepository = new AccountRepository(mysql.getConnection(), new AccountMapper());
        accountRepository.create(new Account("1236969", "123"));

        List<Account> all = accountRepository.findAll();
        System.out.println(all);
        System.out.println(accountRepository.findById(1L));

    }

}
