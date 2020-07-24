package br.unesp.banco.core.util.ui;

import br.unesp.banco.ui.interfaces.Screen;

import javax.swing.*;

public class JFrameLoader {

    public static void load(JFrameManager frameManager, Class<? extends Screen> clazz, String windowTitle) {
        load(frameManager, clazz, JFrame.EXIT_ON_CLOSE, 700, 700, windowTitle, false);
    }

    public static void load(JFrameManager frameManager, Class<? extends Screen> clazz, String windowTitle, boolean fullscreen) {
        load(frameManager, clazz, JFrame.EXIT_ON_CLOSE, 700, 700, windowTitle, fullscreen);
    }

    public static void load(JFrameManager frameManager, Class<? extends Screen> clazz, int actionOnClose, int width, int height, String windowTitle, boolean fullscreen) {
        try {
            Screen screen = clazz.newInstance();
            JFrame frame = new JFrame(windowTitle);

            frame.setContentPane(screen.getMainPanel());
            frame.setDefaultCloseOperation(actionOnClose);
            frame.pack();
            frame.setSize(width, height);
            frame.setVisible(true);
            screen.addStyle();
            JFrameUtils.center(frame);

            if (frameManager.getFrame() != null) {
                frameManager.getFrame().setVisible(false);
            }

            frameManager.setFrame(frame);
            screen.setFrameManager(frameManager);

            if (fullscreen) {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
