package br.unesp.banco.core.db.init;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

class Credential {

    private String host;
    private String user;
    private String password;
    private String database;

    protected Credential() {
        loadValues();
    }

    private void loadValues() {
        Yaml yaml = new Yaml();
        InputStream inputStream;
        try {
            inputStream = new FileInputStream("database.yaml");
            Map<String, Object> configuration = yaml.load(inputStream);

            this.host = configuration.get("host").toString();
            this.user = configuration.get("user").toString();
            this.password = configuration.get("password").toString();
            this.database = configuration.get("database").toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected String getHost() {
        return host;
    }

    protected String getUser() {
        return user;
    }

    protected String getPassword() {
        return password;
    }

    protected String getDatabase() {
        return database;
    }

    @Override
    public String toString() {
        return "Credential{" +
                "host='" + host + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", database='" + database + '\'' +
                '}';
    }
}
