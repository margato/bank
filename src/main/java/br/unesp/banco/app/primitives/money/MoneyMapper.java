package br.unesp.banco.app.primitives.money;

import br.unesp.banco.core.db.operation.QueryMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MoneyMapper implements QueryMapper<Money> {
    @Override
    public Money toEntity(ResultSet rs) throws SQLException {
        Double credito = rs.getDouble("credito");
        Double debito = rs.getDouble("debito");
        Double amount = credito + debito;

        Money money = new Money(amount, Currency.REAL);
        return money;
    }

    @Override
    public String toQuery(Money money) {
        return null;
    }
}
