package fourier;

import fourier.algorithms.FourierAlgorithms;
import fourier.common.Constants;
import fourier.imageprocessing.ImageProcessor;
import fourier.models.ComplexNumber;
import fourier.models.Coordinate;
import fourier.models.Epicycle;
import fourier.rendering.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class MainPanel extends JPanel implements ActionListener
{
    private String imageUrl;
    private double time = 0d;
    private List<Epicycle> epicycles = new ArrayList<>();
    private List<Coordinate> fn = new ArrayList<>();

    public MainPanel(String imageUrl)
    {
        this.setImageUrl(imageUrl);
        init();
        Timer timer = new Timer(Constants.TIMER_DELAY, this);
        timer.start();
        setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setVisible(true);
    }

    public void init()
    {
        var image = ImageProcessor.loadImage(this.imageUrl);
        var orderedPixelPositions = ImageProcessor.getOrderedPixelPositions(image);
        var imagePointsFromFile =  ImageProcessor.loadImagePointsFromFile("D:\\dev\\sandbox\\java\\Fourier\\src\\main\\java\\fourier\\einstein.txt");
        List<ComplexNumber> imagePoints = ComplexNumber.convertToComplex(imagePointsFromFile);
        List<ComplexNumber> transformedImage = FourierAlgorithms.discreteFourierTransform(imagePoints);
        this.epicycles = Epicycle.generateEpicycles(transformedImage);
    }

    public void setImageUrl(String url)
    {
        this.imageUrl = url;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        g.setColor(Color.WHITE);
        Renderer.graphics2D = (Graphics2D) g;
        var  transform = new AffineTransform();
        transform.translate(Constants.HORIZONTAL_TRANSLATION, Constants.VERTICAL_TRANSLATION);
        Renderer.graphics2D.transform(transform);

        Renderer.animationTimer = time;
        Epicycle.updateEpicycles(epicycles, time);
        Renderer.drawEpiCycles(epicycles);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        time += (2 * Math.PI) / epicycles.size();
        if(time > 2 * Math.PI) {
            time = 0;
            Renderer.drawing.clear();
        }
        this.repaint();
    }
}
