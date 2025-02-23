package utils;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class Loader {

    public static Font loadFont(String path) {
        InputStream inputStream = Loader.class.getResourceAsStream(path);

        try {
            if (inputStream != null) {
                return Font.createFont(Font.TRUETYPE_FONT, inputStream);
            }
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException("Failed to load font: " + path, e);
        }

        throw new RuntimeException("Font not found: " + path);
    }
}
