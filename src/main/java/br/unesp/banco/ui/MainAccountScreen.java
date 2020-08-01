package br.unesp.banco.ui;

import br.unesp.banco.core.util.ui.JFrameManager;
import br.unesp.banco.ui.interfaces.Screen;

import javax.swing.*;

public class MainAccountScreen extends Screen {
    private JButton saldoButton;
    private JButton extratoButton;
    private JButton saqueButton;
    private JButton transfButton;
    private JPanel PainelBody;
    private JPanel PainelInfo;
    private JLabel contaNameLabel;
    private JPanel PainelPrinc;
    private JFrameManager frameManager;
    private JPanel painelBts;
    private String ct_name;

    public MainAccountScreen(){
        ct_name = String.format("Conta: %s",frameManager.getUserCredentials().getAccount_num());
        System.out.println(ct_name);
        contaNameLabel.setText(ct_name);

    }

    @Override
    public void addStyle() {

    }

    @Override
    public JPanel getPainelBody() {
        return PainelBody;
    }

    @Override
    public JFrameManager getFrameManager() {
        return this.frameManager;
    }

    @Override
    public void setFrameManager(JFrameManager frameManager) {
        this.frameManager = frameManager;

    }
}
