package br.unesp.banco.core.util.ui;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Callable;

public class Popup {

    public static void show(String title, String message, String buttonMessage, Callable<Void> onClick) {
        JButton button = new JButton(buttonMessage);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("IBM Plex Sans", Font.BOLD, 20));
        button.setBorderPainted(false);
        button.setMargin(null);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.addActionListener(actionEvent -> {
            JOptionPane.getRootFrame().dispose();
            try {
                onClick.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        UIManager.put("OptionPane.background", Color.white);
        UIManager.put("Panel.background", Color.white);

        JButton[] buttons = {button};

        JLabel label = new JLabel(message);
        label.setFont(new Font("IBM Plex Sans", Font.PLAIN, 18));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBackground(Color.WHITE);

        JOptionPane.showOptionDialog(null, label, title,
                                     JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE,
                                     new ImageIcon(), buttons, buttons[0]);

    }

}
