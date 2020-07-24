package br.unesp.banco.core.db.operation;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface QueryMapper<T> {
    T toEntity(ResultSet rs) throws SQLException;
    String toQuery(T t);
}
