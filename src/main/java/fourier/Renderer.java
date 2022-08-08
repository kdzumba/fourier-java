//package fourier;
//
//import fourier.models.Vector2D;
//import fourier.models.SinusoidVector;
//
//import java.awt.*;
//import java.awt.geom.Ellipse2D;
//import java.awt.geom.Line2D;
//import java.util.List;
//
//public class Renderer
//{
//    static void drawEpiCycle(Graphics2D g2d, SinusoidVector vector)
//    {
//        drawCircle(vector.getAmplitude(), vector.getInitialPoint(), g2d);
//        drawVectorLine(vector.getInitialPoint(), vector.getTerminalPoint(), g2d);
//    }
//
//    public static void drawEpicycles(Graphics2D g2d, List<SinusoidVector> vectors)
//    {
//        for(int n = 0; n < vectors.size(); n++)
//        {
//            var vector = vectors.get(n);
//            if(n == 0)
//            {
//                drawEpiCycle(g2d, vector);
//            }
//            else
//            {
//                drawCircle(vector.getAmplitude(), vectors.get(n -1).getTerminalPoint(), g2d);
//                drawVectorLine(vectors.get(n-1).getTerminalPoint(), vector.getTerminalPoint(), g2d);
//            }
//
//            if(n + 1 == vectors.size())
//            {
//                Renderer.drawCursor(g2d, vector.getTerminalPoint(), new Vector2D(fourier.common.Constants.CURSOR_X_TRANSLATION, vector.getTerminalPoint().y));
//            }
//        }
//    }
//
//    public static void drawCircle(double radius, Vector2D center, Graphics2D g2d)
//    {
//        Shape circle = new Ellipse2D.Double(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
//        g2d.setStroke(new BasicStroke(1.0f));
//        g2d.draw(circle);
//    }
//
//    public static void drawVectorLine(Vector2D initial, Vector2D terminal, Graphics2D g2d)
//    {
//        g2d.draw(new Line2D.Double(initial.x, initial.y, terminal.x, terminal.y));
//    }
//
//    public static void drawCursor(Graphics2D g, Vector2D initialPoint, Vector2D terminalPoint)
//    {
//        g.draw(new Line2D.Double(initialPoint.x, initialPoint.y, terminalPoint.x, terminalPoint.y));
//    }
//}
