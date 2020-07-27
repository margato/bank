package br.unesp.banco;

import br.unesp.banco.core.db.init.MySQL;
import br.unesp.banco.core.db.init.migration.DatabaseInitializer;
import br.unesp.banco.system.account.AccountFacade;
import br.unesp.banco.system.account.AccountMapper;
import br.unesp.banco.system.account.AccountRepository;
import br.unesp.banco.ui.BankApplication;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws SQLException {
        MySQL mysql = new MySQL();
        DatabaseInitializer.execute(mysql);

        AccountRepository accountRepository = new AccountRepository(mysql.getConnection(), new AccountMapper());
        AccountFacade accountFacade = new AccountFacade(accountRepository);

        Map<String, Object> facades = new HashMap<String, Object>() {{
            put("account", accountFacade);
        }};

        BankApplication bankApplication = new BankApplication(facades);
        bankApplication.run();
    }

}
