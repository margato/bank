package br.unesp.banco.app.transaction;

import br.unesp.banco.core.db.operation.QueryMapper;
import br.unesp.banco.app.primitives.money.Currency;
import br.unesp.banco.app.primitives.money.Money;
import br.unesp.banco.core.db.stereotype.Entity;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneOffset;

public class TransactionMapper implements QueryMapper<Transaction> {
    @Override
    public Transaction toEntity(ResultSet rs) throws SQLException {
        Transaction transaction = new Transaction();

        transaction.setId(rs.getLong("id"));
        transaction.setValue(new Money(rs.getDouble("value"), Currency.REAL));
        transaction.setType(TransactionType.valueOf(rs.getString("type")));
        transaction.setDate(rs.getTimestamp("date").toLocalDateTime());
        transaction.setAccountId(rs.getLong("account_id"));

        return transaction;
    }

    @Override
    public String toQuery(Transaction transaction) {

        Date date = Date.valueOf(transaction.getDate().toLocalDate());


        return String.format("INSERT INTO %s (    value, type, date, account_id) VALUES (%s, '%s',  '%s', %d)",
                Transaction.class.getAnnotation(Entity.class).table(), transaction.getValue().getAmount().toString(),
                transaction.getType(), date,transaction.getAccountId());
    }
}
