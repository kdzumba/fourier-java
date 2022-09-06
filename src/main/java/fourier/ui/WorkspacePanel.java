package fourier.ui;

import fourier.algorithms.FourierAlgorithms;
import fourier.common.Constants;
import fourier.models.ComplexNumber;
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
    private final OriginalImagePanel originalImagePanel;

    public WorkspacePanel()
    {
        var layout = new BoxLayout(this, BoxLayout.X_AXIS);
        setLayout(layout);
        drawingPanel = new DrawingPanel();
        originalImagePanel = new OriginalImagePanel();
        this.add(drawingPanel);
        this.add(originalImagePanel);
    }

    public void update(List<Coordinate> coordinates)
    {
        drawingPanel.update(coordinates);
    }

    public void setOriginalImage(BufferedImage image)
    {
        originalImagePanel.setOriginalImage(image);
    }

    public

    static class OriginalImagePanel extends JPanel
    {
        private BufferedImage originalImage;
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            if(Objects.nonNull(originalImage))
            {
                g.drawImage(originalImage, 0, 0, this);
            }
        }
        public void setOriginalImage(BufferedImage image)
        {
            originalImage = image;
        }
    }

    static class DrawingPanel extends JPanel implements ActionListener
    {
        private double time = 0d;
        private static List<Epicycle> epicycles = new ArrayList<>();

        public DrawingPanel()
        {
            Timer timer = new Timer(Constants.TIMER_DELAY, this);
            timer.start();
            this.setBackground(new Color(240, 240, 240));
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
            transform.translate(Constants.HORIZONTAL_TRANSLATION, Constants.VERTICAL_TRANSLATION);
            Renderer.graphics2D.transform(transform);

            Renderer.animationTimer = time;
            Epicycle.updateEpicycles(epicycles, time);
            Renderer.drawEpiCycles(epicycles);
        }

        public void update(List<Coordinate> coordinates)
        {
            Renderer.graphics2D.clearRect(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
            Renderer.drawing.clear();
            this.time = 0;
            var imagePoints = ComplexNumber.convertToComplex(coordinates);
            var transformedImage = FourierAlgorithms.discreteFourierTransform(imagePoints);
            epicycles = new ArrayList<>(Arrays.asList(Epicycle.generateEpicycles(transformedImage)));
            this.removeAll();
            this.repaint();
            this.revalidate();
        }
    }
}
