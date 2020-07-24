package br.unesp.banco.ui;

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
    private JFrameManager frameManager;

    public LoginScreen() {
        loginButton.addActionListener(e -> {
            String account = accountInput.getText();
            String password = new String(passwordInput.getPassword());
            Logger.logInput(account);
            Logger.logInput(password);
        });

        openAccountButton.addActionListener(e -> JFrameLoader.load(frameManager, OpenAccountScreen.class, "Abrir uma nova conta"));
    }

    //TODO ADICIONAR HORIZONTAL SPACE
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

    @Override
    public JFrameManager getFrameManager() {
        return frameManager;
    }

    @Override
    public void setFrameManager(JFrameManager frameManager) {
        this.frameManager = frameManager;
    }
}
