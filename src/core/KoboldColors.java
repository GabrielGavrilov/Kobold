package core;

import java.awt.*;

public class KoboldColors {
    /**
     * Collection of colors that Kobold can use
     */
    public enum Colors {
        BLACK(new Color(0, 0, 0, 255)),
        DARK_GRAY(new Color(43,43,43,255)),
        RED(new Color(216,64,49,255)),
        ORANGE(new Color(213,121,6,255)),
        PEACH(new Color(206,145,120,255)),
        GRAY(new Color(96,99,102,255)),
        GREEN(new Color(88,152,75,255)),
        TEAL_GREEN(new Color(38,162,105,255)),
        TEAL(new Color(78,201,176,255)),
        LIGHT_BLUE(new Color(84,156,214,255)),
        BLUE(new Color(53,77,156,255)),
        PINK(new Color(197,134,192,255)),
        PURPLE(new Color(163,71,186,255)),
        LIGHT(new Color(203,216,228,255)),
        WHITE(new Color(255, 255, 255)),
        ;

        private final Color colorValue;

        /**
         * Initializes the color enum with the value.
         * @param colorValue Color
         */
        Colors(Color colorValue) {
            this.colorValue = colorValue;
        }

        /**
         * Returns the color related to the enum.
         * @return Color
         */
        public Color getColorValue() {
            return this.colorValue;
        }
    }
}
