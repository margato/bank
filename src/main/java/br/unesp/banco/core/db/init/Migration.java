package br.unesp.banco.core.db.init;

import java.util.ArrayList;
import java.util.List;

public class Migration {

    private static final List<String> queries = new ArrayList<String>() {{
        // TODO ler de um yaml
        add("CREATE TABLE IF NOT EXISTS `accounts` (\n" +
                    "\t`id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "\t`number` VARCHAR(8),\n" +
                    "\t`password` VARCHAR(255),\n" +
                    "\tPRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB;");
    }};

    public static List<String> getQueries() {
        return queries;
    }

}
