package br.unesp.banco.system.account;

import br.unesp.banco.core.db.operation.QueryMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper implements QueryMapper<Account> {
    @Override
    public Account toEntity(ResultSet rs) throws SQLException {
        Account account = new Account();

        account.setId(rs.getLong("id"));
        account.setNumber(rs.getString("number"));
        account.setPassword("------");

        return account;
    }

    @Override
    public String toQuery(Account account) {
        return String.format("INSERT INTO %s (number, password) VALUES ('%s', '%s')", Account.getTableName(), account.getNumber(), account.getPassword());
    }
}
