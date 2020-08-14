package br.unesp.banco.app.transaction;

import br.unesp.banco.app.primitives.money.Money;
import br.unesp.banco.app.primitives.money.MoneyMapper;
import br.unesp.banco.core.db.operation.QueryMapper;
import br.unesp.banco.core.db.operation.Repository;
import br.unesp.banco.core.db.stereotype.Entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TransactionRepository extends Repository<Long, Transaction> {
    MoneyMapper moneyMapper;

    public TransactionRepository(Connection connection, QueryMapper<Transaction> mapper) {
        super(connection, Transaction.class.getAnnotation(Entity.class).table(), mapper);
        this.moneyMapper = new MoneyMapper();
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

    public Money getAccountBalance( Long accountId) throws SQLException{
        String query = String.format(
                "select accounts.id as 'conta_id',\n" +
                "       sum(case when transactions.account_id = accounts.id and (transactions.type = 'DEPOSIT' or  transactions.type = 'TRANSFER_RECEIVED')\n" +
                "          then transactions.value\n" +
                "          else 0 end) as 'credito',\n" +
                "       sum(case when transactions.account_id = accounts.id and (transactions.type = 'WITHDRAW' or  transactions.type = 'TRANSFER_MADE')\n" +
                "          then transactions.value\n" +
                "          else 0 end) as 'debito'\n" +
                "   from accounts\n" +
                "   inner join transactions  on transactions.account_id = accounts.id  where accounts.id = %s",accountId);
        ResultSet rs = this.getResult(query);
        rs.next();
        return  moneyMapper.toEntity(rs);

    }
}
