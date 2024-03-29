package fourier.rendering;


import fourier.models.Coordinate;
import fourier.models.Epicycle;
import fourier.ui.UICommon;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.*;
import java.util.List;

public class Renderer
{
    public static Graphics2D graphics2D;
    public static double animationTimer = 0d;
    public static List<Coordinate> drawing = new ArrayList<>();
    public static Color penColor = UICommon.PEN_COLOR;

    public static void drawEpiCycles(List<Epicycle> signal)
    {
        for (Epicycle epicycle : signal) {
            drawEpiCycle(epicycle);
        }
        sketch(drawing);
    }

    public static void sketch(List<Coordinate> points)
    {
        for(int n = 0; n < points.size(); n++)
        {
            var current = points.get(n);
            graphics2D.setStroke(new BasicStroke(1f));
            graphics2D.setColor(penColor);
            if(n != 0)
            {
                var prev = points.get(n - 1);
                if(prev.getDistanceTo(current) <= 6)
                    graphics2D.draw(new Line2D.Double(prev.getX(), prev.getY(), current.getX(), current.getY()));
            }
            else
            {
                graphics2D.draw(new Line2D.Double(current.getX(), current.getY(), current.getX(), current.getY()));
            }
        }
    }

    private static void drawVectorLine(Coordinate initial, Coordinate terminal)
    {
        graphics2D.setStroke(new BasicStroke(1f));
        graphics2D.setColor(new Color(250, 250, 0));
        graphics2D.draw(new Line2D.Double(initial.getX(), initial.getY(), terminal.getX(), terminal.getY()));
    }

    private static void drawCircle(double radius, Coordinate center)
    {
        double diameter = radius * 2;

        Shape circle = new Ellipse2D.Double(center.getX() - radius, center.getY()  - radius, diameter, diameter);
        graphics2D.setStroke(new BasicStroke(.5f));
        graphics2D.setColor(new Color(150, 10, 70));
        graphics2D.draw(circle);
    }

    private static void drawEpiCycle(Epicycle epicycle)
    {
        var radius = epicycle.getRadius();
        var center = epicycle.getCenter();
        
        drawCircle(radius, center);
        drawVectorLine(epicycle.getCenter(), epicycle.getTerminal());
    }
}
