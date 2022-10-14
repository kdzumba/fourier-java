package fourier.ui;

import fourier.algorithms.FourierAlgorithms;
import fourier.common.Constants;
import fourier.models.Phasor;
import fourier.models.Coordinate;
import fourier.models.Epicycle;
import fourier.rendering.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class WorkspacePanel extends JPanel
{
    public static boolean isDrawing = false;
    private final DrawingPanel drawingPanel;

    public WorkspacePanel()
    {
        var layout = new BoxLayout(this, BoxLayout.X_AXIS);
        setLayout(layout);
        drawingPanel = new DrawingPanel();
        this.add(drawingPanel);
    }

    public void update(List<Coordinate> coordinates)
    {
        drawingPanel.update(coordinates);
    }
    static class DrawingPanel extends JPanel implements ActionListener
    {
        private double time = 0d;
        private static List<Epicycle> epicycles = new ArrayList<>();

        public DrawingPanel()
        {
            Timer timer = new Timer(Constants.TIMER_DELAY, this);
            timer.start();
            this.setBackground(new Color(0, 0, 0));
            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(isDrawing)
            {
                time += (2 * Math.PI) / epicycles.size();
                this.repaint();
            }
            if(time > 2 * Math.PI)
            {
                isDrawing = false;
                time = 0;
                epicycles.clear();
            }
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            g.setColor(Color.WHITE);
            Renderer.graphics2D = (Graphics2D) g;
            var  transform = new AffineTransform();
            transform.translate(getWidth() / 4.0, getHeight() / 4.0);
            Renderer.graphics2D.transform(transform);

            Renderer.animationTimer = time;
            Epicycle.updateEpicycles(epicycles, time);
            Renderer.drawEpiCycles(epicycles);
        }

        public void update(List<Coordinate> coordinates)
        {
            Renderer.drawing.clear();
            this.time = 0;
            var transformedImage = FourierAlgorithms.discreteFourierTransform(coordinates);
            epicycles = Epicycle.generateEpicycles(transformedImage);
            this.revalidate();
            this.repaint();
        }
    }
}
