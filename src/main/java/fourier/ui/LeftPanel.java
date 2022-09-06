package fourier.ui;

import javax.swing.*;
import java.awt.*;

public class LeftPanel extends JPanel
{
    public LeftPanel()
    {
        this.setLayout(new GridLayout(3, 1));
        this.setBackground(Color.darkGray);
        this.setBorder(BorderFactory.createEmptyBorder(UICommon.H_SEPARATOR, UICommon.H_SEPARATOR, UICommon.H_SEPARATOR, UICommon.H_SEPARATOR));
        PreLoadedImagesPanel imagesPanel = new PreLoadedImagesPanel();
        this.add(imagesPanel);

        URLPanel urlPanel = new URLPanel();
        FileChooserPanel fileChooserPanel = new FileChooserPanel();

        var selectImagePanel = new JPanel();
        selectImagePanel.setLayout(new GridLayout(0, 1));
        var border = BorderFactory.createTitledBorder("Image Source");
        border.setTitleFont(UICommon.ALGO_FONT);
        border.setTitleColor(Color.WHITE);
        selectImagePanel.setBorder(border);
        selectImagePanel.setBackground(UICommon.COMPONENT_BACKGROUND);
        selectImagePanel.add(urlPanel);
        selectImagePanel.add(fileChooserPanel);
        this.add(selectImagePanel);
    }
}
