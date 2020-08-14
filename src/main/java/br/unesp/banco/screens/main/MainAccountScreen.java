package br.unesp.banco.screens.main;


import br.unesp.banco.core.ui.JFrameLoader;
import br.unesp.banco.core.ui.JFrameManager;
import br.unesp.banco.core.ui.Screen;
import br.unesp.banco.screens.login.LoginScreen;
import br.unesp.banco.screens.statement.StatementScreen;

import javax.swing.*;

public class MainAccountScreen extends Screen {
    private JButton bankStatementButton;
    private JPanel bodyPanel;
    private JPanel PainelInfo;
    private JLabel welcome;
    private JPanel mainPanel;
    private JPanel painelBts;
    private JButton exitButton;
    private JButton transferButton;
    private JButton withdrawButton;
    private JButton depositButton;

    public MainAccountScreen(JFrameManager frameManager) {
        super(frameManager);

        exitButton.addActionListener(e -> JFrameLoader.load(getFrameManager(), LoginScreen.class, "Login"));
        bankStatementButton.addActionListener(e -> JFrameLoader.load(getFrameManager(), StatementScreen.class, "Extrato"));
    }

    @Override
    public void addStyle() {

    }

    @Override
    public JPanel getMainPanel() {
        return bodyPanel;
    }

}
