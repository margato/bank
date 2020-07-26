package br.unesp.banco.core.db.operation;

import br.unesp.banco.core.util.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository<K, T> implements Crud<K, T> {

    private final Connection connection;
    private final String tableName;
    private final QueryMapper<T> mapper;

    public Repository(Connection connection, String tableName, QueryMapper<T> mapper) {
        this.connection = connection;
        this.tableName = tableName;
        this.mapper = mapper;
    }

    @Override
    public List<T> findAll() throws SQLException {
        String query = String.format("SELECT * FROM %s", tableName);

        ResultSet resultSet = getResult(query);

        List<T> resultArray = new ArrayList<>();
        while (resultSet.next()) {
            resultArray.add(mapper.toEntity(resultSet));
        }

        return resultArray;
    }

    @Override
    public T findById(K id) throws SQLException {
        String query = String.format("SELECT * FROM %s WHERE id = %d", tableName, id);

        ResultSet resultSet = getResult(query);
        Boolean notExists = !resultSet.next();

        if (notExists) {
            System.out.println("nao encontrado"); // TODO add exception
        }

        return mapper.toEntity(resultSet);
    }

    private ResultSet getResult(String query) throws SQLException {
        ResultSet resultSet = connection.prepareStatement(query).executeQuery();
        Logger.logDb(query);
        return resultSet;
    }

    @Override
    public T create(T input) throws SQLException {
        String query = mapper.toQuery(input);
        Logger.logDb(query);
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.executeUpdate();

        try (ResultSet result = preparedStatement.getGeneratedKeys()) {
            if (result.next()) {
                K key = (K) result.getObject(1);
                return findById(key);
            }
        }

        return null;
    }

}
