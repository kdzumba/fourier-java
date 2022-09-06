package fourier.ui;

import fourier.interfaces.IPublisher;
import fourier.interfaces.ISubscriber;
import fourier.rendering.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ColorPickerPanel extends JPanel
{
    private String selectedColor;
    public ColorPickerPanel()
    {
//        this.setLayout(new BorderLayout());
        var red = new ColorCheckBox(Color.RED);
        red.setBackground(Color.red);
        var blue = new ColorCheckBox(Color.BLUE);
        blue.setBackground(Color.blue);
        var green = new ColorCheckBox(Color.GREEN);
        green.setBackground(Color.green);
        var yellow = new ColorCheckBox(Color.YELLOW);
        yellow.setBackground(Color.yellow);

        this.add(red);
        this.add(green);
        this.add(yellow);
    }

    public static class ColorCheckBox extends JCheckBox implements ActionListener, IPublisher
    {
        private final Color color;
        private final List<ISubscriber> subscribers = new ArrayList<>();

        public ColorCheckBox(Color color)
        {
            this.color = color;
            this.addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            Renderer.penColor = this.color;
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
            subscribers.forEach(ISubscriber::update);
        }
    }
}
