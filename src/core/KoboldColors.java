package core;

import java.awt.*;

public class KoboldColors {
    /**
     * Collection of colors that Kobold can use
     */
    public enum Colors {
        DARK(new Color(40,40,40,255)),
        GREY(new Color(118,105,96,255)),
        RED(new Color(248,75,60,255)),
        LIGHT_GREEN(new Color(177,179,54,255)),
        GREEN(new Color(136,191,127,255)),
        PINK(new Color(210,135,139,255)),
        WHITE(new Color(235,218,180,255))
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
