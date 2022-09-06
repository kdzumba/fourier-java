package fourier.imageprocessing;

import fourier.algorithms.GraphAlgorithms;
import fourier.models.Coordinate;
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
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class ImageProcessor extends JFrame
{
    private static BufferedImage processed = null;

    public ImageProcessor()
    {
        var source = loadImageFromWebURL("https://upload.wikimedia.org/wikipedia/en/5/52/Phineas_Flynn.png");
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
        var imgHeight = img.getHeight();
        var imgWidth = img.getWidth();
        var imageNodes = new HashSet<Node>();

        for(int x = 0; x < imgWidth; x+=1)
        {
            for(int y = 0; y < imgHeight; y+=1)
            {
                var c = new Color(img.getRGB(x, y));
                if(c.getGreen() != 255 && c.getRed() != 255 && c.getBlue() != 255)
                {
                    var node = new Node();
                    node.setVisited(false);
                    node.setCoordinate(new Coordinate(x, y));

                    if(x - 1 >= 0)
                    {
                        var leftNeighbour = new Node();
                        leftNeighbour.setVisited(false);
                        leftNeighbour.setCoordinate(new Coordinate(x - 1, y));
                        node.getNeighbours().add(leftNeighbour);
                        imageNodes.add(leftNeighbour);
                    }

                    if(x + 1 < imgWidth)
                    {
                        var rightNeigbour = new Node();
                        rightNeigbour.setVisited(false);
                        rightNeigbour.setCoordinate(new Coordinate(x + 1, y));
                        node.getNeighbours().add(rightNeigbour);
                        imageNodes.add(rightNeigbour);
                    }

                    if(y - 1 >= 0)
                    {
                        var topNeighbour = new Node();
                        topNeighbour.setVisited(false);
                        topNeighbour.setCoordinate(new Coordinate(x, y - 1));
                        node.getNeighbours().add(topNeighbour);
                        imageNodes.add(topNeighbour);
                    }

                    if(y + 1 < imgHeight)
                    {
                        var bottomNeighbour = new Node();
                        bottomNeighbour.setVisited(false);
                        bottomNeighbour.setCoordinate(new Coordinate(x, y - 1));
                        node.getNeighbours().add(bottomNeighbour);
                        imageNodes.add(bottomNeighbour);
                    }
                    if(x == 0 && y == 0)
                        node.setPriority(Integer.MAX_VALUE);
                    imageNodes.add(node);
                }
            }
        }
        var nodesList = new ArrayList<>(imageNodes);
        return GraphAlgorithms.nearestNeighbour(nodesList);
    }

    public static BufferedImage loadImageFromFile(String filePath)
    {
        try {
            File img = new File(filePath);
            return ImageIO.read(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BufferedImage loadImageFromWebURL(String imageUrl)
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
        return image;
    }

    public static List<Coordinate> loadImagePointsFromFile(URL url)
    {
        var imagePoints = new ArrayList<Coordinate>();
        try
        {
            File file = new File(url.toURI());
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
        catch(IOException | URISyntaxException e)
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
