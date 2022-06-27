import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class HarmonicVector extends JPanel
{
    private final Pair initialPoint;
    private Pair terminalPoint;
    private final double RADIUS = 75;
    private boolean isLastHarmonic = false;

    public HarmonicVector(Pair initialPoint, Pair terminalPoint)
    {
        this.initialPoint = initialPoint;
        this.terminalPoint = terminalPoint;
    }

    public void setTerminalPoint(Pair point)
    {
        this.terminalPoint = point;
    }

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
    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        Shape circle = new Ellipse2D.Double(initialPoint.x, initialPoint.y, RADIUS * 2, RADIUS * 2);
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.draw(circle);
        g2d.draw(new Line2D.Double(initialPoint.x + RADIUS, initialPoint.y + RADIUS, terminalPoint.x, terminalPoint.y));
    }
}