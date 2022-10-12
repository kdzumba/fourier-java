package fourier.common;

import java.awt.*;

public class Constants
{
    static DisplayMode currentDisplayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
    public static final int HORIZONTAL_TRANSLATION = currentDisplayMode.getWidth() / 2;
    public static final int VERTICAL_TRANSLATION = currentDisplayMode.getHeight() / 2;
    public static final int WINDOW_WIDTH = 1920;
    public static final int WINDOW_HEIGHT = 1200;
    public static final int TIMER_DELAY = 0;
    public static final String APPLICATION_TITLE = "Fourier Analysis";
}
