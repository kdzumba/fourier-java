package fourier.imageprocessing;

import fourier.algorithms.GraphAlgorithms;
import fourier.models.Coordinate;
import fourier.models.ImageGraph;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ImageProcessor extends JFrame
{

    public ImageProcessor()
    {
        setSize(800, 600);
        setTitle("Image Processor");
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public static List<Coordinate> getOrderedPixelPositions(BufferedImage img)
    {
        var result = new ArrayList<Coordinate>();
        var imgHeight = img.getHeight();
        var imgWidth = img.getWidth();

        for(int x = 0; x < imgWidth; x++)
        {
            for(int y = 0; y < imgHeight; y++)
            {
                var c = new Color(img.getRGB(x, y));
                if(c.getGreen() != 255 && c.getRed() != 255 && c.getBlue() != 255)
                    result.add(new Coordinate(x, y));
            }
        }

        ImageGraph g = new ImageGraph(result);
        return GraphAlgorithms.nearestNeighbour(g);
    }

    public static BufferedImage loadImage(String imageUrl)
    {
        URL url;
        BufferedImage img;
        try
        {
            url = new URL(imageUrl);
        }
        catch(MalformedURLException e)
        {
            throw new RuntimeException(e);
        }

        try
        {
            img = ImageIO.read(url);
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
        return img;
    }

    public static List<Coordinate> loadImagePointsFromFile(String filename)
    {
        var imagePoints = new ArrayList<Coordinate>();
        try
        {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                String[] pointString = line.split(" , ");
                var x = Integer.parseInt(pointString[0]);
                var y = Integer.parseInt(pointString[1]);
                imagePoints.add(new Coordinate(x,y));
            }
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
        return imagePoints;
    }

    @Override
    public void paint(Graphics g)
    {
        URL imageURL;
        Graphics2D g2d = (Graphics2D) g;

        try {
            imageURL = new URL("https://www.drawinghowtodraw.com/stepbystepdrawinglessons/wp-content/uploads/2009/10/2howtodrawcat-finished-small.png");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        List<Coordinate> pixelPositions;
        try {
            BufferedImage img = ImageIO.read(imageURL);
            pixelPositions = getOrderedPixelPositions(img);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        pixelPositions.forEach(p -> {
            g2d.drawLine((int) p.getX(), (int) p.getY(), (int) p.getX(), (int) p.getY());
        });
    }


    public static void main(String[] args) throws IOException {
       new ImageProcessor();
    }
}
