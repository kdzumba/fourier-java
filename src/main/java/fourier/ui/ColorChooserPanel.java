package fourier.ui;

import fourier.rendering.Renderer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ColorChooserPanel extends JPanel implements ChangeListener
{
    private JColorChooser colorChooser;
    public ColorChooserPanel()
    {
        this.colorChooser = new JColorChooser();
        this.colorChooser.setPreferredSize(new Dimension(400, 400));
        this.colorChooser.setPreviewPanel(new JPanel());
        this.add(colorChooser);
        this.colorChooser.getSelectionModel().addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        System.out.println(this.colorChooser.getColor().toString());
        Renderer.penColor = this.colorChooser.getColor();
    }
}
