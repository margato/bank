package br.unesp.banco;

import br.unesp.banco.core.db.init.MySQL;
import br.unesp.banco.core.db.init.migration.DatabaseInitializer;
import br.unesp.banco.ui.BankApplication;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        MySQL mysql = new MySQL();
        DatabaseInitializer.execute(mysql);

        BankApplication bankApplication = new BankApplication();
        bankApplication.run();
    }

}
