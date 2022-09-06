package fourier.ui;

import fourier.Application;
import fourier.imageprocessing.ImageProcessor;
import fourier.interfaces.IPublisher;
import fourier.interfaces.ISubscriber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class URLPanel extends JPanel implements ActionListener, IPublisher
{
    private final HintTextField textField;
    private final List<ISubscriber> subscribers = new ArrayList<>();

    public URLPanel()
    {
        textField = new HintTextField("Image URL ...");
        textField.setPreferredSize(new Dimension(UICommon.COMPONENT_WIDTH, UICommon.COMPONENT_HEIGHT));
        textField.addActionListener(this);
        add(textField);

        this.setBackground(UICommon.COMPONENT_BACKGROUND);
        this.setForeground(Color.WHITE);
        this.setFont(UICommon.ALGO_FONT);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        var image = ImageProcessor.loadImageFromWebURL(textField.getText());
        Application.imagePoints = ImageProcessor.getOrderedPixelPositions(image);
        subscribers.forEach(ISubscriber::update);
    }

    @Override
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
}
