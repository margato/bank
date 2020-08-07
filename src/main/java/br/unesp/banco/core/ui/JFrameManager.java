package br.unesp.banco.core.ui;

import br.unesp.banco.app.usercredentials.UserCredentials;

import javax.swing.*;
import java.util.Map;

public class JFrameManager {

    private JFrame frame;
    private final Map<String, Object> facades;
    private UserCredentials userCredentials;

    public JFrameManager(JFrame frame, Map<String, Object> facades, UserCredentials userCredentials) {
        this.frame = frame;
        this.facades = facades;
        this.userCredentials = userCredentials;
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

    public UserCredentials getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
    }
}

