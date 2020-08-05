package br.unesp.banco.system.transaction;

import br.unesp.banco.core.db.operation.QueryMapper;
import br.unesp.banco.core.db.operation.Repository;
import br.unesp.banco.core.db.stereotype.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TransactionRepository extends Repository<Long, Transaction> {

    public TransactionRepository(Connection connection, QueryMapper<Transaction> mapper) {
        super(connection, Transaction.class.getAnnotation(Entity.class).table(), mapper);
    }

    public List<Transaction> findAllInTheLastNDays(Integer days, Long accountId) throws SQLException {
        String query = String.format("SELECT * FROM %s WHERE DATE(date) >= (NOW() - INTERVAL %d DAY) AND account_id=%d ORDER BY date DESC",
                                     Transaction.class.getAnnotation(Entity.class).table(),
                                     days,
                                     accountId
        );

        return this.findAllByQuery(query);
    }

    public List<Transaction> findAllOrderDesc(Long accountId) throws SQLException {
        String query = String.format("SELECT * FROM %s WHERE account_id=%d ORDER BY date DESC",
                                     Transaction.class.getAnnotation(Entity.class).table(),
                                     accountId
        );
        return this.findAllByQuery(query);
    }
}
