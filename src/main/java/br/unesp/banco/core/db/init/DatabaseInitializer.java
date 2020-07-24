package br.unesp.banco.core.db.init;

import br.unesp.banco.core.util.Logger;

import java.sql.SQLException;

public class DatabaseInitializer {

    public static void executeMigrations(MySQL mySQL) throws SQLException {
        for (String query : Migration.getQueries()) {
            Logger.logDb(query);
            mySQL.getConnection().prepareStatement(query).execute();
        }
    }

}
