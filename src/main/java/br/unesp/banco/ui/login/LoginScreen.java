package br.unesp.banco.ui.login;

import br.unesp.banco.core.log.Logger;
import br.unesp.banco.core.ui.JFrameLoader;
import br.unesp.banco.core.ui.JFrameManager;
import br.unesp.banco.core.ui.Screen;
import br.unesp.banco.app.account.Account;
import br.unesp.banco.app.account.AccountFacade;
import br.unesp.banco.app.usercredentials.UserCredentials;
import br.unesp.banco.ui.main.MainAccountScreen;
import br.unesp.banco.ui.openaccount.OpenAccountScreen;

import javax.swing.*;

public class LoginScreen extends Screen {
    private JTextField accountInput;
    private JPanel mainPanel;
    private JLabel accountLabel;
    private JButton loginButton;
    private JPasswordField passwordInput;
    private JButton openAccountButton;
    private JLabel errorMessage;


    public LoginScreen(JFrameManager frameManager) {
        super(frameManager);
        loginButton.addActionListener(e -> {
            String number = accountInput.getText();
            String password = new String(passwordInput.getPassword());

            AccountFacade accountFacade = (AccountFacade) getFrameManager().getFacades().get("account");

            try {
                Account account = accountFacade.login(number, password);
                System.out.println(account);
                getFrameManager().setUserCredentials(new UserCredentials(number, account.getId()));
                JFrameLoader.load(getFrameManager(), MainAccountScreen.class, "Banco");
            } catch (Exception exception) {
                errorMessage.setText(exception.getMessage());
            }
        });

        openAccountButton.addActionListener(e -> JFrameLoader.load(getFrameManager(), OpenAccountScreen.class, "Abrir uma nova conta"));
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public void addStyle() {
        Logger.logUi("Carregando estilos", LoginScreen.class);
        // Padding
        accountInput.setSize(accountInput.getWidth(), 100);
        accountInput.setBorder(BorderFactory.createCompoundBorder(
                accountInput.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        passwordInput.setSize(passwordInput.getWidth(), 100);
        passwordInput.setBorder(BorderFactory.createCompoundBorder(
                passwordInput.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }
}

