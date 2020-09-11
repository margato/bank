package br.unesp.banco.screens.statement;

import br.unesp.banco.Main;
import br.unesp.banco.app.account.Account;
import br.unesp.banco.app.account.AccountFacade;
import br.unesp.banco.app.primitives.money.Money;
import br.unesp.banco.app.primitives.statement.StatementFacade;
import br.unesp.banco.app.primitives.statement.StatementRow;
import br.unesp.banco.app.transaction.TransactionFacade;
import br.unesp.banco.app.transaction.TransactionType;
import br.unesp.banco.core.ui.JFrameLoader;
import br.unesp.banco.core.ui.JFrameManager;
import br.unesp.banco.core.ui.JTablePrinter;
import br.unesp.banco.core.ui.Screen;
import br.unesp.banco.screens.main.MainAccountScreen;
import br.unesp.banco.screens.openaccount.OpenAccountScreen;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;

import static javax.swing.BorderFactory.createEmptyBorder;

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

        String balanceValue = transactionFacade.getBalance(account.getId()).toString();
        balance.setText(balanceValue);
        backButton.addActionListener(e -> JFrameLoader.load(getFrameManager(), MainAccountScreen.class, MainAccountScreen.WIDTH, MainAccountScreen.HEIGHT, "Banco"));
        String header = String.format("Saldo: %s", balanceValue);
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

        statementTable.setBorder(createEmptyBorder());
        scrollPane.setBorder(new EmptyBorder(20, 0, 0, 0));

        balance.setBorder(new EmptyBorder(0, 0, 0, 20));
        backButton.setText("Voltar");

        statementTable.setFocusable(false);
        statementTable.setRowSelectionAllowed(false);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        Font ibmFont = new Font(backButton.getFont().getName(), Font.PLAIN, 16);
        Font ibmFontBold = new Font(backButton.getFont().getName(), Font.BOLD, 16);

        statementTable.setFont(ibmFont);

        renderer.setFont(ibmFontBold);
        renderer.setBackground(Color.WHITE);
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(4, 19, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.setBackground(new Color(-1));
        mainPanel.setEnabled(true);
        mainPanel.setFocusable(true);
        Font mainPanelFont = this.$$$getFont$$$("IBM Plex Sans", -1, 18, mainPanel.getFont());
        if (mainPanelFont != null) mainPanel.setFont(mainPanelFont);
        mainPanel.setForeground(new Color(-16777216));
        scrollPane = new JScrollPane();
        scrollPane.setBackground(new Color(-1));
        scrollPane.setFocusable(false);
        Font scrollPaneFont = this.$$$getFont$$$("IBM Plex Sans", -1, 18, scrollPane.getFont());
        if (scrollPaneFont != null) scrollPane.setFont(scrollPaneFont);
        scrollPane.setForeground(new Color(-1));
        scrollPane.setVerticalScrollBarPolicy(20);
        mainPanel.add(scrollPane, new GridConstraints(3, 0, 1, 19, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        statementTable = new JTable();
        statementTable.setAutoCreateRowSorter(false);
        statementTable.setBackground(new Color(-1));
        statementTable.setDoubleBuffered(false);
        statementTable.setFillsViewportHeight(true);
        Font statementTableFont = this.$$$getFont$$$("IBM Plex Sans", -1, 16, statementTable.getFont());
        if (statementTableFont != null) statementTable.setFont(statementTableFont);
        statementTable.setForeground(new Color(-16777216));
        statementTable.setGridColor(new Color(-1315088));
        statementTable.setRequestFocusEnabled(false);
        statementTable.setRowHeight(40);
        statementTable.setRowMargin(10);
        statementTable.setRowSelectionAllowed(false);
        statementTable.setShowHorizontalLines(true);
        statementTable.setShowVerticalLines(false);
        statementTable.setSurrendersFocusOnKeystroke(false);
        statementTable.setUpdateSelectionOnSort(false);
        statementTable.setVerifyInputWhenFocusTarget(false);
        scrollPane.setViewportView(statementTable);
        backButton = new JButton();
        backButton.setBackground(new Color(-16777216));
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        Font backButtonFont = this.$$$getFont$$$("IBM Plex Sans", Font.BOLD, 16, backButton.getFont());
        if (backButtonFont != null) backButton.setFont(backButtonFont);
        backButton.setForeground(new Color(-1));
        backButton.setHideActionText(false);
        backButton.setHorizontalAlignment(0);
        backButton.setMargin(new Insets(5, 10, 5, 10));
        backButton.setText("Voltar");
        mainPanel.add(backButton, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        mainPanel.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        balance = new JLabel();
        Font balanceFont = this.$$$getFont$$$("IBM Plex Sans", Font.PLAIN, 20, balance.getFont());
        if (balanceFont != null) balance.setFont(balanceFont);
        balance.setForeground(new Color(-15592942));
        balance.setHorizontalTextPosition(2);
        balance.setText("Label");
        mainPanel.add(balance, new GridConstraints(0, 18, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        balanceLabel = new JLabel();
        Font balanceLabelFont = this.$$$getFont$$$("IBM Plex Sans", Font.BOLD, 18, balanceLabel.getFont());
        if (balanceLabelFont != null) balanceLabel.setFont(balanceLabelFont);
        balanceLabel.setForeground(new Color(-15592942));
        balanceLabel.setHorizontalTextPosition(4);
        balanceLabel.setText("Saldo: ");
        mainPanel.add(balanceLabel, new GridConstraints(0, 17, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        mainPanel.add(spacer2, new GridConstraints(1, 0, 1, 19, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        mainPanel.add(spacer3, new GridConstraints(2, 0, 1, 19, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(-1, 30), null, 0, false));
        print = new JButton();
        print.setBackground(new Color(-14452481));
        print.setBorderPainted(false);
        print.setFocusPainted(false);
        Font printFont = this.$$$getFont$$$("IBM Plex Sans", Font.BOLD, 16, print.getFont());
        if (printFont != null) print.setFont(printFont);
        print.setForeground(new Color(-1));
        print.setHideActionText(false);
        print.setHorizontalAlignment(0);
        print.setMargin(new Insets(5, 10, 5, 10));
        print.setText("Gerar PDF");
        mainPanel.add(print, new GridConstraints(0, 16, 1, 1, GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
