package br.unesp.banco.core.ui;

import br.unesp.banco.core.utils.Logger;

import javax.swing.*;

public abstract class Screen {
    private JFrameManager frameManager;

    public Screen(JFrameManager frameManager) {
        this.frameManager = frameManager;
        Logger.logUi("Loading", this.getClass());
    }

    public abstract void addStyle();

    public abstract JPanel getMainPanel();

    public JFrameManager getFrameManager() {
        return frameManager;
    }
}
