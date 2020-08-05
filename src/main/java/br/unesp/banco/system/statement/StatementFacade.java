package br.unesp.banco.system.statement;

import br.unesp.banco.system.transaction.Transaction;
import br.unesp.banco.system.transaction.TransactionFacade;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class StatementFacade {

    private final TransactionFacade transactionFacade;

    public StatementFacade(TransactionFacade transactionFacade) {
        this.transactionFacade = transactionFacade;
    }

    public List<StatementRow> generateStatement(Long accountId) throws SQLException {
        List<Transaction> transactions = transactionFacade.getAllByAccountId(accountId);

        List<StatementRow> statementRows = transactions.stream()
                                                       .map(this::toStatementRow)
                                                       .collect(Collectors.toList());


        return statementRows;
    }

    private StatementRow toStatementRow(Transaction t) {
        StatementRow s = new StatementRow();

        s.setDate(t.getDate());
        s.setType(t.getType());
        s.setValue(t.getValue());

        return s;
    }

    public Object[][] convertToPureObject(List<StatementRow> statementRows) {
        Object[][] pureStatement = new Object[statementRows.size()][];
        for (int i = 0; i < statementRows.size(); i++) {
            pureStatement[i] = new Object[]{
                    statementRows.get(i).getDate(),
                    statementRows.get(i).getType(),
                    statementRows.get(i).getValue(),
            };
        }
        return pureStatement;
    }


}
