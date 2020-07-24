package br.unesp.banco.core.db.operation;

import java.sql.SQLException;
import java.util.List;

public interface Crud<K, T> {

    List<T> findAll() throws SQLException;
    T findById(K id) throws SQLException;
    T create(T input) throws SQLException;

}
