package br.unesp.banco.ui;

import br.unesp.banco.ui.interfaces.Screen;
import br.unesp.banco.core.util.Logger;
import br.unesp.banco.core.util.ui.Clipboard;
import br.unesp.banco.core.util.ui.JFrameLoader;
import br.unesp.banco.core.util.ui.JFrameManager;
import br.unesp.banco.core.util.ui.Popup;

import javax.swing.*;

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
            String accountNumber = "00000";

            Clipboard.copy(accountNumber);

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
