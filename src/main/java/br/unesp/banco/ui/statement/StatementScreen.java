package br.unesp.banco.ui.statement;

import br.unesp.banco.core.ui.JFrameLoader;
import br.unesp.banco.core.ui.JFrameManager;
import br.unesp.banco.core.ui.Screen;
import br.unesp.banco.system.account.Account;
import br.unesp.banco.system.account.AccountFacade;
import br.unesp.banco.system.statement.StatementFacade;
import br.unesp.banco.system.statement.StatementRow;
import br.unesp.banco.system.transaction.TransactionType;
import br.unesp.banco.ui.main.MainAccountScreen;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;

public class StatementScreen extends Screen {
    private JPanel mainPanel;
    private JTable statementTable;
    private JLabel balanceLabel;
    private JLabel balance;
    private JButton backButton;
    private TableModel model;

    public StatementScreen(JFrameManager frameManager) throws Exception {
        super(frameManager);

        AccountFacade accountFacade = (AccountFacade) getFrameManager().getFacades().get("account");
        StatementFacade statementFacade = (StatementFacade) getFrameManager().getFacades().get("statement");

        List<StatementRow> statementRows = statementFacade.generateStatement(getFrameManager().getUserCredentials().getId());

        String[] columnNames = {"Data", "Tipo", "Valor"};
        Object[][] data = statementFacade.convertToPureObject(statementRows);

        model = new DefaultTableModel(data, columnNames);
        statementTable.setModel(model);

        Account account = accountFacade.getAccount(frameManager.getUserCredentials().getId());

        balance.setText(account.getBalance().toString());

        backButton.addActionListener(e -> JFrameLoader.load(getFrameManager(), MainAccountScreen.class, "Banco"));
    }

    @Override
    public void addStyle() {
        DefaultTableCellRenderer defaultRenderer = (DefaultTableCellRenderer) statementTable.getTableHeader().getDefaultRenderer();

        defaultRenderer.setHorizontalAlignment(JLabel.LEFT);
        Font font = defaultRenderer.getFont();
        defaultRenderer.setFont(font.deriveFont(18));

        int typeColumn = 1;
        int valueColumn = 2;

        for (int i = 0; i < statementTable.getRowCount(); i++) {
            TransactionType type = TransactionType.getByName(String.valueOf(statementTable.getValueAt(i, typeColumn)));

            Object value = statementTable.getValueAt(i, valueColumn);

            statementTable.setValueAt(type.getSignal() + value, i, valueColumn);
        }

        balance.setBorder(new EmptyBorder(0, 0, 0, 20));
        backButton.setText("< Voltar");
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

}
