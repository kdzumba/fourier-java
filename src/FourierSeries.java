import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class FourierSeries extends JFrame implements ActionListener
{
    private final int LEFT = 50;
    private final int UP = 200;
    private final int DIAMETER = 150;
    private final int WINDOW_WIDTH = 800;
    private final int WINDOW_HEIGHT = 600;
    private final int CURSOR_X_TRANSLATION = 400;
    private final int TIMER_DELAY = 10;
    private final int RADIUS = 75;
    private float angle = 0f;
    private final double HARMONIC_FREQUENCY = 0.1;
    private final List<HarmonicVector> harmonics;
    private final List<Double> waveHeights;
    public FourierSeries(String title)
    {
        this.waveHeights = new ArrayList<>();
        this.harmonics = new ArrayList<>();

        var firstHarmonic = new HarmonicVector(new Pair(LEFT + RADIUS, UP + RADIUS), new Pair(LEFT + RADIUS * 2, UP + RADIUS));
        firstHarmonic.setIsLastHarmonic(true);
        this.harmonics.add(firstHarmonic);

        Timer timer = new Timer(TIMER_DELAY, this);
        timer.start();

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle(title);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void drawCursor(Graphics2D g, Pair initialPoint, Pair terminalPoint)
    {
        g.draw(new Line2D.Double(initialPoint.x, initialPoint.y, terminalPoint.x, terminalPoint.y));
    }

    private void removeOffScreenPoints()
    {
        //Use traditional for loop here to avoid ConcurrentModificatoinException
        for(int index = 0; index < waveHeights.size(); index++)
        {
            if(index + CURSOR_X_TRANSLATION > WINDOW_WIDTH)
            {
                waveHeights.remove(waveHeights.get(index));
            }
        }
    }
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.WHITE);

        harmonics.forEach(harmonic -> {
            harmonic.setTerminalPoint(new Pair(harmonic.getInitialPoint().x + RADIUS + RADIUS * Math.cos(angle),
                    harmonic.getInitialPoint().y + RADIUS + RADIUS * Math.sin(angle)));
            harmonic.paintComponent(g2d);

            if(harmonic.getIsLastHarmonic())
            {
                drawCursor(g2d, harmonic.getTerminalPoint(), new Pair(CURSOR_X_TRANSLATION, harmonic.getTerminalPoint().y));
                this.waveHeights.add(0, harmonic.getTerminalPoint().y);
            }
        });

        this.removeOffScreenPoints();
        waveHeights.forEach(height -> {
            g2d.draw(new Line2D.Double(CURSOR_X_TRANSLATION + waveHeights.indexOf(height), height, CURSOR_X_TRANSLATION + waveHeights.indexOf(height), height));
        });
    }

    public static void main(String[] args) {
        new FourierSeries("Fourier Series");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        angle += HARMONIC_FREQUENCY;
        this.repaint();
    }
}
