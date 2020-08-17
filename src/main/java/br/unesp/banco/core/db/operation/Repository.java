package br.unesp.banco.core.db.operation;

import br.unesp.banco.core.log.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class Repository<K, T> implements Crud<K, T> {

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
    public Optional<T> findById(K id) throws SQLException {
        String query = String.format("SELECT * FROM %s WHERE id = " + id, tableName);

        ResultSet resultSet = getResult(query);
        Boolean notExists = !resultSet.next();

        if (notExists) {
            System.out.printf("Entity not found. Table: %s, id: " + id + "%n", tableName);
            return Optional.empty();
        }

        return Optional.of(mapper.toEntity(resultSet));
    }

    @Override
    public Optional<T> findByAnd(Map<String, Object> columnValue) throws SQLException {
        ResultSet resultSet = applyAnd(columnValue);

        Boolean notExists = !resultSet.next();


        if (notExists) {
            Logger.logDb("Entity not found.");
            return Optional.empty();
        }

        return Optional.of(mapper.toEntity(resultSet));
    }

    @Override
    public List<T> findAllByAnd(Map<String, Object> columnValue) throws SQLException {
        ResultSet resultSet = applyAnd(columnValue);

        List<T> resultArray = new ArrayList<>();
        while (resultSet.next()) {
            resultArray.add(mapper.toEntity(resultSet));
        }

        return resultArray;
    }

    private ResultSet applyAnd(Map<String, Object> columnValue) throws SQLException {
        StringBuilder sb = new StringBuilder(String.format("SELECT * FROM %s WHERE", tableName));

        columnValue.forEach((key, value) -> sb.append(String.format(" %s = '" + value + "' AND", key)));

        String query = sb.toString().substring(0, sb.length() - 3);

        return getResult(query);
    }


    protected ResultSet getResult(String query) throws SQLException {
        Logger.logDb(query);
        ResultSet resultSet = connection.prepareStatement(query).executeQuery();
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
                return findById(key).get();
            }
        }

        return null;
    }

    @Override
    public List<T> findAllByQuery(String query) throws SQLException {
        ResultSet resultSet = getResult(query);

        List<T> resultArray = new ArrayList<>();
        while (resultSet.next()) {
            resultArray.add(mapper.toEntity(resultSet));
        }

        return resultArray;
    }

}
