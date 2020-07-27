package br.unesp.banco.core.db.init.migration;

import br.unesp.banco.core.util.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Migration {

    private Integer last;
    private Map<String, Object> migration;

    public Migration() {
        try {
            getVersion();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        loadValues();
    }

    private void getVersion() throws IOException {
        Yaml yaml = new Yaml();
        Map<String, Integer> data;
        try {
            InputStream inputStream = new FileInputStream(".migration_version.yaml");
            data = yaml.load(inputStream);
            last = data.get("version");
        } catch (FileNotFoundException e) {
            File file = new File(".migration_version.yaml");
            file.createNewFile();

            FileWriter writer = new FileWriter(file);
            data = new HashMap<>();
            data.put("version", 0);
            yaml.dump(data, writer);

            last = 0;
            Logger.logDb("Migration file not found. Creating new one.");
            writer.close();
        }
    }

    private void updateVersion(Integer version) throws IOException {
        Yaml yaml = new Yaml();
        Map<String, Integer> data = new HashMap<>();
        last = version;
        data.put("version", version);

        FileWriter writer = new FileWriter(".migration_version.yaml");
        yaml.dump(data, writer);
        writer.close();

        Logger.logDb("Migration version updated to version " + last);
    }

    private void loadValues() {
        Yaml yaml = new Yaml();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("migrations.yaml");
            migration = yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void migrate(Connection connection) throws SQLException {
        Map<Integer, List<String>> migrations = (Map<Integer, List<String>>) migration.get("migrations");
        List<String> queries = new ArrayList<>();

        Integer version = last;

        for (int i = last; i < migrations.size(); i++) {
            queries.addAll(migrations.get(i));
            version++;
        }

        if (last == migrations.size()) {
            Logger.logDb("No migration executed");
            return;
        }

        Logger.logDb("Executing migrations...");
        for (String query : queries) {
            connection.prepareStatement(query).execute();
            Logger.logDb(query);
        }
        Logger.logDb("Migrations executed");

        try {
            updateVersion(version);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
