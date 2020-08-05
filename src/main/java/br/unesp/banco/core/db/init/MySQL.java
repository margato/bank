package br.unesp.banco.core.db.init;

import br.unesp.banco.core.log.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private Connection connection;

    public MySQL() {
        Credential credential = new Credential();
        String baseUrl = String.format("jdbc:mysql://%s", credential.getHost());
        String url = String.format("%s?serverTimezone=UTC", baseUrl);

        try {
            connection = DriverManager.getConnection(url, credential.getUser(), credential.getPassword());

            String createDatabaseIfNotExists = String.format("CREATE DATABASE IF NOT EXISTS %s", credential.getDatabase());

            connection.prepareStatement(createDatabaseIfNotExists).execute();

            baseUrl += "/" + credential.getDatabase() + "?serverTimezone=UTC";
            connection = DriverManager.getConnection(baseUrl, credential.getUser(), credential.getPassword());
            Logger.logDb(url);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public String toString() {
        return "MySQL{" +
                "connection=" + connection +
                '}';
    }
}
