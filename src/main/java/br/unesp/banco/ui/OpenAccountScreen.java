package br.unesp.banco.ui;

import br.unesp.banco.system.account.AccountFacade;
import br.unesp.banco.ui.interfaces.Screen;
import br.unesp.banco.core.util.Logger;
import br.unesp.banco.core.util.ui.Clipboard;
import br.unesp.banco.core.util.ui.JFrameLoader;
import br.unesp.banco.core.util.ui.JFrameManager;
import br.unesp.banco.core.util.ui.Popup;

import javax.swing.*;
import java.sql.SQLException;
import java.util.Random;

public class OpenAccountScreen extends Screen {
    private JPanel mainPanel;
    private JButton openAccountButton;
    private JPasswordField passwordInput;
    private JButton loginButton;
    private JFrameManager frameManager;

    public OpenAccountScreen() {
        loginButton.addActionListener(e -> JFrameLoader.load(frameManager, LoginScreen.class, "Acessar conta"));

        openAccountButton.addActionListener(e -> {

            JFrameLoader.load(frameManager, LoginScreen.class, "Acessar conta");
            String accountNumber = String.valueOf(10000000 + new Random().nextInt(90000000));

            Clipboard.copy(accountNumber);

            AccountFacade accountFacade = (AccountFacade) frameManager.getFacades().get("account");

            try {
                accountFacade.createAccount(accountNumber, new String(passwordInput.getPassword()));
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            Popup.show("Nova conta bancária",
                       String.format("<html>" +
                                             "<h1>Conta criada</h1>" +
                                             "<p>nº da conta: <b>%s</b></p>" +
                                             "<p>Agora você já pode acessá-la<br/></p>" +
                                             "</html>", accountNumber),
                       "Copiar nº da conta para a área de transferência",
                       () -> (Void) Clipboard.copy(accountNumber));
        });
    }

    public void addStyle() {
        Logger.logUi("Carregando estilos", OpenAccountScreen.class);

        passwordInput.setSize(passwordInput.getWidth(), 100);
        passwordInput.setBorder(BorderFactory.createCompoundBorder(
                passwordInput.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public JFrameManager getFrameManager() {
        return this.frameManager;
    }

    @Override
    public void setFrameManager(JFrameManager frameManager) {
        this.frameManager = frameManager;
    }
}
