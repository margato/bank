package br.unesp.banco.core.db.operation;

public interface Entity<K> {
    static String getTableName() {
        return null;
    }
}
