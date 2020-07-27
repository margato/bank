package br.unesp.banco.core.util.ui;

import javax.swing.*;
import java.util.Map;

public class JFrameManager {

    private JFrame frame;
    private Map<String, Object> facades;

    public JFrameManager(JFrame frame, Map<String, Object> facades) {
        this.frame = frame;
        this.facades = facades;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public Map<String, Object> getFacades() {
        return facades;
    }
}
