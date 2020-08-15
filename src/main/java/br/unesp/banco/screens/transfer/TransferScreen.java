package br.unesp.banco.screens.transfer;

import br.unesp.banco.core.ui.JFrameManager;
import br.unesp.banco.core.ui.Screen;

import javax.swing.*;

public class TransferScreen  extends Screen {
    private JPanel bodyPanel;
    private JPanel painelBts;
    private JButton backButton;
    private JLabel valueLabel;
    private JButton confirmButton;
    private JTextField valueInput;
    private JLabel errorMessage;
    private JLabel accountLabel;
    private JTextField accountInput;

    public TransferScreen(JFrameManager frameManager) {
        super(frameManager);
    }

    @Override
    public void addStyle() {

    }

    @Override
    public JPanel getMainPanel() {
        return bodyPanel;
    }
}
