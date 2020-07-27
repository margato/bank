package br.unesp.banco.core.db.init.migration;

import br.unesp.banco.core.db.init.MySQL;

import java.sql.SQLException;

public class DatabaseInitializer {

    public static void execute(MySQL mySQL) throws SQLException {
        Migration migration = new Migration();
        migration.migrate(mySQL.getConnection());
    }

}
