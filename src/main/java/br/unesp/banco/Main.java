package br.unesp.banco;

import br.unesp.banco.app.primitives.money.Money;
import br.unesp.banco.core.db.init.MySQL;
import br.unesp.banco.core.db.init.migration.DatabaseInitializer;
import br.unesp.banco.app.account.AccountFacade;
import br.unesp.banco.app.account.AccountMapper;
import br.unesp.banco.app.account.AccountRepository;
import br.unesp.banco.app.primitives.statement.StatementFacade;
import br.unesp.banco.app.transaction.TransactionFacade;
import br.unesp.banco.app.transaction.TransactionMapper;
import br.unesp.banco.app.transaction.TransactionRepository;
import br.unesp.banco.screens.BankApplication;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws SQLException {
        MySQL mysql = new MySQL();
        DatabaseInitializer.execute(mysql);

        AccountRepository accountRepository = new AccountRepository(mysql.getConnection(), new AccountMapper());
        AccountFacade accountFacade = new AccountFacade(accountRepository);

        TransactionRepository transactionRepository = new TransactionRepository(mysql.getConnection(), new TransactionMapper());
        TransactionFacade transactionFacade = new TransactionFacade(transactionRepository);

        StatementFacade statementFacade = new StatementFacade();

        Map<String, Object> facades = new HashMap<String, Object>() {{
            put("account", accountFacade);
            put("transaction", transactionFacade);
            put("statement", statementFacade);
        }};
//        System.out.printf("Saldo da conta %d antes de depositar %.3f\n",3L,transactionFacade.getBalance(3L).getAmount());
//        transactionFacade.makeDeposit(3L,new Money(500));
//        System.out.printf("Saldo da conta %d depois de depositar %.3f\n",3L,transactionFacade.getBalance(3L).getAmount());
//
//        System.out.printf("Saldo da conta %d antes de sacar %.3f\n",3L,transactionFacade.getBalance(3L).getAmount());
//        transactionFacade.withdraw(3L, new Money(400));
//        System.out.printf("Saldo da conta %d depois de sacar %.3f\n",3L,transactionFacade.getBalance(3L).getAmount());
//
//        System.out.printf("Saldo da conta %d antes de transferir %.3f\n",3L,transactionFacade.getBalance(3L).getAmount());
//
//        System.out.printf("Saldo da conta %d antes de transferir %.3f\n",1L,transactionFacade.getBalance(1L).getAmount());
//        transactionFacade.transfer(3L,1L,new Money(200));
//        System.out.printf("Saldo da conta %d depois de transferir %.3f\n",1L,transactionFacade.getBalance(1L).getAmount());
//        System.out.printf("Saldo da conta %d depois de transferir %.3f\n",3L,transactionFacade.getBalance(3L).getAmount());





        BankApplication bankApplication = new BankApplication(facades);
        bankApplication.run();
    }

}
