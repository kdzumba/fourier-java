package fourier.ui;

import javax.swing.*;
import java.awt.*;

public class UICommon
{
        //Colors
        public static final Color COMPONENT_BACKGROUND = new Color(80, 80, 80);
        public static final Color WATER_COLOR = new Color(128, 197, 222);
        public static final Color ROCK_COLOR = new Color(139, 69, 19);
        public static final Color GRASS_COLOR = new Color(126, 200, 80);
        public static final Color WALL_COLOR = new Color(0, 0, 0);
        public static final Color PATH_COLOR = new Color(190,155,123);
        public static final Color START_COLOR = new Color(249,97,97);
        public static final Color PROCESSED_COLOR = new Color(220, 220, 20);
        public static final Color DEST_COLOR = Color.red;

        //Dimensions
        public static final int COMPONENT_HEIGHT = 30;
        public static final int COMPONENT_WIDTH = 200;
        public static final int H_SEPARATOR = 5;
        private static final Dimension MIN_SIZE = new Dimension(COMPONENT_WIDTH, H_SEPARATOR);
        private static final Dimension PREF_SIZE = new Dimension(COMPONENT_WIDTH, H_SEPARATOR);
        private static final Dimension MAX_SIZE = new Dimension(COMPONENT_WIDTH, H_SEPARATOR);

        //Fonts
        public static final Font ALGO_FONT = new Font(Font.MONOSPACED, Font.BOLD, 12);

        public static JComponent createSeparator(float alignment){
            JComponent separator = new Box.Filler(MIN_SIZE, PREF_SIZE, MAX_SIZE);
            separator.setAlignmentX(alignment);
            return separator;
        }
}
