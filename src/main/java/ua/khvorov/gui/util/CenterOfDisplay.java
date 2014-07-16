package ua.khvorov.gui.util;

import java.awt.*;

public class CenterOfDisplay {

    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    public static int getXPosition(Component component) {
        return SCREEN_SIZE.width / 2 - (component.getWidth() / 2);
    }

    public static int getYPosition(Component component) {
        return SCREEN_SIZE.height / 2 - (component.getHeight() / 2);
    }
}
