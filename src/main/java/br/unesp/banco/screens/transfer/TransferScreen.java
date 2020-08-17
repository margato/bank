package br.unesp.banco.screens.transfer;

import br.unesp.banco.app.account.Account;
import br.unesp.banco.app.account.AccountFacade;
import br.unesp.banco.core.async.Debounce;
import br.unesp.banco.core.ui.JFrameManager;
import br.unesp.banco.core.ui.Screen;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

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
    public final static int WIDTH = 700;
    public final static int HEIGHT = 450;
    private Runnable runnable;

    public TransferScreen(JFrameManager frameManager) {
        super(frameManager);

        runnable = new Debounce(this::searchForAccounts, 1000);

        this.accountInput.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                searchForAccountIds();
            }

            public void keyReleased(KeyEvent e) {}

            public void keyTyped(KeyEvent e) {}
        });
    }

    private class AccountSearch implements Runnable {
        @Override
        public void run() {
            AccountFacade accountFacade = (AccountFacade) getFrameManager().getFacades().get("account");
            String accountNumber = accountInput.getText();

            try {
                List<Account> accountsLikeNumber = accountFacade.getAccountsLikeNumber(accountNumber);
                System.out.println(accountsLikeNumber);
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
        runnable.run();
    }

    @Override
    public void addStyle() {

    }

    @Override
    public JPanel getMainPanel() {
        return bodyPanel;
    }
}
