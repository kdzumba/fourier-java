package fourier;

import fourier.common.Constants;

import javax.swing.*;
import java.awt.*;

public class Application extends JFrame
{
    private String cat = "https://www.drawinghowtodraw.com/stepbystepdrawinglessons/wp-content/uploads/2009/10/2howtodrawcat-finished-small.png";
    private String phineas = "https://www.drawinghowtodraw.com/stepbystepdrawinglessons/wp-content/uploads/2009/10/phineas.png";
    private String bart = "https://i.stack.imgur.com/wyRLn.png";
    public Application()
    {
        setLayout(new BorderLayout());
        var panel = new MainPanel(phineas);
        panel.init();
        this.add(panel);
        setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setTitle(Constants.APPLICATION_TITLE);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args)
    {
        new Application();
    }
}