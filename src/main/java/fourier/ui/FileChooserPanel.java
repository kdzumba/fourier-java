package fourier.ui;

import fourier.Application;
import fourier.imageprocessing.ImageProcessor;
import fourier.interfaces.IPublisher;
import fourier.interfaces.ISubscriber;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileChooserPanel extends JPanel implements ActionListener, IPublisher
{
    private final JButton selectFileButton;
    private final List<ISubscriber> subscribers = new ArrayList<>();

    public FileChooserPanel()
    {
        selectFileButton = new JButton("Select Image...");
        selectFileButton.addActionListener(this);
        selectFileButton.setPreferredSize(new Dimension(UICommon.COMPONENT_WIDTH, UICommon.COMPONENT_HEIGHT));
        selectFileButton.setBackground(UICommon.COMPONENT_BACKGROUND);
        selectFileButton.setForeground(Color.WHITE);
        selectFileButton.setFont(UICommon.ALGO_FONT);

        this.setBackground(UICommon.COMPONENT_BACKGROUND);
        this.add(selectFileButton);
    }

    public void addSubscriber(ISubscriber subscriber)
    {
        this.subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(ISubscriber subscriber)
    {
        this.subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers()
    {
        this.subscribers.forEach(ISubscriber::update);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == selectFileButton)
        {
            var fileChooser  = new JFileChooser();
            fileChooser.setBackground(UICommon.COMPONENT_BACKGROUND);
            fileChooser.setForeground(Color.WHITE);
            fileChooser.setFont(UICommon.ALGO_FONT);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, PNG & GIF Images", "jpg", "png", "gif");
            fileChooser.setFileFilter(filter);

            int response = fileChooser.showOpenDialog(null);

            if(response == JFileChooser.APPROVE_OPTION)
            {
                var imagePath = fileChooser.getSelectedFile().getAbsolutePath();
                var image = ImageProcessor.loadImageFromFile(imagePath);
                Application.imagePoints = ImageProcessor.getOrderedPixelPositions(image);
                subscribers.forEach(ISubscriber::update);
            }
        }
    }
}
