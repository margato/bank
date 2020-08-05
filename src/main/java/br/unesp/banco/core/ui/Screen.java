package br.unesp.banco.core.ui;

import javax.swing.*;

public abstract class Screen {
    private JFrameManager frameManager;

    public Screen(JFrameManager frameManager) {
        this.frameManager = frameManager;
    }

    public abstract void addStyle();

    public abstract JPanel getBodyPanel();

    public JFrameManager getFrameManager() {
        return frameManager;
    }
}
