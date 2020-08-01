package br.unesp.banco.ui;

import br.unesp.banco.system.account.Account;
import br.unesp.banco.system.account.AccountFacade;
import br.unesp.banco.system.usercredentials.UserCredentials;
import br.unesp.banco.ui.interfaces.Screen;
import br.unesp.banco.core.util.Logger;
import br.unesp.banco.core.util.ui.JFrameLoader;
import br.unesp.banco.core.util.ui.JFrameManager;

import javax.swing.*;

public class LoginScreen extends Screen {
    private JTextField accountInput;
    private JPanel mainPanel;
    private JLabel accountLabel;
    private JButton loginButton;
    private JPasswordField passwordInput;
    private JButton openAccountButton;
    private JLabel errorMessage;
    private JFrameManager frameManager;


    public LoginScreen() {
        loginButton.addActionListener(e -> {
            String number = accountInput.getText();
            String password = new String(passwordInput.getPassword());

            AccountFacade accountFacade = (AccountFacade) frameManager.getFacades().get("account");

            try {
                Account account = accountFacade.login(number, password);
                System.out.println(account);
                frameManager.setUserCredentials(new UserCredentials(number, account.getId()));
                JFrameLoader.load(frameManager, MainAccountScreen.class,"Banco");
            } catch (Exception exception) {
                errorMessage.setText(exception.getMessage());
            }
        });

        openAccountButton.addActionListener(e -> JFrameLoader.load(frameManager, OpenAccountScreen.class, "Abrir uma nova conta"));
    }

    //TODO ADICIONAR HORIZONTAL SPACE
    @Override
    public JPanel getPainelBody() {
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

    @Override
    public JFrameManager getFrameManager() {
        return frameManager;
    }

    @Override
    public void setFrameManager(JFrameManager frameManager) {
        this.frameManager = frameManager;
    }
}
