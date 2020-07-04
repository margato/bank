package br.unesp.banco.ui;

import br.unesp.banco.utils.ui.JFrameManager;
import br.unesp.banco.utils.ui.JFrameLoader;
import br.unesp.banco.utils.Logger;

import javax.swing.*;

public class BankApplication {

    private JFrame frame;
    private JFrameManager frameManager;

    public BankApplication() {
        frame = new JFrame("Bank");
        frameManager = new JFrameManager(frame);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void run() {
        Logger.logUi("Iniciando");

        JFrameLoader.load(frameManager, LoginScreen.class, "Acessar conta");
    }

}
