package br.unesp.banco.ui;

import br.unesp.banco.core.util.ui.JFrameManager;
import br.unesp.banco.ui.interfaces.Screen;

import javax.swing.*;

public class MainAccountScreen extends Screen {
    private JButton bankStatementButton;
    private JButton withdrawButton;
    private JButton transferButton;
    private JPanel bodyPanel;
    private JPanel PainelInfo;
    private JLabel contaNameLabel;
    private JPanel mainPanel;
    private JPanel painelBts;

    public MainAccountScreen(JFrameManager frameManager) {
        super(frameManager);
        contaNameLabel.setText(String.format("Conta: %s", this.getFrameManager().getUserCredentials().getAccount_num()));
    }

    @Override
    public void addStyle() {

    }

    @Override
    public JPanel getBodyPanel() {
        return bodyPanel;
    }

}
