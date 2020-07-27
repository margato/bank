package br.unesp.banco.system.account;

import br.unesp.banco.core.db.operation.Entity;
import br.unesp.banco.core.db.operation.QueryMapper;
import br.unesp.banco.core.db.operation.Repository;

import java.sql.Connection;

public class AccountRepository extends Repository<Long, Account> {

    public AccountRepository(Connection connection, QueryMapper<Account> mapper) {
        super(connection, Account.class.getAnnotation(Entity.class).table(), mapper);
    }



}
