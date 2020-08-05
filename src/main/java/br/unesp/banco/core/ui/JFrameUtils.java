package br.unesp.banco.core.ui;

import javax.swing.*;
import java.awt.*;

public class JFrameUtils {

    public static void center(JFrame jFrame) {
        Dimension dimensions = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setLocation(dimensions.width / 2 - jFrame.getSize().width / 2, dimensions.height / 2 - jFrame.getSize().height / 2);
    }

}
