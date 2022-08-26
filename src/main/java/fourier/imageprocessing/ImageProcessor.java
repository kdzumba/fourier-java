package fourier.imageprocessing;

import fourier.algorithms.GraphAlgorithms;
import fourier.models.Coordinate;
import fourier.models.ImageGraph;
import fourier.models.Node;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ImageProcessor extends JFrame
{
    private static List<Coordinate> orderedPixelPositions = new ArrayList<>();
    private static BufferedImage processed = null;

    public ImageProcessor()
    {
        var source = loadImageFromURL("https://upload.wikimedia.org/wikipedia/en/5/52/Phineas_Flynn.png");
        processed = createBlackAndWhite(source);

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
        orderedPixelPositions = GraphAlgorithms.nearestNeighbour(g);
        return orderedPixelPositions;
    }

    public static BufferedImage loadImageFromURL(String imageUrl)
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

    public static BufferedImage createBlackAndWhite(BufferedImage source)
    {
        var image = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        Graphics g = image.createGraphics();
        g.drawImage(source, 0, 0, null);
        ColorConvertOp colorConvertOp = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        colorConvertOp.filter(image, image);


//        blackWhite = new BufferedImage(master.getWidth(), master.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
//        Graphics2D g2d = blackWhite.createGraphics();
//        g2d.drawImage(master, 0, 0, this);
//        g2d.dispose();


        return image;
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
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(processed, 0, 0, processed.getWidth(), processed.getHeight(), null);
    }


    public static void main(String[] args) throws IOException {
       new ImageProcessor();
    }
}
