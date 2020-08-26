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

import javax.swing.*;
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
