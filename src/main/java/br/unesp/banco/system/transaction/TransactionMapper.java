package br.unesp.banco.system.transaction;

import br.unesp.banco.core.db.operation.QueryMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionMapper implements QueryMapper<Transaction> {
    @Override
    public Transaction toEntity(ResultSet rs) throws SQLException {
        Transaction transaction = new Transaction();

        transaction.setId(rs.getLong("id"));
//        transaction.setValue(rs.getDouble("value"));
        transaction.setTypeOp(TransactionType.valueOf(rs.getString("transaction_type")));

        transaction.setAccountId(rs.getInt("account_id"));

        return transaction;
    }

    @Override
    public String toQuery(Transaction transaction) {
        return "";
//        return String.format("INSERT INTO %s (number, password) VALUES ('%s', '%s')", Transaction.class.getAnnotation(Entity.class).table(), transaction.getNumber(), transaction.getPassword());
    }
}
