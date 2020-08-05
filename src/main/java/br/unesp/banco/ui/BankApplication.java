package br.unesp.banco.ui;

import br.unesp.banco.core.util.ui.JFrameManager;
import br.unesp.banco.core.util.ui.JFrameLoader;
import br.unesp.banco.core.util.Logger;
import br.unesp.banco.system.usercredentials.UserCredentials;

import javax.swing.*;
import java.util.Map;

public class BankApplication {

    private JFrame frame;
    private final JFrameManager frameManager;

    public BankApplication(Map<String, Object> facades) {
        frame = new JFrame("Bank");
        frameManager = new JFrameManager(frame, facades, null);
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
