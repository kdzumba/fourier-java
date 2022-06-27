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
    private float angle = 0f;
    private final double harmonicFrequency = 1d;
    private final List<HarmonicVector> harmonics;
    private final List<Double> waveHeights;
    public FourierSeries(String title)
    {
        this.waveHeights = new ArrayList<>();
        this.harmonics = new ArrayList<>();

        var firstHarmonic = new HarmonicVector(new Pair(LEFT + DIAMETER /2d, UP + DIAMETER / 2d), new Pair(LEFT + DIAMETER, UP + DIAMETER / 2d));
        firstHarmonic.setIsLastHarmonic(true);
        this.harmonics.add(firstHarmonic);

        Timer timer = new Timer(10, this);
        timer.start();

        int WINDOW_WIDTH = 800;
        int WINDOW_HEIGHT = 600;

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
            if(index + 400 > 800)
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
            harmonic.setTerminalPoint(new Pair(harmonic.getInitialPoint().x + DIAMETER / 2d + 75 * Math.cos(angle),
                    harmonic.getInitialPoint().y + DIAMETER / 2d + 75 * Math.sin(angle)));
            harmonic.paintComponent(g2d);

            if(harmonic.getIsLastHarmonic())
            {
                drawCursor(g2d, harmonic.getTerminalPoint(), new Pair(400, harmonic.getTerminalPoint().y));
                this.waveHeights.add(0, harmonic.getTerminalPoint().y);
            }
        });

        this.removeOffScreenPoints();
        waveHeights.forEach(height -> {
            g2d.draw(new Line2D.Double(400 + waveHeights.indexOf(height), height, 400 + waveHeights.indexOf(height), height));
        });
    }

    public static void main(String[] args) {
        new FourierSeries("Fourier Series");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        angle += 0.1;
        this.repaint();
    }
}
