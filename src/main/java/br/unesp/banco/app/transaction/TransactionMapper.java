package br.unesp.banco.app.transaction;

import br.unesp.banco.app.primitives.money.Currency;
import br.unesp.banco.app.primitives.money.Money;
import br.unesp.banco.core.db.operation.QueryMapper;
import br.unesp.banco.core.db.stereotype.Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TransactionMapper implements QueryMapper<Transaction> {
    @Override
    public Transaction toEntity(ResultSet rs) throws SQLException {
        Transaction transaction = new Transaction();

        transaction.setId(rs.getLong("id"));
        transaction.setValue(new Money(rs.getDouble("value"), Currency.REAL));
        transaction.setType(TransactionType.valueOf(rs.getString("type")));

        int utc = 3;
        LocalDateTime date = rs.getTimestamp("date").toLocalDateTime().plusHours(utc);
        transaction.setDate(date);

        transaction.setAccountId(rs.getLong("account_id"));

        return transaction;
    }

    @Override
    public String toQuery(Transaction transaction) {

        return String.format("INSERT INTO %s (value, type, date, account_id) VALUES (%s, '%s', '%s', %d)",
                Transaction.class.getAnnotation(Entity.class).table(),
                transaction.getValue().getAmount().toString(),
                transaction.getType(),
                transaction.getDate().toString(),
                transaction.getAccountId());
    }
}
