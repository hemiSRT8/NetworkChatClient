package ua.khvorov.gui.fonts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class InputFont {

    public final static Font INPUT_FONT = initInputFont();
    private static final Logger LOGGER = LoggerFactory.getLogger(InputFont.class);

    private static Font initInputFont() {
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("src\\main\\resources\\fonts\\inputFont.ttf"))
                    .deriveFont(18f)
                    .deriveFont(Font.BOLD);
        } catch (FontFormatException e) {
            LOGGER.error("Font format exception", e);
        } catch (IOException e) {
            LOGGER.error("IO exception", e);
        }
        return font;
    }


}
