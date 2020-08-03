package br.unesp.banco.ui;

import br.unesp.banco.system.account.AccountFacade;
import br.unesp.banco.ui.interfaces.Screen;
import br.unesp.banco.core.util.Logger;
import br.unesp.banco.core.util.ui.Clipboard;
import br.unesp.banco.core.util.ui.JFrameLoader;
import br.unesp.banco.core.util.ui.JFrameManager;
import br.unesp.banco.core.util.ui.Popup;

import javax.swing.*;
import java.util.Random;

public class OpenAccountScreen extends Screen {
    private JPanel mainPanel;
    private JButton openAccountButton;
    private JPasswordField passwordInput;
    private JButton loginButton;
    private JLabel errorMessage;

    public OpenAccountScreen(JFrameManager frameManager) {
        super(frameManager);
        loginButton.addActionListener(e -> JFrameLoader.load(getFrameManager(), LoginScreen.class, "Acessar conta"));

        openAccountButton.addActionListener(e -> {

            String accountNumber = String.valueOf(10000000 + new Random().nextInt(90000000));

            Clipboard.copy(accountNumber);

            AccountFacade accountFacade = (AccountFacade) getFrameManager().getFacades().get("account");

            try {
                confirmAccount(accountNumber, accountFacade);
            } catch (Exception exception) {
                errorMessage.setText(exception.getMessage());

            }
        });


    }

    public void confirmAccount(String accountNumber, AccountFacade accountFacade) throws Exception {
        accountFacade.createAccount(accountNumber, new String(passwordInput.getPassword()));
        JFrameLoader.load(getFrameManager(), LoginScreen.class, "Acessar conta");
        Popup.show("Nova conta bancária",
                   String.format("<html>" +
                                         "<h1>Conta criada</h1>" +
                                         "<p>nº da conta: <b>%s</b></p>" +
                                         "<p>Agora você já pode acessá-la<br/></p>" +
                                         "</html>", accountNumber),
                   "Copiar nº da conta para a área de transferência",
                   () -> (Void) Clipboard.copy(accountNumber));

    }

    public void addStyle() {
        Logger.logUi("Carregando estilos", OpenAccountScreen.class);

        passwordInput.setSize(passwordInput.getWidth(), 100);
        passwordInput.setBorder(BorderFactory.createCompoundBorder(
                passwordInput.getBorder(),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
    }


    public JPanel getBodyPanel() {
        return mainPanel;
    }

}
