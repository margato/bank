package br.unesp.banco.app.primitives.statement;

import br.unesp.banco.app.transaction.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class StatementFacade {

    public List<StatementRow> generateStatement(List<Transaction> transactions) {
        return transactions.stream()
                           .map(this::toStatementRow)
                           .collect(Collectors.toList());
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
