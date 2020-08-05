package br.unesp.banco.core.ui;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class Clipboard {

    public static Object copy(String text) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
        return null;
    }

}
