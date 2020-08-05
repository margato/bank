package br.unesp.banco.core.util.ui;

import br.unesp.banco.ui.interfaces.Screen;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class JFrameLoader {

    public static void load(JFrameManager frameManager, Class<? extends Screen> clazz, String windowTitle) {
        load(frameManager, clazz, JFrame.EXIT_ON_CLOSE, 700, 700, windowTitle, false);
    }

    public static void load(JFrameManager frameManager, Class<? extends Screen> clazz, String windowTitle, boolean fullscreen) {
        load(frameManager, clazz, JFrame.EXIT_ON_CLOSE, 700, 700, windowTitle, fullscreen);
    }

    public static void load(JFrameManager frameManager, Class<? extends Screen> clazz, int actionOnClose, int width, int height, String windowTitle, boolean fullscreen) {
        try {
            Screen screen = null;
            try {
                screen = clazz.getConstructor(JFrameManager.class).newInstance(frameManager);
            } catch (NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
                System.exit(-1);
            }
            JFrame frame = new JFrame(windowTitle);

            frame.setContentPane(screen.getBodyPanel());
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

            if (fullscreen) {
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
