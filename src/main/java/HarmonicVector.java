import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class HarmonicVector extends JPanel
{
    private Pair initialPoint;
    private Pair terminalPoint;
    private double radius;
    private double frequency;
    private boolean isLastHarmonic = false;

    public HarmonicVector(Pair initialPoint, Pair terminalPoint, double radius, double frequency)
    {
        this.initialPoint = initialPoint;
        this.terminalPoint = terminalPoint;
        this.radius = radius;
        this.frequency = frequency;
    }

    public void setTerminalPoint(Pair point)
    {
        this.terminalPoint = point;
    }
    public void setInitialPoint(Pair point) { this.initialPoint = point;}

    public void setIsLastHarmonic(boolean isLast)
    {
        this.isLastHarmonic = isLast;
    }

    public boolean getIsLastHarmonic()
    {
        return this.isLastHarmonic;
    }

    public Pair getTerminalPoint()
    {
        return this.terminalPoint;
    }

    public Pair getInitialPoint()
    {
        return this.initialPoint;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public double getRadius() {
        return radius;
    }

    public double getFrequency() {
        return frequency;
    }
    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        Shape circle = new Ellipse2D.Double(initialPoint.x, initialPoint.y, radius * 2, radius * 2);
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.draw(circle);
        g2d.draw(new Line2D.Double(initialPoint.x + radius, initialPoint.y + radius, terminalPoint.x, terminalPoint.y));
    }
}