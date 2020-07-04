package br.unesp.banco.ui.interfaces;

import br.unesp.banco.utils.ui.JFrameManager;

import javax.swing.*;

public abstract class Screen {
    public abstract void addStyle();
    public abstract JPanel getMainPanel();
    public abstract JFrameManager getFrameManager();
    public abstract void setFrameManager(JFrameManager frameManager);
}
