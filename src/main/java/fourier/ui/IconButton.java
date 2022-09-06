package fourier.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Objects;

public class IconButton extends JButton
{
    public IconButton(String iconName)
    {
        var icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/" + iconName + ".png")).getPath());
        this.setIcon(icon);
        this.setBackground(UICommon.COMPONENT_BACKGROUND);
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
    }

    public void setIcon(String name)
    {
        this.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/" + name + ".png")).getPath()));
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(20, 20);
    }

    @Override
    public Dimension getMaximumSize(){
        return new Dimension(20, 20);
    }

    @Override
    public Dimension getMinimumSize(){
        return new Dimension(20, 20);
    }
}
