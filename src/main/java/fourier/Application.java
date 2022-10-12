package fourier;

import fourier.common.Constants;
import fourier.interfaces.IPublisher;
import fourier.interfaces.ISubscriber;
import fourier.models.Coordinate;
import fourier.ui.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Application extends JFrame implements ISubscriber
{
    private final WorkspacePanel drawingPanel;
    public static List<Coordinate> imagePoints = new ArrayList<>();
    public Application()
    {
        setLayout(new BorderLayout());
        drawingPanel = new WorkspacePanel();
        LeftPanel leftPanel = new LeftPanel();

        var actionsPanel = new ActionsPanel();
//        var screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        var currentDisplayMode = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();

        this.add(drawingPanel, BorderLayout.CENTER);
        this.add(leftPanel, BorderLayout.WEST);
        this.add(actionsPanel, BorderLayout.NORTH);
        this.setSize(currentDisplayMode.getWidth(), currentDisplayMode.getHeight());
        this.setTitle(Constants.APPLICATION_TITLE);
        this.setResizable(true);
        this.getContentPane().setBackground(Color.BLACK);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        for(Component c : leftPanel.getComponents())
        {
            if(c instanceof JPanel)
                for(Component child : ((JPanel) c).getComponents())
                    if(child instanceof IPublisher)
                        ((IPublisher) child).addSubscriber(this);
        }
    }

    public static void main(String[] args)
    {
        new Application();
    }

    @Override
    public void update() {
        this.drawingPanel.update(imagePoints);
    }
}