package br.unesp.banco.ui;

import br.unesp.banco.core.util.ui.JFrameManager;
import br.unesp.banco.core.util.ui.JFrameLoader;
import br.unesp.banco.core.util.Logger;
import br.unesp.banco.system.usercredentials.UserCredentials;

import javax.swing.*;
import java.util.Map;

public class BankApplication {

    private JFrame frame;
    private UserCredentials usr_cred;
    private final JFrameManager frameManager;

    public BankApplication(Map<String, Object> facades) {
        frame = new JFrame("Bank");
        usr_cred = new UserCredentials("", (long) 0);
        frameManager = new JFrameManager(frame, facades, usr_cred);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void run() {
        Logger.logUi("Iniciando", LoginScreen.class);
        JFrameLoader.load(frameManager, LoginScreen.class, "Acessar conta");
    }

}
