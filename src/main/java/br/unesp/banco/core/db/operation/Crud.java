package br.unesp.banco.core.db.operation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Crud<K, T> {

    List<T> findAll() throws SQLException;
    Optional<T> findById(K id) throws SQLException;
    Optional<T> findByAnd(Map<String, Object> columnValue) throws SQLException;
    T create(T input) throws SQLException;

}
