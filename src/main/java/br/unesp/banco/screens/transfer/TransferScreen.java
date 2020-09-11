package br.unesp.banco.screens.transfer;

import br.unesp.banco.app.account.Account;
import br.unesp.banco.app.account.AccountFacade;
import br.unesp.banco.app.primitives.money.Money;
import br.unesp.banco.app.transaction.TransactionFacade;
import br.unesp.banco.core.ui.JFrameLoader;
import br.unesp.banco.core.ui.JFrameManager;
import br.unesp.banco.core.ui.Popup;
import br.unesp.banco.core.ui.Screen;
import br.unesp.banco.core.utils.Debounce;
import br.unesp.banco.screens.main.MainAccountScreen;
import br.unesp.banco.screens.statement.StatementScreen;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class TransferScreen extends Screen {
    private JPanel bodyPanel;
    private JPanel painelBts;
    private JButton backButton;
    private JLabel valueLabel;
    private JButton confirmButton;
    private JTextField valueInput;
    private JLabel errorMessage;
    private JLabel accountLabel;
    private JTextField accountInput;
    private JComboBox suggestions;
    public final static int WIDTH = 700;
    public final static int HEIGHT = 520;
    private final Runnable searchRunnable;
    private Object lastSelected;
    private static final String NO_SELECTED = "Sugestões";

    public TransferScreen(JFrameManager frameManager) {
        super(frameManager);
        AccountFacade accountFacade = (AccountFacade) getFrameManager().getFacades().get("account");

        suggestions.setVisible(false);

        searchRunnable = new Debounce(this::searchForAccounts, 1000);

        suggestions.addItemListener(this::comboBoxChanged);

        this.accountInput.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
                searchForAccountIds();

                if (accountInput.getText().isEmpty()) {
                    suggestions.setVisible(false);
                }
            }
        });
        confirmButton.addActionListener(e -> {
            TransactionFacade transactionFacade = (TransactionFacade) getFrameManager().getFacades().get("transaction");
            try {
                BigDecimal value = new BigDecimal(valueInput.getText().replace(",", "."));

                String origin = frameManager.getUserCredentials().getAccountNumber();
                String dest = accountInput.getText();

                transactionFacade.transfer(origin, dest, new Money(value));
                Popup.show("Saque", "Transferência efetuada!", "Ok", null);

                JFrameLoader.load(getFrameManager(), StatementScreen.class, "Extrato");
            } catch (NumberFormatException ex) {
                errorMessage.setText("Dados inválidos");
                ex.printStackTrace();
            } catch (Exception ex) {
                errorMessage.setText(ex.getMessage());
                ex.printStackTrace();
            }
        });

        backButton.addActionListener(e -> {
            JFrameLoader.load(getFrameManager(), MainAccountScreen.class, 700, 500, "Banco");

        });
    }

    private boolean selected() {
        Object selectedItem = suggestions.getSelectedItem();
        if (selectedItem == NO_SELECTED) return false;
        return selectedItem != lastSelected;
    }

    private void comboBoxChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED && selected()) {
            Object item = e.getItem();
            accountInput.setText(item.toString());
            suggestions.setSelectedItem(NO_SELECTED);
        }
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
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        bodyPanel = new JPanel();
        bodyPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        bodyPanel.setBackground(new Color(-1));
        bodyPanel.setForeground(new Color(-1));
        panel1.add(bodyPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        painelBts = new JPanel();
        painelBts.setLayout(new GridBagLayout());
        painelBts.setBackground(new Color(-1));
        painelBts.setEnabled(true);
        painelBts.setForeground(new Color(-1));
        bodyPanel.add(painelBts, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        painelBts.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        backButton = new JButton();
        backButton.setActionCommand("Saque");
        backButton.setAutoscrolls(false);
        backButton.setBackground(new Color(-16777216));
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        Font backButtonFont = this.$$$getFont$$$("IBM Plex Sans", Font.BOLD, 20, backButton.getFont());
        if (backButtonFont != null) backButton.setFont(backButtonFont);
        backButton.setForeground(new Color(-1));
        backButton.setHorizontalAlignment(0);
        backButton.setMargin(new Insets(20, 20, 20, 20));
        backButton.setText("Voltar");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.ipadx = 220;
        painelBts.add(backButton, gbc);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel2.setAutoscrolls(false);
        panel2.setBackground(new Color(-1));
        panel2.setForeground(new Color(-1));
        bodyPanel.add(panel2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        accountLabel = new JLabel();
        accountLabel.setBackground(new Color(-1));
        accountLabel.setEnabled(true);
        accountLabel.setFocusable(true);
        Font accountLabelFont = this.$$$getFont$$$("IBM Plex Sans", -1, 18, accountLabel.getFont());
        if (accountLabelFont != null) accountLabel.setFont(accountLabelFont);
        accountLabel.setForeground(new Color(-16777216));
        accountLabel.setHorizontalTextPosition(0);
        accountLabel.setText("nº da conta");
        accountLabel.putClientProperty("html.disable", Boolean.FALSE);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(accountLabel, gbc);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.BOTH;
        panel2.add(panel3, gbc);
        final Spacer spacer1 = new Spacer();
        panel3.add(spacer1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        accountInput = new JTextField();
        accountInput.setBackground(new Color(-1));
        accountInput.setCaretColor(new Color(-16777216));
        accountInput.setEditable(true);
        Font accountInputFont = this.$$$getFont$$$("IBM Plex Sans", -1, 18, accountInput.getFont());
        if (accountInputFont != null) accountInput.setFont(accountInputFont);
        accountInput.setForeground(new Color(-16777216));
        accountInput.setMargin(new Insets(2, 6, 2, 6));
        accountInput.setOpaque(true);
        accountInput.setSelectedTextColor(new Color(-15592942));
        accountInput.setSelectionStart(0);
        accountInput.setText("");
        accountInput.putClientProperty("caretWidth", new Integer(2));
        panel3.add(accountInput, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        suggestions = new JComboBox();
        suggestions.setBackground(new Color(-1));
        Font suggestionsFont = this.$$$getFont$$$("IBM Plex Sans", -1, 16, suggestions.getFont());
        if (suggestionsFont != null) suggestions.setFont(suggestionsFont);
        suggestions.setForeground(new Color(-15592942));
        suggestions.setInheritsPopupMenu(false);
        panel3.add(suggestions, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("IBM Plex Sans", -1, 18, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-2555883));
        label1.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(label1, gbc);
        errorMessage = new JLabel();
        Font errorMessageFont = this.$$$getFont$$$("IBM Plex Sans", -1, 18, errorMessage.getFont());
        if (errorMessageFont != null) errorMessage.setFont(errorMessageFont);
        errorMessage.setForeground(new Color(-2555883));
        errorMessage.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(errorMessage, gbc);
        valueLabel = new JLabel();
        valueLabel.setBackground(new Color(-1));
        valueLabel.setEnabled(true);
        valueLabel.setFocusable(true);
        Font valueLabelFont = this.$$$getFont$$$("IBM Plex Sans", -1, 18, valueLabel.getFont());
        if (valueLabelFont != null) valueLabel.setFont(valueLabelFont);
        valueLabel.setForeground(new Color(-16777216));
        valueLabel.setHorizontalTextPosition(0);
        valueLabel.setText("Valor");
        valueLabel.putClientProperty("html.disable", Boolean.FALSE);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        panel2.add(valueLabel, gbc);
        valueInput = new JTextField();
        valueInput.setBackground(new Color(-1));
        valueInput.setCaretColor(new Color(-16777216));
        valueInput.setEditable(true);
        Font valueInputFont = this.$$$getFont$$$("IBM Plex Sans", -1, 18, valueInput.getFont());
        if (valueInputFont != null) valueInput.setFont(valueInputFont);
        valueInput.setForeground(new Color(-16777216));
        valueInput.setMargin(new Insets(2, 6, 2, 6));
        valueInput.setOpaque(true);
        valueInput.setSelectedTextColor(new Color(-15592942));
        valueInput.setSelectionStart(0);
        valueInput.setText("");
        valueInput.putClientProperty("caretWidth", new Integer(2));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel2.add(valueInput, gbc);
        confirmButton = new JButton();
        confirmButton.setActionCommand("Saque");
        confirmButton.setAutoscrolls(false);
        confirmButton.setBackground(new Color(-14452481));
        confirmButton.setBorderPainted(false);
        confirmButton.setFocusPainted(false);
        Font confirmButtonFont = this.$$$getFont$$$("IBM Plex Sans", Font.BOLD, 20, confirmButton.getFont());
        if (confirmButtonFont != null) confirmButton.setFont(confirmButtonFont);
        confirmButton.setForeground(new Color(-1));
        confirmButton.setHorizontalAlignment(0);
        confirmButton.setMargin(new Insets(20, 20, 20, 20));
        confirmButton.setText("Confirmar");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 0, 0);
        panel2.add(confirmButton, gbc);
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

    private class AccountSearch implements Runnable {
        @Override
        public void run() {
            AccountFacade accountFacade = (AccountFacade) getFrameManager().getFacades().get("account");
            String accountNumber = accountInput.getText();

            try {
                List<String> accountsLikeNumber = accountFacade.getAccountsLikeNumber(getFrameManager().getUserCredentials().getAccountNumber(), accountNumber)
                        .stream()
                        .map(Account::getNumber)
                        .collect(Collectors.toList());

                boolean thereAreAccountsLike = accountsLikeNumber.size() > 0;
                suggestions.setVisible(thereAreAccountsLike);

                suggestions.removeAllItems();

                suggestions.addItem(NO_SELECTED);
                accountsLikeNumber.forEach(suggestions::addItem);
                suggestions.setSelectedItem(NO_SELECTED);
                lastSelected = NO_SELECTED;
            } catch (Exception e) {
                e.printStackTrace();
                errorMessage.setText(e.getMessage());
            }
        }
    }

    private void searchForAccounts() {
        new AccountSearch().run();
    }

    private void searchForAccountIds() {
        searchRunnable.run();
    }

    @Override
    public void addStyle() {
        valueInput.setSize(valueInput.getWidth(), 100);
        valueInput.setBorder(BorderFactory.createCompoundBorder(
                valueInput.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        valueLabel.setBorder(BorderFactory.createCompoundBorder(
                valueLabel.getBorder(),
                BorderFactory.createEmptyBorder(10, 0, 0, 0)));

        accountInput.setSize(accountInput.getWidth(), 100);
        accountInput.setBorder(BorderFactory.createCompoundBorder(
                accountInput.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }

    @Override
    public JPanel getMainPanel() {
        return bodyPanel;
    }
}
