package br.unesp.banco.utils.ui;

import javax.swing.*;

public class JFrameManager {

    private JFrame frame;

    public JFrameManager(JFrame frame) {
        this.frame = frame;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
