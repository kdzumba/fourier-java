package fourier.ui;

import javax.swing.*;
import java.awt.*;

public class ActionsPanel extends JPanel
{
    private final IconButton play;
    public ActionsPanel()
    {
        setBackground(Color.darkGray);
        play = new IconButton("play");
        play.addActionListener(e -> WorkspacePanel.isDrawing = !WorkspacePanel.isDrawing);
        add(play);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(WorkspacePanel.isDrawing)
        {
            play.setIcon("pause");
        }
        else
        {
            play.setIcon("play");
        }
    }
}
