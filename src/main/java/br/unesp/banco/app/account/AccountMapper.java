package br.unesp.banco.app.account;

import br.unesp.banco.core.db.operation.QueryMapper;
import br.unesp.banco.core.db.stereotype.Entity;
import br.unesp.banco.app.primitives.money.Currency;
import br.unesp.banco.app.primitives.money.Money;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements QueryMapper<Account> {
    @Override
    public Account toEntity(ResultSet rs) throws SQLException {
        Account account = new Account();

        account.setId(rs.getLong("id"));
        account.setNumber(rs.getString("number"));
        account.setPassword("protected");
        account.setBalance(new Money(rs.getDouble("balance"), Currency.REAL));

        return account;
    }

    @Override
    public String toQuery(Account account) {
        return String.format("INSERT INTO %s (number, password, balance) VALUES ('%s', '%s', %s)",
                Account.class.getAnnotation(Entity.class).table(),
                account.getNumber(),
                account.getPassword(),
                account.getBalance().getAmount().toString()
        );
    }
}
