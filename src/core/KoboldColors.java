package core;

import java.awt.*;

public class KoboldColors {
    public enum Colors {
        BLACK(new Color(0, 0, 0, 255)),
        DARK_GRAY(new Color(43,43,43,255)),
        GRAY(new Color(96,99,102,255)),
        GREEN(new Color(88,152,75,255)),
        PINK(new Color(197,134,192,255)),
        PEACH(new Color(206,145,120,255)),
        LIGHT_BLUE(new Color(78,201,176,255)),
        BLUE(new Color(84,156,214,255)),
        LIGHT(new Color(203,216,228,255)),
        WHITE(new Color(255, 255, 255)),
        ;

        private Color colorValue;

        Colors(Color colorValue) {
            this.colorValue = colorValue;
        }

        public Color getColorValue() {
            return this.colorValue;
        }
    }
}
