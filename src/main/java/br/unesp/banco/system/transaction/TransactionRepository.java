package br.unesp.banco.system.transaction;

import br.unesp.banco.core.db.operation.Entity;
import br.unesp.banco.core.db.operation.QueryMapper;
import br.unesp.banco.core.db.operation.Repository;

import java.sql.Connection;

public class TransactionRepository extends Repository<Long, Transaction> {

    public TransactionRepository(Connection connection, QueryMapper<Transaction> mapper) {
        super(connection, Transaction.class.getAnnotation(Entity.class).table(), mapper);
    }

}
