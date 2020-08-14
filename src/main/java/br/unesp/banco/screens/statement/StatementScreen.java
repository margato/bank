package br.unesp.banco.screens.statement;

import br.unesp.banco.app.transaction.TransactionRepository;
import br.unesp.banco.core.ui.JFrameLoader;
import br.unesp.banco.core.ui.JFrameManager;
import br.unesp.banco.core.ui.JTablePrinter;
import br.unesp.banco.core.ui.Screen;
import br.unesp.banco.app.account.Account;
import br.unesp.banco.app.account.AccountFacade;
import br.unesp.banco.app.primitives.statement.StatementFacade;
import br.unesp.banco.app.primitives.statement.StatementRow;
import br.unesp.banco.app.transaction.TransactionFacade;
import br.unesp.banco.app.transaction.TransactionType;
import br.unesp.banco.screens.main.MainAccountScreen;

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
    private JButton print;
    private JScrollPane scrollPane;
    private TableModel model;

    public StatementScreen(JFrameManager frameManager) throws Exception {
        super(frameManager);

        AccountFacade accountFacade = (AccountFacade) getFrameManager().getFacades().get("account");
        TransactionFacade transactionFacade = (TransactionFacade) getFrameManager().getFacades().get("transaction");
        StatementFacade statementFacade = (StatementFacade) getFrameManager().getFacades().get("statement");


        Account account = accountFacade.getAccount(frameManager.getUserCredentials().getId());
        List<StatementRow> statementRows = statementFacade.generateStatement(transactionFacade.getAllByAccountId(account.getId()));

        String[] columnNames = {"Data", "Tipo", "Valor"};
        Object[][] data = statementFacade.convertToPureObject(statementRows);

        model = new DefaultTableModel(data, columnNames);
        statementTable.setModel(model);

//        balance.setText(account.getBalance().toString());
        backButton.addActionListener(e -> JFrameLoader.load(getFrameManager(), MainAccountScreen.class, "Banco"));

        String header = String.format("Saldo: %s", transactionFacade.getBalance(account.getId()));
        List<StatementRow> statementRows10Days = statementFacade.generateStatement(transactionFacade.getInTheLastTenDays(account.getId()));
        JTable printableTable = new JTable(statementFacade.convertToPureObject(statementRows10Days), columnNames);
        print.addActionListener(e -> JTablePrinter.print(header, printableTable));
    }

    @Override
    public void addStyle() {
        int dateColumn = 0;
        int typeColumn = 1;
        int valueColumn = 2;

        for (int i = 0; i < statementTable.getRowCount(); i++) {
            TransactionType type = TransactionType.getByName(String.valueOf(statementTable.getValueAt(i, typeColumn)));

            Object value = statementTable.getValueAt(i, valueColumn);

            statementTable.setValueAt(type.getSignal() + value, i, valueColumn);
        }

        balance.setBorder(new EmptyBorder(0, 0, 0, 20));
        backButton.setText("< Voltar");

        statementTable.setFocusable(false);
        statementTable.setRowSelectionAllowed(false);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();

        renderer.setFont(statementTable.getFont());
        renderer.setBackground(Color.LIGHT_GRAY);
        renderer.setForeground(Color.BLACK);
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer rightAlignment = new DefaultTableCellRenderer();
        rightAlignment.setHorizontalAlignment(SwingConstants.RIGHT);

        DefaultTableCellRenderer centerAlignment = new DefaultTableCellRenderer();
        centerAlignment.setHorizontalAlignment(SwingConstants.CENTER);

        statementTable.getTableHeader().setDefaultRenderer(renderer);
        statementTable.getTableHeader().getColumnModel().getColumn(dateColumn).setCellRenderer(centerAlignment);
        statementTable.getTableHeader().getColumnModel().getColumn(valueColumn).setCellRenderer(rightAlignment);
        statementTable.getColumnModel().getColumn(valueColumn).setCellRenderer(centerAlignment);

        statementTable.setIntercellSpacing(new Dimension(50, 20));
        statementTable.setDragEnabled(false);

    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

}
