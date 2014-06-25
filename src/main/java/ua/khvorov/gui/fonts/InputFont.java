package ua.khvorov.gui.fonts;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class InputFont {

    private static Font inputFont;

    public static Font getInputFont() {
        if (inputFont != null) {
            return inputFont;
        }

        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("src\\main\\resources\\fonts\\inputFont.ttf"))
                    .deriveFont(18f)
                    .deriveFont(Font.BOLD);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return font;
    }
}